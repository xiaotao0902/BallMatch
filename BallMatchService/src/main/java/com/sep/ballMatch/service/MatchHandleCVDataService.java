package com.sep.ballMatch.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sep.ballMatch.common.PropUtil;
import com.sep.ballMatch.entity.ChooseBeen;
import com.sep.ballMatch.entity.GameCache;
import com.sep.ballMatch.entity.GameProcess;
import com.sep.ballMatch.entity.GameRound;
import com.sep.ballMatch.entity.GameRoundList;
import com.sep.ballMatch.entity.GameStatus;
import com.sep.ballMatch.entity.Status;

public class MatchHandleCVDataService {
	
	private final static Logger logger = LogManager.getLogger(MatchHandleCVDataService.class);
	
	public GameStatus handleCVdata(GameProcess last,GameProcess current) {
		GameStatus gameStatus = new GameStatus();
		if(!GameCache.first_kick) {// not first time 
			
			if(!GameCache.choose_flag) {// to do which play kick which part ball
				logger.info("to do which play kick which part ball");
				chooseFullOrHalf(last,current);
			}
			
			logger.info("not first time");
		    if(last.equals(current)) {//the ball didn't move
		    	logger.info("the ball didn't move");
				return null;
			}else {// the ball move
				logger.info("the ball move");
				if(GameCache.white_move) {// if white was moved(in the hole) and other balls weren't move
					logger.info("if white was moved(in the hole) and other balls weren't move");
					if(!current.equalsOthers(last)) {//others ball were moved
						logger.info("others ball were moved");
						gameStatus = matchNormalRull(last,current);
						GameCache.white_move = false;
					}else {
						logger.info("others ball were not moved");
						gameStatus = null;
					}
				}else {
					logger.info("normal match !!!!!");
					gameStatus = matchNormalRull(last,current);
				}
				
			}
		}else {// first time to kick 
			logger.info("first time to do");
			
			gameStatus = matchNormalRull(last,current);
			
			GameCache.first_kick = false;
		}
		
		return gameStatus;
	}
	
	public boolean ifTriangle(GameProcess current) {
		List<Status> currentData = current.getData();
		List<Integer> x = new ArrayList<Integer>();
		
		for(int i = 1 ; i < currentData.size(); i++) {
			if(currentData.get(i).getX() != 0)
				x.add(currentData.get(i).getX());
		}
		Collections.sort(x);
		
		List<Integer> y = new ArrayList<Integer>();
		for(int i = 1 ; i < currentData.size(); i++) {
			if(currentData.get(i).getY() != 0)
				y.add(currentData.get(i).getY());
		}
		Collections.sort(y);
		
		int length_x = x.get(x.size() - 1) - x.get(0);
		int length_y = y.get(y.size() - 1) - y.get(0);
		
		int x_position = Integer.parseInt(PropUtil.getProperty("x_position"));
		int y_position = Integer.parseInt(PropUtil.getProperty("y_position"));
		
		if(length_x <= x_position && length_y <= y_position) {
			return true;
		}
		return false;
	} 
	
