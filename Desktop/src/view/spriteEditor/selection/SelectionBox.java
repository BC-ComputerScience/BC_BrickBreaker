package view.spriteEditor.selection;

import java.awt.Graphics;
import java.awt.Rectangle;

import view.Sprite;

public class SelectionBox {
	Sprite s;
	int cornerSize=10;
	Rectangle topLeft;
	Rectangle topRight;
	Rectangle bottomLeft;
	Rectangle bottomRight;
	Rectangle selected;
	public SelectionBox(Sprite sprite){
		s=sprite;
		
	}
	
	public void draw(Graphics g){
		g.drawRect(s.getSheetX(), s.getSheetY(), s.getSheetWidth(), s.getSheetHeight());
		this.resetRects();
		g.drawRect((int)topLeft.getX(), (int)topLeft.getY(), (int)topLeft.getWidth(), (int)topLeft.getHeight());
		g.drawRect((int)topRight.getX(), (int)topRight.getY(), (int)topRight.getWidth(), (int)topRight.getHeight());
		g.drawRect((int)bottomLeft.getX(), (int)bottomLeft.getY(), (int)bottomLeft.getWidth(), (int)bottomLeft.getHeight());
		g.drawRect((int)bottomRight.getX(), (int)bottomRight.getY(), (int)bottomRight.getWidth(), (int)bottomRight.getHeight());
		if(selected!=null){
			g.drawRect((int)selected.getX(), (int)selected.getY(), (int)selected.getWidth(), (int)selected.getHeight());
		}
	}

	public boolean isSelecting(int x, int y) {
		
		// TODO Auto-generated method stub
		return (topLeft.contains(x, y)||topRight.contains(x, y)||bottomRight.contains(x, y)||bottomLeft.contains(x, y));
	}

	public void drag(int startX, int startY, int endX, int endY) {
		selected=fromDrag(startX, startY,endX,endY);
	}
	public void release(int startX, int startY, int endX, int endY){
		selected=fromDrag(startX, startY,endX,endY);
		
		this.s.setSheetX((int)selected.getX());
		this.s.setSheetY((int)selected.getY());
		this.s.setSheetWidth((int)selected.getWidth());
		this.s.setSheetHeight((int)selected.getHeight());
		selected=null;
		this.resetRects();
	}
	
	
	public void resetRects(){
		topLeft=new Rectangle(s.getSheetX(), s.getSheetY(), cornerSize, cornerSize);
		topRight=new Rectangle(s.getSheetX()+s.getSheetWidth()-cornerSize, s.getSheetY(), cornerSize, cornerSize);
		bottomLeft=new Rectangle(s.getSheetX(), s.getSheetY()+s.getSheetHeight()-cornerSize, cornerSize, cornerSize);
		bottomRight=new Rectangle(s.getSheetX()+s.getSheetWidth()-cornerSize, s.getSheetY()+s.getSheetHeight()-cornerSize, cornerSize, cornerSize);
	}
	public Rectangle fromDrag(int startX, int startY, int endX, int endY){
		int x1=0,y1=0,x2=0,y2=0;
		if(topLeft.contains(startX, startY)){
			x1=(int)bottomRight.getMaxX();
			y1=(int)bottomRight.getMaxY();
			x2=(int)topLeft.getX();
			y2=(int)topLeft.getY();
		}else if(bottomRight.contains(startX, startY)){
			x1=(int)topLeft.getX();
			y1=(int)topLeft.getY();
			x2=(int)bottomRight.getMaxX();
			y2=(int)bottomRight.getMaxY();
		}else if(bottomLeft.contains(startX, startY)){
			x1=(int)topRight.getMaxX();
			y1=(int)topRight.getY();
			x2=(int)bottomLeft.getX();
			y2=(int)bottomLeft.getMaxY();
		}else if(topRight.contains(startX, startY)){
			x1=(int)bottomLeft.getX();
			y1=(int)bottomLeft.getMaxY();
			x2=(int)topRight.getMaxX();
			y2=(int)topRight.getY();
		}else return null;
		x2+=(endX-startX);
		y2+=(endY-startY);
		
		int x=Math.min(x1, x2);
		int y=Math.min(y1, y2);
		return new Rectangle(x,y,Math.max(x1, x2)-x,Math.max(y1, y2)-y);
	}
}
