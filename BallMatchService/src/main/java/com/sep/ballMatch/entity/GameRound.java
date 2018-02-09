package com.sep.ballMatch.entity;

import java.util.List;

public class GameRound {
	
	private int round;
	private List<String> ball;
	
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public List<String> getBall() {
		return ball;
	}
	public void setBall(List<String> ball) {
		this.ball = ball;
	}
	
}