	public boolean filterPerson(GameProcess last,GameProcess current) {
		try {
			if(last == null || current == null) {
				return false;
			}
			if(last.equalsWhite(current)) {//if white ball didn't move, verity others call if move. if move there is a person
				if(!last.equalsOthers(current)) {
					logger.info("person is in the cv !!!!!");
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public GameStatus matchNormalRull(GameProcess last,GameProcess current) {
		GameStatus gameStatus = new GameStatus();
		if(current.ifWhiteInHole()) {// if white ball in the hole change player
			logger.info("matchNormalRul : if white ball in the hole change player");
			GameCache.doSwith();
			GameCache.white_move = true;
		}
		else if (current.equalsOthers(last) && !current.equalsWhite(last)) {
			logger.info("matchNormalRul : if white ball move, others not move");
			GameCache.white_move = true;
		} 
		else if (!current.ifOthersInHole(last)) {// if no ball in the hole, change player
			logger.info("matchNormalRul : if no ball in the hole, change player");
			GameCache.doSwith();
		}
		gameStatus = makeResultToUI(current);
		return gameStatus;
	}
	
	public GameStatus makeResultToUI(GameProcess current) {
		GameStatus gameStatus = new GameStatus();
		List<Integer> balls = new ArrayList<Integer>();
		List<Status> currentData = current.getData();
		
		if(currentData != null ) {
			for(Status s : currentData) {
				balls.add(s.getStatus());
			}
		}
		gameStatus.setBalls(balls);
		gameStatus.setPlayer(GameCache.currentPlayer);
		gameStatus.setChoose(GameCache.choose);
		
		//set round 
		if(!"".equals(GameCache.choose)) {
			GameRoundList currentRound = new GameRoundList();
			currentRound.setPlayer(GameCache.currentPlayer);
			currentRound.setBalls(balls);
			
			GameCache.STACK.push(currentRound);
			
			GameRoundList lastRound = new GameRoundList();
			String lastPlayer = "" ;
			List<Integer> lastRoundBalls = new ArrayList<Integer>();
			List<String> gameRound_ball = new ArrayList<String>();
			
			if (GameCache.STACK.size() > 1) {
				lastRound = GameCache.STACK.getLastball();
				lastPlayer = lastRound.getPlayer();
				lastRoundBalls = lastRound.getBalls();
				gameRound_ball = generateBallNum(lastRoundBalls,balls);
			}else {
				lastRound = GameCache.STACK.getCurrentball();
				lastPlayer = lastRound.getPlayer();
				gameRound_ball = generateBallNum(balls);
			}
			String currentPlayer = currentRound.getPlayer();
			
			if("A".equals(currentPlayer)) {
				GameRound gameRound_A = new GameRound();
				if(lastPlayer.equals(currentPlayer)) {
					GameCache.double_kill_A ++;
					
				}else {
					GameCache.round_A ++;
					GameCache.double_kill_A = 0;
				}
				
				gameRound_A.setBall(gameRound_ball);
				gameRound_A.setRound(GameCache.round_A);
				GameCache.setRound_LIST_A(gameRound_A);
				gameStatus.setGameRound(GameCache.Round_LIST_A);
				gameStatus.setDoubleKill(String.valueOf(GameCache.double_kill_A));
				
			}else if("B".equals(currentPlayer)) {
				GameRound gameRound_B = new GameRound();
				if(lastPlayer.equals(currentPlayer)) {
					GameCache.double_kill_B ++;
				}else {
					GameCache.round_B ++;
					GameCache.double_kill_B = 0;
				}
				gameRound_B.setBall(gameRound_ball);
				gameRound_B.setRound(GameCache.round_B);
				GameCache.setRound_LIST_B(gameRound_B);
				gameStatus.setGameRound(GameCache.Round_LIST_B);
				gameStatus.setDoubleKill(String.valueOf(GameCache.double_kill_B));
			}
		}
		return gameStatus;
	}
	
	public List<String> generateBallNum(List<Integer> lastBalls,List<Integer> currentBalls) {
		List<String> balls = new ArrayList<String>();
		
		for(int i = 1; i < 16 ; i++ ) {
			if(lastBalls.get(i) != currentBalls.get(i) && currentBalls.get(i)==0) {
				balls.add(String.valueOf(i));
			}
		}
		return balls;
		
	}
	
	public List<String> generateBallNum(List<Integer> currentBalls) {
		List<Integer> choose_status_balls = new ArrayList<Integer>();
	
		for(Status s : GameCache.choose_status_data) {
			choose_status_balls.add(s.getStatus());
		}
		List<String> balls = new ArrayList<String>();
		
		for(int i = 1; i < 16 ; i++ ) {
			if(choose_status_balls.get(i) != currentBalls.get(i) && currentBalls.get(i)==0) {
				balls.add(String.valueOf(i));
			}
		}
		return balls;
		
	}
	
	public void chooseFullOrHalf(GameProcess last,GameProcess current) {
		if(!GameCache.white_move) {
			List<Integer> list = last.ifOthersInHoleResult(current);
			if(list.size() == 1) {
				int ball = list.get(0);
				if(ball < 8 && last.equalsHalf(current)) {
					logger.info("if full ball in the hole, half ball didn't move");
					if("A".equals(GameCache.currentPlayer)) {
						GameCache.setChoose("A");
					}else {
						GameCache.setChoose("B");
					}
					
					GameCache.choose_flag = true;
					
					GameCache.choose_status_data = last.getData();
					
				}else if (ball > 8 && last.equalsFull(current)) {
					logger.info("if half ball in the hole, full ball didn't move");
					if("A".equals(GameCache.currentPlayer)) {
						GameCache.setChoose("B");
					}else {
						GameCache.setChoose("A");
					}
					
					GameCache.choose_flag = true;
					
					GameCache.choose_status_data = last.getData();
					
				}else {
					logger.info(" if full ball in the hole, half ball move !!!!");
					int size = GameCache.ChooseBeens.size();
					if(size == 0) {
						logger.info(" any ball is not in the hole before !!!");
						if("A".equals(GameCache.currentPlayer)) {
							logger.info(" store the ball ");
							ChooseBeen cb = new ChooseBeen();
							cb.setCurrentPlayer("A");
							cb.setBall(ball);
							GameCache.ChooseBeens.add(cb);
						}else {
							ChooseBeen cb = new ChooseBeen();
							cb.setCurrentPlayer("B");
							cb.setBall(ball);
							GameCache.ChooseBeens.add(cb);
						}
						GameCache.choose = "";
					}else {
						ChooseBeen cb = GameCache.ChooseBeens.get(0);
						String lastPlayer = cb.getCurrentPlayer();
						int lastball = cb.getBall();
						String currentPlayer = GameCache.currentPlayer;
						if("A".equals(GameCache.currentPlayer)) {
							if (lastPlayer.equals(currentPlayer)) {
								if(lastball < 8 && ball < 8) {
									GameCache.setChoose("A");
								}else {
									GameCache.setChoose("B");
								}
							}
						}else {
							if (lastPlayer.equals(currentPlayer)) {
								if(lastball < 8 && ball < 8) {
									GameCache.setChoose("B");
								}else {
									GameCache.setChoose("A");
								}
							}
						}
						GameCache.ChooseBeens.remove(0);
						GameCache.choose_flag = true;
						GameCache.choose_status_data = last.getData();
					}
				}
			}
		}
	}
}
