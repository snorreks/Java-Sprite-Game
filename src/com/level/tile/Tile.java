package com.level.tile;

import com.graphics.Screen;
import com.graphics.Sprite;
import com.level.tile.basic.GrassTile;
import com.level.tile.basic.LampTile;
import com.level.tile.basic.RockTile;
import com.level.tile.basic.WallTile;
import com.level.tile.basic.WaterTile;
import com.level.tile.basic.voidTile;
import com.level.tile.prop.SolidProp;

public class Tile {

	public Sprite sprite;

	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile rock = new RockTile(Sprite.rock);
	public static Tile dirt = new GrassTile(Sprite.dirt);
	public static Tile plank = new WallTile(Sprite.plank);
	public static Tile stone = new RockTile(Sprite.stone);
	public static Tile brick = new RockTile(Sprite.brick);
	public static Tile rockB = new RockTile(Sprite.rockB);
	public static Tile gravel = new GrassTile(Sprite.gravel);
	public static Tile sand = new GrassTile(Sprite.sand);
	public static Tile lamp = new LampTile(Sprite.lamp);
	public static Tile water = new WaterTile(Sprite.water);

	//big boies
	public static Tile tree = new SolidProp(Sprite.tree);

	public static final int col_grass = 0xff00FF21;
	public static final int col_water = 0xff0026FF;
	public static final int col_rock = 0xff878787;
	public static final int col_dirt = 0xff7F3300;
	public static final int col_gravel = 0xffE2A600;
	public static final int col_stone = 0xff000000;
	public static final int col_plank = 0xffFF6A00;
	public static final int col_sand = 0xffFFD800;
	public static final int col_tree = 0xffFF006E;
	public static final int col_rockB = 0xffFF0000;

	public static Tile voidTile = new voidTile(Sprite.voidSprite);

	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}

	public void render(int x, int y, Screen screen) {

	}

	public boolean solid() {
		return false;
	}

	public boolean passthru() {
		return true;
	}
}
