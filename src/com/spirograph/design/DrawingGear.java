package com.spirograph.design;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.spirograph.util.PointDouble;

public class DrawingGear extends Gear{

	private float dotRadius;
	
	public DrawingGear(Gear container, Spirograph spiro, int gearNum) {
		super(container, spiro, gearNum);
		String gearInfo = spiro.getInfo().getGearInfo(gearNum);
		dotRadius = info.getDotRadius(gearInfo);
	}

	@Override
	public void tick(PointDouble containerCenter) {
		super.tick(containerCenter);
		placePoint();
	}
	
	/**
	 * Renders this gear as a blue circle and a radius line that indicates the value of innerRotation.  The circle will be centered at the specified screen
	 * coordinate, which can also be thought of as an offset from the gear's default relation to the point (0, 0)
	 * @param g The graphics object to be used in rendering the gear
	 * @param offset The offset of the gear from its default location
	 */
	@Override
	public void render(Graphics g, Point offset) {
		if(visible) {
			double xOffset = offset.getX();
			double yOffset = offset.getY();
			g.drawOval((int) (center.getX() - radius + xOffset), (int) (center.getY() - radius + yOffset), (int) (2 * radius), (int) (2 * radius));
			double pointX = center.getX() + Math.cos(Math.toRadians(innerAngle)) * dotRadius + xOffset;
			double pointY  = center.getY() + Math.sin(Math.toRadians(innerAngle)) * dotRadius + yOffset;
			g.drawLine((int) (center.getX() + xOffset), (int) (center.getY() + yOffset), (int) pointX, (int) pointY);
			if(inner != null) inner.render(g, offset);
		}
	}
	
	@Override
	public float getSpirographRadius() {
		if(!placementType) return radius + dotRadius;
		else {
			if(radius > container.getRadius()) return -2 * container.getRadius() + radius + dotRadius;
			return dotRadius - radius;
		}
	}
	
	@Override
	public void scaleAllRadii(float scale) { 
		radius *= scale;
		dotRadius *= scale;
		System.out.println("Radius #" + gearNum + ": " + (radius / scale) + " --> " + radius);
		refresh();
	}
	
	private void placePoint() {
		if(dotRadius == 0) spiro.addPoint(center, Color.RED);
		else {
			double x = center.getX() + Math.cos(Math.toRadians(innerAngle)) * dotRadius;
			double y = center.getY() + Math.sin(Math.toRadians(innerAngle)) * dotRadius;
			if(x != 0 || y != 0) spiro.addPoint(x, y, Color.RED);
		}
	}
	
	public float getDotRadius() {
		return dotRadius;
	}
}
