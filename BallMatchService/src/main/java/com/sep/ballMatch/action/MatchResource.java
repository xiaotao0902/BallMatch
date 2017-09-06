package com.sep.ballMatch.action;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sep.ballMatch.common.BaseResource;
import com.sep.ballMatch.entity.GameProcess;
import com.sep.ballMatch.entity.Result;

@Path("/game") 
public class MatchResource extends BaseResource {
	
	private final static Logger logger = LogManager.getLogger(MatchResource.class);
	
	@POST  
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response getGameInfo(GameProcess gameProcess) {
		Result result = new Result();
		result.setCode("200");
		result.setMessage("success");
		logger.info(result);
		return Response.ok(result).build();
	}
}
