package com.util;

public class Vector2i { //Vector 2 coordinates aka x and y, i ist type of data aka int

	//node a waypoint we can go to
	//1 node = 1 tile
	public int x, y;

	public Vector2i() {
		set(0, 0);
	}

	public Vector2i(Vector2i vector) {
		set(vector.x, vector.y);
	}

	public Vector2i(int x, int y) {
		set(x, y);
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Vector2i add(Vector2i vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}

	public Vector2i add(int value) {
		this.x += value;
		this.y += value;
		return this;
	}

	public Vector2i subtract(Vector2i vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		return this;
	}

	public Vector2i setX(int x) {
		this.x = x;
		return this;
	}
	//grunnen er at man kan endre paa ny Vector2i mens man lager det
	// Vector2i postion = new Vector2i(50,50).setX(20); setter x 20 sistedet for 50

	public Vector2i setY(int y) {
		this.y = y;
		return this;
	}

	//manhatten distance sjekker ikke diagonalt
	public static double getDistance(Vector2i v0, Vector2i v1) {
		double x = v0.getX() - v1.getX();
		double y = v0.getY() - v1.getY();
		return Math.hypot(x, y);
	}

	public boolean equals(Object object) {
		if (!(object instanceof Vector2i)) return false;
		Vector2i vec = (Vector2i) object;
		if (vec.getX() == getX() && vec.getY() == getY()) return true;
		return false;
	}
}