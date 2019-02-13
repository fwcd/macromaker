package fwcd.macromaker.model.action;

import fwcd.macromaker.model.RobotProxy;

public class MouseMoveAction implements MacroAction {
	private int x;
	private int y;
	
	/** Deserialization constructor. */
	protected MouseMoveAction() {}
	
	public MouseMoveAction(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void run(RobotProxy robot) {
		robot.moveMouse(x, y);
	}
}
