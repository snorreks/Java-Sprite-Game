package com.dex.WeDemBoyz.level.tile.basic;

import com.dex.WeDemBoyz.graphics.Screen;
import com.dex.WeDemBoyz.graphics.Sprite;
import com.dex.WeDemBoyz.level.tile.Tile;

public class WallTile extends Tile {

	public WallTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this); //this betyr klassen selv awwwaw
		//Do render screen stuff
	}

	public boolean solid() {
		return false;
	}

	public boolean passthru() {
		return false;
	}
}