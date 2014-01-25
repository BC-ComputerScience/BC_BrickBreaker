package resources;

import view.Sprite;

public interface ImageResource extends Resource {
	public Sprite createSprite(int x, int y, int width, int height);
}
