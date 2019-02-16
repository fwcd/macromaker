package fwcd.macromaker.model.shortcuts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyboardShortcutsModel {
	private Map<String, KeyboardShortcut> shortcuts = new HashMap<>();
	private List<String> shortcutNames = new ArrayList<>();
	
	public List<String> getShortcutNames() {
		return shortcutNames;
	}
	
	public KeyboardShortcut getShortcut(String name) {
		return shortcuts.get(name);
	}
	
	public KeyboardShortcut getShortcutByIndex(int index) {
		return shortcuts.get(shortcutNames.get(index));
	}
	
	public void putShortcut(String name, KeyboardShortcut shortcut) {
		KeyboardShortcut previous = shortcuts.put(name, shortcut);
		if (previous == null) {
			shortcutNames.add(name);
		}
	}
	
	public int size() {
		return shortcuts.size();
	}
	
	public void removeShortcut(String name) {
		shortcutNames.remove(name);
	}
	
	public void rebindShortcut(String name, String newShortcut) {
		putShortcut(name, getShortcut(name).reboundTo(newShortcut));
	}
}
