package com.spirograph.ui;

import java.awt.Rectangle;

import com.spirograph.design.SpiroInfo;
import com.spirograph.main.SpiroSimulator;

public class ButtonPositioner {
	
	private final int buttonSize = 200;
	private final int gridWidth = 5, gridHeight = 3;
	private final Rectangle spiroButtonSpace = new Rectangle(100, 100, SpiroSimulator.WIDTH - 80, SpiroSimulator.HEIGHT - 200);
	private final int buttonIterations = 3600;
	
	public static final Rectangle enterButtonBounds = new Rectangle((SpiroSimulator.WIDTH - 100) / 2 - 300, SpiroSimulator.HEIGHT - 100, 100, 50);
	public static final Rectangle newButtonBounds = new Rectangle((SpiroSimulator.WIDTH - 100) / 2 - 150, SpiroSimulator.HEIGHT - 100, 100, 50);
	public static final Rectangle resetButtonBounds = new Rectangle((SpiroSimulator.WIDTH - 100) / 2, SpiroSimulator.HEIGHT - 100, 100, 50);
	public static final Rectangle changeMutationBounds = new Rectangle((SpiroSimulator.WIDTH - 100) / 2 + 150, SpiroSimulator.HEIGHT - 100, 100, 50);
	public static final Rectangle animateBounds = new Rectangle((SpiroSimulator.WIDTH - 100) / 2 + 300, SpiroSimulator.HEIGHT - 100, 100, 50);
	
	private SpiroSimulator spiroSimulator;
	
	public ButtonPositioner(SpiroSimulator spiroSimulator) {
		this.spiroSimulator = spiroSimulator;
	}
	
	public SpiroButton[] createButtons(SpiroInfo[] spiros) {
		SpiroButton[] spiroButtons = new SpiroButton[gridWidth * gridHeight];
		int paddingX = (int) ((spiroButtonSpace.getWidth() - (gridWidth * buttonSize)) / gridWidth - 1);
		int paddingY = (int) ((spiroButtonSpace.getHeight() - (gridHeight * buttonSize)) / gridHeight - 1);
		for(int y = 0; y < gridHeight; y++) {
			int yCoord;
			if(y == 0) yCoord = (int) spiroButtonSpace.getY();
			else yCoord = (int) (spiroButtonSpace.getY() + y * (buttonSize + paddingY));
			for(int x = 0; x < gridWidth; x++) {
				int xCoord;
				if(x == 0) xCoord = (int) spiroButtonSpace.getX();
				else xCoord = (int) (spiroButtonSpace.getX() + x * (buttonSize + paddingX));
				Rectangle buttonBounds = new Rectangle(xCoord, yCoord, buttonSize, buttonSize);
				SpiroButton button = new SpiroButton(spiroSimulator, spiros[gridWidth * y + x], buttonIterations, buttonBounds);
				if(spiroButtons[gridWidth * y + x] != null) spiroSimulator.removeMouseListener(spiroButtons[gridWidth * y + x]);
				spiroButtons[gridWidth * y + x] = button;
				spiroSimulator.addMouseListener(button);
			}
		}
		return spiroButtons;
	}
}
