package com.dex.WeDemBoyz.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.dex.WeDemBoyz.events.EventListener;
import com.dex.WeDemBoyz.events.types.MouseMovedEvent;
import com.dex.WeDemBoyz.events.types.MousePressedEvent;
import com.dex.WeDemBoyz.events.types.MouseReleasedEvent;

public class Mouse implements MouseListener, MouseMotionListener {

	private static int mouseX = -1; //x-akse
	private static int mouseY = -1; //y-akse
	private static int mouseB = -1; //button

	public EventListener eventListener;

	public Mouse(EventListener listener) {
		eventListener = listener;
	}

	public static int getX() {
		return mouseX;
	}

	public static int getY() {
		return mouseY;
	}

	public static int getButton() {
		return mouseB;
	}

	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();

		MouseMovedEvent event = new MouseMovedEvent(e.getX(), e.getY(), true);
		eventListener.onEvent(event);
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();

		MouseMovedEvent event = new MouseMovedEvent(e.getX(), e.getY(), false);
		eventListener.onEvent(event);

	}

	public void mouseClicked(MouseEvent arg0) {

	}

	public void mouseEntered(MouseEvent arg0) {

	}

	public void mouseExited(MouseEvent arg0) {

	}

	public void mousePressed(MouseEvent e) {
		mouseB = e.getButton();

		MousePressedEvent event = new MousePressedEvent(e.getButton(), e.getX(), e.getY());
		eventListener.onEvent(event);
	}

	public void mouseReleased(MouseEvent e) {
		mouseB = MouseEvent.NOBUTTON;

		MouseReleasedEvent event = new MouseReleasedEvent(e.getButton(), e.getX(), e.getY());
		eventListener.onEvent(event);
	}

}
