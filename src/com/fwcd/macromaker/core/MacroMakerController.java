package com.fwcd.macromaker.core;

import javax.swing.JComponent;

import com.fwcd.fructose.swing.Viewable;

public class MacroMakerController implements Viewable {
	private final MacroMakerView view;
	
	private Macro currentMacro = null;
	private Thread macroRunner = null;
	
	public MacroMakerController() {
		view = new MacroMakerView(this);
	}

	public void record() {
		currentMacro = new Macro();
		currentMacro.startRecording();
		view.setStatus("Recording...");
	}
	
	public void stop() {
		if (macroRunner != null && macroRunner.isAlive()) {
			macroRunner.interrupt();
		} else if (currentMacro != null) {
			currentMacro.stopRecording();
			view.setStatus("Idling...");
		}
	}
	
	public void play() {
		if (currentMacro == null) {
			view.showMessage("No macro recorded.");
		} else {
			view.setStatus("Playing...");
			final int repeats = view.getRepeats();
			
			macroRunner = new Thread(() -> {
				int i = 0;
				while (i < repeats && !Thread.interrupted()) {
					try {
						currentMacro.play(view::updateProgress);
						i++;
					} catch (InterruptedException e) {
						break;
					}
				}
				view.setStatus("Idling...");
			});
			macroRunner.start();
		}
	}
	
	@Override
	public JComponent getView() {
		return view.getView();
	}
}
