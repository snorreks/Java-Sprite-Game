package com.dex.WeDemBoyz.entity.mob;

import com.dex.WeDemBoyz.graphics.AnimatedSprite;
import com.dex.WeDemBoyz.graphics.Screen;
import com.dex.WeDemBoyz.graphics.Sprite;
import com.dex.WeDemBoyz.graphics.SpriteSheet;

public class Dummy extends Mob {

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.skeleman_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.skeleman_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.skeleman_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.skeleman_right, 32, 32, 3);

	private AnimatedSprite animSprite = down;

	private int time = 0;
	private int xa = 0;
	private int ya = 0;

	public Dummy(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.skeleman;
	}

	public void update() {
		time++; // time % 60 == 0  = 1 s
		if (time % (random.nextInt(50) + 30) == 0) {
			xa = random.nextInt(3) - 1;
			ya = random.nextInt(3) - 1;
			if (random.nextInt(5) == 0) {
				xa = 0;
				ya = 0;
			}
		}
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
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) (x - 16), (int) (y - 16), sprite, 0);
	}

}
