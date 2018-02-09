package com.sep.ballMatch.entity;

import java.util.List;

public class GameStatus {
	private String player;//player : A , B
	private List<Integer> balls;// balls info
	private String choose;//full ball, half ball
	private List<GameRound> gameRound;
	private String doubleKill;
	
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

	public List<GameRound> getGameRound() {
		return gameRound;
	}
	public void setGameRound(List<GameRound> gameRound) {
		this.gameRound = gameRound;
	}
	public String getDoubleKill() {
		return doubleKill;
	}
	public void setDoubleKill(String doubleKill) {
		this.doubleKill = doubleKill;
	}
	
}
