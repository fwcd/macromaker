package fwcd.macromaker.ui.dispatch;

import java.util.ArrayList;
import java.util.List;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;
import org.jnativehook.mouse.NativeMouseMotionListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

public class GlobalEventDispatcher {
	private final List<DispatchMouseListener> mouseListeners = new ArrayList<>();
	private final List<DispatchMouseMotionListener> mouseMotionListeners = new ArrayList<>();
	private final List<DispatchMouseWheelListener> mouseWheelListeners = new ArrayList<>();
	private final List<DispatchKeyListener> keyListeners = new ArrayList<>();
	private final NativeMouseListener nativeMouseListener = new NativeMouseListener() {
		@Override
		public void nativeMouseReleased(NativeMouseEvent nativeEvent) {
			for (DispatchMouseListener listener : mouseListeners) {
				if (listener.mouseReleased(nativeEvent)) break;
			}
		}
	
		@Override
		public void nativeMousePressed(NativeMouseEvent nativeEvent) {
			for (DispatchMouseListener listener : mouseListeners) {
				if (listener.mousePressed(nativeEvent)) break;
			}
		}
	
		@Override
		public void nativeMouseClicked(NativeMouseEvent nativeEvent) {
			for (DispatchMouseListener listener : mouseListeners) {
				if (listener.mouseClicked(nativeEvent)) break;
			}
		}
	};
	private final NativeMouseMotionListener nativeMouseMotionListener = new NativeMouseMotionListener() {
		@Override
		public void nativeMouseMoved(NativeMouseEvent nativeEvent) {
			for (DispatchMouseMotionListener listener : mouseMotionListeners) {
				if (listener.mouseMoved(nativeEvent)) break;
			}
		}
	
		@Override
		public void nativeMouseDragged(NativeMouseEvent nativeEvent) {
			for (DispatchMouseMotionListener listener : mouseMotionListeners) {
				if (listener.mouseDragged(nativeEvent)) break;
			}
		}
	};
	private final NativeMouseWheelListener nativeMouseWheelListener = new NativeMouseWheelListener() {
		@Override
		public void nativeMouseWheelMoved(NativeMouseWheelEvent nativeEvent) {
			for (DispatchMouseWheelListener listener : mouseWheelListeners) {
				if (listener.mouseWheelMoved(nativeEvent)) break;
			}
		}
	};
	private final NativeKeyListener nativeKeyListener = new NativeKeyListener() {
		@Override
		public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
			for (DispatchKeyListener listener : keyListeners) {
				if (listener.keyTyped(nativeEvent)) break;
			}
		}
	
		@Override
		public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
			for (DispatchKeyListener listener : keyListeners) {
				if (listener.keyReleased(nativeEvent)) break;
			}
		}
	
		@Override
		public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
			for (DispatchKeyListener listener : keyListeners) {
				if (listener.keyPressed(nativeEvent)) break;
			}
		}
	};
	
	public void registerListeners() {
		GlobalScreen.addNativeKeyListener(nativeKeyListener);
		GlobalScreen.addNativeMouseListener(nativeMouseListener);
		GlobalScreen.addNativeMouseMotionListener(nativeMouseMotionListener);
		GlobalScreen.addNativeMouseWheelListener(nativeMouseWheelListener);
	}
	
	public void unregisterListeners() {
		GlobalScreen.removeNativeKeyListener(nativeKeyListener);
		GlobalScreen.removeNativeMouseListener(nativeMouseListener);
		GlobalScreen.removeNativeMouseMotionListener(nativeMouseMotionListener);
		GlobalScreen.removeNativeMouseWheelListener(nativeMouseWheelListener);
	}
}
