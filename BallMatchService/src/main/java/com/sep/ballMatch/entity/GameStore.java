package com.sep.ballMatch.entity;

import java.util.List;

public class GameStore {
	
	private String user_id;
	
	private String vs_user_id;
	
	private String matchId;
	
	private String player;
	
	private String timestamp;
	
	private List<Status>data;

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public List<Status> getData() {
		return data;
	}

	public void setData(List<Status> data) {
		this.data = data;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getVs_user_id() {
		return vs_user_id;
	}

	public void setVs_user_id(String vs_user_id) {
		this.vs_user_id = vs_user_id;
	}

	
	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
