package com.sep.ballMatch.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @ClassName: CommonResponse
 * @Description: TODO
 * @author Tony
 * @date Mar 2, 2016 5:02:13 PM
 *
 */

@SuppressWarnings({ "unchecked", "rawtypes" })
@XmlRootElement
public class CommonResponse implements Serializable {
	
	private final static Logger logger = LogManager.getLogger(CommonResponse.class);
	
	private static final long serialVersionUID = 1L;

	private static Map<String, String> messages;
	
	private String original;

	private int common_id = 600;

	private String common_msg = "Unknown Error";

	private int status;

	private String detail;

	private String schemas[] = { "urn:ietf:params:scim:api:messages:2.0:Error" };

	static {
		Properties properties = new Properties();
		InputStream in = CommonResponse.class
				.getResourceAsStream("/Msgs.properties");
		try {
			properties.load(in);
			messages = new HashMap<String, String>((Map) properties);

		} catch (IOException e) {
			logger.error(e);
		}
	}

	public CommonResponse getInstance(int code) {
		
		CommonResponse cr = new CommonResponse();
		if (messages.containsKey(String.valueOf(code))) {
			cr.setStatus(code);
			cr.setDetail(messages.get(String.valueOf(code)));
		} else {
			cr.setStatus(common_id);
			cr.setDetail(common_msg);
		}
		return cr;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail
	 *            the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * @return the schemas
	 */
	public String[] getSchemas() {
		return schemas;
	}

	/**
	 * @param schemas
	 *            the schemas to set
	 */
	public void setSchemas(String[] schemas) {
		this.schemas = schemas;
	}

	/**
	 * @return the original
	 */
	public String getOriginal() {
		return original;
	}

	/**
	 * @param original the original to set
	 */
	public void setOriginal(String original) {
		this.original = original;
	}
	
}
