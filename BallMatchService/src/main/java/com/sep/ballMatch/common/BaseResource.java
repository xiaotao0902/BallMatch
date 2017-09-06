package com.sep.ballMatch.common;


import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class BaseResource {
	
	CommonResponse cr = new CommonResponse();
	
	public Response commonResponse(Status responseStatus,int errorCode){
        return Response.status(responseStatus).entity(cr.getInstance(errorCode)).build();
	}
	
	public Response commonResponse(Status responseStatus,InternalServerException e){
		CommonResponse cr = e.getCommonResponse();
        return Response.status(responseStatus).entity(cr).build();
	}
	
	public Response commonResponse(Status responseStatus,BadRequestException e){
		CommonResponse cr = e.getCommonResponse();
        return Response.status(responseStatus).entity(cr).build();
	}
}
