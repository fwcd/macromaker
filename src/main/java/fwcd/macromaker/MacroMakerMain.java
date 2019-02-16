package fwcd.macromaker;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import fwcd.fructose.exception.Rethrow;
import fwcd.fructose.swing.PanelFrame;
import fwcd.fructose.time.Stopwatch;
import fwcd.macromaker.model.shortcuts.KeyboardShortcut;
import fwcd.macromaker.model.shortcuts.KeyboardShortcutsModel;
import fwcd.macromaker.ui.CommonShortcuts;
import fwcd.macromaker.ui.MacroMakerMenuBar;
import fwcd.macromaker.ui.MacroMakerViewController;

public class MacroMakerMain {
	private static final Logger LOG = LoggerFactory.getLogger(MacroMakerMain.class);
	
	public static void main(String[] args) throws NativeHookException {
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		// Uncomment to enable JNativeHook logging
		// SLF4JBridgeHandler.install();
		
		GlobalScreen.registerNativeHook();
		
		Stopwatch watch = new Stopwatch();
		watch.start();
		
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "MacroMaker");
		
		// Setup shortcut model and default shortcuts
		KeyboardShortcutsModel shortcuts = new KeyboardShortcutsModel();
		shortcuts.put(CommonShortcuts.START_RECORDING, new KeyboardShortcut("Start recording", "Shift+F8"));
		shortcuts.put(CommonShortcuts.STOP_RECORDING, new KeyboardShortcut("Stop recording", "Shift+F9"));
		
		MacroMakerViewController vc = new MacroMakerViewController(shortcuts);
		PanelFrame frame = new PanelFrame("MacroMaker", 350, 150, vc.getView().getComponent());
		frame.setJMenuBar(new MacroMakerMenuBar(vc, shortcuts).getComponent());
		frame.revalidate();
		frame.setAlwaysOnTop(true);
		
		// Ensure that native resources are closed when the JVM terminates
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			vc.close();
			try {
				GlobalScreen.registerNativeHook();
			} catch (NativeHookException e) {
				throw new Rethrow(e);
			}
		}));
		
		LOG.info("Launched application in {} ms", watch.stop());
	}
}
