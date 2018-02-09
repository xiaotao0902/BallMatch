package com.sep.ballMatch.entity;

import java.util.ArrayList;
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameProcess other = (GameProcess) obj;
		if (!equalsWhite(other) || !equalsOthers(other))
			return false;
		return true;
	}
	public boolean equalsWhite(Object obj) {
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
		} else if (!compareWhite(data,other.data))
			return false;
		return true;
	}
	
	public boolean equalsOthers(Object obj) {
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
		} else if (!compareOthers(data,other.data))
			return false;
		return true;
	}
	
	public boolean equalsFull(Object obj) {
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
		} else if (!compareFull(data,other.data))
			return false;
		return true;
	}
	
	public boolean equalsHalf(Object obj) {
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
		} else if (!compareHalf(data,other.data))
			return false;
		return true;
	}
	
	public boolean ifWhiteInHole() {
		int status = 0;
		if(this.data != null ) {
			status = this.data.get(0).getStatus();
			if(status == 0 ) {
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}
	
	public boolean ifOthersInHole(GameProcess last) {
		if(last == null && this.data != null) {// first kick
			for(Status s : data) {
				if(s.getStatus() == 0 ) {
					return true;
				}
			}
			return false;
		}
		else if(this.data != null && last != null) {// not first kick 
			List<Status> lastData = last.getData();
			for(int i = 1 ; i < 16 ; i++) {
				if(this.data.get(i).getStatus()==0 && lastData.get(i).getStatus()==1) {
					return true;
				}
			}
		}
		return false;
	}
	
	public List<Integer> ifOthersInHoleResult(GameProcess other) {
		List<Integer> list = new ArrayList<Integer>();
		List<Status> otherData = other.getData();
		for(int i = 1 ; i < 16 ; i++) {
			if(this.data.get(i).getStatus()==1 && otherData.get(i).getStatus()==0) {
				list.add(i);
			}
		}
		return list;
	}
	
	private boolean compareOthers(List<Status> data,List<Status> otherData) {
		int size = data.size();
		for(int i = 1 ; i < size ; i++) {
			if(!data.get(i).equalsOthers(otherData.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	private boolean compareWhite(List<Status> data,List<Status> otherData) {
		if(!data.get(0).equalsWhite(otherData.get(0))) {
			return false;
		}
		return true;
	}
	
	private boolean compareFull(List<Status> data,List<Status> otherData) {
		for(int i = 1 ; i < 8 ; i++) {
			if(!data.get(i).equalsOthers(otherData.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	private boolean compareHalf(List<Status> data,List<Status> otherData) {
		for(int i = 7; i < 16 ; i++) {
			if(!data.get(i).equalsOthers(otherData.get(i))) {
				return false;
			}
		}
		return true;
	}
	
}
