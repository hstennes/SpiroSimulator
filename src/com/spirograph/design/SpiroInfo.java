package com.spirograph.design;

import java.util.ArrayList;

public class SpiroInfo {

	//Gear format: radius,dot radius,speed,placementType
	private ArrayList<String> info; 
	
	/**
	 * Constructs a new SpiroInfo object using the contents of the ArrayList parameter.  The ArrayList is interpreted to create the specifications for a 
	 * spirograph using these rules:
	 * Each String element in the ArrayList represents one gear, in order from outermost to innermost
	 * A String for a gear is read with the format "radius,dot radius,speed,placementType"
	 * Radius and speed are floats, dotRadius is an integer, and placement type is a boolean word ("true" for a nested gear, "false" for a orbiting gear)
	 * If a gear does not draw, its dot radius must be set to -1
	 * The first gear must have a speed of zero because it is fixed with all other gears moving relative to it
	 * @param info The ArrayList that specifies all information about how to generate a spirograph, according to the above rules
	 */
	public SpiroInfo(ArrayList<String> info) {
		this.info = info;
	}
	
	public ArrayList<String> getInfo(){
		return info;
	}
	
	public String getGearInfo(int gearNum) {
		return info.get(gearNum); 
	}
	
	public float getRadius(String getInfo) {
		return Float.parseFloat(getInfo.split(",")[0]);
	}
	
	public int getDotRadius(String getInfo) {
		return Integer.parseInt(getInfo.split(",")[1]);
	}
	
	public float getSpeed(String getInfo) {
		return Float.parseFloat(getInfo.split(",")[2]); 
	}
	
	public boolean getPlacementType(String getInfo) {
		return Boolean.parseBoolean(getInfo.split(",")[3]); 
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int i = 0; i < info.size(); i++) {
			s = s + "Gear #" + i + ": " + getGearInfo(i) + "\n";
		}
		return s;
	}
}
