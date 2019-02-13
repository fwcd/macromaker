package fwcd.macromaker.model;

/**
 * An action associated with a time delta.
 */
public class TimedMacroAction {
	private MacroAction action;
	private long delayMs;
	
	/** Deserialization constructor. */
	protected TimedMacroAction() {}
	
	public TimedMacroAction(MacroAction action, long delayMs) {
		this.action = action;
		this.delayMs = delayMs;
	}
	
	public MacroAction getAction() { return action; }
	
	public long getDelayMs() { return delayMs; }
}
