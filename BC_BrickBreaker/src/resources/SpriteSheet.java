package resources;

import java.util.List;

import view.Sprite;

public class SpriteSheet implements Savable{
	Sprite[] sprites;
	int totalFrames;
	private String name;
	
	


	public SpriteSheet(List<Sprite> sprites, String name){
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


	@Override
	public String toXml() {
		StringBuilder ret=new StringBuilder("<bc:spritesheet id=\"");
		ret.append(hashCode());
		ret.append("\" name=\"");
		ret.append(this.name);
		ret.append("\">");
		for(Sprite s:sprites){
			ret.append(s.toXml());
		}
		ret.append("</bc:spritesheet>");
		
		return ret.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

}
