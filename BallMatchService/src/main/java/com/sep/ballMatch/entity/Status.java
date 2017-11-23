package com.sep.ballMatch.entity;

public class Status {
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Status other = (Status) obj;
		if (status != other.status)
			return false;
		if ((x-other.x) > 2 || (x-other.x) < -2)
			return false;
		if ((y-other.y) > 2 ||(y-other.y) < -2)
			return false;
		return true;
	}
}
