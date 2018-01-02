package com.sep.ballMatch.entity;

import com.sep.ballMatch.common.PropUtil;

public class Status {
	
	private int verify_move_white = Integer.parseInt(PropUtil.getProperty("verify_move_white"));
	private int verify_move = Integer.parseInt(PropUtil.getProperty("verify_move"));
	
	private int x;
	private int y;
	private int status;

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Status [x=" + x + ", y=" + y + ", status=" + status + "]";
	}

	public boolean equalsWhite(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Status other = (Status) obj;
		if (status != other.status)
			return false;
		if ((x - other.x) > verify_move_white || (x - other.x) < - verify_move_white)
			return false;
		if ((y - other.y) > verify_move_white || (y - other.y) < - verify_move_white)
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
		Status other = (Status) obj;
		if (status != other.status)
			return false;
		if ((x - other.x) > verify_move || (x - other.x) < - verify_move)
			return false;
		if ((y - other.y) > verify_move || (y - other.y) < - verify_move)
			return false;
		return true;
	}
	
}
