package fwcd.macromaker.ui;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

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
import fwcd.macromaker.model.Macro;
import fwcd.macromaker.model.action.KeyPressAction;
import fwcd.macromaker.model.action.MacroAction;
import fwcd.macromaker.model.action.MouseMoveAction;
import fwcd.macromaker.model.action.MousePressAction;
import fwcd.macromaker.model.action.MouseReleaseAction;
import fwcd.macromaker.model.action.MouseScrollAction;
import fwcd.macromaker.model.action.TimedAction;

/**
 * A facility to record macros using native listeners.
 */
public class MacroRecorder {
	private final NativeKeyListener keyListener;
	private final NativeMouseListener mouseListener;
	private final NativeMouseMotionListener mouseMotionListener;
	private final NativeMouseWheelListener mouseWheelListener;
	
	private final List<MacroAction> actions = new ArrayList<>();
	private final Stopwatch watch = new Stopwatch();
	private long durationMs = 0;
	
	public MacroRecorder() {
		keyListener = new SwingKeyAdapter() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void keyPressed(KeyEvent event) {
				final int awtKeyCode = event.getKeyCode();
				final long millis = watch.getMillis();
				addAction(new KeyPressAction(awtKeyCode), millis);
				watch.start();
			}

			@Override
			public void keyReleased(KeyEvent event) {
				final int awtKeyCode = event.getKeyCode();
				final long millis = watch.getMillis();
				addAction(new KeyPressAction(awtKeyCode), millis);
				watch.start();
			}
		};
		mouseListener = new NativeMouseAdapter() {
			@Override
			public void nativeMouseReleased(NativeMouseEvent nativeEvent) {
				final int buttonCombo = InputEvent.getMaskForButton(nativeEvent.getButton());
				final long millis = watch.getMillis();
				addAction(new MouseReleaseAction(buttonCombo), millis);
				watch.start();
			}
			
			@Override
			public void nativeMousePressed(NativeMouseEvent nativeEvent) {
				final int buttonCombo = InputEvent.getMaskForButton(nativeEvent.getButton());
				final long millis = watch.getMillis();
				addAction(new MousePressAction(buttonCombo), millis);
				watch.start();
			}
		};
		mouseMotionListener = new NativeMouseMotionAdapter() {
			@Override
			public void nativeMouseMoved(NativeMouseEvent nativeEvent) {
				final int x = nativeEvent.getX();
				final int y = nativeEvent.getY();
				final long millis = watch.getMillis();
				addAction(new MouseMoveAction(x, y), millis);
				watch.start();
			}

			@Override
			public void nativeMouseDragged(NativeMouseEvent nativeEvent) {
				nativeMouseMoved(nativeEvent);
			}
		};
		mouseWheelListener = new NativeMouseWheelAdapter() {
			@Override
			public void nativeMouseWheelMoved(NativeMouseWheelEvent nativeEvent) {
				final int mouseWheelDelta = nativeEvent.getScrollAmount();
				final long millis = watch.getMillis();
				addAction(new MouseScrollAction(mouseWheelDelta), millis);
				watch.start();
			}
		};
	}
	
	public void reset() {
		watch.stop();
		actions.clear();
	}
	
	private void addAction(MacroAction action, long delayMs) {
		actions.add(new TimedAction(action, delayMs));
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
	
	public Macro getRecordedMacro() {
		return new Macro(actions);
	}
}
