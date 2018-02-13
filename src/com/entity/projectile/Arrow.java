package com.entity.projectile;

import com.entity.spawner.ParticleSpawner;
import com.graphics.Screen;
import com.graphics.Sprite;

public class Arrow extends Projectile {

	public static final int FIRE_RATE = 10;
	private int xEdge = 5;
	private int yEdge = 5;
	//private int time = 0;
	private int size = 12;

	public Arrow(double x, double y, double dir) {
		super(x, y, dir);
		range = 200;
		speed = 2;
		damage = 20;
		sprite = Sprite.rotate(sprite.arrowStraight, angle);
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void update() {
		//time++;
		move();
		if (level.playerProjectileHit((int) (x + nx), (int) (y + ny), this)) {
			level.add(new ParticleSpawner((int) x, (int) y, 20, 20, level));
			level.getClientPlayer().dmg(1);
			remove();
		}
		if (!level.tileCollision((int) (x + nx), (int) (y + ny), size, xEdge, yEdge)) {
			level.add(new ParticleSpawner((int) x, (int) y, 20, 20, level));
			remove();
		}

		//if (time % 10 == 0) sprite = Sprite.rotate(sprite, Math.PI / 4);
		//Sprite.rotate(sprite,90); oppdaterer vinkelen, hvis det er liksom targeting arrow som folger spilleren
		if (getDistance() > range) remove();
	}

	public void render(Screen screen) {
		screen.renderProjectile((int) x - xEdge, (int) y - yEdge, this);
	}

	protected void move() {
		x += nx;
		y += ny;
	}

	private double getDistance() {
		double dist = 0;
		dist = Math.hypot(xOrgin - x, yOrgin - y);
		return dist;
	}
}
