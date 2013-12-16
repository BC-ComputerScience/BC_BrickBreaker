package controller;

import java.awt.image.BufferedImage;

public interface SpriteSheet {
	public BufferedImage currentSprite();
	public void advance();

}
