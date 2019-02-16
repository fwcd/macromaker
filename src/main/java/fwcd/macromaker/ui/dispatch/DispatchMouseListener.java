package fwcd.macromaker.ui.dispatch;

import org.jnativehook.mouse.NativeMouseEvent;

public interface DispatchMouseListener {
	default boolean mousePressed(NativeMouseEvent event) { return false; }
	
	default boolean mouseClicked(NativeMouseEvent event) { return false; }
	
	default boolean mouseReleased(NativeMouseEvent event) { return false; }
}
