package com.spirograph.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.spirograph.main.SpiroButtonPanel;

public class LabeledButton extends MouseAdapter{

	private boolean buttonDown;
	private boolean active = true;
	private Rectangle bounds;
	private String text;
	private Font font;
	private LabeledButtonContainer buttonPanel;
	
	public LabeledButton(Rectangle bounds, String text, Font font, LabeledButtonContainer buttonPanel) {
		this.bounds = bounds;
		this.text = text;
		this.font = font;
		this.buttonPanel = buttonPanel;
	}
	
	public void render(Graphics g) {
		if(active) {
			Point center = SpiroButtonPanel.centerText(g, text, bounds, font);
			g.setFont(font);
			g.drawString(text, (int) center.getX(), (int) center.getY()); 
			Graphics2D g2d = (Graphics2D) g;
			g.setColor(Color.BLACK);
			g2d.draw(bounds);
		}
	}
	
	public void mousePressed(MouseEvent e) {
		Point mouse = new Point(e.getX(), e.getY());
		if(bounds.contains(mouse) && active) buttonDown = true;
	}
	
	public void mouseReleased(MouseEvent e) {
		if(buttonDown && active) {
			buttonDown = false;
			buttonPressed();
		}
	}
	
	private void buttonPressed() {
		if(active) buttonPanel.buttonPressed(text);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
