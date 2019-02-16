package fwcd.macromaker.ui;

import static fwcd.fructose.util.BoolUtils.implies;

import java.util.HashMap;
import java.util.Map;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import fwcd.macromaker.model.shortcuts.KeyboardShortcut;
import fwcd.macromaker.model.shortcuts.KeyboardShortcutsModel;

public class GlobalShortcutsHandler implements AutoCloseable {
	private final NativeKeyListener listener;
	private final Map<String, Runnable> actions = new HashMap<>();
	private boolean enabled = false;
	
	public GlobalShortcutsHandler(KeyboardShortcutsModel model) {
		listener = new NativeKeyListener() {
			@Override
			public void nativeKeyTyped(NativeKeyEvent event) {
				// Do nothing
			}
		
			@Override
			public void nativeKeyReleased(NativeKeyEvent event) {
				// Do nothing
			}
		
			@Override
			public void nativeKeyPressed(NativeKeyEvent event) {
				if (enabled) {
					for (String name : model.getShortcutNames()) {
						if (actions.containsKey(name) && matches(event, model.get(name))) {
							actions.get(name).run();
						}
					}
				}
			}
			
			private boolean matches(NativeKeyEvent event, KeyboardShortcut shortcut) {
				String raw = shortcut.getShortcut().toLowerCase();
				String actualKey = raw.replaceFirst("(?:(?:ctrl|meta|shift|alt)\\+)?\\s*", "");
				int modifiers = event.getModifiers();
				return implies(raw.contains("ctrl"), (modifiers & NativeKeyEvent.CTRL_MASK) != 0)
					&& implies(raw.contains("meta"), (modifiers & NativeKeyEvent.META_MASK) != 0)
					&& implies(raw.contains("shift"), (modifiers & NativeKeyEvent.SHIFT_MASK) != 0)
					&& implies(raw.contains("alt"), (modifiers & NativeKeyEvent.ALT_MASK) != 0)
					&& actualKey.equals(NativeKeyEvent.getKeyText(event.getKeyCode()).toLowerCase());
			}
		};
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void addAction(String keybindName, Runnable action) {
		actions.put(keybindName, action);
	}
	
	public void registerListeners() {
		GlobalScreen.addNativeKeyListener(listener);
	}
	
	public void unregisterListeners() {
		GlobalScreen.removeNativeKeyListener(listener);
	}
	
	@Override
	public void close() {
		unregisterListeners();
	}
}
