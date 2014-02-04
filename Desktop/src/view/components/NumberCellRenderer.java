package view.components;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class NumberCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {
	boolean floating;
	public NumberCellRenderer(){
		this(true);
	}
	public NumberCellRenderer(boolean floating){
		this.floating=floating;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if(isAcceptable(value)){
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
		return super.getTableCellRendererComponent(table, "#Error", isSelected, hasFocus, row, column);
	}
	
	private boolean isAcceptable(Object o){
		if(o instanceof Integer)return true;
		if(o instanceof Double &&floating)return true;
		if(o instanceof String){
			String s=(String)o;
			if(isInt(s))return true;
			if(isDouble(s)&&floating)return true;
		}
		return false;
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
