package com.zayed.physics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * The vector field of the charges
 * 
 * @author Zayed
 *
 */
public class VectorField {

	private Point.Double[][] field; // discrete field
	private int cols, rows; // number of vectors columns and vector rows
	private int gridSize = 25; // distance between 2 adjacent vectors

	/**
	 * Constructor
	 * 
	 * @param w -> Width of screen
	 * @param h -> Height of screen
	 */
	public VectorField(int w, int h) {
		cols = w / gridSize + 1;
		rows = h / gridSize + 1;

		field = new Point.Double[cols][rows];

		createField();
	}

	/**
	 * 
	 * @param w       -> Width of screen
	 * @param h       -> Height of screen
	 * @param density -> predefined grid size
	 */
	public VectorField(int w, int h, int density) {
		if (density <= 1)
			gridSize = 2;
		else if (density > 100)
			gridSize = 100;
		else
			gridSize = density;

		cols = w / gridSize + 1;
		rows = h / gridSize + 1;

		field = new Point.Double[cols][rows];

		createField();
	}

	/**
	 * @return the gridSize
	 */
	public int getGridSize() {
		return gridSize;
	}

	/**
	 * create all the vectors and assign roots
	 */
	private void createField() {
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				field[i][j] = new Point.Double();
			}
		}
	}

	/**
	 * setup the field according to the given charges
	 * 
	 * @param q -> charges to calculate net electric field with
	 */
	public void compute(Charge[] charges) {
		final long k = 100000000l;

		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				Point.Double vec = field[i][j];

				double electricFieldX = 0;
				double electricFieldY = 0;

				for (Charge charge : charges) {
					double distX = (i * gridSize - charge.getX());
					double distY = (j * gridSize - charge.getY());
					double dist = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2)); // distance equation (hypotenuse)

					double electricFieldMag = 0;

					if (dist != 0) {
						electricFieldMag = k * charge.getMagnitude() / Math.pow(dist, 2); // electric field equation

						// components
						electricFieldX += electricFieldMag * distX / dist; // Ex = E*cos(a) = E * dx/d
						electricFieldY += electricFieldMag * distY / dist; // Ey = E*sin(a) = E * dy/d
					}
				}

				vec.setLocation(electricFieldX, electricFieldY);
			}
		}
	}

	/**
	 * Draw the vector field
	 * 
	 * @param g2d -> tool to draw 2D
	 */
	public void draw(Graphics2D g2d) {
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				Point.Double vec = field[i][j];

				double magnitude = Math.sqrt(Math.pow(vec.x, 2) + Math.pow(vec.y, 2));

				if (magnitude == 0)
					return;

				int x = i * gridSize;
				int y = j * gridSize;

				// coordinates of the tip of the vector
				int x2 = (int) (x + vec.x / magnitude * gridSize / 2);
				int y2 = (int) (y + vec.y / magnitude * gridSize / 2);

				int t = gridSize / 10;
				g2d.setStroke(new BasicStroke(t));

				g2d.setColor(Color.WHITE);
				g2d.drawLine(x, y, x2, y2);

				if (t >= 1) {
					int w = t + 1; // width of circle at tip
					g2d.setColor(Color.GREEN);
					g2d.fillOval(x2 - w / 2, y2 - w / 2, w, w);
				}
			}
		}
	}
}
