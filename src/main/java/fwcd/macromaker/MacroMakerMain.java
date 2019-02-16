package fwcd.macromaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import fwcd.fructose.swing.PanelFrame;
import fwcd.fructose.time.Stopwatch;
import fwcd.macromaker.model.shortcuts.KeyboardShortcutsModel;
import fwcd.macromaker.ui.MacroMakerMenuBar;
import fwcd.macromaker.ui.MacroMakerViewController;

public class MacroMakerMain {
	private static final Logger LOG = LoggerFactory.getLogger(MacroMakerMain.class);
	
	public static void main(String[] args) {
		// Reroute JNativeHook loggers to SLF4J at the fine -> debug level
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
		
		Stopwatch watch = new Stopwatch();
		watch.start();
		
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "MacroMaker");
		
		KeyboardShortcutsModel shortcuts = new KeyboardShortcutsModel();
		MacroMakerViewController vc = new MacroMakerViewController(shortcuts);
		PanelFrame frame = new PanelFrame("MacroMaker", 350, 150, vc.getView().getComponent());
		frame.setJMenuBar(new MacroMakerMenuBar(vc, shortcuts).getComponent());
		frame.revalidate();
		frame.setAlwaysOnTop(true);
		
		// Ensure that native resources are closed when the JVM terminates
		Runtime.getRuntime().addShutdownHook(new Thread(vc::close));
		
		LOG.info("Launched application in {} ms", watch.stop());
	}
}
