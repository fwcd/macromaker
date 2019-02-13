package fwcd.macromaker.model.action;

import com.google.gson.annotations.SerializedName;

import fwcd.macromaker.model.RobotProxy;

public class KeyReleaseAction implements MacroAction {
	@SerializedName("ky")
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
