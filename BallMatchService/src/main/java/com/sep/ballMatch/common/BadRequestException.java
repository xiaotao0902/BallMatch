package com.sep.ballMatch.common;

/**
 * @ClassName: CommonException
 * @Description: TODO
 * @author Tony
 * @date Mar 2, 2016 4:59:31 PM
 *
 */
public class BadRequestException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CommonResponse cr = new CommonResponse();
	
	
	private CommonResponse commonResponse;
	
	public BadRequestException(int code) {
		super();
		this.commonResponse = cr.getInstance(code);
	}

	public BadRequestException(CommonResponse comRes) {
		super();
		this.commonResponse = comRes;
	}

	/**
	 * @return the commonResponse
	 */
	public CommonResponse getCommonResponse() {
		return commonResponse;
	}

	/**
	 * @param commonResponse the commonResponse to set
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

}
