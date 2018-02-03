package com.dex.WeDemBoyz.entity.mob;

import com.dex.WeDemBoyz.entity.Entity;
import com.dex.WeDemBoyz.entity.projectile.Arrow;
import com.dex.WeDemBoyz.entity.projectile.Projectile;
import com.dex.WeDemBoyz.graphics.Screen;

public abstract class Mob extends Entity {

	protected boolean moving = false;
	protected boolean walking = false;
	private int xOffset = 200;
	private int yOffset = 2;

	protected int health;

	protected enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	protected Direction dir;

	public void move(double xa, double ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		if (xa > 0) dir = Direction.RIGHT;
		if (xa < 0) dir = Direction.LEFT;
		if (ya > 0) dir = Direction.DOWN;
		if (ya < 0) dir = Direction.UP;

		while (xa != 0) {
			if (Math.abs(xa) > 1) { //samme som if( (xa - 1) > 0) alts� ha desimal
				if (!collision(abs(xa), ya)) {
					this.x += abs(xa);
				}
				xa -= abs(xa);
			} else {
				if (!collision(abs(xa), ya)) {
					this.x += xa;
				}
				xa = 0;
			}
		}
		while (ya != 0) {
			if (Math.abs(ya) > 1) { //samme som if( (xa - 1) > 0) alts� ha desimal
				if (!collision(xa, abs(ya))) {
					this.y += abs(ya);
				}
				ya -= abs(ya);
			} else {
				if (!collision(xa, abs(ya))) {
					this.y += ya;
				}
				ya = 0;
			}
		}
	}

	private int abs(double value) {
		if (value < 0) return -1;
		return 1;
	}

	protected void shoot(double x, double y, double dir) {
		//dir = Math.toDegrees(dir)
		Projectile p = new Arrow(x, y, dir);
		level.add(p);
	}

	public abstract void update();

	public abstract void render(Screen screen);

	private boolean collision(double xa, double ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = (x + xa - c % 2 * 20 + 4) / 16;
			double yt = (y + ya - (c / 2) * 2) / 16;
			int ix = (int) Math.ceil(xt); //gjoor om 4,6 om til 5. runder opp
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			if (level.getTile(ix, iy).solid()) solid = true;
		}
		return solid;
	}

	public void dmg(int amount) {
		health -= amount;
	}

	public void heal(int amount) {
		health += amount;

	}

}
