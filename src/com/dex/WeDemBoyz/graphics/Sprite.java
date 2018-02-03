package com.dex.WeDemBoyz.graphics;

public class Sprite {

	public final int SIZE;
	private int x, y;
	private int width, height;

	public int[] pixels;
	protected SpriteSheet sheet;

	//Sprites basic:

	public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite rock = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite dirt = new Sprite(16, 2, 0, SpriteSheet.tiles);
	public static Sprite plank = new Sprite(16, 4, 0, SpriteSheet.tiles);
	public static Sprite stone = new Sprite(16, 5, 0, SpriteSheet.tiles);
	public static Sprite brick = new Sprite(16, 7, 0, SpriteSheet.tiles);
	public static Sprite rockB = new Sprite(16, 0, 1, SpriteSheet.tiles);
	public static Sprite gravel = new Sprite(16, 3, 1, SpriteSheet.tiles);
	public static Sprite sand = new Sprite(16, 2, 1, SpriteSheet.tiles);
	public static Sprite lamp = new Sprite(16, 7, 1, SpriteSheet.tiles);
	public static Sprite water = new Sprite(16, 0, 2, SpriteSheet.tiles);

	//big boies
	public static Sprite tree = new Sprite(32, 4, 0, SpriteSheet.tiles);

	public static Sprite voidSprite = new Sprite(16, 0x0000C4);

	//projeciles

	public static Sprite arrowBent = new Sprite(16, 0, 0, SpriteSheet.arrows);
	public static Sprite arrowStraight = new Sprite(16, 1, 0, SpriteSheet.arrows);
	public static Sprite arrowSide = new Sprite(16, 2, 0, SpriteSheet.arrows);

	//Particles:

	public static Sprite particle_normal = new Sprite(3, 0xffAAAAAA);

	//player Sprite:
	public static Sprite playerfs = new Sprite(32, 0, 0, SpriteSheet.player);
	public static Sprite skeleman = new Sprite(32, 0, 0, SpriteSheet.skeleman);
	public static Sprite luna = new Sprite(32, 0, 0, SpriteSheet.luna);
	public static Sprite jojo = new Sprite(32, 0, 0, SpriteSheet.jojo);

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}

	protected Sprite(SpriteSheet sheet, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		this.sheet = sheet;

	}

	public Sprite(int width, int height, int color) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		setColor(color);

	}

	public Sprite(int size, int color) {
		this.width = size;
		this.height = size;
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		setColor(color);
	}

	public static Sprite rotate(Sprite sprite, double angle) {
		return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height);
	}

	private static int[] rotate(int[] pixels, int width, int height, double angle) {
		int[] result = new int[width * height];

		//vektor hvor den skal gaa, hvor mye man skal flytt i x og y
		double nx_x = rot_x(-angle, 1.0, 0.0);
		double nx_y = rot_y(-angle, 1.0, 0.0);
		double ny_x = rot_x(-angle, 0.0, 1.0);
		double ny_y = rot_y(-angle, 0.0, 1.0);
		//Startveriden til pixelen
		double x0 = rot_x(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
		double y0 = rot_y(-angle, -width / 2.0, -height / 2.0) + height / 2.0;

		for (int y = 0; y < height; y++) {
			double x1 = x0;
			double y1 = y0;
			for (int x = 0; x < width; x++) {
				int xx = (int) x1;
				int yy = (int) y1;
				int col = 0;
				//kopierer pixelene fra orginale sprite, over til ny copi der plasseringen er endret
				if (xx < 0 || xx >= width || yy < 0 || yy >= height) col = 0xffA098A8;
				else col = pixels[xx + yy * width];
				result[x + y * width] = col;
				x1 += nx_x;
				y1 += nx_y;
			}
			x0 += ny_x;
			y0 += ny_y;
		}

		return result;
	}

	private static double rot_x(double angle, double x, double y) { //i enhets sirkelen vil man g� motsatt vei en logisk, s� vi tar -angel
		double cos = Math.cos(angle - Math.PI / 2);
		double sin = Math.sin(angle - Math.PI / 2);
		return (x * cos + y * -sin);
	}

	private static double rot_y(double angle, double x, double y) {
		double cos = Math.cos(angle - Math.PI / 2);
		double sin = Math.sin(angle - Math.PI / 2);
		return (x * sin + y * cos);
	}

	public static Sprite[] split(SpriteSheet sheet) {
		int amount = (sheet.getWidth() * sheet.getHeight()) / (sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT);
		Sprite[] sprites = new Sprite[amount];
		int[] pixels = new int[sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT];
		int current = 0;

		for (int yp = 0; yp < sheet.getHeight() / sheet.SPRITE_HEIGHT; yp++) {
			for (int xp = 0; xp < sheet.getWidth() / sheet.SPRITE_WIDTH; xp++) {
				//finner kordniatene
				for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
					for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
						//alle pixelene for � rende current kordinat
						int xo = x + xp * sheet.SPRITE_WIDTH;
						int yo = y + yp * sheet.SPRITE_HEIGHT;
						pixels[x + y * sheet.SPRITE_WIDTH] = sheet.getPixels()[xo + yo * sheet.getWidth()];
					}
				}
				sprites[current++] = new Sprite(pixels, sheet.SPRITE_WIDTH, sheet.SPRITE_HEIGHT);
			}
		}
		return sprites;
	}

	public Sprite(int[] pixels, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.pixels = new int[pixels.length];

		//	System.arraycopy(pixels, 0, this.pixels, 0, pixels.length); helt lik dette.
		for (int i = 0; i < pixels.length; i++) {
			this.pixels[i] = pixels[i];

		}
	}

	private void setColor(int color) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = color;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void load() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SPRITE_WIDTH];
			}
		}
	}

}
