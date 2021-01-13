package com.spirograph.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;

import com.spirograph.design.SpiroInfo;
import com.spirograph.design.Spirograph;

public class SpiroDisplay extends MouseAdapter{

	protected Spirograph spiro; 
	protected Rectangle bounds;

	public SpiroDisplay(SpiroInfo spiroInfo, Rectangle bounds) {
		this.bounds = bounds;
		createSpiro(spiroInfo);
	}
	
	private void createSpiro(SpiroInfo spiroInfo) {
		spiro = new Spirograph(spiroInfo);
		spiro.setGearsVisible(false);
		spiro.setLargestRadius((float) Math.min(bounds.getWidth() / 2, bounds.getHeight() / 2)); 
	}
	
	public void generateSpiro(int iterations) {
		spiro.quickGenerate(iterations);
	}
	
	public void render(Graphics g) {
		spiro.render(g, centerPoint());
		g.setColor(Color.BLACK);
		((Graphics2D) g).draw(bounds);
	}
	
	public Point centerPoint() {
		return new Point((int) (bounds.getX() + bounds.getWidth() / 2), (int) (bounds.getY() + bounds.getHeight() / 2));
	}
	
	public Spirograph getSpiro() {
		return spiro;
	}
	
	public void setSpiro(SpiroInfo spiroInfo) {
		createSpiro(spiroInfo); 
	}
}
