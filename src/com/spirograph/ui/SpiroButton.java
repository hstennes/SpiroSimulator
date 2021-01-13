package com.spirograph.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;

import com.spirograph.design.SpiroInfo;
import com.spirograph.main.SpiroSimulator;

public class SpiroButton extends SpiroDisplay {
	
	private final Color hoveringColor = new Color(252, 255, 171);
	private final Color buttonDownColor = new Color(247, 255, 28);
	private final int selectedBorderWidth = 6;
	private boolean selected = false;
	private boolean buttonDown;
	
	private SpiroSimulator spiroSimulator;

	public SpiroButton(SpiroSimulator spiroSimulator, SpiroInfo spiroInfo, int iterations, Rectangle bounds) {
		super(spiroInfo, bounds);
		this.spiroSimulator = spiroSimulator;
		generateSpiro(iterations);
	}
	
	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Point mouse = getMouseLocation();
		if(buttonDown) g2d.setColor(buttonDownColor);
		else if(bounds.contains(mouse)) g2d.setColor(hoveringColor);
		else g2d.setColor(Color.WHITE);
		g2d.fill(bounds);
		if(selected) {
			Stroke prevStroke = g2d.getStroke();
			g2d.setStroke(new BasicStroke(selectedBorderWidth));
			g2d.setColor(Color.BLUE);
			g2d.draw(bounds);
			g2d.setStroke(prevStroke);
		}
		super.render(g);
	}
	
	private Point getMouseLocation() {
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		mouse.translate((int) -spiroSimulator.getLocationOnScreen().getX(), (int) -spiroSimulator.getLocationOnScreen().getY());
		return mouse;
	}
	
	private void buttonPressed() {
		System.out.println(toString() + " was pressed");
		selected = !selected;
	}
	
	public void mousePressed(MouseEvent e){
		Point mouse = new Point(e.getX(), e.getY());
		if(bounds.contains(mouse)) buttonDown = true;
	}
	
	public void mouseReleased(MouseEvent e) {
		if(buttonDown) {
			buttonDown = false;
			buttonPressed();
		}
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void deselect() {
		selected = false;
	}
}
