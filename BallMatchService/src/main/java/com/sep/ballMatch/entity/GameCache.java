package com.sep.ballMatch.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class GameCache {
	
	public static List<GameProcess> game_cache = new Vector<GameProcess>();
	
	public static String[] players = {"A","B"};
	
	public static String currentPlayer = "A";
	 
	public static int playerNum = 0;
	
	public static boolean triangle;	
	
	public static boolean kick_off;
	
	public static boolean first_kick;
	
	public static boolean white_move;
	
	public static boolean choose_flag;
	
	public static String choose = "";
	
	public static List<Status> choose_status_data = new ArrayList<Status>();;
	
	public static List<ChooseBeen> ChooseBeens = new ArrayList<ChooseBeen>();
	
	public static String matchId;
	
	public static String user_id;
	
	public static String vs_user_id;
	
	// game Rouand 
	
	public static GameStack STACK = new GameStack();
	
	public static int round_A = 1;
	
	public static int round_B = 1;
	
	public static int double_kill_A = 0;
	
	public static int double_kill_B = 0;
	
	
	public static List<GameRound> Round_LIST_A = new ArrayList<GameRound>();
	
	public static List<GameRound> Round_LIST_B = new ArrayList<GameRound>();
	
	public static void cleanCache() {
		game_cache.clear();
		triangle = false;
		kick_off = false;
		choose_flag = false;
		choose = "";
		ChooseBeens.clear();
		STACK = null;
		STACK = new GameStack();
		round_A = 1;
	    round_B = 1;
		double_kill_A = 0;
		double_kill_B = 0;
		Round_LIST_A.clear();
		Round_LIST_B.clear();
		choose_status_data.clear();
	}
	
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
