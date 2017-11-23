package com.sep.ballMatch.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.sep.ballMatch.action.MatchSocket;
import com.sep.ballMatch.entity.GameCache;
import com.sep.ballMatch.entity.GameProcess;
import com.sep.ballMatch.entity.GameStatus;
import com.sep.ballMatch.entity.Result;
import com.sep.ballMatch.entity.Status;

public class MatchService {
	
	private final static Logger logger = LogManager.getLogger(MatchService.class);
	
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
	
	public GameStatus doDetailMatch(GameProcess last,GameProcess current) {
		GameStatus gameStatus = new GameStatus();
		if(last != null) {// not first time 
			logger.info("not first time");
			List<Status> lastData = last.getData();
			List<Status> currentData = current.getData();
		    if(last.equals(current)) {//the ball didn't move
		    	logger.info("the ball didn't move");
				if(currentData.get(0).getStatus()==0) {// if white ball in the hole change player
					logger.info("if white ball in the hole change player");
					GameCache.doSwith();
					gameStatus.setPlayer(GameCache.currentPlayer);
					List<Integer> balls = new ArrayList<Integer>();
					for(Status status : currentData) {
						balls.add(status.getStatus());
					}
					gameStatus.setBalls(balls);
				}else {
					return null;
				}
			}else {// the ball move
				logger.info("the ball move");
				boolean changePlayer = true;
				gameStatus.setPlayer(GameCache.currentPlayer);
				if(currentData.get(0).getStatus()==0) {// if white ball in the hole change player
					logger.info("if white ball in the hole change player");
					changePlayer = true;
				}else {
					int size = lastData.size();
					for(int i = 0 ; i < size ; i++) {
						if(!lastData.get(i).equals(currentData.get(i))) {// if any ball in the hole not change player
							logger.info("if any ball in the hole not change player");
							if(lastData.get(i).getStatus() != currentData.get(i).getStatus()) {
								changePlayer = false;
								break;
							}
						}
					}
				}
				
				if(changePlayer) {
					GameCache.doSwith();
				}
				gameStatus.setPlayer(GameCache.currentPlayer);
				
				List<Integer> balls = new ArrayList<Integer>();
				for(Status status : currentData) {
					balls.add(status.getStatus());
				}
				gameStatus.setBalls(balls);
			}
		}else {// first time to do
			logger.info("first time to do");
			boolean changePlayer = true;
			List<Status> currentData = current.getData();
			
			List<Integer> balls = new ArrayList<Integer>();
			for(Status status : currentData) {
				if(status.getStatus()==0) {// if some ball in the hole, not change player
					logger.info("if some ball in the hole, not change player");
					changePlayer = false;
				}
				balls.add(status.getStatus());
			}
			gameStatus.setBalls(balls);
			
			if(currentData.get(0).getStatus()==0) {// if white ball in the hole change player
				logger.info(" if white ball in the hole change player");
				changePlayer = true;
			}
			
			if(changePlayer) {
				GameCache.doSwith();
			}
			gameStatus.setPlayer(GameCache.currentPlayer);
		}
		Gson gson = new Gson();
		logger.info(gson.toJson(gameStatus));
		return gameStatus;
	}
}
