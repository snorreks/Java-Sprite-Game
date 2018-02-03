package com.dex.WeDemBoyz.entity.spawner;

import com.dex.WeDemBoyz.entity.Entity;
import com.dex.WeDemBoyz.level.Level;

public abstract class Spawner extends Entity {

	public enum Type { //nesten som boolean, kan bare vere en av de to typene, enten mob eller particle
		MOB, PARTICLE;
	}

	private Type type;

	public Spawner(int x, int y, Type type, int amount, Level level) {
		init(level);
		this.x = x;
		this.y = y;
		this.type = type;
	}

}
