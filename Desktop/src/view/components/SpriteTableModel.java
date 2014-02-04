package view.components;

import java.util.ArrayList;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import view.Sprite;

public class SpriteTableModel extends AbstractTableModel{
	private DefaultTableColumnModel columnModel;
	ArrayList<Sprite> sprites=new ArrayList<Sprite>();
	public SpriteTableModel(){
		this.generateColumns();
	}
	@Override
	public int getColumnCount() {
		return columnModel.getColumnCount();
	}

	@Override
	public int getRowCount() {
		return sprites.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Sprite s=sprites.get(row);
		switch(column){
		case 1:return s.getSheetX();
		case 2:return s.getSheetY();
		case 3:return s.getSheetWidth();
		case 4:return s.getSheetHeight();
		case 5:return s.getOffsetX();
		case 6:return s.getOffsetY();
		case 7:return s.getDuration();
		}
		return "sprite"+(row+1);
	}
	@Override
	public void setValueAt(Object obj, int rowIndex, int columnIndex){
		
		double o=(Double.parseDouble((String) obj));
		
		Sprite s=this.sprites.get(rowIndex);
		switch(columnIndex){
		case 1:s.setSheetX((int)o);
		break;
		case 2:s.setSheetY((int)o);
		break;
		case 3:s.setSheetWidth((int)o);
		break;
		case 4:s.setSheetHeight((int)o);
		break;
		case 5:s.setOffsetX((int)o);
		break;
		case 6:s.setOffsetY((int)o);
		break;
		case 7:s.setDuration((double)o);
		break;
		}
		this.fireTableCellUpdated(rowIndex, columnIndex);
	}
	public boolean isCellEditable(int rowIndex, int columnIndex){
		if(rowIndex>=0&&rowIndex<this.sprites.size()){
			return columnIndex>0;
		}
		return false;
	}

	public void addRow(Sprite s) {
		this.sprites.add(s);
		this.fireTableRowsInserted(sprites.size(), sprites.size());
	}
	public TableColumnModel getColumnModel(){
		return this.columnModel;
	}
	private void generateColumns(){
		columnModel=new DefaultTableColumnModel();
		TableColumn toAdd= new TableColumn(0, 75);
		toAdd.setHeaderValue("Name");
		this.columnModel.addColumn(toAdd);
		toAdd= new TableColumn(1, 75, new NumberCellRenderer(false), new NumberCellEditor(false));
		toAdd.setHeaderValue("X");
		this.columnModel.addColumn(toAdd);
		toAdd= new TableColumn(2, 75, new NumberCellRenderer(false), new NumberCellEditor(false));
		toAdd.setHeaderValue("Y");
		this.columnModel.addColumn(toAdd);
		toAdd= new TableColumn(3, 75, new NumberCellRenderer(false), new NumberCellEditor(false));
		toAdd.setHeaderValue("Width");
		this.columnModel.addColumn(toAdd);
		toAdd= new TableColumn(4, 75, new NumberCellRenderer(false), new NumberCellEditor(false));
		toAdd.setHeaderValue("Height");
		this.columnModel.addColumn(toAdd);
		toAdd= new TableColumn(5, 75, new NumberCellRenderer(false), new NumberCellEditor(false));
		toAdd.setHeaderValue("Offset X");
		this.columnModel.addColumn(toAdd);
		toAdd= new TableColumn(6, 75, new NumberCellRenderer(false), new NumberCellEditor(false));
		toAdd.setHeaderValue("Ofset Y");
		this.columnModel.addColumn(toAdd);
		toAdd= new TableColumn(7, 75, new NumberCellRenderer(true), new NumberCellEditor(true));
		toAdd.setHeaderValue("Duration");
		this.columnModel.addColumn(toAdd);
	}
	

}
