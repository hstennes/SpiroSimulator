package com.spirograph.ui;

import javax.swing.JOptionPane;

import com.spirograph.evolve.SpiroEvolver;

public class MutationChanger {

	private SpiroEvolver evolver;
	
	public MutationChanger(SpiroEvolver evolver) {
		this.evolver = evolver;
	}
	
	public void changeMutation() {
		String input = JOptionPane.showInputDialog("Mutation % (between 0 and 100)?: ");
		float newMutationChance = 0;
		try {
			if(input != null) newMutationChance = Float.parseFloat(input);
		} catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Invalid mutation %. Must be a number between 0 and 100");
		}
		if(newMutationChance < 0 || newMutationChance > 100) JOptionPane.showMessageDialog(null, "Invalid mutation %. Must be a number between 0 and 100");
		evolver.setMutationChance(newMutationChance / 100);
	}	
}
