package view.components;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.PlainDocument;
import javax.swing.text.Position;
import javax.swing.text.Segment;

public class NumberField extends JTextField{
	boolean floating;
	public NumberField(){
		this(true);
	}
	public NumberField(boolean floating){
		super(new NumberDocument(floating),"",0);
		this.floating=floating;
	}
	
	private static class NumberDocument extends PlainDocument {
		private boolean floating;
		public NumberDocument(boolean floating){
			this.floating=floating;
		}
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException{
			if(isInt(str)){
				super.insertString(offs, str, a);//if the string in purely numeric, it'll work
			}else if(floating){
				if(str.equals(".")&&isInt(this.getText(0, this.getLength()-1))){//if it has a decimal, check that no decimal is already in document
					super.insertString(offs, str, a);
				}
			}else if(isDouble(str)){
				super.insertString(offs, str, a);
			}
		}
		private boolean isInt(String s){
			try{
				Integer.parseInt(s);
				return true;
			}catch( Exception e){
				//so sue me;
			}
			return false;
		}
		private boolean isDouble(String s){
			try{
				Double.parseDouble(s);
				return true;
			}catch( Exception e){
				//so sue me;
			}
			return false;
		}
	}

}
