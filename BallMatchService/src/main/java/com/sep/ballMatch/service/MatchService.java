package com.sep.ballMatch.service;

import com.sep.ballMatch.entity.GameProcess;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.sep.ballMatch.action.MatchResource;
import com.sep.ballMatch.common.HttpUtil;
import com.sep.ballMatch.common.LogUtils;
import com.sep.ballMatch.common.PropUtil;
import com.sep.ballMatch.entity.GameCache;
import com.sep.ballMatch.entity.GameStatus;
import com.sep.ballMatch.entity.Result;

public class MatchService {
	
	private final static Logger logger = LogManager.getLogger(MatchResource.class);
	
	public Result doMatch(GameProcess current) {
		Result result = new Result();
		result.setStatus("ok");
		GameProcess last = GameCache.getGameProcess(current);
		if(current.equals(last)) {
			return result;
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
						GameCache.playerNum = 0;
					}else {
						GameCache.playerNum = 1;
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
}
