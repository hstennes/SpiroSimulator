package com.spirograph.ui;

import java.awt.Rectangle;

import com.spirograph.design.SpiroInfo;

public class DrawAnimation extends SpiroDisplay {

	private final int viewTicks = 1800;
	private int iterationsPerformed;
	private int maxIterations;
	
	public DrawAnimation(SpiroInfo spiroInfo, int iterations, Rectangle bounds) {
		super(spiroInfo, bounds);
		spiro.setGearsVisible(true);
		maxIterations = iterations;
	}
	
	public void tick() {
		if(iterationsPerformed < maxIterations) spiro.tick();
		else if(iterationsPerformed >= maxIterations + viewTicks) restartAnimation();
		iterationsPerformed++;
	}
	
	private void restartAnimation() {
		spiro.clearDesign();
		iterationsPerformed = 0;
	}
}
