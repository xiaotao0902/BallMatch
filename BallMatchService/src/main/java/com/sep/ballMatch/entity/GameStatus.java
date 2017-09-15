package com.sep.ballMatch.entity;

public class GameStatus {
	private String player;
	private String score;
	private String[] balls;
	private boolean ifPlay;
	
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String[] getBalls() {
		return balls;
	}
	public void setBalls(String[] balls) {
		this.balls = balls;
	}
	public boolean isIfPlay() {
		return ifPlay;
	}
	public void setIfPlay(boolean ifPlay) {
		this.ifPlay = ifPlay;
	}
	
}
