package fwcd.macromaker.ui;

import java.awt.AWTException;
import java.awt.Robot;

import fwcd.fructose.exception.Rethrow;
import fwcd.macromaker.model.RobotProxy;

/**
 * An AWT-based robot backend.
 */
public class AwtRobotProxy implements RobotProxy {
	private final Robot robot;

	public AwtRobotProxy() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			throw new Rethrow(e);
		}
	}
	
	@Override
	public void pressKey(int keyCode) { robot.keyPress(keyCode); }
	
	@Override
	public void releaseKey(int keyCode) { robot.keyRelease(keyCode); }
	
	@Override
	public void moveMouse(int x, int y) { robot.mouseMove(x, y); }
	
	@Override
	public void pressMouse(int buttons) { robot.mousePress(buttons); }
	
	@Override
	public void releaseMouse(int buttons) { robot.mouseRelease(buttons); }
	
	@Override
	public void scroll(int mouseWheelDelta) { robot.mouseWheel(mouseWheelDelta); }
}
