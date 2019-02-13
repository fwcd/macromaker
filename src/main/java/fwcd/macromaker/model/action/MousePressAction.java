package fwcd.macromaker.model.action;

import com.google.gson.annotations.SerializedName;

import fwcd.macromaker.model.RobotProxy;

public class MousePressAction implements MacroAction {
	@SerializedName("btns")
	private int buttons;
	
	/** Deserialization constructor. */
	protected MousePressAction() {}
	
	public MousePressAction(int buttons) {
		this.buttons = buttons;
	}
	
	@Override
	public void run(RobotProxy robot) {
		robot.pressMouse(buttons);
	}
}
