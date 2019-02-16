package fwcd.macromaker.ui.dispatch;

import org.jnativehook.mouse.NativeMouseWheelEvent;

public interface DispatchMouseWheelListener {
	default boolean mouseWheelMoved(NativeMouseWheelEvent event) { return false; }
}
