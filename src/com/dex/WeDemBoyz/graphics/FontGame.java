package com.dex.WeDemBoyz.graphics;

public class FontGame {

	private static SpriteSheet font = new SpriteSheet("/fonts/pokemon1.png", 16);
	private static Sprite[] characters = Sprite.split(font);
	private static String charIndex = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" //
			+ "abcdefghijklmnopqrstuvwxyz" // 
			+ "0123456789" //
			+ ".,;:?!-_~#\"\'&()[]|" //
			+ "'\\/@+?=*$E<>";

	public FontGame() {

	}

	public void render(int x, int y, String text, Screen screen) {
		render(x, y, 0, 0, text, screen);
	}

	public void render(int x, int y, int color, String text, Screen screen) {
		render(x, y, 0, color, text, screen);
	}

	public void render(int x, int y, int spacing, int color, String text, Screen screen) {
		int line = 0;
		int xOffset = 0;
		for (int i = 0; i < text.length(); i++) {
			char currentChar = text.charAt(i);
			int yOffset = 0;
			if (currentChar == 'y') yOffset = 3;
			if (currentChar == 'g' || currentChar == 'j' || currentChar == 'q') yOffset = 1;
			if (currentChar == 'i' || currentChar == 'g' || currentChar == 'f' || currentChar == 'l' || currentChar == 'j' || currentChar == 'q' || currentChar == 's' || currentChar == 't' || currentChar == '2') xOffset -= 2;
			if (currentChar == 'I') xOffset -= 5;
			xOffset += 16 + spacing;
			if (currentChar == '\n') {
				line++;
				xOffset = 0;
			}
			int index = charIndex.indexOf(currentChar);
			if (index >= 0 && index <= 26) xOffset += 2;
			if (index == -1) {
				xOffset -= 6;
				continue;
			}
			screen.renderTextCharacter(x + xOffset, y + line * 20 + yOffset + line, characters[index], color, false);
		}
	}
}