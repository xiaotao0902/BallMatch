package com.sep.ballMatch.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class GameCache {
	
	public static List<GameProcess> game_cache = new Vector<GameProcess>();
	
	public static String[] players = {"A","B"};
	
	public static String[] chooses = {"A","B"};
	
	public static String currentPlayer = "A";
	
	public static int playerNum = 0;
	
	public static int chooseNum = 0;
	
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
	
	public static List<GameStatus> gameGround_A = new ArrayList<GameStatus>();
	
	public static List<GameStatus> gameGround_B = new ArrayList<GameStatus>();
	
	public static List<GameStatus> gameBall = new ArrayList<GameStatus>();
	
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
		gameGround_A.clear();
		gameGround_B.clear();
		gameBall.clear();
	}
	
	public static void setRound_LIST_A(GameRound gameRound) {
		boolean flag = false;
		if(Round_LIST_A.size() == 0) {
			Round_LIST_A.add(gameRound);
		}else {
			for(GameRound gr : Round_LIST_A) {
				if(gr.getRound() == gameRound.getRound()) {
					flag = true;
					List<String> balls = gameRound.getBall();
					for(String str : balls) {
						gr.getBall().add(str);
					}
				}
			}
			if(!flag) {
				Round_LIST_A.add(gameRound);
			}
		}
	}
	
	public static void setRound_LIST_B(GameRound gameRound) {
		boolean flag = false;
		if(Round_LIST_B.size() == 0) {
			Round_LIST_B.add(gameRound);
		}else {
			for(GameRound gr : Round_LIST_B) {
				if(gr.getRound() == gameRound.getRound()) {
					flag = true;
					List<String> balls = gameRound.getBall();
					for(String str : balls) {
						gr.getBall().add(str);
					}
				}
			}
			if(!flag) {
				Round_LIST_B.add(gameRound);
			}
		}
	}
	
	public static void setGameStatus(GameStatus gameStatus) {
		
		GameStatus gameBallCache = new GameStatus();
		gameBallCache.setBalls(gameStatus.getBalls());
		
		if(gameBall.size() != 0) {
			gameBall.remove(0);
			gameBall.add(gameBallCache);
		}else {
			gameBall.add(gameBallCache);
		}
		
		GameStatus gameGroundCache = new GameStatus();
		gameGroundCache.setGameRound(gameStatus.getGameRound());
		
		if("A".equals(gameStatus.getPlayer())) {
			if(gameGround_A.size() != 0 ) {
				gameGround_A.remove(0);
				gameGround_A.add(gameGroundCache);
			}else {
				gameGround_A.add(gameGroundCache);
			}
		}else if("B".equals(gameStatus.getPlayer())) {
			if(gameGround_B.size() != 0 ) {
				gameGround_B.remove(0);
				gameGround_B.add(gameGroundCache);
			}else {
				gameGround_B.add(gameGroundCache);
			}
		}
	}
	
	public static GameStatus getGameStatus(String player) {
		GameStatus gameStatus = new GameStatus();
		
		gameStatus.setBalls(gameBall.get(0).getBalls());
		
		if("A".equals(player)) {
			if(gameGround_A.size() != 0) {
				gameStatus.setGameRound(gameGround_A.get(0).getGameRound());
			}
		}
		if("B".equals(player)) {
			if(gameGround_B.size() != 0) {
				gameStatus.setGameRound(gameGround_B.get(0).getGameRound());
			}
		}
		return gameStatus;
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
	
	public static String setChoose(String c) {
		if("A".equals(c))
			chooseNum = 1;
		else
			chooseNum = 0;
		choose = c;
		return choose;
	}
	
	public static String swithChoose() {
		int j = chooseNum ++;
		choose = chooses[j%2];
		return choose;
	}
	
	public static String doSwith() {
		int i = playerNum ++;
		currentPlayer = players[i%2];
		
		if(choose_flag) {
			int j = chooseNum ++;
			choose = chooses[j%2];
		}
		
		return currentPlayer;
	}
	
	public static void main(String args[]) {
		setCurrentPlayer("B");
		doSwith();
		System.out.println(currentPlayer);
	}
	
}
