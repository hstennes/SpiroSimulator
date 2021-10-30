package com.spirograph.design;

import java.awt.*;
import java.util.ArrayList;

import com.spirograph.main.SpiroSimulator;
import com.spirograph.util.PointDouble;
import com.spirograph.util.PolylineInfo;

public class Spirograph{

	private final int avgSpeedTarget = 5;
	
	private ArrayList<Point> design = new ArrayList<Point>();
	private ArrayList<Color> designColors = new ArrayList<Color>();
	private SpiroInfo info;
	private Gear mainGear;
	
	/**
	 * Constructs a new spirograph based on a SpiroInfo object
	 * @param info the SpiroInfo object that specifies the parameters for this spirograph
	 */
	public Spirograph(SpiroInfo info) {
		this.info = info;
		constructGears();
		cleanUpSpeeds();
		System.out.println(toString());
	}
	
	private void constructGears() {
		mainGear = new Gear(null, this, 0);
	}
	
	/**
	 * Performs one iteration on all gears in this spirograph
	 */
	public void tick() {
		mainGear.tick(new PointDouble(0, 0));
	}
	
	/**
	 * Similar to Spirograph.render(Graphics g, Point p), but centers the spirograph at the center point of the window, which is given by 
	 * SpiroSimulator.CENTER_POINT
	 * @param g The graphics object to render the spirograph
	 */
	public void render(Graphics g) {
		render(g, SpiroSimulator.CENTER_POINT);
	}
	
	/**
	 * Renders this spirograph's drawing as specified by the design ArrayList and renders all gears if the gears are set to be visible.  The design is 
	 * translated from the default center of (0, 0) to be centered at the specified point.
	 * @param g The graphics object to render the spirograph
	 * @param center The center to draw the design at
	 */
	public void render(Graphics g, Point center) { 
		g.setColor(Color.BLUE);
		mainGear.render(g, center);
		g.setColor(Color.RED);
		((Graphics2D) g).setStroke(new BasicStroke(1));
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		PolylineInfo polyline = createPolyline(center);
		for(int i = 0; i < polyline.getXPoints().length - 1; i++) {
			g.setColor(designColors.get(i));
			g.drawLine(polyline.getXPoints()[i], polyline.getYPoints()[i], polyline.getXPoints()[i + 1], polyline.getYPoints()[i + 1]);
		}
	}
	
	/**
	 * Clear all points in the design ArrayList, setting this spirograph to have no design when it is rendered.  This method also resets all gears
	 * to their default locations so that once the spirograph begins drawing again it will make the same design it did when it was first created.
	 */
	public void clearDesign() {
		design.clear();
		mainGear.onDesignCleared();
	}
	
	/**
	 * Performs the specified number of gear iterations and returns the contents of the design ArrayList when the iterations are complete
	 * @param iterations the number of iterations to perform
	 * @return the value of the design ArrayList after these iterations are performed
	 */
	public void quickGenerate(int iterations) {
		System.out.println("Running quickGenerate(" + iterations + ")...");
		clearDesign();
		for(int i = 0; i < iterations; i++) tick();
		System.out.println("Done\n");
	}
	
	/**
	 * Returns a PolylineInfo representation of the design ArrayList as it is stored in this Spirograph, which means that it is centered at (0, 0)
	 * @return A (0, 0) centered PolylineInfo representation of this Spirograph's design
	 */
	public PolylineInfo createPolyLine() {
		return createPolyline(new Point());
	}
	
	/**
	 * Returns a PolylineInfo representation of the design ArrayList with all points translated so that the resulting image will be centered at the 
	 * specified point.
	 * @param center The point to center the Polyline image around
	 * @return
	 */
	public PolylineInfo createPolyline(Point center) {
		int[] xPoints = new int[design.size()];
		int[] yPoints = new int[design.size()];
		Point current;
		for(int i = 0; i < design.size(); i++) {
			current = design.get(i);
			xPoints[i] = (int) (current.getX() + center.getX());
			yPoints[i] = (int) (current.getY() + center.getY());
		}
		return new PolylineInfo(xPoints, yPoints);
	}
	
	/**
	 * Adds the specified point to the design ArrayList so that it is rendered as part of this spirograph
	 * @param x the x-coordinate of the point to be added
	 * @param y the y-coordinate of the point to be added
	 */
	public void addPoint(double x, double y, Color c) {
		design.add(new Point((int) x, (int) y));
		designColors.add(c);
	}
	
	/**
	 * Adds the specified point to the design ArrayList so that it is rendered as part of this spirograph
	 * @param p the point to be added
	 */
	public void addPoint(PointDouble p, Color c) {
		design.add(new Point((int) p.getX(), (int) p.getY()));
		designColors.add(c);
	}
	
	/**
	 * Returns this spirograoh's SpiroInfo object, which specifies how the design is generated
	 * @return this spirograph's SpiroInfo object
	 */
	public SpiroInfo getInfo() {
		return info;
	}
	
	public Gear getGear(int gearNum) {
		return mainGear.getGear(gearNum);
	}
	
	/**
	 * Sets all gears to be either visible of invisible based on the parameter.  When a gear is visible, it is shown as a blue circle with a line through
	 * it that indicates is innerRotation
	 * @param visible The visibility state to set all gears to
	 */
	public void setGearsVisible(boolean visible) {
		mainGear.setAllVisible(visible);
	}
	
	/**
	 * Scales the speeds in this spirograph to (hopefully) optimal values by computing their average and scaling all speeds so that the average is 
	 * equal to the value specified by the final field avgSpeedTarget
	 */
	private void cleanUpSpeeds() {
		float[] speeds = new float[info.getInfo().size()];
		for(int i = 0; i < speeds.length; i++) speeds[i] = info.getSpeed(info.getGearInfo(i));
		float sum = 0;
		for(int i = 0; i < speeds.length; i++) {
			if(speeds[i] != 0) sum += speeds[i];
		}
		float avg = sum / (speeds.length - 1);
		float scale = avgSpeedTarget / avg;
		System.out.println("Average speed: " + avg);
		System.out.println("Scaling speeds by: " + scale);
		mainGear.scaleAllSpeeds(scale);
	}
	
	/**
	 * Scales all gears in this spirograph so that the distance between the center of the resulting design and the farthest point from the center (the
	 * "largest radius") is equal to the specified float
	 * @param r The largest radius to scale the spirograph to
	 */
	public void setLargestRadius(float r) {
		System.out.println("Target new radius: " + r);
		float currentRadius = mainGear.getSpirographRadius();
		System.out.println("Original radius: " + currentRadius);
		float scale = r / currentRadius;
		System.out.println("Scaling radii by: " + scale);
		mainGear.scaleAllRadii(scale);
		System.out.println("New radius: " + mainGear.getSpirographRadius());
	}
	
	@Override
	public String toString() {
		String object = super.toString() + ":\n";
		String drawing = "Currently holding " + design.size() + " Points";
		return object + info.toString() + drawing;
	}
}
