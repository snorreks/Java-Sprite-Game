package com.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.entity.mob.Chaser;
import com.entity.mob.Shooter;
import com.entity.mob.Star;
import com.entity.mob.NPC.Luna;

public class SpawnLevel extends Level {

	public SpawnLevel(String path) {
		super(path);
	}

	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception, could not load level file");
		}
		add(new Chaser(20, 23));
		add(new Luna(20, 20));
		add(new Star(10, 20));
		add(new Shooter(20, 20));
		add(new Shooter(25, 20));

	}

	protected void generateLevel() {

	}
}