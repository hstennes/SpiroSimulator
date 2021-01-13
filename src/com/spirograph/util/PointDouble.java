package com.spirograph.util;

import java.awt.Point;

public class PointDouble {

	private double x, y;
	
	public PointDouble() {
		x = 0;
		y = 0;
	}
	
	public PointDouble(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void setLocation(double x, double y) {
		this.x  = x;
		this.y = y;
	}
	
	public void setLocation(Point p) {
		this.x = p.getX();
		this.y = p.getY();
	}
	
	public void setLocation(PointDouble p) {
		this.x = p.getX();
		this.y = p.getY();
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
}
