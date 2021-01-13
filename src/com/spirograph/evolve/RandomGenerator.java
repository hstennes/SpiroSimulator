package com.spirograph.evolve;

import java.util.ArrayList;
import java.util.Random;

import com.spirograph.design.SpiroInfo;
import com.spirograph.main.SpiroSimulator;

public class RandomGenerator {

	private final int minMainRadius = 100;
	private final int maxMainRadius = 800;
	private final int defaultMainRadius = 400;
	private final int minRadius = 50;
	private final int maxRadius = 800;
	private final float minSpeed = 0.1f;
	private final float maxSpeed = 4;
	private final int maxCircles = 6; 
	private final int minRScale = 2;
	private final int maxRScale = 5;
	
	private Random rand;
	
	public RandomGenerator() {
		rand = new Random();
	}
	
	public SpiroInfo randomSpirograph() {
		ArrayList<String> info = new ArrayList<String>();
		int circles = rand.nextInt(maxCircles - 2) + 2;
		info.add(defaultMainRadius + ",-1,0,true");
		for(int i = 1; i < circles; i++) {
			info.add(randomGear(i == circles - 1, 0));
		}
		return new SpiroInfo(info);
	}
	
	public SpiroInfo geometricRadiusSpirograph() {
		ArrayList<String> info = new ArrayList<String>();
		int scale = rand.nextInt(maxRScale - minRScale) + minRScale;
		double currentRadius = minMainRadius + rand.nextInt(maxMainRadius - minMainRadius); 	
		int circles = rand.nextInt(maxCircles - 2) + 2;
		
		info.add(currentRadius + ",-1,0,true");
		for(int i = 1; i < circles; i++) {
			currentRadius /= scale;
			info.add(randomGear(i == circles - 1, currentRadius));
		}
		return new SpiroInfo(info);
	}
	
	private String randomGear(boolean drawing, double radius) {
		if(radius == 0) radius = rand.nextInt(maxRadius - minRadius) + minRadius;
		float speed = minSpeed + rand.nextFloat() * (maxSpeed - minSpeed);
		speed = SpiroSimulator.truncate(speed, 1);
		boolean placement = rand.nextBoolean();
		
		int dotRadius;
		if(drawing) dotRadius = rand.nextInt(maxRadius - minRadius) + minRadius;
		else dotRadius = -1;
		
		String gear = radius + "," + dotRadius + "," + speed + "," + placement;
		return gear;
	}
}
