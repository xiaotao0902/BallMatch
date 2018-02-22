package com.sep.ballMatch.entity;

public class GameRank {
	
	private String id;
	private String matchId;
	private String kickOff; // 开球成功
	private String avgTime; // 打击均速
	private String kickCount;//打击杆数
	private String matchTime;//比赛用时
	private String avgFlow;//连杆率
	private String singleStick;//单干球数
	private String goalCount;//进球总数
	private String finalGoal;//8号球
	private String result; //1 win 0 lose
	private String winRate;//胜率
	private String matchCount;//比赛场次
	private String user_id;
	private String vs_user_id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMatchId() {
		return matchId;
	}
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
	public String getKickOff() {
		return kickOff;
	}
	public void setKickOff(String kickOff) {
		this.kickOff = kickOff;
	}
	public String getAvgTime() {
		return avgTime;
	}
	public void setAvgTime(String avgTime) {
		this.avgTime = avgTime;
	}
	
	public String getAvgFlow() {
		return avgFlow;
	}
	public void setAvgFlow(String avgFlow) {
		this.avgFlow = avgFlow;
	}
	public String getSingleStick() {
		return singleStick;
	}
	public void setSingleStick(String singleStick) {
		this.singleStick = singleStick;
	}
	public String getFinalGoal() {
		return finalGoal;
	}
	public void setFinalGoal(String finalGoal) {
		this.finalGoal = finalGoal;
	}
	public String getGoalCount() {
		return goalCount;
	}
	public void setGoalCount(String goalCount) {
		this.goalCount = goalCount;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	public String getMatchTime() {
		return matchTime;
	}
	public void setMatchTime(String matchTime) {
		this.matchTime = matchTime;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getVs_user_id() {
		return vs_user_id;
	}
	public void setVs_user_id(String vs_user_id) {
		this.vs_user_id = vs_user_id;
	}
	public String getKickCount() {
		return kickCount;
	}
	public void setKickCount(String kickCount) {
		this.kickCount = kickCount;
	}
	public String getWinRate() {
		return winRate;
	}
	public void setWinRate(String winRate) {
		this.winRate = winRate;
	}
	public String getMatchCount() {
		return matchCount;
	}
	public void setMatchCount(String matchCount) {
		this.matchCount = matchCount;
	}
	
}
