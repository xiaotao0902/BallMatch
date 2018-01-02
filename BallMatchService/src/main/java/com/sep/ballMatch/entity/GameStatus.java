package com.sep.ballMatch.entity;

import java.util.List;

public class GameStatus {
	private String player;
	private List<Integer> balls;
	private String choose;
	
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
	public String getChoose() {
		return choose;
	}
	public void setChoose(String choose) {
		this.choose = choose;
	}
	
}
