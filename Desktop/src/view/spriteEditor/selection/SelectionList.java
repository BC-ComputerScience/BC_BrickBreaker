package view.spriteEditor.selection;

import java.util.ArrayList;
import java.util.Iterator;

public class SelectionList implements Iterable<SelectionBox>{

	int startX;
	int startY;
	SelectionBox selected;
	
	ArrayList<SelectionBox> list= new ArrayList<SelectionBox>();
	
	@Override
	public Iterator<SelectionBox> iterator() {
		// TODO Auto-generated method stub
		return list.iterator();
	}

	public void add(SelectionBox selectionBox) {
		list.add(selectionBox);
		
	}

	public boolean pressed(int x, int y) {
		System.out.println("pressed"+x+","+y);
		this.startX=x;
		this.startY=y;
		selected=null;
		for(SelectionBox sb:list){
			if(sb.isSelecting(x,y)){
				selected=sb;
			}
		}
		return selected!=null;
	}

	public boolean released(int x, int y) {
		if(selected!=null){
			selected.release(startX, startY,x,y);
			selected=null;
			return true;
		}
		
		return false;
	}

	public void dragged(int x, int y) {
		if(selected!=null){
			selected.drag(startX, startY,x,y);
			//selected=null;
		}
		
	}

}
