package com.spirograph.ui;

import java.awt.Font;
import java.awt.Graphics;

import com.spirograph.evolve.SpiroEvolver;

public class InfoDisplay {

	private final Font infoFont = new Font("TimesRoman", Font.PLAIN, 30);
	
	private SpiroEvolver evolver;
	
	public InfoDisplay(SpiroEvolver evolver) {
		this.evolver = evolver;
	}
	
	public void render(Graphics g) {
		g.setFont(infoFont); 
		g.drawString("Generation: " + evolver.getGeneration(), 100, 50);
		g.drawString("Mutation % chance: " + evolver.getMutationChance() * 100, 100, 80);
	}
}
