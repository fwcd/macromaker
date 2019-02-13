package fwcd.macromaker.model;

import java.util.Comparator;
import java.util.List;

import fwcd.macromaker.model.action.MacroAction;

/**
 * A sequence of mouse/key actions in their chronological order.
 */
public class Macro {
	private List<MacroAction> actions;
	
	/** Deserialization constructor. */
	protected Macro() {}
	
	public Macro(List<MacroAction> actions) {
		actions.sort(Comparator.comparing(MacroAction::getTimeStamp));
		this.actions = actions;
	}
	
	public long getDurationMs() {
		if (actions.size() == 0) {
			return 0L;
		} else {
			return actions.get(actions.size() - 1).getTimeStamp();
		}
	}
	
	public List<MacroAction> getActions() { return actions; }
}
