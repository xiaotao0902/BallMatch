package com.sep.ballMatch.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sep.ballMatch.common.HttpUtil;
import com.sep.ballMatch.common.LogUtils;
import com.sep.ballMatch.common.PropUtil;

public class MatchStartService implements Runnable {
	private final static Logger logger = LogManager.getLogger(MatchStartService.class);
	@Override
	public void run() {
		String cv_start_url = PropUtil.getProperty("cv_start_url");
		logger.info("CV server URL : " + cv_start_url);
		try {
			HttpUtil.sendPostRequest(cv_start_url, "", "", null);
		} catch (Exception e) {
			logger.error(LogUtils.getExceptionToString(e));
			e.printStackTrace();
		}

	}

}
