package com.dex.WeDemBoyz.level.tile.prop;

import com.dex.WeDemBoyz.graphics.Screen;
import com.dex.WeDemBoyz.graphics.Sprite;
import com.dex.WeDemBoyz.level.tile.Tile;

public class SolidProp extends Tile {

	public SolidProp(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderSprite(x, y, sprite, true); //this betyr klassen selv awwwaw
		//Do render screen stuff
	}

	public boolean solid() {
		return true;
	}
}

