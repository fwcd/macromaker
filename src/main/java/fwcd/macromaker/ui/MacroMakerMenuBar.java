package fwcd.macromaker.ui;

import java.io.File;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import fwcd.fructose.swing.View;
import fwcd.macromaker.model.shortcuts.KeyboardShortcutsModel;

public class MacroMakerMenuBar implements View {
	private final JFileChooser fileChooser = new JFileChooser();
	private final MacroMakerResponder responder;
	private final KeyboardShortcutsModel shortcuts;
	private final JMenuBar component;
	
	public MacroMakerMenuBar(MacroMakerResponder responder, KeyboardShortcutsModel shortcuts) {
		this.responder = responder;
		this.shortcuts = shortcuts;
		
		component = new JMenuBar();
		component.add(menuOf("File",
			menuItemOf("New", () -> responder.newMacro()),
			menuItemOf("Open", () -> showOpenDialog()),
			menuItemOf("Save", () -> showSaveDialog())
		));
		component.add(menuOf("Edit",
			menuItemOf("Keyboard Shortcuts", () -> showShortcutsDialog())
		));
	}
	
	private void showShortcutsDialog() {
		KeyboardShortcutsViewController vc = new KeyboardShortcutsViewController(shortcuts);
		JOptionPane.showMessageDialog(component, vc.getComponent(), "Keyboard Shortcuts", JOptionPane.PLAIN_MESSAGE);
	}
	
	private void showOpenDialog() {
		if (fileChooser.showOpenDialog(component) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (file != null) {
				responder.open(file.toPath());
			}
		}
	}
	
	private void showSaveDialog() {
		if (fileChooser.showSaveDialog(component) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (file != null) {
				String fileName = file.getName();
				Path path;
				if (fileName.endsWith(".json")) {
					path = file.toPath();
				} else {
					path = file.getParentFile().toPath().resolve(fileName + ".json");
				}
				responder.save(path);
			}
		}
	}
	
	private JMenu menuOf(String name, JMenuItem... items) {
		JMenu menu = new JMenu(name);
		for (JMenuItem item : items) {
			menu.add(item);
		}
		return menu;
	}
	
	private JMenuItem menuItemOf(String name, Runnable action) {
		JMenuItem item = new JMenuItem(name);
		item.addActionListener(l -> action.run());
		return item;
	}
	
	@Override
	public JMenuBar getComponent() {
		return component;
	}
}
