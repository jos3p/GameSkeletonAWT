package jos3p.gaming.learning;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class GameSkeletonAWT extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static final String NAME = "Untitled Game";
	public static final int HEIGHT = 720;
	public static final int WIDTH = HEIGHT * 16 / 9;

	private boolean running = false;
	private Thread thread;

	public synchronized void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Game Loop "by Notch" (?) (seen at LudumDare22)
	 */
	public void run() {
		int renderedFrames = 0;
		int gameUpdates = 0;
		long lastTime = System.nanoTime(); // most precise
		// double amountOfTicks = 60.0;
		double ns = 1000000000.0 / 60.0;
		double delta = 0;
		long timer = System.currentTimeMillis();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				gameUpdates++;
				delta--;
			}

			try {
				Thread.sleep(3);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			render();
			renderedFrames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(gameUpdates + " ticks, " + renderedFrames + " frames.");

				gameUpdates = 0;
				renderedFrames = 0;
			}
		}
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT); // filling background

		/*
		 * Fill with magic here / call other render() methods to fill the view
		 */

		g.dispose();
		bs.show();
	}

	private void tick() {
		/*
		 * Add more magic here / call other tick() methods
		 */
	}

	public static void main(String[] args) {
		GameSkeletonAWT game = new GameSkeletonAWT();
		JFrame frame = new JFrame(GameSkeletonAWT.NAME);

		frame.getContentPane().add(game);
		frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(0, 0);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);

		game.start();
	}

}
