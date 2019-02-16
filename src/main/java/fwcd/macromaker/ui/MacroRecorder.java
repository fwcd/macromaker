package fwcd.macromaker.ui;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseWheelEvent;

import fwcd.fructose.time.Stopwatch;
import fwcd.macromaker.model.Macro;
import fwcd.macromaker.model.action.KeyPressAction;
import fwcd.macromaker.model.action.KeyReleaseAction;
import fwcd.macromaker.model.action.MacroAction;
import fwcd.macromaker.model.action.MouseMoveAction;
import fwcd.macromaker.model.action.MousePressAction;
import fwcd.macromaker.model.action.MouseReleaseAction;
import fwcd.macromaker.model.action.MouseScrollAction;
import fwcd.macromaker.model.action.TimedAction;
import fwcd.macromaker.ui.dispatch.DispatchKeyListener;
import fwcd.macromaker.ui.dispatch.DispatchMouseListener;
import fwcd.macromaker.ui.dispatch.DispatchMouseMotionListener;
import fwcd.macromaker.ui.dispatch.DispatchMouseWheelListener;
import fwcd.macromaker.ui.dispatch.GlobalEventDispatcher;
import fwcd.macromaker.ui.dispatch.SwingDispatchKeyListener;

/**
 * A facility to record macros using native listeners.
 */
public class MacroRecorder {
	private final DispatchKeyListener keyListener;
	private final DispatchMouseListener mouseListener;
	private final DispatchMouseMotionListener mouseMotionListener;
	private final DispatchMouseWheelListener mouseWheelListener;
	
	private final List<MacroAction> actions = new ArrayList<>();
	private final Stopwatch watch = new Stopwatch();
	
	public MacroRecorder() {
		keyListener = new SwingDispatchKeyListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void keyPressed(KeyEvent event) {
				final int awtKeyCode = event.getKeyCode();
				final long millis = watch.getMillis();
				addAction(new KeyPressAction(awtKeyCode), millis);
			}

			@Override
			public void keyReleased(KeyEvent event) {
				final int awtKeyCode = event.getKeyCode();
				final long millis = watch.getMillis();
				addAction(new KeyReleaseAction(awtKeyCode), millis);
			}
		};
		mouseListener = new DispatchMouseListener() {
			@Override
			public boolean mouseReleased(NativeMouseEvent nativeEvent) {
				final int buttonCombo = InputEvent.getMaskForButton(nativeEvent.getButton());
				final long millis = watch.getMillis();
				addAction(new MouseReleaseAction(buttonCombo), millis);
				return true;
			}
			
			@Override
			public boolean mousePressed(NativeMouseEvent nativeEvent) {
				final int buttonCombo = InputEvent.getMaskForButton(nativeEvent.getButton());
				final long millis = watch.getMillis();
				addAction(new MousePressAction(buttonCombo), millis);
				return true;
			}
		};
		mouseMotionListener = new DispatchMouseMotionListener() {
			@Override
			public boolean mouseMoved(NativeMouseEvent nativeEvent) {
				final int x = nativeEvent.getX();
				final int y = nativeEvent.getY();
				final long millis = watch.getMillis();
				addAction(new MouseMoveAction(x, y), millis);
				return true;
			}

			@Override
			public boolean mouseDragged(NativeMouseEvent nativeEvent) {
				return mouseMoved(nativeEvent);
			}
		};
		mouseWheelListener = new DispatchMouseWheelListener() {
			@Override
			public boolean mouseWheelMoved(NativeMouseWheelEvent nativeEvent) {
				final int mouseWheelDelta = nativeEvent.getScrollAmount();
				final long millis = watch.getMillis();
				addAction(new MouseScrollAction(mouseWheelDelta), millis);
				return true;
			}
		};
	}
	
	public void reset() {
		watch.stop();
		actions.clear();
	}
	
	private void addAction(MacroAction action, long timeStamp) {
		actions.add(new TimedAction(action, timeStamp));
	}
	
	public void startRecording(GlobalEventDispatcher eventDispatcher) {
		watch.start();
		eventDispatcher.addKeyListener(keyListener);
		eventDispatcher.addMouseListener(mouseListener);
		eventDispatcher.addMouseMotionListener(mouseMotionListener);
		eventDispatcher.addMouseWheelListener(mouseWheelListener);
	}
	
	public void stopRecording(GlobalEventDispatcher eventDispatcher) {
		watch.stop();
		eventDispatcher.removeKeyListener(keyListener);
		eventDispatcher.removeMouseListener(mouseListener);
		eventDispatcher.removeMouseMotionListener(mouseMotionListener);
		eventDispatcher.removeMouseWheelListener(mouseWheelListener);
	}
	
	public Macro getRecordedMacro() {
		return new Macro(actions);
	}
}
