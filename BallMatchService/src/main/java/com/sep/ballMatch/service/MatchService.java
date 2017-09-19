package com.sep.ballMatch.service;

import com.sep.ballMatch.entity.GameProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.sep.ballMatch.action.MatchResource;
import com.sep.ballMatch.action.MatchSocket;
import com.sep.ballMatch.common.HttpUtil;
import com.sep.ballMatch.common.LogUtils;
import com.sep.ballMatch.common.PropUtil;
import com.sep.ballMatch.entity.GameCache;
import com.sep.ballMatch.entity.GameStatus;
import com.sep.ballMatch.entity.Result;
import com.sep.ballMatch.entity.Status;

public class MatchService {
	
	private final static Logger logger = LogManager.getLogger(MatchResource.class);
	
	private MatchSocket matchSocket = new MatchSocket();
	
	public Result doMatch(GameProcess current) {
		Gson gson = new Gson();
		Result result = new Result();
		result.setStatus("ok");
		// if all the balls are not change , don't do anything
		GameProcess last = GameCache.getGameProcess(current);
		
		GameStatus gameStatus = doDetailMatch(last,current);
		if(gameStatus != null) {
			String gameStatusJson = gson.toJson(gameStatus);
			matchSocket.onMessage(gameStatusJson);
		}
		return result;
	} 
	
	public Result startMatch(GameStatus gameStatus) {
		String cv_start_url = PropUtil.getProperty("cv_start_url");
		Gson gson = new Gson();
		Result result = new Result();
		result.setStatus("error");
		try {
			Map<String, Object> resultMap = HttpUtil.sendPostRequest(cv_start_url, "", "", null);
			
			Integer responseCode = (Integer) resultMap.get("responseCode");

			if (responseCode == 200||responseCode == 201) {
				String resultJson = (String) resultMap.get("result");
				result = gson.fromJson(resultJson, Result.class);
				if("ok".equalsIgnoreCase(result.getStatus())) {
					if("A".equals(gameStatus.getPlayer())){
						GameCache.currentPlayer = "A";
					}else {
						GameCache.currentPlayer = "B";
					}
					return result;
				}
				
			} else {
				return result;
			}
		} catch (Exception e) {
			logger.error(LogUtils.getExceptionToString(e));
			e.printStackTrace();
		}
		return result;
	}
	
	public GameStatus doDetailMatch(GameProcess last,GameProcess current) {
		GameStatus gameStatus = new GameStatus();
		if(last.equals(current)) {
			return null;
		}else {
			List<Status> lastData = last.getData();
			List<Status> currentData = current.getData();
			
			if(currentData.get(15).getStatus()==0) {
				GameCache.doSwith();
				gameStatus.setPlayer(GameCache.currentPlayer);
			}else {
				int size = lastData.size();
				for(int i = 0 ; i < size ; i++) {
					if(!lastData.get(i).equals(currentData.get(i))) {
						if(lastData.get(i).getStatus() == currentData.get(i).getStatus()) {
							GameCache.doSwith();
							gameStatus.setPlayer(GameCache.currentPlayer);
							break;
						}
					}
				}
			}
			List<Integer> balls = new ArrayList<Integer>();
			for(Status status : currentData) {
				balls.add(status.getStatus());
			}
		}
		return gameStatus;
	}
}
