package fwcd.macromaker.model.action;

import fwcd.macromaker.model.RobotProxy;

public class MouseReleaseAction implements MacroAction {
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
