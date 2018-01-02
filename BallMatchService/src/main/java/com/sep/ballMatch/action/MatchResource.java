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
import com.sep.ballMatch.entity.GameStore;
import com.sep.ballMatch.entity.Result;
import com.sep.ballMatch.entity.original.GameOriProcess;
import com.sep.ballMatch.service.MatchOriDataService;
import com.sep.ballMatch.service.MatchService;
import com.sep.ballMatch.service.MatchStartService;

@Path("/game") 
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class MatchResource extends BaseResource {
	
	private final static Logger logger = LogManager.getLogger(MatchResource.class);
	
	private MatchService matchService = new MatchService();
	
	private MatchOriDataService gameOriService = new MatchOriDataService();
	
	@POST
	@Path("/info") 
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response postGameInfo(String json, @Context HttpServletRequest request) {
		
		logger.info("triangle : " + GameCache.triangle);
		logger.info("kick_off : " + GameCache.kick_off);
		logger.info("first_kick : " + GameCache.first_kick);
		
		
		Result result = new Result();
		result.setStatus("ok");
		Gson gson = new Gson();
		GameOriProcess gameOriProcess = gson.fromJson(json, GameOriProcess.class);
		//detail the original data
		GameProcess gameProcess = gameOriService.detailOriginal(gameOriProcess);
		matchService.doMatch(gameProcess);
		
		return Response.ok(result).build();
	}
	
	@POST  
	@Path("/start")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response startGame(String json, @Context HttpServletRequest request) {
		Gson gson = new Gson();
		GameStore gameStore = gson.fromJson(json, GameStore.class);
		GameCache.setCurrentPlayer(gameStore.getPlayer());
		GameCache.user_id = gameStore.getUser_id();
		GameCache.vs_user_id = gameStore.getVs_user_id();
		
		Thread start = new Thread(new MatchStartService());
		start.start();
		
		Result result = new Result();
		result.setStatus("ok");
		
		GameCache.triangle = false;
		GameCache.kick_off = false;
		return Response.ok(result).build();
	}
	
	@GET 
	@Path("/changePlayer")
	public Response changePlayer(@QueryParam("player") String player,@Context HttpServletRequest request) {
		GameCache.setCurrentPlayer(player);
		return Response.ok("ok").build();
	}
	
	@GET 
	@Path("/setPlayer")
	public Response setPlayer(@QueryParam("player") String player,@Context HttpServletRequest request) {
		GameCache.setCurrentPlayer(player);
		GameCache.triangle = false;
		GameCache.kick_off = false;
		if("A".equals(player)) {
			GameCache.result = GameCache.user_id;
		}else {
			GameCache.result = GameCache.vs_user_id;
		}
		return Response.ok("ok").build();
	}
}
