package fwcd.macromaker.model.action;

import fwcd.macromaker.model.RobotProxy;

public class KeyPressAction implements MacroAction {
	private int keyCode;
	
	/** Deserialization constructor. */
	protected KeyPressAction() {}
	
	public KeyPressAction(int keyCode) {
		this.keyCode = keyCode;
	}
	
	@Override
	public void run(RobotProxy robot) {
		robot.pressKey(keyCode);
	}
}
