package com.spirograph.design;

import java.awt.Graphics;
import java.awt.Point;

import com.spirograph.util.PointDouble;

public class Gear{

	private final int startingAngle = 270;
	
	protected Spirograph spiro;
	protected SpiroInfo info;
	protected Gear container;
	protected Gear inner;
	
	protected int gearNum;
	protected float orbitRadius;
	protected float radius;
	protected float speed;
	protected float outerAngle;
	protected float innerAngle;
	protected float innerAngleIncrement;
	protected boolean placementType;
	protected boolean visible;
	protected PointDouble center;
	
	/**
	 * Creates a new Gear object, which represents one of the circles rotating in a spirograph.  
	 * @param container The gear that this gear is either orbiting around or moving inside of (its parent gear)
	 * @param spiro The spirograph object that has a reference to the main gear linked to this gear
	 * @param gearNum This gear's index in the chain of gears (the main gear starts with gearNum 0)
	 */
	public Gear(Gear container, Spirograph spiro, int gearNum) {
		this.spiro = spiro;
		info = spiro.getInfo();
		this.container = container;
		this.gearNum = gearNum;
		visible = true;
		String gearInfo = info.getGearInfo(gearNum);
		radius = info.getRadius(gearInfo);
		speed = info.getSpeed(gearInfo);
		placementType = info.getPlacementType(gearInfo);	
		refresh();
		if(info.getInfo().size() - 1 > gearNum) addInnerGear();
	}
	
	private void addInnerGear() {
		if(gearNum + 2 == info.getInfo().size()) inner = new DrawingGear(this, spiro, gearNum + 1);
		else inner = new Gear(this, spiro, gearNum + 1);
	}

	/**
	 * Updates this gear's location inside of its container (outerAngle), updates is rotation (innerAngle), draws a point if this gear is a drawing gear,
	 * and calls tick() on the gear directly inside this one, if it exists.
	 * @param containerCenter The center point of the container gear, set to (0, 0) if this is the main gear
	 */
	public void tick(PointDouble containerCenter) {
		normalizeAngles();
		outerAngle += speed;
		if(container != null) innerAngle += innerAngleIncrement;
		calcCenter(containerCenter);
		if(inner != null) inner.tick(center);
	}
	
	/**
	 * Renders this gear as a blue circle and a radius line that indicates the value of innerRotation.  The circle will be centered at the specified screen
	 * coordinate, which can also be thought of as an offset from the gear's default relation to the point (0, 0)
	 * @param g The graphics object to be used in rendering the gear
	 * @param offset The offset of the gear from its default location
	 */
	public void render(Graphics g, Point offset) {
		if(visible) {
			double xOffset = offset.getX();
			double yOffset = offset.getY();
			g.drawOval((int) (center.getX() - radius + xOffset), (int) (center.getY() - radius + yOffset), (int) (2 * radius), (int) (2 * radius));
			double pointX = center.getX() + Math.cos(Math.toRadians(innerAngle)) * radius + xOffset;
			double pointY  = center.getY() + Math.sin(Math.toRadians(innerAngle)) * radius + yOffset;
			g.drawLine((int) (center.getX() + xOffset), (int) (center.getY() + yOffset), (int) pointX, (int) pointY);
			if(inner != null) inner.render(g, offset);
		}
	}
	
	private void calcCenter(PointDouble containerCenter) {
		if(gearNum != 0) {
			double x = containerCenter.getX() + Math.cos(Math.toRadians(outerAngle)) * orbitRadius;
			double y = containerCenter.getY() + Math.sin(Math.toRadians(outerAngle)) * orbitRadius;
			center.setLocation(x, y);
		} 
	}
	
	private void normalizeAngles() {
		innerAngle %= 360;
		outerAngle %= 360;
	}
	
	/**
	 * Recalculates all instance variables on this gear besides the values that are directly taken from a SpiroInfo object.  This method should be called
	 * when the gear is constructed or when any essential value is changed, such as the radius.
	 */
	protected void refresh() {
		outerAngle = startingAngle;
		innerAngle = startingAngle;
		center = new PointDouble();
		
		if(gearNum != 0) {
			innerAngleIncrement = (container.getRadius() * speed) / radius;
			if(placementType) {
				orbitRadius = container.getRadius() - radius;
				innerAngleIncrement *= -1;
			}
			else orbitRadius = container.getRadius() + radius;
		}
	}
	
	/**
	 * Returns the distance between the farthest point that will be generated on the resulting spriograph and the center of the spirograph.  This should
	 * only be called on the main gear by any class outside of Gear because the outside call of the method on an inner gear will give an inaccurate result.
	 * @return The largest radius of the spriograph, if called on the main (outermost) gear
	 */
	public float getSpirographRadius() {
		if(gearNum == 0) return radius + inner.getSpirographRadius();
		else if(!placementType) return 2 * radius + inner.getSpirographRadius();
		else {
			if(radius > container.getRadius()) return (2 * radius) - (2 * container.getRadius()) + inner.getSpirographRadius();
			else return inner.getSpirographRadius();
		}
	}
	
	/**
	 * Scales the radii of all gears in this spirograph by the specified scale factor. This method should only be called after the design ArrayList on the
	 * Spirograph object has been cleared because otherwise it will cause an ugly jump in the graph.  This method DOES NOT change values in the spirograph's
	 * SpiroInfo object, which means if any new gears are constructed they will still use the old radius values.
	 * @param scale The scale factor for the radii;
	 */
	public void scaleAllRadii(float scale) {
		radius *= scale;
		System.out.println("Radius #" + gearNum + ": " + (radius / scale) + " --> " + radius);
		refresh();
		if(inner != null) inner.scaleAllRadii(scale);
	}
	
	public void scaleAllSpeeds(float scale) {
		speed *= scale;
		if(inner != null) inner.scaleAllSpeeds(scale);
	}
	
	public void onDesignCleared() {
		outerAngle = startingAngle;
		innerAngle = startingAngle;
		if(inner != null) inner.onDesignCleared();
	}
	
	public float getRadius() {
		return radius;
	}
	
	public float getOuterAngle() {
		return outerAngle;
	}
	
	public boolean getVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void setAllVisible(boolean visible) {
		this.visible = visible;
		if(inner != null) inner.setAllVisible(visible);
	}
	
	public Gear getGear(int gearNum) {
		if(this.gearNum == gearNum) return this;
		else if(inner != null) return inner.getGear(gearNum);
		else return null;
	}
}
