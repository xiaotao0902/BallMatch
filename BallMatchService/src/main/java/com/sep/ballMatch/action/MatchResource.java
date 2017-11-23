package com.sep.ballMatch.action;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.sep.ballMatch.common.BaseResource;
import com.sep.ballMatch.entity.GameCache;
import com.sep.ballMatch.entity.GameProcess;
import com.sep.ballMatch.entity.GameStatus;
import com.sep.ballMatch.entity.Result;
import com.sep.ballMatch.entity.original.GameOriProcess;
import com.sep.ballMatch.entity.original.GameOriService;
import com.sep.ballMatch.service.MatchService;
import com.sep.ballMatch.service.MatchStartService;

@Path("/game") 
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class MatchResource extends BaseResource {
	
	private final static Logger logger = LogManager.getLogger(MatchResource.class);
	
	private MatchService matchService = new MatchService();
	
	private GameOriService gameOriService = new GameOriService();
	
	@POST
	@Path("/info") 
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response postGameInfo(String json, @Context HttpServletRequest request) {
		logger.info(json);
		Gson gson = new Gson();
		GameOriProcess gameOriProcess = gson.fromJson(json, GameOriProcess.class);
		//detail the original data
		GameProcess gameProcess = gameOriService.detailOriginal(gameOriProcess);
		
		Result result = matchService.doMatch(gameProcess);
		
		return Response.ok(result).build();
	}
	
	@POST  
	@Path("/start")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response startGame(String json, @Context HttpServletRequest request) {
		logger.info(json);
		Gson gson = new Gson();
		GameStatus gameStatus = gson.fromJson(json, GameStatus.class);
		GameCache.setCurrentPlayer(gameStatus.getPlayer());
		
		Thread start = new Thread(new MatchStartService());
		start.start();
		Result result = new Result();
		result.setStatus("ok");
		
		return Response.ok(result).build();
	}
	
	@GET 
	@Path("/changePlayer")
	public Response changePlayer(@Context HttpServletRequest request) {
		GameCache.doSwith();
		return Response.ok("ok").build();
	}
	
	@GET 
	@Path("/setPlayer")
	public Response setPlayer(@QueryParam("player") String player,@Context HttpServletRequest request) {
		GameCache.setCurrentPlayer(player);
		return Response.ok("ok").build();
	}
}
