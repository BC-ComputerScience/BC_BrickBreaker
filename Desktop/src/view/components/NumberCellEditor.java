package view.components;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

public class NumberCellEditor extends DefaultCellEditor implements TableCellEditor {

	public NumberCellEditor() {
		super(new NumberField(true));
	}
	public NumberCellEditor(boolean floating) {
		super(new NumberField(floating));
	}


}
