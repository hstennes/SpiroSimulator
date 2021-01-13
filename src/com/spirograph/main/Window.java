package com.spirograph.main;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas{

	private static final long serialVersionUID = -1478604005915452565L;
	
	private JFrame frame;
	private int width, height;
	private String title;
	
	public Window(int width, int height, String title){
		this.width = width;
		this.height = height;
		this.title = title;
	}	
	
	/**
	 * Opens a new JFrame window with the parameters specified when this Window object was constructed
	 * @param spiroSimulator This SpiroSimulator object to add to the JFrame
	 */
	public void open(SpiroSimulator spiroSimulator) {
		frame = new JFrame();
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setTitle(title);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.add(spiroSimulator);
		frame.setVisible(true);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
		frame.setTitle(title);
	}
}
