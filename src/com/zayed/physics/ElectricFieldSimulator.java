package com.zayed.physics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

/**
 * Main class
 * 
 * @author Zayed
 *
 */
public class ElectricFieldSimulator extends Canvas implements Runnable {

	private static final long serialVersionUID = 8105594952433832877L;

	public final static int WIDTH = 850;
	public final static int HEIGHT = WIDTH; // 1:1 aspect ratio

	public boolean running = false; // true if the game is running
	private Thread gameThread; // thread where the game is updated AND rendered (single thread game)

	// Game properties...
	public Charge[] charges; // all the charges in the field
	public int pressedIndex = -1; // index of the clicked charge (used when dragging and all)

	public VectorField field; // the vector field illustrating the electric field

	/**
	 * Constructor
	 */
	public ElectricFieldSimulator() {

		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setMinimumSize(new Dimension(WIDTH, HEIGHT));

		initialize();

		/*
		 * Listen for keyboard actions
		 */
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();

				if (code == KeyEvent.VK_R) // 'R' for Restart
					initialize();
			}
		});

		/*
		 * replaces the update function, listens for mouse movement
		 */
		this.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();

				if (pressedIndex >= 0) {
					// move the charge according to the mouse
					charges[pressedIndex].setPosition(x, y);
					field.compute(charges);
				}
			}
		});

		/*
		 * Mouse event listener
		 */
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();

				int i = 0;
				boolean clicked = false;

				/*
				 * find pressed charge
				 */
				while (!clicked && i < charges.length) {
					if (charges[i].clicked(x, y)) {
						clicked = true;
						pressedIndex = i;
					}

					i++;
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				pressedIndex = -1; // unclick the charge
			}
		});

		this.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				field = new VectorField(WIDTH, HEIGHT, field.getGridSize() + e.getWheelRotation());
				field.compute(charges);
			}
		});

		this.setFocusable(true);
	}

	/**
	 * initialize all our game objects
	 */
	private void initialize() {
		int n = 5;
		charges = new Charge[n];

		for (int i = 0; i < n; i++)
			charges[i] = new Charge(WIDTH, HEIGHT);

		if (field == null)
			field = new VectorField(WIDTH, HEIGHT, 25);
		else
			field = new VectorField(WIDTH, HEIGHT, field.getGridSize());

		field.compute(charges);
	}

	@Override
	public void run() {
		// A simple game loop because a more complicated one is not necessary. I do have
		// a video on the ultimate game loop though. Check that out on my YouTube
		// channel. Link on my website.

		this.requestFocus();

		// game timer
		int frames = 0;
		long timer = System.currentTimeMillis();

		while (running) {

			render();
			frames++;

			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}

		stop();
	}

	/**
	 * start the thread and the game
	 */
	public synchronized void start() {
		gameThread = new Thread(this);
		/*
		 * since "this" is the "Game" Class you are in right now and it implements the
		 * Runnable Interface we can give it to a thread constructor. That thread with
		 * call it's "run" method which this class inherited (it's directly above)
		 */
		gameThread.start(); // start thread
		running = true;
	}

	/**
	 * Stop the thread and the game
	 */
	public void stop() {
		try {
			gameThread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * render the back and all the objects
	 */
	public void render() {
		// Initialize drawing tools first before drawing

		BufferStrategy buffer = this.getBufferStrategy(); // extract buffer so we can use them
		// a buffer is basically like a blank canvas we can draw on

		if (buffer == null) { // if it does not exist, we can't draw! So create it please
			this.createBufferStrategy(3); // Creating a Triple Buffer
			/*
			 * triple buffering basically means we have 3 different canvases this is used to
			 * improve performance but the drawbacks are the more buffers, the more memory
			 * needed so if you get like a memory error or something, put 2 instead of 3 or
			 * even 1...if you run a computer from 2002...
			 * 
			 * BufferStrategy:
			 * https://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferStrategy.html
			 */

			return;
		}

		Graphics g = buffer.getDrawGraphics(); // extract drawing tool from the buffers
		/*
		 * Graphics is class used to draw rectangles, ovals and all sorts of shapes and
		 * pictures so it's a tool used to draw on a buffer
		 * 
		 * Graphics: https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics.html
		 */

		// draw background
		drawBackground(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

		// draw Game Objects here
		if (field != null)
			field.draw(g2d);

		for (Charge charge : charges) {
			charge.draw(g2d);
		}

		// actually draw
		g.dispose();
		buffer.show();

	}

	/**
	 * Draw background
	 * 
	 * @param g Graphics used to draw on the Canvas
	 */
	private void drawBackground(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}

	/**
	 * start of the program
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Electric Field Simulator2");
		ElectricFieldSimulator simulator = new ElectricFieldSimulator();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(simulator);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		simulator.start();
	}
}