package view.spriteEditor;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import view.Sprite;
import view.components.NumberCellEditor;
import view.components.NumberCellRenderer;
import view.components.NumberField;
import view.components.SpriteTableModel;

public class SpriteProperties extends JPanel{
	private JTable table;
	private SpriteTableModel  tableModel;
	private JScrollPane scrollPane=new JScrollPane();
	int number=1;
	public SpriteProperties(){
		
		
		tableModel=new SpriteTableModel();
		table =new JTable(tableModel,tableModel.getColumnModel());
		Dimension dim=new Dimension(800, 200);
		scrollPane.setViewportView(table);
		scrollPane.setPreferredSize(dim);
		scrollPane.setMinimumSize(dim);
		scrollPane.setMaximumSize(dim);
		table.setPreferredScrollableViewportSize(new Dimension(800, 200));
		table.setFillsViewportHeight(true);
		this.add(scrollPane);
	}
	
	ArrayList<Sprite> sprites=new ArrayList<Sprite>();
	public void addSprite(Sprite s){
		//Object[] args=new Object[]{"Sprite"+this.number++ , ""+s.getSheetX(),""+s.getSheetY(),""+s.getSheetWidth(),""+s.getSheetHeight(),""+s.getOffsetX(),""+s.getOffsetY(),""+s.getDuration()};
		this.tableModel.addRow(s);
		sprites.add(s);
	}
	
	public void addTableModelListener(TableModelListener tl){
		this.tableModel.addTableModelListener(tl);
	}
	
}
