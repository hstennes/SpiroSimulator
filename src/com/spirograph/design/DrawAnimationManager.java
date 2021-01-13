package com.spirograph.design;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.spirograph.main.SpiroSimulator;
import com.spirograph.ui.DrawAnimation;
import com.spirograph.ui.LabeledButtonContainer;

public class DrawAnimationManager extends LabeledButtonContainer{
	
	private final Rectangle bounds = new Rectangle(100, 75, SpiroSimulator.WIDTH - 200, SpiroSimulator.HEIGHT - 200);
	private final Rectangle backButtonBounds = new Rectangle((SpiroSimulator.WIDTH - 100) / 2, SpiroSimulator.HEIGHT - 100, 100, 50);
	private DrawAnimation drawAnimation;
	private SpiroSimulator spiroSimulator;
	private boolean running = false;
	
	public DrawAnimationManager(SpiroSimulator spiroSimulator) {
		super(spiroSimulator);	
		this.spiroSimulator = spiroSimulator;
		addLabeledButton(backButtonBounds, "Back", buttonFont);
	}
	
	public void startAnimation(SpiroInfo spiro, int iterations) {
		drawAnimation = new DrawAnimation(spiro, iterations, bounds);
		running = true;
		setActive(true);
		spiroSimulator.getButtonPanel().setActive(false);
	}
	
	public void stopAnimation() {
		running = false;
		setActive(false);
		spiroSimulator.getButtonPanel().setActive(true);
	}
	
	public void tick() {
		if(running) drawAnimation.tick();
	}
	
	public void render(Graphics g) {
		if(running) {
			drawAnimation.render(g);
			super.render(g);
		}
	}

	@Override
	public void buttonPressed(String name) {
		if(running && name.equals("Back")) stopAnimation();
	}
	
	public boolean isRunning() {
		return running;
	}
}
