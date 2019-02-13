package fwcd.macromaker.model.action;

import fwcd.macromaker.model.RobotProxy;

public class MacroKeyPressAction implements MacroAction {
	private int keyCode;
	
	/** Deserialization constructor. */
	protected MacroKeyPressAction() {}
	
	public MacroKeyPressAction(int keyCode) {
		this.keyCode = keyCode;
	}
	
	@Override
	public void run(RobotProxy robot) {
		robot.pressKey(keyCode);
	}
}
