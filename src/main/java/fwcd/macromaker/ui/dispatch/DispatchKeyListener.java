package fwcd.macromaker.ui.dispatch;

import org.jnativehook.keyboard.NativeKeyEvent;

public interface DispatchKeyListener {
	default boolean keyPressed(NativeKeyEvent event) { return false; }
	
	default boolean keyTyped(NativeKeyEvent event) { return false; }
	
	default boolean keyReleased(NativeKeyEvent event) { return false; }
}
