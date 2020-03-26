package com.zayed.physics;

import java.awt.Graphics;

/**
 * The vector field of the charges
 * 
 * @author Zayed
 *
 */
public class VectorField {

	private FieldVector[][] field; // discrete field
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

		field = new FieldVector[cols][rows];

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

		field = new FieldVector[cols][rows];

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
		int length = gridSize / 2;

		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				field[i][j] = new FieldVector(i * gridSize, j * gridSize, length);
			}
		}
	}

	/**
	 * setup the field according to the given charges
	 * 
	 * @param q -> charges to calculate net electric field with
	 */
	public void setField(Charge[] charges) {
		final long k = 100000000l;
		
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				FieldVector vec = field[i][j];

				double electricFieldX = 0;
				double electricFieldY = 0;

				for (Charge charge : charges) {
					double distX = (vec.getX() - charge.getX());
					double distY = (vec.getY() - charge.getY());
					double dist = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2)); // distance equation (hypotenuse)

					double electricFieldMag = 0;

					if (dist != 0) {
						electricFieldMag = k * charge.getMagnitude() / Math.pow(dist, 2); // electric field equation

						// components
						electricFieldX += electricFieldMag * distX / dist; // Ex = E*cos(a) = E * dx/d
						electricFieldY += electricFieldMag * distY / dist; // Ey = E*sin(a) = E * dy/d
					}
				}

				vec.setVector(electricFieldX, electricFieldY);
			}
		}

	}

	/**
	 * Display field magnitudes in console
	 */
	public void displayInConsole() {
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				FieldVector vec = field[j][i];
				System.out.print(vec.getMagnitude() + ", ");
			}
			System.out.println();
		}
	}

	/**
	 * Draw the vector field
	 * 
	 * @param g -> tool to draw
	 */
	public void draw(Graphics g) {
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				field[i][j].draw(g, gridSize);
			}
		}
	}

}
