package com.level;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.entity.Entity;
import com.entity.mob.Player;
import com.entity.particle.Particle;
import com.entity.projectile.Arrow;
import com.entity.projectile.Projectile;
import com.entity.spawner.ParticleSpawner;
import com.graphics.Screen;
import com.level.tile.Tile;
import com.util.Vector2i;

public class Level {

	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;

	private int xScroll, yScroll;

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();
	private List<Player> players = new ArrayList<Player>();

	private Comparator<Node> nodeSorter = new Comparator<Node>() { //som compareTo, ogs� boubleSort. Dette er basicly hjelpe middel for Collections.sort
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost) return +1;
			if (n1.fCost > n0.fCost) return -1;
			return 0;
		}
	};

	public static Level spawn = new SpawnLevel("/levels/spawn.png");

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		generateLevel();
	}

	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}

	protected void generateLevel() {
	}

	protected void loadLevel(String path) {
	}

	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
		}
		for (int i = 0; i < players.size(); i++) {
			players.get(i).update();
		}
		remove();
	}

	private void remove() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved()) entities.remove(i);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved()) projectiles.remove(i);
		}
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved()) particles.remove(i);
		}
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved()) players.remove(i);
		}
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	private void time() {
	}

	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean passthru = true;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + xOffset) >> 4;
			int yt = (y - c / 2 * size + yOffset) >> 4;
			if (!getTile(xt, yt).passthru()) passthru = false;
		}
		return passthru;
	}

    public boolean playerProjectileHit(int x, int y, Arrow p) {
	    int xOffset = 0;
        int yOffset = 0;
        int ax1 = getClientPlayer().getX();
        int ay1 = getClientPlayer().getY();
        int ax2 = ax1 + getClientPlayer().getWidth();
        int ay2 = ay1 + getClientPlayer().getHeight();
        int bx1 = x;
        int by1 = y;
        int bx2 = bx1 + p.getSprite().getWidth();
        int by2 = by1 + p.getSprite().getHeight();
/*
        if (by2 < ay1 || ay2 < by1 || bx2 < ax1 || ax2 < bx1) {
            return false; // Collision is impossible.
        } else {
*/


			//int xt = x - p.getSprite().getWidth();
			//int yt = y - p.getSprite().getHeight();

            boolean passthru = true;

            for (int c = 0; c < 4; c++) {
                int xt = (x - c % 2 * p.getSprite().getWidth() + xOffset);
                int yt = (y - c / 2 * p.getSprite().getHeight() + yOffset);



                if(xt > ax1 + 100 && xt < ax1 - 100){
                	if(yt > ay1 + 100 && yt < ay1 - 100) {
                		return true;
					}
				}

                int pds = xt * width/2;

                System.out.println(getClientPlayer().getX() + "  " + xt);

                if (ax1 == xt && ay1 == yt) return true;
            }
            return false;
        }
        /*
	public boolean playerProjectileHit(int x, int y, Arrow p) {

		int ax1 = getClientPlayer().getX();
		int ay1 = getClientPlayer().getY();
		int ax2 = ax1 + getClientPlayer().getWidth();
		int ay2 = ay1 + getClientPlayer().getHeight();
		int bx1 = x;
		int by1 = y;
		int bx2 = bx1 + p.getSprite().getWidth();
		int by2 = by1 + p.getSprite().getHeight();

		if (by2 < ay1 || ay2 < by1 || bx2 < ax1 || ax2 < bx1) {
			return false; // Collision is impossible.
		} else // Collision is possible.
		{
			Rectangle2D k1 = new Rectangle(ax1, ay1, getClientPlayer().getWidth(), getClientPlayer().getHeight());
			Rectangle2D k2 = new Rectangle(x, y, p.getSprite().getWidth(), p.getSprite().getHeight());
			if (k1.contains(k2)) return true;

		}
		return false;
	}
	*/

	public Entity npcProjectileHit(int x, int y, Projectile p) {
		for(Entity e: entities) {
			int ax1 = e.getX();
			int ay1 = e.getY();
			int ax2 = e.getWidth();
			int ay2 = e.getHeight();
			int bx1 = x;
			int by1 = y;
			int bx2 = bx1 + p.getSprite().getWidth();
			int by2 = by1 + p.getSprite().getHeight();
			if (by2 < ay1 || ay2 < by1 || bx2 < ax1 || ax2 < bx1) {
				continue; // Collision is impossible.
			} else // Collision is possible.
			{

				Rectangle2D k1 = new Rectangle(ax1, ay1, ax2, ay2);
				Rectangle2D k2 = new Rectangle(x, y, p.getSprite().getWidth(), p.getSprite().getHeight());
				if (k1.contains(k2)) return e;
			}
		}
		return null;
	}

	public void setScroll(int xScroll, int yScroll) {
		this.xScroll = xScroll;
		this.yScroll = yScroll;
	}

	public void render(Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4; //venstre linja opp og ned vertikal
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}
		for (int i = 0; i < players.size(); i++) {
			players.get(i).render(screen);
		}
	}

	public void add(Entity e) {
		e.init(this);
		if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if (e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else if (e instanceof Player) {
			players.add((Player) e);
		} else if (e instanceof ParticleSpawner) {
			//particle.add((Player) e);	
		} else {
			entities.add(e);
		}
	}
	/* Singel uten ArrayList<Player>
		public Player getPlayer() {
			for (int i = 0; i < entities.size(); i++) {
				if (entities.get(i) instanceof Player) {
					return (Player) entities.get(i);
				}
			}
			return null;
		}
	*/

	public List<Player> getPLayers() {
		return players;
	}

	public Player getPlayerAt(int index) {
		return players.get(index);
	}

	public Player getClientPlayer() {
		return players.get(0);
	}

	public List<Node> findPath(Vector2i start, Vector2i goal) {
		List<Node> openList = new ArrayList<Node>(); //Alle adjustent nodes til forrige cosed nodes
		List<Node> closedList = new ArrayList<Node>(); //De vi nodesa er ferdig meg
		Node current = new Node(start, null, 0, getDistance(start, goal)); //den noden man consider er null fordi parent nodes er null, er foorste node
		openList.add(current);
		while (openList.size() > 0) {
			Collections.sort(openList, nodeSorter); //ser bare p� den n�rmeste noden, selv om man kan adde opp til 9
			current = openList.get(0);
			if (current.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while (current.parent != null) { //g�r helt tilbake til start tilen,
					path.add(current); //adder i lista
					current = current.parent; //g�r tilbake
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current); //fjerner
			closedList.add(current); //alerede sjekket, un�vendig � sjekke der vi kom fra
			for (int i = 0; i < 9; i++) {
				if (i == 4) continue; //skipper 4, fordi det er midten, aka tilen du er
				int x = current.tile.getX(); //plassering
				int y = current.tile.getY();
				int xi = (i % 3) - 1; //retningen til current neigbur. se ford deg 3*3 grid der -1, 0, 1 for x og y
				int yi = (i / 3) - 1; //retningen
				Tile at = getTile(x + xi, y + yi); //hver tile i listen, - midten.
				if (at == null) continue; //skipper
				if (at.solid()) continue; //er solid
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = (current.gCost + ((getDistance(current.tile, a) == 1) ? 1 : 0.95)); //distansen av forrige tile og den n�, alts� enten +1 /rett eller sqr(2) /diagonalt
				double hCost = getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if (vecInList(closedList, a) && gCost >= node.gCost) continue; //hvis den eksisterer i closedList skip, gCost greie handler om man �pner closedList igjen
				if (!vecInList(openList, a) || gCost < node.gCost) openList.add(node); //hvis den ikke er i openList  s� adder vi
			}
		}
		closedList.clear();
		//System.out.println("returner null");
		return null;
	}

	public double getDistance(Vector2i v0, Vector2i v1) {
		double x = v0.getX() - v1.getX();
		double y = v0.getY() - v1.getY();
		return Math.hypot(x, y);
	}

	private boolean vecInList(List<Node> list, Vector2i vector) {
		for (Node n : list) { //for hver  node i listen
			if (n.tile.equals(vector)) return true;
		}
		return false;
	}

	public List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		int ex = e.getX();
		int ey = e.getY();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity.equals(e)) continue;
			int x = entity.getX();
			int y = entity.getY();

			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.hypot(dx, dy);
			if (distance <= radius) result.add(entity);
		}
		return result;
	}

	public List<Player> getPlayers(Entity e, int radius) {
		List<Player> result = new ArrayList<Player>();
		int ex = (int) e.getX();
		int ey = (int) e.getY();
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			int x = (int) player.getX();
			int y = (int) player.getY();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.hypot(dx, dy);
			if (distance <= radius) result.add(player);
		}
		return result;
	}

	// Grass = 0xFF00  Flower = 0xfff00 Rock = 0x7f7f00
	//0xff betyr at det er full
	//0x00 betyr at det er transperenet
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;

		if (tiles[x + y * width] == Tile.col_gravel) return Tile.gravel;
		if (tiles[x + y * width] == Tile.col_rock) return Tile.rock;
		if (tiles[x + y * width] == Tile.col_dirt) return Tile.dirt;
		if (tiles[x + y * width] == Tile.col_grass) return Tile.grass;
		if (tiles[x + y * width] == Tile.col_water) return Tile.water;
		if (tiles[x + y * width] == Tile.col_stone) return Tile.stone;
		if (tiles[x + y * width] == Tile.col_plank) return Tile.plank;
		if (tiles[x + y * width] == Tile.col_sand) return Tile.sand;
		if (tiles[x + y * width] == Tile.col_tree) return Tile.tree;
		if (tiles[x + y * width] == Tile.col_rockB) return Tile.rockB;

		return Tile.grass;
	}
}
