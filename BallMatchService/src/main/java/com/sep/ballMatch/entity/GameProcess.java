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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameProcess other = (GameProcess) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!compareData(data,other.data))
			return false;
		return true;
	}
	
	private boolean compareData(List<Status> data,List<Status> otherData) {
		int size = data.size();
		for(int i = 0 ; i < size ; i++) {
			if(!data.get(i).equals(otherData.get(i))) {
				return false;
			}
		}
		return true;
	}
	
}
