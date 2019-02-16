package fwcd.macromaker.ui;

import javax.swing.table.AbstractTableModel;

import fwcd.macromaker.model.shortcuts.KeyboardShortcut;
import fwcd.macromaker.model.shortcuts.KeyboardShortcutsModel;

public class KeyboardShortcutsTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -2987234337378641111L;
	private static final String[] COLUMN_HEADERS = {"Name", "Description", "Shortcut"};
	private static final int NAME_COLUMN = 0;
	private static final int DESCRIPTION_COLUMN = 1;
	private static final int SHORTCUT_COLUMN = 2;
	
	private final KeyboardShortcutsModel model;
	
	public KeyboardShortcutsTableModel(KeyboardShortcutsModel model) {
		this.model = model;
	}
	
	@Override
	public int getColumnCount() {
		return COLUMN_HEADERS.length;
	}
	
	@Override
	public int getRowCount() {
		return model.size();
	}
	
	@Override
	public String getColumnName(int column) {
		return COLUMN_HEADERS[column];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String name = model.getShortcutNames().get(rowIndex);
		KeyboardShortcut shortcut = model.getShortcut(name);
		
		switch (columnIndex) {
			case NAME_COLUMN: return name;
			case DESCRIPTION_COLUMN: return shortcut.getDescription();
			case SHORTCUT_COLUMN: return shortcut.getShortcut();
			default: throw new IllegalArgumentException("Unrecognized shortcut column index: " + columnIndex);
		}
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		String name = model.getShortcutNames().get(rowIndex);
		
		if (columnIndex == SHORTCUT_COLUMN) {
			model.rebindShortcut(name, (String) aValue);
		}
	}
}
