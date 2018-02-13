package com.graphics.ui;

import static com.util.MathUtils.clamp;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;

public class ImageUtils {

	private ImageUtils() {

	}

	//returns new image, kan ta negativ darker, og positiv lysere amount
	public static BufferedImage changeBrightness(BufferedImage orignal, int amount) {

		BufferedImage result = new BufferedImage(orignal.getWidth(), orignal.getHeight(), BufferedImage.TYPE_INT_ARGB);

		byte[] pixels = ((DataBufferByte) orignal.getRaster().getDataBuffer()).getData();
		int[] resultPixels = ((DataBufferInt) result.getRaster().getDataBuffer()).getData();

		int offset = 0;
		for (int yy = 0; yy < orignal.getHeight(); yy++) {
			for (int xx = 0; xx < orignal.getWidth(); xx++) {
				int a = Byte.toUnsignedInt(pixels[offset++]);
				int r = Byte.toUnsignedInt(pixels[offset++]);
				int g = Byte.toUnsignedInt(pixels[offset++]);
				int b = Byte.toUnsignedInt(pixels[offset++]);

				r = clamp(r + amount, 0, 255);
				g = clamp(g + amount, 0, 255);
				b = clamp(b + amount, 0, 255);
				resultPixels[xx + yy * result.getWidth()] = a << 24 | r << 16 | g << 8 | b;
			}
		}
		return result;

	}

}
