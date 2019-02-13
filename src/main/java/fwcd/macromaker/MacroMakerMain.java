package fwcd.macromaker;

import fwcd.fructose.swing.PanelFrame;
import fwcd.macromaker.ui.MacroMakerMenuBar;
import fwcd.macromaker.ui.MacroMakerViewController;

public class MacroMakerMain {
	public static void main(String[] args) {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "MacroMaker");
		
		MacroMakerViewController vc = new MacroMakerViewController();
		PanelFrame frame = new PanelFrame("MacroMaker", 350, 150, vc.getView().getComponent());
		frame.setJMenuBar(new MacroMakerMenuBar(vc).getComponent());
		frame.revalidate();
		frame.setAlwaysOnTop(true);
	}
}
