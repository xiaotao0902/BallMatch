package com.sep.ballMatch.service;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.sep.ballMatch.action.MatchSocket;
import com.sep.ballMatch.common.LogUtils;
import com.sep.ballMatch.common.Utils;
import com.sep.ballMatch.dao.MySqlDao;
import com.sep.ballMatch.entity.GameCache;
import com.sep.ballMatch.entity.GameProcess;
import com.sep.ballMatch.entity.GameRank;
import com.sep.ballMatch.entity.GameStatus;
import com.sep.ballMatch.entity.GameStore;
import com.sep.ballMatch.entity.Result;

public class MatchService {
	
	private final static Logger logger = LogManager.getLogger(MatchService.class);
	
	private MatchSocket matchSocket = new MatchSocket();
	
	private MatchHandleCVDataService handleCV = new MatchHandleCVDataService();
	
	private MySqlDao mySqlDao = new MySqlDao();
	
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
				
				Thread startSql = new Thread(new MatchMySqlDataService(gameStore));
				startSql.start();
				
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
	
	public GameRank getRankInfo(String id) {
		
		GameRank gameRank = new GameRank();
		
		try {
			String sql_kickoff = " select CAST(sum(kickoff)/count(1)as decimal(38, 2)) kickoff from ( "
					+ "select kickoff, user_id from ballmatch.match_level_t where kickoff <> '' and user_id=? ) t "
					+ "group by t.user_id ";
			
			int[] types_kickoff = new int[]{ Types.VARCHAR} ;
			
			Object[] values_kickoff = new Object[]{id};
		
			Map<String, Object> kickoffResult = mySqlDao.executeQueryForMap(sql_kickoff, types_kickoff, values_kickoff);
			
			if(kickoffResult != null) {
				String kickOff = kickoffResult.get("kickoff").toString();
				gameRank.setKickOff(kickOff);
			}
			
			String sql_rank = " select CAST(avg(avg_time) as decimal) avg_time,  "
					+ "		 		   CAST(avg(kick_count) as decimal(38, 1))  kick_count, "
					+ "		 		   CAST(avg(goal_count) as decimal(38, 1)) goal_count,"
					+ "      		   CAST(avg(match_time) as decimal) match_time, "
					+ "      		   CAST(avg(avg_flow) as decimal(38, 2) ) avg_flow,"
					+ "		 	       CAST(avg(single_stick) as decimal(38, 2) ) single_stick,"
					+ "                CAST(avg(final_goal) as decimal(38, 2) ) final_goal, "
					+ "                CAST(sum(result)/count(1)as decimal(38, 2) )winRate,"
					+ "				   count(1) match_count "
					+ "         from ("
					+ "              select avg_time, kick_count,goal_count, match_time,avg_flow,single_stick,final_goal,result,user_id "
					+ "              from ballmatch.match_level_t where user_id=? ) t"
					+ "         group by t.user_id";
			
			int[] types_rank = new int[]{ Types.VARCHAR} ;
			
			Object[] values_rank = new Object[]{id};
		
			Map<String, Object> rankResult = mySqlDao.executeQueryForMap(sql_rank, types_rank, values_rank);
			
			if(rankResult != null) {
				gameRank.setAvgTime(rankResult.get("avg_time").toString());
				gameRank.setKickCount(rankResult.get("kick_count").toString());
				gameRank.setGoalCount(rankResult.get("goal_count").toString());
				gameRank.setMatchTime(rankResult.get("match_time").toString());
				gameRank.setAvgFlow(rankResult.get("avg_flow").toString());
				gameRank.setSingleStick(rankResult.get("single_stick").toString());
				gameRank.setFinalGoal(rankResult.get("final_goal").toString());
				gameRank.setWinRate(rankResult.get("winRate").toString());
				gameRank.setMatchCount(rankResult.get("match_count").toString());
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			logger.error(LogUtils.getExceptionToString(e));
			e.printStackTrace();
		}
		
		return gameRank;
	}
}
