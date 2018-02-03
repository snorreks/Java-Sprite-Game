package com.dex.WeDemBoyz.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;

import com.dex.WeDemBoyz.util.Vector2i;

public class UIComponent {

	public Vector2i position;
	protected Vector2i offset, size;
	public Color color;
	protected UIPanel panel;
	public boolean active = true;

	public UIComponent(Vector2i position) {
		this.position = position;
		offset = new Vector2i();
	}

	public UIComponent(Vector2i position, Vector2i size) {
		this.position = position;
		this.size = size;
		offset = new Vector2i();
	}

	public UIComponent setColor(int color) {
		this.color = new Color(color);
		return this;
	}

	public void update() {

	}

	public void render(Graphics g) {
	}

	public Vector2i getAbsolutePosition() {
		return new Vector2i(position).add(offset);
	}

	void setOffset(Vector2i offset) { //kan kalle paa den i klassen og pakken, ikke andre steder,
		this.offset = offset;
	}

	void init(UIPanel panel) {
		this.panel = panel;
	}
}
