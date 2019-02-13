package fwcd.macromaker.model.action;

import fwcd.macromaker.model.RobotProxy;

public interface MacroAction {
	void run(RobotProxy robot);
	
	default long getDelayMs() { return 0; }
}
