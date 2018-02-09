package com.sep.ballMatch.entity;

import java.util.List;

public class GameRoundList {
	
	private String player;
	
	private List<Integer> balls;
	
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public List<Integer> getBalls() {
		return balls;
	}
	public void setBalls(List<Integer> balls) {
		this.balls = balls;
	}
	
}
