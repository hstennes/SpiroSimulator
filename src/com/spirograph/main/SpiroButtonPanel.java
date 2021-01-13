package com.spirograph.main;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.spirograph.design.SpiroInfo;
import com.spirograph.evolve.RandomGenerator;
import com.spirograph.evolve.SpiroEvolver;
import com.spirograph.ui.ButtonPositioner;
import com.spirograph.ui.InfoDisplay;
import com.spirograph.ui.LabeledButtonContainer;
import com.spirograph.ui.MutationChanger;
import com.spirograph.ui.SpiroButton;

public class SpiroButtonPanel extends LabeledButtonContainer{

	private final int numButtons = 15;
	private final int buttonIterations = 3600;
	
	private SpiroButton[] spiroButtons = new SpiroButton[numButtons];
	private SpiroInfo[] gen1Buttons = new SpiroInfo[numButtons];
	private RandomGenerator spiroGenerator;
	private SpiroEvolver evolver;
	private ButtonPositioner positioner;
	private InfoDisplay infoDisplay;
	private MutationChanger mutationChanger;
	
	public SpiroButtonPanel(SpiroSimulator spiroSimulator) {
		super(spiroSimulator);
		evolver = new SpiroEvolver();
		positioner = new ButtonPositioner(spiroSimulator);
		infoDisplay = new InfoDisplay(evolver);
		mutationChanger = new MutationChanger(evolver);
		spiroGenerator = new RandomGenerator();
		addLabeledButtons();
		spiroButtons = positioner.createButtons(randomSpiros(spiroButtons.length));
		saveSpiroButtons();
	}
	
	public void changeButtons(SpiroInfo[] infos) {
		for(int i = 0; i < spiroButtons.length; i++) {
			spiroButtons[i].setSpiro(infos[i]);
			spiroButtons[i].generateSpiro(buttonIterations);
		}
	}
	
	private SpiroInfo[] randomSpiros(int numSpiros) {
		SpiroInfo[] spiros = new SpiroInfo[numSpiros];
		for(int i = 0; i < spiros.length; i++) {
			spiros[i] = spiroGenerator.geometricRadiusSpirograph();
		}
		return spiros;
	}
	
	public void render(Graphics g) {
		if(!spiroSimulator.getAnimationManager().isRunning()) {
			for(int i = 0; i < spiroButtons.length; i++) spiroButtons[i].render(g);
			infoDisplay.render(g);
			super.render(g);
		}
	}

	private void enterButtonPressed() {
		System.out.println("Enter button pressed");
		ArrayList<SpiroInfo> buttonInfos = new ArrayList<SpiroInfo>();
		for(int i = 0; i < spiroButtons.length; i++) {
			if(spiroButtons[i].isSelected()) { 
				buttonInfos.add(spiroButtons[i].getSpiro().getInfo());
				System.out.print(i + ", ");
			}
		}
		deselectAll();
		if(buttonInfos.size() > 0) {
			System.out.println("Creating new generation");
			SpiroInfo[] newButtons = evolver.newGeneration(buttonInfos, spiroButtons.length);
			System.out.println("Population size: " + newButtons.length);
			System.out.println("Creating buttons");
			changeButtons(newButtons);
		}
	}
	
	private void newButtonPressed() {
		System.out.println("New button pressed");
		evolver.reset();
		changeButtons(randomSpiros(numButtons));
		saveSpiroButtons();
	}
	
	private void resetButtonPressed() {
		changeButtons(gen1Buttons);
		evolver.reset();
		System.out.println("reset button pressed");
	}
	
	private void mutationButtonPressed() {
		mutationChanger.changeMutation();
	}
	
	private void animateButtonPressed() {
		boolean spiroSelected = false;
		boolean invalid = false;
		int selectedNumber = -1;
		for(int i = 0; i < spiroButtons.length; i++) {
			if(spiroButtons[i].isSelected()) {
				if(spiroSelected) {
					invalid = true;
					break;
				}
				else {
					spiroSelected = true;
					selectedNumber = i;
				}
			}
			if(i == spiroButtons.length - 1 && spiroSelected == false) invalid = true;
		}
		if(!invalid) spiroSimulator.getAnimationManager().startAnimation(spiroButtons[selectedNumber].getSpiro().getInfo(), buttonIterations);
		else JOptionPane.showMessageDialog(null, "This operation requires one selection");
	}
	
	@Override
	public void buttonPressed(String text) {
		if(text.equals("Enter")) enterButtonPressed();
		else if(text.equals("New")) newButtonPressed();
		else if(text.equals("Reset")) resetButtonPressed();
		else if(text.equals("Mutation")) mutationButtonPressed();
		else if(text.equals("Animate")) animateButtonPressed();
	}
	
	private void addLabeledButtons() {
		addLabeledButton(ButtonPositioner.enterButtonBounds, "Enter", buttonFont);
		addLabeledButton(ButtonPositioner.newButtonBounds, "New", buttonFont);
		addLabeledButton(ButtonPositioner.resetButtonBounds, "Reset", buttonFont);
		addLabeledButton(ButtonPositioner.changeMutationBounds, "Mutation", buttonFont);
		addLabeledButton(ButtonPositioner.animateBounds, "Animate", buttonFont);
	}
	
	public void saveSpiroButtons() {
		for(int i = 0; i < gen1Buttons.length; i++) gen1Buttons[i] = spiroButtons[i].getSpiro().getInfo();
	}
	
	private void deselectAll() {
		for(int i = 0; i < spiroButtons.length; i++) spiroButtons[i].deselect();
	}
	
	/**
	 * Returns a point that can be used to center the given text within a specified Rectangle.
	 * @param g A graphics object that is used to obtain the FontMetrics
	 * @param text The text to be centered
	 * @param rect The rectangle to center the text in
	 * @param font The font the text will be drawn in
	 * @return A point with the x and y coordinates that center the given text.
	 */
	public static Point centerText(Graphics g, String text, Rectangle rect, Font font) {
	    FontMetrics metrics = g.getFontMetrics(font);
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    return new Point(x, y);
	}
}
