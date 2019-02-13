package fwcd.macromaker.model.action;

import fwcd.macromaker.model.RobotProxy;

public class MousePressAction implements MacroAction {
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
