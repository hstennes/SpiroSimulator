package com.spirograph.design;

import java.util.ArrayList;

public class Tester {

	private final int circles = 10;
	private int quality;
	
	public Tester(int quality) {
		this.quality = quality;
	}
	
	public SpiroInfo testSpiro() {
		double[] radii = new double[circles];
		double[] speeds = new double[circles];
		for(int i = 0; i < circles; i++) {
			radii[i] = 200 / Math.pow(3, i);
			speeds[i] = Math.pow(-4, i - 1) / quality;
		}
		return createInfo(radii, speeds);
	}
	
	public SpiroInfo createInfo(double[] radii, double[] speeds) {
		ArrayList<String> info = new ArrayList<String>();
		for(int i = 0; i < radii.length; i++) {
			if(i == 0) info.add(radii[i] + "," + -1 + "," + 0 + "," + false);
			else if(i == radii.length - 1) info.add(radii[i] + "," + 0 + "," + speeds[i] + "," + false);
			else info.add(radii[i] + "," + -1 + "," + speeds[i] + "," + false);
		}
		return new SpiroInfo(info);
	}	
}
