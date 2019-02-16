package fwcd.macromaker.ui.dispatch;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.SwingKeyAdapter;

public abstract class SwingDispatchKeyListener extends SwingKeyAdapter implements DispatchKeyListener {
	private static final long serialVersionUID = -9056490518178336815L;
	
	@Override
	public final boolean keyPressed(NativeKeyEvent event) {
		nativeKeyPressed(event);
		return true;
	}
	
	@Override
	public final boolean keyReleased(NativeKeyEvent event) {
		nativeKeyReleased(event);
		return true;
	}
	
	@Override
	public final boolean keyTyped(NativeKeyEvent event) {
		nativeKeyTyped(event);
		return true;
	}
	
	@Override
	public final void nativeKeyPressed(NativeKeyEvent nativeEvent) {
		super.nativeKeyPressed(nativeEvent);
	}
	
	@Override
	public final void nativeKeyReleased(NativeKeyEvent nativeEvent) {
		super.nativeKeyReleased(nativeEvent);
	}
	
	@Override
	public final void nativeKeyTyped(NativeKeyEvent nativeEvent) {
		super.nativeKeyTyped(nativeEvent);
	}
}
