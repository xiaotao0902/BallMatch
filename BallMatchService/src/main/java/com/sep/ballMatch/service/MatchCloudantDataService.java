package com.sep.ballMatch.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sep.ballMatch.common.InternalServerException;
import com.sep.ballMatch.common.LogUtils;
import com.sep.ballMatch.dao.MatchDaoCloudant;
import com.sep.ballMatch.entity.GameStore;

public class MatchCloudantDataService implements Runnable{
	
	private final static Logger logger = LogManager.getLogger(MatchCloudantDataService.class);
	
	MatchDaoCloudant matchDaoCloudant = new MatchDaoCloudant();
	
	GameStore gameStore;
	
	public MatchCloudantDataService (GameStore gameStore) {
		this.gameStore = gameStore;
	}

	@Override
	public void run() {
		try {
			matchDaoCloudant.addMatch(gameStore);
		} catch (InternalServerException e) {
			logger.error(LogUtils.getExceptionToString(e));
			e.printStackTrace();
		}
	}
}
