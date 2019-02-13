package fwcd.macromaker.model.action;

import com.google.gson.annotations.SerializedName;

import fwcd.macromaker.model.RobotProxy;

public class MouseScrollAction implements MacroAction {
	@SerializedName("mwd")
	private int mouseWheelDelta;
	
	/** Deserialization constructor. */
	protected MouseScrollAction() {}
	
	public MouseScrollAction(int mouseWheelDelta) {
		this.mouseWheelDelta = mouseWheelDelta;
	}
	
	@Override
	public void run(RobotProxy robot) {
		robot.scroll(mouseWheelDelta);
	}
}
