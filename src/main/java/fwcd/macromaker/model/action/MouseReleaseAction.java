package fwcd.macromaker.model.action;

import com.google.gson.annotations.SerializedName;

import fwcd.macromaker.model.RobotProxy;

public class MouseReleaseAction implements MacroAction {
	@SerializedName("btns")
	private int buttons;
	
	/** Deserialization constructor. */
	protected MouseReleaseAction() {}
	
	public MouseReleaseAction(int buttons) {
		this.buttons = buttons;
	}
	
	@Override
	public void run(RobotProxy robot) {
		robot.releaseMouse(buttons);
	}
}
