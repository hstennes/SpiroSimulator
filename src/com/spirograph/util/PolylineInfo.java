
package com.spirograph.util;

public class PolylineInfo {
	
	private int[] xPoints, yPoints;
	
	public PolylineInfo(int[] xPoints, int[] yPoints) {
		this.xPoints = xPoints;
		this.yPoints = yPoints;
	}
	
	public int[] getXPoints() {
		return xPoints;
	}
	
	public int[] getYPoints() {
		return yPoints;
	}
	
}