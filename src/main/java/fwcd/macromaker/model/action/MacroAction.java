package fwcd.macromaker.model.action;

import fwcd.macromaker.model.RobotProxy;

public interface MacroAction {
	void run(RobotProxy robot);
	
	default long getTimeStamp() { return 0; }
}
