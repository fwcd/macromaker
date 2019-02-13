package fwcd.macromaker.model;

import java.util.List;

import fwcd.macromaker.model.action.MacroAction;

/**
 * A runnable sequence of mouse/key actions.
 */
public class Macro {
	private List<MacroAction> actions;
	
	/** Deserialization constructor. */
	protected Macro() {}
	
	public Macro(List<MacroAction> actions) {
		this.actions = actions;
	}
	
	public void run(RobotProxy robot) {
		for (MacroAction action : actions) {
			action.run(robot);
		}
	}
}
