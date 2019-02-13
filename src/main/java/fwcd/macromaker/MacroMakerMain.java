package fwcd.macromaker;

import fwcd.fructose.swing.PanelFrame;
import fwcd.macromaker.core.MacroMakerViewController;

public class MacroMakerMain {
	public static void main(String[] args) {
		MacroMakerViewController vc = new MacroMakerViewController();
		PanelFrame frame = new PanelFrame("MacroMaker", 350, 150, vc.getView().getComponent());
		frame.setAlwaysOnTop(true);
	}
}
