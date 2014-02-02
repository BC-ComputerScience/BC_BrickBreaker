package view;


import resources.SpriteSet;

public interface Renderable {
	int getImageX();
	int getImageY();
	int getImageWidth();
	int getImageHeight();
	Sprite getImage();
	void addSpriteSet(SpriteSet set);
}
