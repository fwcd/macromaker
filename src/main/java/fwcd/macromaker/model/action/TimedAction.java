package fwcd.macromaker.model.action;

import com.google.gson.annotations.SerializedName;

import fwcd.macromaker.model.RobotProxy;

/**
 * An macro action decorator featuring a time stamp.
 */
public class TimedAction implements MacroAction {
	@SerializedName("a")
	private MacroAction action;
	@SerializedName("ts")
	private long timeStamp;

	/** Deserialization constructor. */
	protected TimedAction() {}

	public TimedAction(MacroAction action, long timeStamp) {
		this.action = action;
		this.timeStamp = timeStamp;
	}

	public MacroAction getAction() {
		return action;
	}
	
	@Override
	public long getTimeStamp() {
		return timeStamp;
	}

	@Override
	public void run(RobotProxy robot) {
		action.run(robot);
	}
}
