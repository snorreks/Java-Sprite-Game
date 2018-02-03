package com.dex.WeDemBoyz;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.dex.WeDemBoyz.entity.mob.Player;
import com.dex.WeDemBoyz.events.Event;
import com.dex.WeDemBoyz.events.EventListener;
import com.dex.WeDemBoyz.graphics.Screen;
import com.dex.WeDemBoyz.graphics.layers.Layer;
import com.dex.WeDemBoyz.graphics.ui.UIManager;
import com.dex.WeDemBoyz.input.Keyboard;
import com.dex.WeDemBoyz.input.Mouse;
import com.dex.WeDemBoyz.level.Level;
import com.dex.WeDemBoyz.level.TileCoordinate;

public class Game extends Canvas implements Runnable, EventListener {
	private static final long serialVersionUID = 1L;

	private static int width = 300 - 80; //rendrer ikke hoyre
	private static int height = 300 / 16 * 9;
	private static int scale = 3;
	public static String title = "WeDemBoyz";

	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	private Menu menu;
	private Player player;
	private boolean running = false;
	private static UIManager uiManager;
	private BufferedImage menuB = null;

	private enum STATE {
		MENU, GAME
	};

	private STATE state = STATE.GAME;
	private Screen screen;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	private List<Layer> layerStack = new ArrayList<Layer>();

	public Game() {
		Dimension size = new Dimension(width * scale + 80 * scale, height * scale); //+ det du fjerner * scale 
		setPreferredSize(size);

		menu = new Menu();

		screen = new Screen(width, height);
		uiManager = new UIManager();
		frame = new JFrame();
		key = new Keyboard();
		level = Level.spawn;
		addLayer(level);
		TileCoordinate playerSpawn = new TileCoordinate(40, 20);
		player = new Player("Per", playerSpawn.x(), playerSpawn.y(), key);
		level.add(player);

		Mouse mouse = new Mouse(this);
		addKeyListener(key);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	public static int getWindowWidth() {
		return width * scale;
	}

	public static int getWindowHeight() {
		return height * scale;
	}

	public static UIManager getUIManager() {
		return uiManager;
	}

	public void addLayer(Layer layer) {
		layerStack.add(layer);
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + "  |  " + updates + " ups, " + frames + "  fps");
				frames = 0;
				updates = 0;

			}
		}
		stop();
	}

	private void init() {
		try {
			menuB = ImageIO.read((getClass().getResource("/res/pictures/zelda.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Image didnt load");
		}

	}

	public void onEvent(Event event) {
		for (int i = layerStack.size() - 1; i >= 0; i--) {
			layerStack.get(i).onEvent(event);
		}
	}

	public void update() {
		if (state == STATE.GAME) {
			key.update();
			uiManager.update();

			//Update layers:

			for (int i = 0; i < layerStack.size(); i++) {
				layerStack.get(i).update();
			}
		}

	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, width * scale, height * scale, null);
		if (state == STATE.GAME) {
			screen.clear();
			int xScroll = player.getX() - screen.width / 2;
			int yScroll = player.getY() - screen.height / 2;
			level.setScroll(xScroll, yScroll);

			//Render layers:
			for (int i = 0; i < layerStack.size(); i++) {
				layerStack.get(i).render(screen);
			}

			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = screen.pixels[i];
			}
			uiManager.render(g);

		} else if (state == STATE.MENU) {
			g.drawImage(menuB, 0, 0, this);
			menu.render(g);
		}
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(Game.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();
	}
}
