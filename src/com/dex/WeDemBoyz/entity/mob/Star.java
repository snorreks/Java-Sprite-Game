package com.dex.WeDemBoyz.entity.mob;

import java.util.List;

import com.dex.WeDemBoyz.graphics.AnimatedSprite;
import com.dex.WeDemBoyz.graphics.Screen;
import com.dex.WeDemBoyz.graphics.SpriteSheet;
import com.dex.WeDemBoyz.level.Node;
import com.dex.WeDemBoyz.util.Vector2i;

public class Star extends Mob {

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.jojo_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.jojo_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.jojo_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.jojo_right, 32, 32, 3);
	private AnimatedSprite animSprite = down;

	private double xa = 0;
	private double ya = 0;
	private double speed = 1;
	private List<Node> path;
	private int time = 0;

	public Star(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = down;
	}

	private void move() {
		xa = 0;
		ya = 0;
		int px = level.getPlayerAt(0).getX();
		int py = level.getPlayerAt(0).getY();
		Vector2i start = new Vector2i(getX() >> 4, getY() >> 4);
		Vector2i destination = new Vector2i(px >> 4, py >> 4);
		if (time % 4 == 0) path = level.findPath(start, destination);
		if (path != null) {
			if (path.size() > 0) {
				Vector2i vec = path.get(path.size() - 1).tile; //grunnen til dette er at 1 node i path.tabellen er den nermeste til targeten ikke selve moben
				if (x < vec.getX() << 4) xa += speed;
				if (x > vec.getX() << 4) xa -= speed;
				if (y < vec.getY() << 4) ya += speed;
				if (y > vec.getY() << 4) ya -= speed;

			}
		} //kan  ha en else, hvis de brott lokker d�ra
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}

	}

	public void update() {
		time++;
		move();
		if (walking) animSprite.update();
		else animSprite.setFrame(0);
		if (ya < 0) {
			animSprite = up;
			dir = Direction.UP;
		} else if (ya > 0) {
			animSprite = down;
			dir = Direction.DOWN;

		}
		if (xa < 0) {
			animSprite = left;
			dir = Direction.LEFT;

		} else if (xa > 0) {
			animSprite = right;
			dir = Direction.RIGHT;
		}

	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) (x - 16), (int) (y - 16), this);
	}

}
