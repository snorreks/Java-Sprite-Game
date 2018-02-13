package com.entity.mob;

import java.util.List;

import com.entity.Entity;
import com.graphics.Screen;
import com.graphics.Sprite;
import com.util.Debug;
import com.util.Vector2i;

public class Shooter extends Mob {

	private Entity rand = null;

	private int firerate;
	private int time;

	public Shooter(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.luna;
	}

	public void update() {
		firerate++;
		time++;
		if (time % 20 == 0) {
			double dx = level.getClientPlayer().getX() - x; //absultt verdi
			double dy = level.getClientPlayer().getY() - y;
			double dir = Math.atan2(dy, dx);
			shootA(x, y, dir);
		}
		//shootRandom();
		//shootClosest();
	}

	private void shootRandom() {
		if (time % 60 == 0) {
			List<Entity> entities = level.getEntities(this, 2000); //bytter det her med player, fordi naa skytern alle
			entities.add(level.getClientPlayer()); //m
			int index = random.nextInt(entities.size()); //8 vil si 0-7
			rand = entities.get(index);
		}
		if (firerate % 20 == 0 && rand != null) {
			double dx = rand.getX() - x; //absultt verdi
			double dy = rand.getY() - y;
			double dir = Math.atan2(dy, dx);
			shootA(x, y, dir);
		}

	}

	private void shootClosest() {
		List<Entity> entities = level.getEntities(this, 80); //bytter det her med player, fordi n� skytern alle
		entities.addAll(level.getPlayers(this, 100));
		double min = 0;
		Entity closest = null;

		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);

			double distance = level.getDistance(new Vector2i(x, y), new Vector2i(e.getX(), e.getY()));
			if (i == 0 || distance < min) {
				min = distance;
				closest = e;
			}
		}

		if (firerate % 20 == 0 && closest != null) {
			double dx = closest.getX() - x; //absultt verdi
			double dy = closest.getY() - y;
			double dir = Math.atan2(dy, dx);
			shootA(x, y, dir);
		}
	}

	public void render(Screen screen) {
		Debug.drawRect(screen, 24 * 16 + 6, 20 * 16 - 2, 20, 20, true);
		screen.renderMob(x - 16, y - 16, this);
	}

}