package fwcd.macromaker.model.action;

import com.google.gson.annotations.SerializedName;

import fwcd.macromaker.model.RobotProxy;

public class KeyPressAction implements MacroAction {
	@SerializedName("ky")
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
