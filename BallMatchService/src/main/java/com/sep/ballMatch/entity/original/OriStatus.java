package com.sep.ballMatch.entity.original;

import com.google.gson.annotations.SerializedName;

public class OriStatus {
	private String right;
	private String bottom;
	private String top;
	@SerializedName("class")
	private String _class;
	private String prob;
	private String left;
	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
	}
	public String getBottom() {
		return bottom;
	}
	public void setBottom(String bottom) {
		this.bottom = bottom;
	}
	public String getTop() {
		return top;
	}
	public void setTop(String top) {
		this.top = top;
	}
	public String get_class() {
		return _class;
	}
	public void set_class(String _class) {
		this._class = _class;
	}
	public String getProb() {
		return prob;
	}
	public void setProb(String prob) {
		this.prob = prob;
	}
	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	
}
