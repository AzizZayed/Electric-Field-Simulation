package com.zayed.physics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

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
	 * @param x -> initial x position
	 * @param y -> initial y position
	 */
	public Charge(int w, int h, int x, int y) {
		setPosition(x, y);

		this.magnitude = Math.random() < 0.5 ? +1d : -1d;
	}

	/**
	 * Constructor
	 * 
	 * @param w -> Width of screen
	 * @param h -> Height of screen
	 */
	public Charge(int w, int h) {
		this.x = (int) (Math.random() * (w - size));
		this.y = (int) (Math.random() * (h - size));

		this.magnitude = Math.random() < 0.5 ? +1d : -1d;
	}

	/**
	 * getter for x
	 * 
	 * @return x position offset to match mouse location
	 */
	public int getX() {
		return x + size / 2;
	}

	/**
	 * getter for y
	 * 
	 * @return y position offset to match mouse location
	 */
	public int getY() {
		return y + size / 2;
	}

	/**
	 * getter for magnitude of charge
	 * 
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
		int r = size / 2;
		int cx = x + r;
		int cy = y + r;

		double dist = Math.sqrt(Math.pow(cx - mx, 2) + Math.pow(cy - my, 2));

		return dist < r;
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
	public void draw(Graphics g) {
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

		Font font = new Font("Calibri", Font.PLAIN, (int) (size * 1.7));
		FontMetrics metrics = g.getFontMetrics(font);

		int strWidth = metrics.stringWidth(sign);
		int strHeight = metrics.getHeight();

		g.setFont(font);
		g.drawString(sign, x + r - strWidth / 2, y + (r - strHeight / 2) + metrics.getAscent());

	}

}
