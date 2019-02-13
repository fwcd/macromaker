package fwcd.macromaker.model.action;

import fwcd.macromaker.model.RobotProxy;

/**
 * An delayed macro action.
 */
public class TimedMacroAction implements MacroAction {
	private MacroAction action;
	private long delayMs;

	/** Deserialization constructor. */
	protected TimedMacroAction() {
	}

	public TimedMacroAction(MacroAction action, long delayMs) {
		this.action = action;
		this.delayMs = delayMs;
	}

	public MacroAction getAction() {
		return action;
	}

	public long getDelayMs() {
		return delayMs;
	}

	@Override
	public void run(RobotProxy robot) {
		try {
			Thread.sleep(delayMs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		action.run(robot);
	}
}
