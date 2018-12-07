package fwcd.macromaker;

import fwcd.fructose.swing.PanelFrame;
import fwcd.macromaker.core.MacroMakerController;

public class MacroMakerMain {
	public static void main(String[] args) {
		PanelFrame frame = new PanelFrame("MacroMaker", 350, 150, new MacroMakerController());
		frame.setAlwaysOnTop(true);
	}
}
