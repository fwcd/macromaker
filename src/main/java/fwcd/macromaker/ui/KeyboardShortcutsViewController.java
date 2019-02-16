package fwcd.macromaker.ui;

import javax.swing.JComponent;
import javax.swing.JTable;

import fwcd.macromaker.model.shortcuts.KeyboardShortcutsModel;

public class KeyboardShortcutsViewController {
	private final JTable component;
	
	public KeyboardShortcutsViewController(KeyboardShortcutsModel model) {
		component = new JTable();
	}
	
	public JComponent getComponent() {
		return component;
	}
}
