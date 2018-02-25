package com.sep.ballMatch.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
 
	/**  
	 * get rand number by date
	 * @return
	 */
	public static String getRand() {
		String return_str = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		int randi=0;
		while(randi<1000){
			randi=getRandId();
		}
		return_str = df.format(new Date()) + randi;
		return return_str;
	}
	/**
	 * get rand ID from 0-9999
	 * @return
	 */
	public static int getRandId() {
		return new Random().nextInt(9999);
	}
	
	public static String getUuid(){
		String uuidStr = "";
		UUID uuid = UUID.randomUUID();
		uuidStr = uuid.toString();
		return uuidStr;
	}
	
	/**
	 * get rand number from 0-max
	 * @param max
	 * @return
	 */
	public static int getRandId(int max) {
		return new Random().nextInt(max);
	}
	/**
	 * get rand name that like 'RandNamexxxx'
	 * @return
	 */
	public static String getRandName(String randNamePrefix) {
		StringBuffer rand = new StringBuffer(randNamePrefix);
		rand.append(new Random().nextInt(9999));
		return rand.toString();
	}

	/**
	 * check the String is the correct mail format
	 * @param mail
	 * @return
	 */
	public static boolean checkMail(String mail){
		return mail.matches("^([a-z0-9A-Z]+[-|\\._]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
	}
	
	/**
	 * Trim string
	 * 
	 * @param String
	 *            trim string
	 * 
	 * @return String after trim if str = null ,return ""
	 */
	public static String trim(String str) {
		String return_str = null;
		if ((str == null) || (str.equals(""))) {
			return_str = "";
		} else {
			return_str = str.toString().trim();
		}
		return return_str;
	}
	
	
	public static Map<String, String> propsToMap(Properties props,
			String idPatternString, String valuePatternString) {

		Map<String, String> map = new HashMap<String, String>();

		Pattern idPattern = Pattern.compile(idPatternString);

		for (Enumeration<?> en = props.propertyNames(); en.hasMoreElements();) {
			String key = (String) en.nextElement();
			String mapKey = (String) props.getProperty(key);

			if (mapKey != null) {
				Matcher idMatcher = idPattern.matcher(key);
				if (idMatcher.matches()) {

					String valueName = valuePatternString.replace("{idGroup}",
							idMatcher.group(1));
					String mapValue = props.getProperty(valueName);

					if (mapValue != null) {
						map.put(mapKey, mapValue);
					}
				}
			}
		}

		return map;
	}


	public static String getTimeStampToTD(){
		Calendar calendar = Calendar.getInstance();    
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA); 
	    String utcTime =simpleDateFormat.format(calendar.getTime());  
	    //System.out.println(utcTime);
		return utcTime;
	}
	
	public static String getTimeStampToTD(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA); 
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));   
	    String utcTime =simpleDateFormat.format(calendar.getTime());  
	    //System.out.println(utcTime);
		return utcTime;
	}
	
	public static String upperName(String str){
    	if(str.startsWith("$")){
    		return str;
    	}else{
    	return str.replaceFirst(str.substring(0, 1),str.substring(0, 1).toUpperCase());
    	}
    }
	
	public static long getTimeMillis() {
		long timeMillis = Calendar.getInstance().getTimeInMillis();
		return timeMillis;
	}
	
	
	public static int calLastedTime(String start,String end) {
		int c = 0;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA); 
			Date startDate = simpleDateFormat.parse(start);
			Date endDate = simpleDateFormat.parse(end);
			long startLong = startDate.getTime();
			long endLong = endDate.getTime();
			c = (int)((endLong - startLong) / 1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public static void main(String args[]) {
		System.out.println(getTimeStampToTD());
	}
	
}
