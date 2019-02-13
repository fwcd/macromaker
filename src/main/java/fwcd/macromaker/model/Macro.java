package fwcd.macromaker.model;

import java.util.List;

import fwcd.macromaker.model.action.MacroAction;

/**
 * A sequence of mouse/key actions.
 */
public class Macro {
	private List<MacroAction> actions;
	
	/** Deserialization constructor. */
	protected Macro() {}
	
	public Macro(List<MacroAction> actions) {
		this.actions = actions;
	}
	
	public long getDurationMs() {
		return actions.stream()
			.mapToLong(MacroAction::getDelayMs)
			.sum();
	}
	
	public List<MacroAction> getActions() { return actions; }
}
