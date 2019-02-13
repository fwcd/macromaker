package fwcd.macromaker.ui;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

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
		LOG.debug("Pressed key {}", KeyEvent.getKeyText(keyCode));
		try {
			robot.keyPress(keyCode);
		} catch (IllegalArgumentException e) {
			LOG.error("Error while pressing key " + KeyEvent.getKeyText(keyCode), e);
		}
	}
	
	@Override
	public void releaseKey(int keyCode) {
		LOG.debug("Released key {}", KeyEvent.getKeyText(keyCode));
		try {
			robot.keyRelease(keyCode);
		} catch (IllegalArgumentException e) {
			LOG.error("Error while releasing key " + KeyEvent.getKeyText(keyCode), e);
		}
	}
	
	@Override
	public void moveMouse(int x, int y) {
		LOG.debug("Moved mouse to x = {}, y = {}", x, y);
		try {
			robot.mouseMove(x, y);
		} catch (IllegalArgumentException e) {
			LOG.error("Error while moving mouse to x = " + x + ", y = " + y, e);
		}
	}
	
	@Override
	public void pressMouse(int buttons) {
		LOG.debug("Pressed mouse with button mask {}", buttons);
		try {
			robot.mousePress(buttons);
		} catch (IllegalArgumentException e) {
			LOG.error("Error while pressing mouse with button mask " + buttons, e);
		}
	}
	
	@Override
	public void releaseMouse(int buttons) {
		LOG.debug("Released mouse with button mask {}", buttons);
		try {
			robot.mouseRelease(buttons);
		} catch (IllegalArgumentException e) {
			LOG.error("Error while releasing mouse with button mask " + buttons, e);
		}
	}
	
	@Override
	public void scroll(int mouseWheelDelta) {
		LOG.debug("Scrolled by {}", mouseWheelDelta);
		try {
			robot.mouseWheel(mouseWheelDelta);
		} catch (IllegalArgumentException e) {
			LOG.error("Error while scrolling by " + mouseWheelDelta, e);
		}
	}
}
