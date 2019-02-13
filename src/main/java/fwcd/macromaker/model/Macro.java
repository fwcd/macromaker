package fwcd.macromaker.model;

import java.util.ArrayList;
import java.util.List;

import fwcd.macromaker.model.action.MacroAction;

public class Macro {
	private List<MacroAction> actions = new ArrayList<>();
	
	/** Deserialization constructor. */
	protected Macro() {}
	
	public List<MacroAction> getActions() { return actions; }
}
