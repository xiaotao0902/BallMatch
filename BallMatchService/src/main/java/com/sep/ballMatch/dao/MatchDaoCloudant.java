package com.sep.ballMatch.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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
	
	public List<GameStore> getMathInfoById(String matchID){
		
		List<GameStore> list = null;

		String selector = "{\"selector\":{\"matchId\"" + ":" + "\"" + matchID + "\"" + "} }";

		try {
			logger.info("[ selector ] : " + selector);
			list = db.findByIndex(selector, GameStore.class);
			
			Collections.sort(list, new Comparator<GameStore>(){
				 
	            @Override
	            public int compare(GameStore o1, GameStore o2) {
	            	SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            	Date date1 = null;
	            	Date date2 = null;
					try {
						date1 = sd.parse(o1.getTimestamp());
						date2 = sd.parse(o2.getTimestamp());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	long s1 = date1.getTime();
	            	long s2 = date2.getTime();
	                if(s1 > s2){
	                    return 1;
	                }
	                if(s1 == s2){
	                    return 0;
	                }
	                return -1;
	            }          
	        });

		} catch (CouchDbException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}

		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}
	
}
