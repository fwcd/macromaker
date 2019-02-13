package fwcd.macromaker.ui;

import java.awt.AWTException;
import java.awt.Robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fwcd.fructose.exception.Rethrow;
import fwcd.macromaker.model.RobotProxy;

/**
 * An AWT-based robot backend.
 */
public class AwtRobotProxy implements RobotProxy {
	private static final Logger LOG = LoggerFactory.getLogger(AwtRobotProxy.class);
	private final Robot robot;

	public AwtRobotProxy() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			throw new Rethrow(e);
		}
	}
	
	@Override
	public void pressKey(int keyCode) {
		LOG.debug("Pressed key {}", keyCode);
		robot.keyPress(keyCode);
	}
	
	@Override
	public void releaseKey(int keyCode) {
		LOG.debug("Released key {}", keyCode);
		robot.keyRelease(keyCode);
	}
	
	@Override
	public void moveMouse(int x, int y) {
		LOG.debug("Moved mouse to x = {}, y = {}", x, y);
		robot.mouseMove(x, y);
	}
	
	@Override
	public void pressMouse(int buttons) {
		LOG.debug("Pressed mouse with button mask {}", buttons);
		robot.mousePress(buttons);
	}
	
	@Override
	public void releaseMouse(int buttons) {
		LOG.debug("Released mouse with button mask {}", buttons);
		robot.mouseRelease(buttons);
	}
	
	@Override
	public void scroll(int mouseWheelDelta) {
		LOG.debug("Scrolled by {}", mouseWheelDelta);
		robot.mouseWheel(mouseWheelDelta);
	}
}
