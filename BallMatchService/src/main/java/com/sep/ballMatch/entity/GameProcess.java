package com.sep.ballMatch.entity;

import java.util.List;

public class GameProcess {
	private String gameId;
	private String tableWidth;
	private String tableHeight;
	private String timeStamp;
	private String requestId;
	private String createdBy;
	private String firstShotPlayerId;
	private String playerIds;
	private List<Status>data;
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getTableWidth() {
		return tableWidth;
	}
	public void setTableWidth(String tableWidth) {
		this.tableWidth = tableWidth;
	}
	public String getTableHeight() {
		return tableHeight;
	}
	public void setTableHeight(String tableHeight) {
		this.tableHeight = tableHeight;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getFirstShotPlayerId() {
		return firstShotPlayerId;
	}
	public void setFirstShotPlayerId(String firstShotPlayerId) {
		this.firstShotPlayerId = firstShotPlayerId;
	}
	public String getPlayerIds() {
		return playerIds;
	}
	public void setPlayerIds(String playerIds) {
		this.playerIds = playerIds;
	}
	public List<Status> getData() {
		return data;
	}
	public void setData(List<Status> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "GameProcess [gameId=" + gameId + ", tableWidth=" + tableWidth + ", tableHeight=" + tableHeight
				+ ", timeStamp=" + timeStamp + ", requestId=" + requestId + ", createdBy=" + createdBy
				+ ", firstShotPlayerId=" + firstShotPlayerId + ", playerIds=" + playerIds + ", data=" + data + "]";
	}
	
}
