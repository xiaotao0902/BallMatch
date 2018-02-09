package com.sep.ballMatch.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sep.ballMatch.common.LogUtils;
import com.sep.ballMatch.common.Utils;
import com.sep.ballMatch.dao.MatchDaoCloudant;
import com.sep.ballMatch.dao.MySqlDao;
import com.sep.ballMatch.entity.GameCache;
import com.sep.ballMatch.entity.GameRank;
import com.sep.ballMatch.entity.GameStore;

public class MatchRankService implements Runnable{
	
	private final static Logger logger = LogManager.getLogger(MatchRankService.class);
	
	private String matchResult;
	
	private MySqlDao mySqlDao = new MySqlDao();
	
	private MatchDaoCloudant matchDaoCloudant = new MatchDaoCloudant();
	
	private MatchRankUtil rankUtil = new MatchRankUtil();
	
	public MatchRankService (String matchResult) {
		this.matchResult = matchResult;
	}

	@Override
	public void run() {
		try {
			
			String match_id = GameCache.matchId;
			
			GameRank rank_A = new GameRank();
			
			GameRank rank_B = new GameRank();
			
			rank_A.setMatchId(match_id);
			rank_B.setMatchId(match_id);
			
			List<GameStore> list = matchDaoCloudant.getMathInfoById(match_id);
			
			Map<String,List<GameStore>> map = rankUtil.separateRank(list);
			
			List<GameStore> list_A = map.get("A"); 
			
			List<GameStore> list_B = map.get("B");
			
			rank_A.setUser_id(GameCache.user_id);
			rank_A.setVs_user_id(GameCache.vs_user_id);
			rank_B.setUser_id(GameCache.vs_user_id);
			rank_B.setVs_user_id(GameCache.user_id);
			
			if(list != null) {
				rankUtil.kickoff(rank_A, rank_B, list);
				rankUtil.timeAndCount(rank_A, rank_B, list);
				rankUtil.kickAndGoal(rank_A, rank_B, list);
				rankUtil.finallGoal(rank_A, rank_B, list_A, list_B, matchResult);
				rankUtil.GoalCount(rank_A, rank_B, list_A, list_B);
			}
			
			String sql_A = "update ballmatch.match_level_t set result=?,kickoff=?,avg_time=?, "
					+ " kick_count=?,goal_count=?,match_time=?,avg_flow=?,single_stick=?,"
					+ " final_goal=? where match_id=?";
			
			int[] types_A = new int[]{ Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
									   Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
									   Types.VARCHAR,Types.VARCHAR} ;
			
			Object[] values_A = new Object[]{matchResult,rank_A.getKickOff(),rank_A.getAvgTime(),
											 rank_A.getKickCount(),rank_A.getGoalCount(),rank_A.getMatchTime(),
											 rank_A.getAvgFlow(),rank_A.getSingleStick(),rank_A.getFinalGoal(),
											 match_id};
			
			mySqlDao.executeUpdate(sql_A, types_A, values_A);
			
			String sql_B = "insert into ballmatch.match_level_t (id,match_id,result,kickoff,avg_time,"
					+ " kick_count,goal_count,match_time,avg_flow,single_stick,final_goal,user_id,vs_user_id)"
					+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			int[] types_B = new int[]{ Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
									 Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
									 Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR} ;
			
			String id = Utils.getUuid();
			
			Object[] values_B = new Object[]{id,match_id,matchResult,rank_B.getKickOff(),rank_B.getAvgTime(),
											 rank_B.getKickCount(),rank_B.getGoalCount(),rank_B.getMatchTime(),
											 rank_B.getAvgFlow(),rank_B.getSingleStick(),rank_B.getFinalGoal(),
											 GameCache.vs_user_id,GameCache.user_id };
			
			mySqlDao.executeUpdate(sql_B, types_B, values_B);
			
			logger.info("update the result in mysql db " + sql_A + " " + values_A);
			logger.info("insert the result in mysql db " + sql_B + " " + values_B);
			
		} catch (ClassNotFoundException e) {
			logger.error(LogUtils.getExceptionToString(e));
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			logger.error(LogUtils.getExceptionToString(e));
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error(LogUtils.getExceptionToString(e));
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(LogUtils.getExceptionToString(e));
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		GameCache.matchId = "318eb364-5686-4b07-91b2-8cf12e779652";
		GameCache.user_id = "1001";
		GameCache.vs_user_id = "1002";
		Thread startCloudant = new Thread(new MatchRankService("A"));
		startCloudant.start();
	}
}
