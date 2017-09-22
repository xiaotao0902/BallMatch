package com.sep.ballMatch.entity;

import java.util.List;
import java.util.Vector;

public class GameCache {
	public static List<GameProcess> game_cache = new Vector<GameProcess>();
	
	public static String[] players= {"A","B"};
	
	public static String currentPlayer = "A";
	 
	public static int playerNum = 0;

	public static GameProcess getGameProcess(GameProcess gameProcess) {
		GameProcess lastBall = new GameProcess();
		if(game_cache.size() != 0) {
			lastBall = game_cache.get(0);
			game_cache.remove(0);
			game_cache.add(gameProcess);
			return lastBall;
		}else {
			game_cache.add(gameProcess);
			return null;
		}
	}
	
	public static String setCurrentPlayer(String cp) {
		if("A".equals(cp))
			playerNum = 1;
		else
			playerNum = 0;
		currentPlayer = cp;
		return currentPlayer;
	}
	
	public static String doSwith() {
		int i = playerNum ++;
		currentPlayer = players[i%2];
		return currentPlayer;
	}
	
	public static void main(String args[]) {
		setCurrentPlayer("B");
		System.out.println(currentPlayer);
	}

}
