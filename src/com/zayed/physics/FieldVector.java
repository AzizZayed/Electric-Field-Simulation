package com.zayed.physics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * The vector inside the vector field
 * 
 * @author Zayed
 *
 */
public class FieldVector {

	private int x, y; // root position, fixed for object lifetime
	private double cx, cy; // vector component magnitudes
	private double magnitude; // vector magnitude
	private int vectorLength = 15; // physical length of vector when drawn, default 15

	/**
	 * Constructor
	 * 
	 * @param x -> root x
	 * @param y -> root y
	 * @param l -> desired physical length of vector when drawn
	 */
	public FieldVector(int x, int y, int l) {
		this.x = x;
		this.y = y;
		vectorLength = l;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the magnitude
	 */
	public double getMagnitude() {
		return magnitude;
	}

	/**
	 * Setup the vector, set the components and magnitude
	 * 
	 * @param fx -> x component of electric field
	 * @param fy -> y component of electric field
	 */
	public void setVector(double fx, double fy) {
		cx = fx;
		cy = fy;
		magnitude = Math.sqrt(Math.pow(fx, 2) + Math.pow(fy, 2));
	}

	/**
	 * Draw the vector and the tip to show direction
	 * 
	 * @param g -> tool to draw
	 */
	public void draw(Graphics g, int size) {

		if (magnitude == 0)
			return;

		// coordinates of the tip of the vector
		int x2 = (int) (x + cx / magnitude * vectorLength);
		int y2 = (int) (y + cy / magnitude * vectorLength);

		int t = size / 10;

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(t));

		g.setColor(Color.WHITE);
		g.drawLine(x, y, x2, y2);

		if (t >= 1) {
			int w = t + 1; // width of circle at tip
			g.setColor(Color.GREEN);
			g.fillOval(x2 - w / 2, y2 - w / 2, w, w);
		}

	}

}
