package resources;

import java.util.List;

import view.Sprite;

public class SpriteSheet {
	Sprite[] sprites;
	int totalFrames;
	
	
	
	
	public SpriteSheet(List<Sprite> sprites){
		totalFrames=sprites.size();
		this.sprites=new Sprite[totalFrames];
		int i=0;
		for(Sprite s:sprites){
			this.sprites[i++]=s;
		}
	}
	

	public boolean isUsable(){
		return sprites[0]!=null;
	}
	
	public SpriteSet getSpriteSheet(){
		return new SpriteSet(this);
		
	}
	public Sprite getSprite(int i){
		return this.sprites[i%this.totalFrames];
	}


	public double getLength() {
		// TODO Auto-generated method stub
		return this.totalFrames;
	}

}
