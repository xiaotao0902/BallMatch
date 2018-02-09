package com.sep.ballMatch.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sep.ballMatch.common.Utils;
import com.sep.ballMatch.entity.GameRank;
import com.sep.ballMatch.entity.GameStore;
import com.sep.ballMatch.entity.Status;

public class MatchRankUtil {
	
	private final static Logger logger = LogManager.getLogger(MatchRankUtil.class);
	
	public Map<String,List<GameStore>> separateRank(List<GameStore> list) {
		
		Map<String,List<GameStore>> map = new Hashtable<String,List<GameStore>>();
		
		List<GameStore> list1 = new ArrayList<GameStore>();
		
		List<GameStore> list2 = new ArrayList<GameStore>();
		
		for(int i = list.size()-1; i >= 0; i--) {
			String player = list.get(i).getPlayer();
			if("A".equals(player)) {
				list1.add(list.get(i));
			}else if ("B".equals(player)){
				list2.add(list.get(i));
			}
		}
		map.put("A", list1);
		map.put("B", list2);
		
		return map;
	}
	
	public void kickoff(GameRank rank_A,GameRank rank_B,List<GameStore> list){
		String kickoff = "0";//1,goal 0,non_goal,2no kick
		
		GameStore gameStore = list.get(0);
		List<Status> statuss = gameStore.getData();
		for(Status status : statuss) {
			if(status.getStatus()==0) {
				kickoff = "1";
				break;
			}
		}
		if("A".equals(gameStore.getPlayer())) {
			rank_A.setKickOff(kickoff);
			rank_B.setKickOff("2");
		}else if ("B".equals(gameStore.getPlayer())) {
			rank_B.setKickOff(kickoff);
			rank_A.setKickOff("2");
		}
		
		logger.info("play A kickoff : " + rank_A.getKickOff() );
		logger.info("play B kickoff : " + rank_B.getKickOff() );
	}
	
	public void timeAndCount(GameRank rank_A,GameRank rank_B,List<GameStore> list){
		
		int rank_A_count = 0;
		int rank_B_count = 0;
		
		int rank_A_time = 0;
		int rank_B_time = 0;
		
		String start_time = list.get(0).getTimestamp();
		String end_time = "";
		
		for(GameStore gameSrore : list) {
			if("A".equals(gameSrore.getPlayer())) {
				rank_A_count ++;
				end_time = gameSrore.getTimestamp();
				rank_A_time += Utils.calLastedTime(start_time, end_time);
				start_time = end_time;
			}else {
				rank_B_count ++;
				end_time = gameSrore.getTimestamp();
				rank_B_time += Utils.calLastedTime(start_time, end_time);
				start_time = end_time;
			}
		}
		logger.info("play A time : " + rank_A_time + " count: " + rank_A_count);
		logger.info("play B time : " + rank_B_time + " count: " + rank_B_count);
		rank_A.setKickCount(String.valueOf(rank_A_count));
		rank_A.setMatchTime(String.valueOf(rank_A_time));
		rank_A.setAvgTime(String.valueOf(rank_A_time/rank_A_count));
		
		rank_B.setKickCount(String.valueOf(rank_B_count));
		rank_B.setMatchTime(String.valueOf(rank_B_time));
		rank_B.setAvgTime(String.valueOf(rank_B_time/rank_B_count));
	}
	
