package com.dex.WeDemBoyz.entity.particle;

import com.dex.WeDemBoyz.entity.Entity;
import com.dex.WeDemBoyz.graphics.Screen;
import com.dex.WeDemBoyz.graphics.Sprite;

public class Particle extends Entity {

	private Sprite sprite;
	private int life;
	private int time = 0;
	protected double xx, yy, zz;
	protected double xa, ya, za;

	public Particle(int x, int y, int life) {
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + (random.nextInt(20) - 10); //endrer tiden shit forsvinner
		sprite = Sprite.particle_normal;

		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 2.0; //bakken

	}

	public void update() {
		time++;
		if (time >= 10000) time = 0; // Integer.MAX_VALUE - 1
		if (time > life) remove();
		za -= 0.1;
		if (zz < 0) {
			zz = 0; //ikke gaa under Z-aksen, bakken
			za *= -0.55; //bounce mellom 0 og 1
			xa *= 0.4; //skli i x-aksen
			ya *= 0.4; //skli i y-aksen
		}
		move((xx + xa), (yy + ya) + (zz + za));

	}

	private void move(double x, double y) {
		if (collision(x, y)) {
			this.xa *= -0.5;
			this.ya *= -0.5;
			this.za *= -0.5;

		}
		this.xx += xa;
		this.yy += ya;
		this.zz += za;
	}

	public boolean collision(double x, double y) {
		boolean passtru = false;
		for (int c = 0; c < 4; c++) {
			double xt = (x - c % 2 * 16) / 16;
			double yt = (y - c / 2 * 16) / 16;
			int ix = (int) Math.ceil(xt); //gj�r om 4,6 om til 5. runder opp
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			if (!level.getTile(ix, iy).passthru()) passtru = true;
		}
		return passtru;
	}

	public void render(Screen screen) {
		screen.renderSprite((int) xx - 1, (int) yy - (int) zz - 1, sprite, true);
	}
}
