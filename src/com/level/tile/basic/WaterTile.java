package com.level.tile.basic;

import com.graphics.Screen;
import com.graphics.Sprite;
import com.level.tile.Tile;

public class WaterTile extends Tile {

	public WaterTile(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this); //this betyr klassen selv awwwaw
		//Do render screen stuff
	}
	
	public boolean solid() {
		return true;	

}
}