	public void kickAndGoal(GameRank rank_A,GameRank rank_B,List<GameStore> list){
		
		String singleStick_A = "";
		String singleStick_B = "";
		
		String avgFlow_A = "";
		String avgFlow_B = "";
		
		List<Integer> rank_A_list = new ArrayList<Integer>();
		List<Integer> rank_B_list = new ArrayList<Integer>();
		
		int goalCount_A = 0;
		int goalCount_B = 0;
		String lastPlayer = "";
		
		for(GameStore gameSrore : list) {
			if("A".equals(gameSrore.getPlayer())) {
				if("".equals(lastPlayer)) {
					lastPlayer = "A";
					continue;
				}
				if("A".equals(lastPlayer)) {
					goalCount_A++;
				}else {
					if(goalCount_B > 1) {
						rank_B_list.add(goalCount_B);
						goalCount_B = 0;
					}
					rank_B_list.add(goalCount_B);
				}
				lastPlayer = "A";
			}else {
				if("".equals(lastPlayer)) {
					lastPlayer = "B";
					continue;
				}
				if("B".equals(lastPlayer)) {
					goalCount_B++;
				}else {
					if(goalCount_A > 1) {
						rank_A_list.add(goalCount_A);
						goalCount_A = 0;
					}
				}
				lastPlayer = "B";
			}
		}
		Collections.reverse(rank_A_list);
		Collections.reverse(rank_B_list);
		
		singleStick_A = String.valueOf(rank_A_list.get(0));
		singleStick_B = String.valueOf(rank_B_list.get(0));
		
		double totalFlow_A = 0;
		for(Integer i : rank_A_list) {
			totalFlow_A += i;
		}
		
		double totalFlow_B = 0;
		for(Integer i : rank_B_list) {
			totalFlow_B += i;
		}
		
		avgFlow_A = String.valueOf(totalFlow_A/Double.parseDouble(rank_A.getKickCount()));
		avgFlow_B = String.valueOf(totalFlow_B/Double.parseDouble(rank_B.getKickCount()));
		
		rank_A.setSingleStick(singleStick_A);
		rank_A.setAvgFlow(avgFlow_A);
		
		rank_B.setSingleStick(singleStick_B);
		rank_B.setAvgFlow(avgFlow_B);
		
		logger.info("play A singleStick : " + rank_A.getSingleStick());
		logger.info("play A avgFlow : " + rank_A.getAvgFlow());
		
		logger.info("play B singleStick : " + rank_B.getSingleStick());
		logger.info("play B avgFlow : " + rank_B.getAvgFlow());
	}
	
	public void finallGoal(GameRank rank_A,GameRank rank_B,List<GameStore> list_A,List<GameStore> list_B,String matchResult){
		int finalGoal_A = 0;
		int finalGoal_B = 0;
		
		if("A".equals(matchResult)) {
			for(GameStore gs : list_A) {
				List<Status> statuss = gs.getData();
				if(statuss.get(7).getStatus()==0) {
					finalGoal_A ++;
					break;
				}
			}
		}else {
			for(GameStore gs : list_B) {
				List<Status> statuss = gs.getData();
				if(statuss.get(7).getStatus()==0) {
					finalGoal_B ++;
					break;
				}
			}
		}
		
		rank_A.setFinalGoal(String.valueOf(finalGoal_A));
		rank_B.setFinalGoal(String.valueOf(finalGoal_B));
		
		logger.info("play A FinalGoal : " + rank_A.getFinalGoal());
		
		logger.info("play B FinalGoal : " + rank_B.getFinalGoal());
	}
	
	public void GoalCount(GameRank rank_A,GameRank rank_B,List<GameStore> list_A,List<GameStore> list_B){
		
		int goalCount_A = 0;
		int goalCount_B = 0;
		
		for(GameStore gs : list_A) {
			List<Status> statuss = gs.getData();
			for(Status s : statuss) {
				if(s.getStatus() == 0) {
					goalCount_A++;
				}
			}
		}
		for(GameStore gs : list_B) {
			List<Status> statuss = gs.getData();
			for(Status s : statuss) {
				if(s.getStatus() == 0) {
					goalCount_B++;
				}
			}
		}
		rank_A.setGoalCount(String.valueOf(goalCount_A));
		rank_B.setGoalCount(String.valueOf(goalCount_B));
		
		logger.info("play A GoalCount : " + rank_A.getGoalCount());
		
		logger.info("play B GoalCount : " + rank_B.getGoalCount());
	}
}
