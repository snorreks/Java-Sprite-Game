package com.dex.WeDemBoyz.entity.mob;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.dex.WeDemBoyz.Game;
import com.dex.WeDemBoyz.entity.projectile.Arrow;
import com.dex.WeDemBoyz.entity.projectile.Projectile;
import com.dex.WeDemBoyz.events.Event;
import com.dex.WeDemBoyz.events.EventDispatcher;
import com.dex.WeDemBoyz.events.EventListener;
import com.dex.WeDemBoyz.events.types.MousePressedEvent;
import com.dex.WeDemBoyz.events.types.MouseReleasedEvent;
import com.dex.WeDemBoyz.graphics.AnimatedSprite;
import com.dex.WeDemBoyz.graphics.Screen;
import com.dex.WeDemBoyz.graphics.Sprite;
import com.dex.WeDemBoyz.graphics.SpriteSheet;
import com.dex.WeDemBoyz.graphics.ui.ImageUtils;
import com.dex.WeDemBoyz.graphics.ui.UIActionListener;
import com.dex.WeDemBoyz.graphics.ui.UIButton;
import com.dex.WeDemBoyz.graphics.ui.UIButtonListener;
import com.dex.WeDemBoyz.graphics.ui.UILabel;
import com.dex.WeDemBoyz.graphics.ui.UIManager;
import com.dex.WeDemBoyz.graphics.ui.UIPanel;
import com.dex.WeDemBoyz.graphics.ui.UIProgressBar;
import com.dex.WeDemBoyz.input.Keyboard;
import com.dex.WeDemBoyz.input.Mouse;
import com.dex.WeDemBoyz.util.Vector2i;

public class Player extends Mob implements EventListener {

	private String name;
	private Keyboard input;
	private Sprite sprite;
	private boolean walking = false;
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);

	private UIManager ui;
	private UIProgressBar uiHealthBar;
	private UIButton button;

	private boolean shooting = false;

	private AnimatedSprite animSprite = down;
	private int fireRate;
	//Projectile p;

	private BufferedImage image = null;

	public Player(String name, Keyboard input) {
		this.name = name;
		this.input = input;
		sprite = Sprite.playerfs;
		health = 100;
	}

	public Player(String name, int x, int y, Keyboard input) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.input = input;
		fireRate = Arrow.FIRE_RATE;

		//Default stats:
		health = 100;

		//UI:
		ui = Game.getUIManager();
		UIPanel panel = (UIPanel) new UIPanel(new Vector2i((300 - 80) * 3, 0), new Vector2i(80 * 3, 168 * 3)).setColor(0x4f4f4f);
		ui.addPanel(panel);
		UILabel nameLabel = new UILabel(new Vector2i(30, 200), name);
		nameLabel.setColor(0xcacaca);
		nameLabel.setFont(new Font("Ariel", Font.BOLD, 24));
		nameLabel.dropShaddow = true;
		nameLabel.dropShadowOffset = 1;
		panel.addComponent(nameLabel);

		button = new UIButton(new Vector2i(10, 260), new Vector2i(120, 40), new UIActionListener() {
			public void perform() {
				System.out.println("button pressed");
				//do shit here
			}
		});
		button.setText("Hello");
		panel.addComponent(button);

		try {
			image = ImageIO.read(new File("res/logo/bow.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		UIButton imageButton = new UIButton(new Vector2i(10, 360), image, new UIActionListener() {
			public void perform() {
				System.out.println("button pressed");
				//do shit here
			}
		});
		imageButton.setButtonListener(new UIButtonListener() {
			public void entered(UIButton button) {
				button.setImage(ImageUtils.changeBrightness(image, 100));
			}

			public void exited(UIButton button) {
				button.setImage(image);
			}

			public void pressed(UIButton button) {
				button.setImage(ImageUtils.changeBrightness(image, -100));
			}

			public void released(UIButton button) {
				button.setImage(ImageUtils.changeBrightness(image, 100));
			}

		});

		panel.addComponent(imageButton);

		uiHealthBar = new UIProgressBar(new Vector2i(10, 210), new Vector2i(80 * 3 - 20, 20));
		uiHealthBar.setColor(0x5f5f5f);
		uiHealthBar.setForegroundColor(0xee3030);
		UILabel hpLabel = new UILabel(new Vector2i(uiHealthBar.position).add(new Vector2i(2, 17)), "HP");
		hpLabel.setColor(0xffffff);
		hpLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		panel.addComponent(uiHealthBar);
		panel.addComponent(hpLabel);
	}

	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePressed((MousePressedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseReleased((MouseReleasedEvent) e));
	}

	public boolean onMousePressed(MousePressedEvent e) {
		if (e.getX() > 660) return false;
		if (e.getButton() == MouseEvent.BUTTON1) {
			shooting = true;
			return true;
		}

		return false;
	}

	public boolean onMouseReleased(MouseReleasedEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			shooting = false;
			return true;
		}

		return false;
	}

	public void update() {
		updateWalking();
		updateUI();
		updateShooting();
		clear();
	}

	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) level.getProjectiles().remove(i);
		}
	}

	private void updateShooting() {
		if (fireRate > 0) fireRate--;

		if (!shooting || fireRate > 0) return;
		double dx = Mouse.getX() - (Game.getWindowWidth() / 2); //absultt verdi
		double dy = Mouse.getY() - (Game.getWindowHeight() / 2);
		double dir = Math.atan2(dy, dx);
		shoot(x, y, dir);
		fireRate = Arrow.FIRE_RATE;
	}

	private void updateWalking() {
		//double dx = 0,dy = 0;
		if (walking) animSprite.update();
		else animSprite.setFrame(0);
		double xa = 0, ya = 0;
		double speed = 2;
		if (input.up) {
			animSprite = up;
			ya -= speed;
			//dy += -1;
		} else if (input.down) {
			animSprite = down;
			ya += speed;
			//dy += 1;
		}
		if (input.left) {
			animSprite = left;
			xa -= speed;
			//dx += -1;
		} else if (input.right) {
			animSprite = right;
			xa += speed;
			//dx += 1;
		}
		/*
		if(Mouse.getButton()==1 && fireRate <= 0) {
			double dir = Math.atan2(dy, dx);
			shoot(x, y, dir);
			fireRate = Arrow.FIRE_RATE;
		}
		*/
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}

	private void updateUI() {
		uiHealthBar.setProgress(health / 100.0);
		if (health <= 0) {
			System.out.println("Nigga u dead");
			health = 100;
		}

	}

	public String getName() {
		return name;
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) (x - 16), (int) (y - 16), sprite, 0);
	}

	public int getWidth() {
		return sprite.getWidth();
	}

	public int getHeight() {
		return sprite.getHeight();
	}

}