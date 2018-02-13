package com;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.entity.mob.Player;
import com.graphics.Screen;
import com.graphics.ui.UIManager;
import com.input.Keyboard;
import com.input.Mouse;
import com.level.Level;
import com.level.TileCoordinate;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private static int width = 300; //rendrer ikke hoyre
	public static int scale = 3;
	private static int bar = scale * 10;
	private static int height = (300) - bar;
	public static String title = "WeDemBoyz";

	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	private Menu menu;
	private Player player;
	private boolean running = false;
	private Sound sound;
	private static UIManager uiManager;

	public static boolean playerUIFirst = true;
	public static boolean menuUIFirst = true;
	public static boolean option = false;

	public enum STATE {
		MENU, GAME
	};

	public static STATE state;
	private Screen screen;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();


	public Game() {
		Dimension size = new Dimension(width * scale, (height + bar) * scale); //+ det du fjerner * scale
		setPreferredSize(size);
        screen = new Screen(width, height);
        sound = new Sound();
        state = STATE.MENU;
        menu = new Menu();
        uiManager = new UIManager();
        frame = new JFrame();
		key = new Keyboard();
		level = Level.spawn;
		TileCoordinate playerSpawn = new TileCoordinate(20, 20);
		player = new Player("Per", playerSpawn.x(), playerSpawn.y(), key);
		level.add(player);

		Mouse mouse = new Mouse();
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
	public static int getBar() {
		return bar;
	}
	public static int getScale(){return scale;}

	public static UIManager getUIManager() {
		return uiManager;
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


	public void update() {

		if (state == STATE.GAME) {
		    if(playerUIFirst){
                menu.menuUIRemove();
            }
			key.update();
			level.update();
		} else if (state == STATE.MENU) {
            if (menuUIFirst) {
                player.playerUIRemove();
                menu.optionUIRemove();
                menu.menuUI();
                menuUIFirst = false;
                //sound.playSound("res/sound/piano2.wav");
            }
            if (option){
                option=false;
                menu.optionUI();
            }

        }
        uiManager.update();

	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, width * scale, height * scale, null);
		screen.clear();
		if (state == STATE.GAME) {
			int xScroll = player.getX() - screen.width / 2;
			int yScroll = player.getY() - screen.height / 2;
			level.setScroll(xScroll, yScroll);
			level.render(screen);
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = screen.pixels[i];
			}
		} else if (state == STATE.MENU) {
		    if (menuUIFirst) menu.initPic();
			g.drawImage(menu.menuB, 0, 0, this);
			menu.render(g);
		}
		uiManager.render(g);
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
