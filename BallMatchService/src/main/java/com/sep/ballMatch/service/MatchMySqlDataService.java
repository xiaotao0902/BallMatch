package com.sep.ballMatch.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sep.ballMatch.common.LogUtils;
import com.sep.ballMatch.common.Utils;
import com.sep.ballMatch.dao.MatchDaoCloudant;
import com.sep.ballMatch.dao.MySqlDao;
import com.sep.ballMatch.entity.GameStore;

public class MatchMySqlDataService implements Runnable{
	
	private final static Logger logger = LogManager.getLogger(MatchMySqlDataService.class);
	
	MySqlDao mySqlDao = new MySqlDao();
	
	MatchDaoCloudant matchDaoCloudant = new MatchDaoCloudant();
	
	GameStore gameStore;
	
	public MatchMySqlDataService (GameStore gameStore) {
		this.gameStore = gameStore;
	}

	@Override
	public void run() {
		try {
			String id = Utils.getUuid();
			String match_id = gameStore.getMatchId();
			String user_id = gameStore.getUser_id();
			String vs_user_id = gameStore.getVs_user_id();
			
			String sql = "select count(1) from ballmatch.match_level_t where match_id=? ";
			int[] types = new int[]{ Types.VARCHAR} ;
			Object[] values = new Object[]{match_id};
			int count = mySqlDao.executeQueryForInt(sql, types, values);
			
			if(count == 0) {
				String iSql = "insert into ballmatch.match_level_t(id,match_id,user_id,vs_user_id) values(?,?,?,?)";
				int[] iTypes = new int[]{ Types.VARCHAR,Types.VARCHAR ,Types.VARCHAR,Types.VARCHAR } ;
				Object[] iValues = new Object[]{id,match_id,user_id,vs_user_id};
				
				mySqlDao.executeUpdate(iSql, iTypes, iValues);
				logger.info("store in mysql db " + sql + " " + iValues);
			}
			
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
	
	
}
