/**  
* @Title: CloudantDbUtil.java
* @Package com.ibm.ngps.entity.picklist.util
* @Description: TODO
* @author Tony 
* @date Jun 13, 2016 9:51:11 PM
* @version V1.0  
*/
package com.sep.ballMatch.dao;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.sep.ballMatch.common.PropUtil;

/**
 * 
 * @author TongQianZhang
 *
 */
public class CloudantDao {
	private final static Logger logger = LogManager.getLogger(CloudantDao.class);
	
	protected static Map<String, Database> database_pool = new HashMap<String, Database>();
	
	protected static CloudantClient client = null;
	protected static CloudantClient auditLogDBclient = null;
	protected static String account;
	protected static String username;
	protected static String password;
	protected static String connections;

	public static CloudantClient cloudantClientConnection() throws Exception{
		
		try {
			account = PropUtil.getProperty("cloudant.account");
			username = PropUtil.getProperty("cloudant.username");
			password = PropUtil.getProperty("cloudant.password");
			connections = PropUtil.getProperty("cloudant.connections");
			
			client = ClientBuilder.url(new URL(account)).username(username).password(password).maxConnections(Integer.parseInt(connections))
			.customSSLSocketFactory(SSLContext.getInstance("default").getSocketFactory()).build();
			
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);

		} catch (IOException e) {
			logger.error(e);
		}
		return client;
	}

	public static CloudantClient getClientConnectorInstance() {
		try {
			if (client == null) {
				client = cloudantClientConnection();
			}

		} catch (Exception ce) {
			logger.error(ce);
		}
		return client;
	}
	
	public static Database getDatabase(String database) {
		try {
			Database db;
	
			CloudantClient client = CloudantDao.getClientConnectorInstance();
	
			String _database = PropUtil.getProperty(database);
	
			if (database_pool.get(database) == null) {
	
				db = client.database(_database, false);
	
				database_pool.put(database, db);
	
			}
		}catch(Exception ce){
			logger.error(ce);
		}
		return database_pool.get(database);
	}
	
	public static void main(String[] args) {
		getDatabase("match_info");
	}

}
