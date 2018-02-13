package com.entity.projectile;

import java.util.Random;

import com.entity.Entity;
import com.graphics.Sprite;

public abstract class Projectile extends Entity {

	protected final double xOrgin, yOrgin;
	protected double angle;
	protected Sprite sprite;
	protected double x, y;
	protected double distance;
	protected double nx, ny;
	protected double speed, range, damage, s, spread;

	//protected double ul,u,ur,r,dr,d,dl,l; //ul = upleft, u = up, ur = upright..

	protected final Random random = new Random();

	public Projectile(double x, double y, double dir) {
		xOrgin = x;
		yOrgin = y;
		angle = dir;
		this.x = x;
		this.y = y;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public int getSpriteSize() {
		return sprite.SIZE;

	}

	protected void move() {
	}

}
