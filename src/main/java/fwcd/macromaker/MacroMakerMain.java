package fwcd.macromaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import fwcd.fructose.swing.PanelFrame;
import fwcd.fructose.time.Stopwatch;
import fwcd.macromaker.ui.MacroMakerMenuBar;
import fwcd.macromaker.ui.MacroMakerViewController;

public class MacroMakerMain {
	private static final Logger LOG = LoggerFactory.getLogger(MacroMakerMain.class);
	
	public static void main(String[] args) {
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
		
		Stopwatch watch = new Stopwatch();
		watch.start();
		
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "MacroMaker");
		
		MacroMakerViewController vc = new MacroMakerViewController();
		PanelFrame frame = new PanelFrame("MacroMaker", 350, 150, vc.getView().getComponent());
		frame.setJMenuBar(new MacroMakerMenuBar(vc).getComponent());
		frame.revalidate();
		frame.setAlwaysOnTop(true);
		
		LOG.info("Launched application in {} ms", watch.stop());
	}
}
