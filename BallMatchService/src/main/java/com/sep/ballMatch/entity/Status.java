package com.sep.ballMatch.entity;

public class Status {
	private double x;
	private double y;
	private int status;
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
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
	
}
