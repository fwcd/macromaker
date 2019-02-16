package fwcd.macromaker.model.shortcuts;

public class KeyboardShortcut {
	private String description;
	private String shortcut;
	
	/** Deserialization constructor. */
	protected KeyboardShortcut() {}
	
	public KeyboardShortcut(String description) {
		this(description, "");
	}
	
	public KeyboardShortcut(String description, String shortcut) {
		this.description = description;
		this.shortcut = shortcut;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getShortcut() {
		return shortcut;
	}
	
	public KeyboardShortcut reboundTo(String newShortcut) {
		return new KeyboardShortcut(description, newShortcut);
	}
}
