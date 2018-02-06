package com.fwcd.macromaker;

import com.fwcd.fructose.swing.PanelFrame;
import com.fwcd.macromaker.core.MacroMakerController;

public class MacroMakerMain {
	public static void main(String[] args) {
		PanelFrame frame = new PanelFrame("MakroMaker", 350, 150, new MacroMakerController());
		frame.setAlwaysOnTop(true);
	}
}