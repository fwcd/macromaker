package fwcd.macromaker.ui;

import javax.swing.JComponent;
import javax.swing.JTable;

public class KeyboardShortcutsViewController {
	private final JTable component;
	
	public KeyboardShortcutsViewController() {
		component = new JTable();
	}
	
	public JComponent getComponent() {
		return component;
	}
}
