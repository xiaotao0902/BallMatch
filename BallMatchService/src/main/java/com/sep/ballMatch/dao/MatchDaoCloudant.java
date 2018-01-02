package com.sep.ballMatch.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cloudant.client.api.Database;
import com.cloudant.client.org.lightcouch.CouchDbException;
import com.sep.ballMatch.common.InternalServerException;
import com.sep.ballMatch.common.LogUtils;
import com.sep.ballMatch.entity.GameStore;

public class MatchDaoCloudant {
	
	private final static Logger logger = LogManager.getLogger(MatchDaoCloudant.class);
	
	private Database db;
	
	private final static String database = "match_info";
	
	public MatchDaoCloudant() {
		db = CloudantDao.getDatabase(database);
	}
	
	public void addMatch(GameStore gameStore) throws InternalServerException {
		
		try {
			db.save(gameStore);
		} catch (CouchDbException e) {
			logger.error(LogUtils.getExceptionToString(e));
			throw new InternalServerException(500102);
		} catch (Exception e) {
			logger.error(LogUtils.getExceptionToString(e));
			throw new InternalServerException(500102);
		}
		
	}

}
