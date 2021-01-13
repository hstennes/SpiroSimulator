package com.spirograph.ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.spirograph.main.SpiroSimulator;

public abstract class LabeledButtonContainer {

	protected ArrayList<LabeledButton> labeledButtons = new ArrayList<LabeledButton>();
	protected SpiroSimulator spiroSimulator;
	protected final Font buttonFont = new Font("TimesRoman", Font.PLAIN, 25);
	
	public LabeledButtonContainer(SpiroSimulator spiroSimulator) {
		this.spiroSimulator = spiroSimulator;
	}
	
	protected void addLabeledButton(Rectangle bounds, String name, Font font) {
		LabeledButton button = new LabeledButton(bounds, name, font, this);
		labeledButtons.add(button);
		spiroSimulator.addMouseListener(button);
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < labeledButtons.size(); i++) labeledButtons.get(i).render(g);
	}
	
	public void setActive(boolean active) {
		for(int i = 0; i < labeledButtons.size(); i++) labeledButtons.get(i).setActive(active);
	}
	
	public abstract void buttonPressed(String name);
}
