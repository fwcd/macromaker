package fwcd.macromaker.ui.dispatch;

import org.jnativehook.mouse.NativeMouseEvent;

public interface DispatchMouseMotionListener {
	default boolean mouseMoved(NativeMouseEvent event) { return false; }
	
	default boolean mouseDragged(NativeMouseEvent event) { return false; }
}
