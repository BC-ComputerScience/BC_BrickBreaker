package view;

import java.awt.Color;
import java.awt.Image;

import resources.SpriteSet;

public interface Renderable extends model.GameObject{
	int getImageX();
	int getImageY();
	int getImageWidth();
	int getImageHeight();
	Sprite getImage();
	void addSpriteSet(SpriteSet set);
}
