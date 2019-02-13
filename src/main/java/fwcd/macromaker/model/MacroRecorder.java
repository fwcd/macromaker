package fwcd.macromaker.model;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.DoubleConsumer;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.keyboard.SwingKeyAdapter;
import org.jnativehook.mouse.NativeMouseAdapter;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;
import org.jnativehook.mouse.NativeMouseMotionAdapter;
import org.jnativehook.mouse.NativeMouseMotionListener;
import org.jnativehook.mouse.NativeMouseWheelAdapter;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

import fwcd.fructose.exception.Rethrow;
import fwcd.fructose.time.Stopwatch;

public class MacroRecorder {
	private final NativeKeyListener keyListener;
	private final NativeMouseListener mouseListener;
	private final NativeMouseMotionListener mouseMotionListener;
	private final NativeMouseWheelListener mouseWheelListener;
	
	private final Map<Long, Runnable> actions = new LinkedHashMap<>();
	private final Robot robot;
	private final Stopwatch watch = new Stopwatch();
	private long durationMs = 0;
	
	public MacroRecorder() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			throw new Rethrow(e);
		}
		keyListener = new SwingKeyAdapter() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void keyPressed(KeyEvent event) {
				final int awtKeyCode = event.getKeyCode();
				final long millis = watch.getMillis();
				actions.put(millis, () -> robot.keyPress(awtKeyCode));
			}

			@Override
			public void keyReleased(KeyEvent event) {
				final int awtKeyCode = event.getKeyCode();
				final long millis = watch.getMillis();
				actions.put(millis, () -> robot.keyRelease(awtKeyCode));
			}
			
		};
		mouseListener = new NativeMouseAdapter() {
			
			@Override
			public void nativeMouseReleased(NativeMouseEvent nativeEvent) {
				final int buttonCombo = InputEvent.getMaskForButton(nativeEvent.getButton());
				final long millis = watch.getMillis();
				actions.put(millis, () -> robot.mouseRelease(buttonCombo));
			}
			
			@Override
			public void nativeMousePressed(NativeMouseEvent nativeEvent) {
				final int buttonCombo = InputEvent.getMaskForButton(nativeEvent.getButton());
				final long millis = watch.getMillis();
				actions.put(millis, () -> robot.mousePress(buttonCombo));
			}
			
		};
		mouseMotionListener = new NativeMouseMotionAdapter() {

			@Override
			public void nativeMouseMoved(NativeMouseEvent nativeEvent) {
				final int x = nativeEvent.getX();
				final int y = nativeEvent.getY();
				final long millis = watch.getMillis();
				actions.put(millis, () -> robot.mouseMove(x, y));
			}

			@Override
			public void nativeMouseDragged(NativeMouseEvent nativeEvent) {
				final int x = nativeEvent.getX();
				final int y = nativeEvent.getY();
				final long millis = watch.getMillis();
				actions.put(millis, () -> robot.mouseMove(x, y));
			}
			
		};
		mouseWheelListener = new NativeMouseWheelAdapter() {

			@Override
			public void nativeMouseWheelMoved(NativeMouseWheelEvent nativeEvent) {
				final int mouseWheelDelta = nativeEvent.getScrollAmount();
				final long millis = watch.getMillis();
				actions.put(millis, () -> robot.mouseWheel(mouseWheelDelta));
			}
			
		};
	}
	
	public void startRecording() {
		watch.start();
		GlobalScreen.addNativeKeyListener(keyListener);
		GlobalScreen.addNativeMouseListener(mouseListener);
		GlobalScreen.addNativeMouseMotionListener(mouseMotionListener);
		GlobalScreen.addNativeMouseWheelListener(mouseWheelListener);
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
			throw new Rethrow(e);
		}
	}
	
	public void stopRecording() {
		if (watch.isRunning()) {
			durationMs = watch.getMillis();
		}
		watch.stop();
		GlobalScreen.removeNativeKeyListener(keyListener);
		GlobalScreen.removeNativeMouseListener(mouseListener);
		GlobalScreen.removeNativeMouseMotionListener(mouseMotionListener);
		GlobalScreen.removeNativeMouseWheelListener(mouseWheelListener);
		try {
			GlobalScreen.unregisterNativeHook();
		} catch (NativeHookException e) {
			throw new Rethrow(e);
		}
	}
	
	public void play(DoubleConsumer progressView) throws InterruptedException {
		double elapsedPercent = 0;
		long elapsed = 0;
		
		for (long timeStamp : actions.keySet()) {
			Runnable action = actions.get(timeStamp);
			Thread.sleep(timeStamp - elapsed);
			action.run();
			
			elapsed = timeStamp;
			elapsedPercent = elapsed / (double) durationMs;
			progressView.accept(elapsedPercent);
		}
	}
}
