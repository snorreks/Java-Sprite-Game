package com.graphics.ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class UIManager {

	private List<UIPanel> panels = new ArrayList<UIPanel>(); //deler opp ui i panels, og plassering relativ tl panel

	public UIManager() {
	}

	public void update() {
		for (UIPanel panel : panels) {
			panel.update();
		}
	}

	public void addPanel(UIPanel panel) {
		panels.add(panel);
	}
	public void removePanel(){
		for (int i = 0; i < panels.size();i++){
			panels.remove(i);
		}
	}

	public void render(Graphics g) {
		for (UIPanel panel : panels) {
			panel.render(g);
		}
	}
}
