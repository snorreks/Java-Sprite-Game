package com.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	public final int SIZE;
	public final int SPRITE_WIDTH, SPRITE_HEIGHT;
	private int width, height;
	public int[] pixels;
	private Sprite[] sprites;

	public static SpriteSheet tiles = new SpriteSheet("/textures/tiles/spritesheet.png", 256);
	public static SpriteSheet projectiles = new SpriteSheet("/textures/projectiles/p.png", 48);

	//player:
	public static SpriteSheet player = new SpriteSheet("/textures/character/player.png", 96, 128);
	public static SpriteSheet player_down = new SpriteSheet(player, 0, 0, 3, 1, 32);
	public static SpriteSheet player_up = new SpriteSheet(player, 0, 3, 3, 1, 32);
	public static SpriteSheet player_right = new SpriteSheet(player, 0, 2, 3, 1, 32);
	public static SpriteSheet player_left = new SpriteSheet(player, 0, 1, 3, 1, 32);
	//skeleman:
	public static SpriteSheet skeleman = new SpriteSheet("/textures/character/skeleman.png", 96, 128);
	public static SpriteSheet skeleman_down = new SpriteSheet(skeleman, 0, 0, 3, 1, 32);
	public static SpriteSheet skeleman_up = new SpriteSheet(skeleman, 0, 3, 3, 1, 32);
	public static SpriteSheet skeleman_right = new SpriteSheet(skeleman, 0, 2, 3, 1, 32);
	public static SpriteSheet skeleman_left = new SpriteSheet(skeleman, 0, 1, 3, 1, 32);
	//Luna:
	public static SpriteSheet luna = new SpriteSheet("/textures/character/luna.png", 96, 128);
	public static SpriteSheet luna_down = new SpriteSheet(luna, 0, 0, 3, 1, 32);
	public static SpriteSheet luna_up = new SpriteSheet(luna, 0, 3, 3, 1, 32);
	public static SpriteSheet luna_right = new SpriteSheet(luna, 0, 2, 3, 1, 32);
	public static SpriteSheet luna_left = new SpriteSheet(luna, 0, 1, 3, 1, 32);

	//JOJO:
	public static SpriteSheet jojo = new SpriteSheet("/textures/character/jojo.png", 96, 128);
	public static SpriteSheet jojo_down = new SpriteSheet(jojo, 0, 0, 3, 1, 32);
	public static SpriteSheet jojo_up = new SpriteSheet(jojo, 0, 3, 3, 1, 32);
	public static SpriteSheet jojo_right = new SpriteSheet(jojo, 0, 2, 3, 1, 32);
	public static SpriteSheet jojo_left = new SpriteSheet(jojo, 0, 1, 3, 1, 32);

	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {
		path = sheet.path;
		int xx = x * spriteSize;
		int yy = y * spriteSize;
		int w = width * spriteSize;
		int h = height * spriteSize;
		SPRITE_WIDTH = w;
		SPRITE_HEIGHT = h;
		SIZE = (width == height) ? width : -1;
		pixels = new int[w * h];
		for (int y0 = 0; y0 < h; y0++) {
			int yp = yy + y0;
			for (int x0 = 0; x0 < w; x0++) {
				int xp = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.SPRITE_WIDTH];
			}
		}
		int frame = 0;
		sprites = new Sprite[width * height];
		for (int ya = 0; ya < height; ya++) {
			for (int xa = 0; xa < width; xa++) {
				int[] spritePixels = new int[spriteSize * spriteSize];
				for (int y0 = 0; y0 < spriteSize; y0++) {
					for (int x0 = 0; x0 < spriteSize; x0++) {
						spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * SPRITE_WIDTH];
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
				sprites[frame++] = sprite;
			}
		}
	}

	public SpriteSheet(String path, int size) {
		this.path = path;
		SIZE = size;
		SPRITE_WIDTH = size;
		SPRITE_HEIGHT = size;
		pixels = new int[SIZE * SIZE];
		load();
	}

	public SpriteSheet(String path, int width, int height) {
		this.path = path;
		SIZE = -1;
		SPRITE_WIDTH = width;
		SPRITE_HEIGHT = height;
		pixels = new int[SPRITE_WIDTH * SPRITE_HEIGHT];
		load();
	}

	public Sprite[] getSprites() {
		return sprites;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void load() {
		try {
			System.out.print("Trying to load:" + path + "...");
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			System.out.println("Nigga we made it");
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Failed");
		}
	}

	public int[] getPixels() {
		return pixels;
	}

}
