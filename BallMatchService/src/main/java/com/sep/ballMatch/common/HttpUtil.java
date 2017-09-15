package com.sep.ballMatch.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @ClassName: HttpUtil
 * @author Sherry
 * @date Sep 22, 2016 9:22:19 PM
 *
 */
public class HttpUtil {
	
	//private static LogUtils logger = LogUtils.getLogger(HttpUtil.class);
	private final static Logger logger = LogManager.getLogger(HttpUtil.class);
	/**
	 * Send POST request to IDaaS
	 * @param url
	 * @param param
	 * @param contentType
	 * @return
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	public static Map<String, Object> sendPostRequest(String url, String param, String contentType, Map<String, String> headerMap) throws Exception {
		
		logger.info("*********** Send POST request start ******************");
		Map<String, Object> resultMap = new HashMap<String, Object>();
//		try {
//			TrustCertificate.doTrustToCertificates();
//		} catch(Exception ex) {
//			ex.printStackTrace();
//			logger.error(ex);
//		}
		
		URL u = null;
		HttpURLConnection connection = null;
		
		// send request
		try {
			u = new URL(url);
			connection = (HttpURLConnection) u.openConnection();
			
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			
			if("json".equals(contentType)) {
				connection.setRequestProperty("Content-Type","application/json");
			} else {
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			}
			
			if (headerMap != null && !headerMap.isEmpty()) {
				for (Map.Entry<String, String> entry : headerMap.entrySet()) {
					String headerKey = entry.getKey();
					String headerValue = entry.getValue();
					connection.setRequestProperty(headerKey, headerValue);
				}
			}
			
			OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			osw.write(param);
			osw.flush();
			osw.close();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		
		// read return result
		StringBuffer buffer = new StringBuffer();
		try {
			
			int responseCode = connection.getResponseCode();
			resultMap.put("responseCode", responseCode);
			
			if(responseCode == 200 || responseCode == 201) {
				
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String temp;
				while ((temp = br.readLine()) != null) {
					buffer.append(temp);
				}
				resultMap.put("result", buffer.toString());
				String cookie = connection.getHeaderField("Set-Cookie");
				resultMap.put("cookie", cookie);
				
			} else {
				
				resultMap.put("responseErrorMessage", connection.getResponseMessage());
				// get error message when IDaaS registration 
				if(connection.getErrorStream() != null) {
					BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "UTF-8"));
					String temp;
					while ((temp = br.readLine()) != null) {
						buffer.append(temp);
					}
					resultMap.put("result", buffer.toString());
				}
				
			}
			
		} catch (Exception e) {
			logger.error(e);
		}
		
		logger.info("*********** Send POST request end ******************");

		return resultMap;

	}
	
	/**
	 * Send GET request to IDaaS
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public static Map<String, Object> sendGetRequest(String url, Map<String, String> params, Map<String, String> headerMap) throws Exception {
		
		logger.info("*********** Send GET request start ******************");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		URL u = null;
		HttpURLConnection connection = null;
		
		try {
			TrustCertificate.doTrustToCertificates();
		} catch(Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		
		// create request parameters
		StringBuffer sb = new StringBuffer();
		if (params != null) {
			for (Entry<String, String> e : params.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		
		// send request
		try {
			u = new URL(url + "?" + sb.toString());
			connection = (HttpURLConnection) u.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			// set header
			if (headerMap != null && !headerMap.isEmpty()) {
				for (Map.Entry<String, String> entry : headerMap.entrySet()) {
					String headerKey = entry.getKey();
					String headerValue = entry.getValue();
					connection.setRequestProperty(headerKey, headerValue);
				}
			}
			connection.connect();
			
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

		// read return result
		StringBuffer buffer = new StringBuffer();
		try {
			
			int responseCode = connection.getResponseCode();
			resultMap.put("responseCode", responseCode);
			
			if(responseCode == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String temp;
				while ((temp = br.readLine()) != null) {
					buffer.append(temp);
				}
				resultMap.put("result", buffer.toString());
			} else {
				resultMap.put("responseErrorMessage", connection.getResponseMessage());
			}
			
		} catch (Exception e) {
			logger.error(e);
		}
		
		logger.info("*********** Send GET request end ******************");

		return resultMap;
	}
	
	/**
	 * Send PUT request to IDaaS
	 * @param url
	 * @param param
	 * @param contentType
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	public static Map<String, Object> sendPutRequest(String url, String param, String contentType) throws Exception {
		
		logger.info("*********** Send PUT request start ******************");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		URL u = null;
		HttpURLConnection connection = null;
		
		// send request
		try {
			u = new URL(url);
			connection = (HttpURLConnection) u.openConnection();
			connection.setRequestMethod("PUT");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			if("json".equals(contentType)) {
				connection.setRequestProperty("Content-Type","application/json");
			} else {
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			}
			OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			osw.write(param);
			osw.flush();
			osw.close();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

		// read return result
		StringBuffer buffer = new StringBuffer();
		try {
			
			int responseCode = connection.getResponseCode();
			resultMap.put("responseCode", responseCode);
			
			if(responseCode == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String temp;
				while ((temp = br.readLine()) != null) {
					buffer.append(temp);
				}
				resultMap.put("result", buffer.toString());
			} else {
				resultMap.put("responseErrorMessage", connection.getResponseMessage());
			}
		} catch (Exception e) {
			logger.error(e);
		}

		logger.info("*********** Send PUT request end ******************");
		return resultMap;
	}
	
}