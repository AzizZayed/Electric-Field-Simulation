package com.zayed.physics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * one charge
 * 
 * @author Zayed
 *
 */
public class Charge {

	private int x, y; // position
	private int size = 25; // size of charge

	private double magnitude; // physical magnitude of charge in coulombs

	/**
	 * Constructor
	 * 
	 * @param w -> Width of screen
	 * @param h -> Height of screen
	 */
	public Charge(int w, int h) {
		this.x = (int) (Math.random() * (w - size));
		this.y = (int) (Math.random() * (h - size));

		this.magnitude = Math.random() < 0.5d ? +1.0d : -1.0d;
	}

	/**
	 * @return x position offset to match mouse location
	 */
	public int getX() {
		return x + size / 2;
	}

	/**
	 * @return y position offset to match mouse location
	 */
	public int getY() {
		return y + size / 2;
	}

	/**
	 * @return magnitude
	 */
	public double getMagnitude() {
		return magnitude;
	}

	/**
	 * Checks if the mouse clicked the charge
	 * 
	 * @param mx -> x position of the mouse
	 * @param my -> y position of the mouse
	 * @return true if mouse clicked the charge, false otherwise
	 */
	public boolean clicked(int mx, int my) {
		int radius = size / 2;
		int xCenter = x + radius;
		int yCenter = y + radius;

		double dist = Math.sqrt(Math.pow(xCenter - mx, 2) + Math.pow(yCenter - my, 2));

		return dist < radius;
	}

	/**
	 * set the position according to the mouse, offset to match mouse position
	 * 
	 * @param mx -> x position of the mouse
	 * @param my -> y position of the mouse
	 */
	public void setPosition(int mx, int my) {
		x = mx - size / 2;
		y = my - size / 2;
	}

	/**
	 * Draw the charge
	 * 
	 * @param g -> tool to draw
	 */
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);

		int r = size / 2;
		g.fillOval(x, y, size, size);

		String sign;

		if (Math.signum(magnitude) < 0) {
			sign = "-";
			g.setColor(Color.RED);
		} else {
			sign = "+";
			g.setColor(Color.BLUE);
		}

		g.setFont(new Font("Calibri", Font.PLAIN, (int) (size * 1.7)));
		FontMetrics metrics = g.getFontMetrics();

		int strWidth = metrics.stringWidth(sign);
		int strHeight = metrics.getHeight();

		g.drawString(sign, x + r - strWidth / 2, y + (r - strHeight / 2) + metrics.getAscent());
	}
}
