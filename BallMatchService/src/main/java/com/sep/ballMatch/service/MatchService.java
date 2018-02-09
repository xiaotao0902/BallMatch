package com.sep.ballMatch.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.sep.ballMatch.action.MatchSocket;
import com.sep.ballMatch.common.Utils;
import com.sep.ballMatch.entity.GameCache;
import com.sep.ballMatch.entity.GameProcess;
import com.sep.ballMatch.entity.GameStatus;
import com.sep.ballMatch.entity.GameStore;
import com.sep.ballMatch.entity.Result;

public class MatchService {
	
	private final static Logger logger = LogManager.getLogger(MatchService.class);
	
	private MatchSocket matchSocket = new MatchSocket();
	
	private MatchHandleCVDataService handleCV = new MatchHandleCVDataService();
	
	public Result doMatch(GameProcess current) {
		
		Result result = new Result();
		result.setStatus("ok");
		
		if(!GameCache.triangle) {//1 : if not triangle
			logger.info("it's not triangle " + Utils.getTimeStampToTD());
			if(handleCV.ifTriangle(current)) {
				logger.info("it's triangle , waiting for kickoff" + Utils.getTimeStampToTD());
				GameCache.triangle = true;
				}
			return result;
			}
		
		if(!GameCache.kick_off) {//2 : if triangle is true, if kick off 
			logger.info("after it's triangle , start to verify kick off" + Utils.getTimeStampToTD());
			if(!handleCV.ifTriangle(current)) {
				logger.info("it's triangle ,it's kickoff" + Utils.getTimeStampToTD());
				GameCache.kick_off = true;
				GameCache.matchId = Utils.getUuid();
				GameStore gameStore = new GameStore(); 
				gameStore.setMatchId(GameCache.matchId);
				gameStore.setUser_id(GameCache.user_id);
				gameStore.setVs_user_id(GameCache.vs_user_id);
				
//				Thread startSql = new Thread(new MatchMySqlDataService(gameStore));
//				startSql.start();
				
				GameCache.first_kick = true;
				handleCV(current);
				}
			return result;
			}
		
		if(GameCache.triangle && GameCache.kick_off) {//3 : triangle and kick off are both true, it is playing
			handleCV(current);
		}
		return result;
	} 
	
	public void handleCV(GameProcess current) {
		Gson gson = new Gson();
		GameProcess last = GameCache.getGameProcess(current);
		if(!handleCV.filterPerson(last,current)) {// if there is a person into the cv ignore the data
			GameStatus gameStatus = handleCV.handleCVdata(last,current);
			
			if(gameStatus != null) {
				String gameStatusJson = gson.toJson(gameStatus);
				logger.info(gson.toJson(gameStatusJson));
				matchSocket.onMessage(gameStatusJson);
				//store the data to nosql database
				GameStore gameStore = new GameStore(); 
				gameStore.setMatchId(GameCache.matchId);
				gameStore.setPlayer(gameStatus.getPlayer());
				gameStore.setData(current.getData());
				gameStore.setTimestamp(Utils.getTimeStampToTD());
				
				Thread startCloudant = new Thread(new MatchCloudantDataService(gameStore));
				startCloudant.start();
			}
		}
	}
	
}
