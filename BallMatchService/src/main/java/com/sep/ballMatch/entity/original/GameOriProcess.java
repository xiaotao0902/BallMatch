package com.sep.ballMatch.entity.original;

import java.util.List;

public class GameOriProcess {
	private String gameId;
	private String tableWidth;
	private String tableHeight;
	private String timeStamp;
	private String requestId;
	private List<OriStatus>data;
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
	public List<OriStatus> getData() {
		return data;
	}
	public void setData(List<OriStatus> data) {
		this.data = data;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((gameId == null) ? 0 : gameId.hashCode());
		result = prime * result + ((requestId == null) ? 0 : requestId.hashCode());
		result = prime * result + ((tableHeight == null) ? 0 : tableHeight.hashCode());
		result = prime * result + ((tableWidth == null) ? 0 : tableWidth.hashCode());
		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
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
		GameOriProcess other = (GameOriProcess) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (gameId == null) {
			if (other.gameId != null)
				return false;
		} else if (!gameId.equals(other.gameId))
			return false;
		if (requestId == null) {
			if (other.requestId != null)
				return false;
		} else if (!requestId.equals(other.requestId))
			return false;
		if (tableHeight == null) {
			if (other.tableHeight != null)
				return false;
		} else if (!tableHeight.equals(other.tableHeight))
			return false;
		if (tableWidth == null) {
			if (other.tableWidth != null)
				return false;
		} else if (!tableWidth.equals(other.tableWidth))
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		return true;
	}
	
}
