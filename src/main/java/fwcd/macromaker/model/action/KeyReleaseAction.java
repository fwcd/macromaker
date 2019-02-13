package fwcd.macromaker.model.action;

import fwcd.macromaker.model.RobotProxy;

public class KeyReleaseAction implements MacroAction {
	private int keyCode;
	
	/** Deserialization constructor. */
	protected KeyReleaseAction() {}
	
	public KeyReleaseAction(int keyCode) {
		this.keyCode = keyCode;
	}
	
	@Override
	public void run(RobotProxy robot) {
		robot.releaseKey(keyCode);
	}
}
