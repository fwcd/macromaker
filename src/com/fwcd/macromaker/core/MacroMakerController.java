package com.fwcd.macromaker.core;

import javax.swing.JComponent;

import com.fwcd.fructose.swing.Viewable;

public class MacroMakerController implements Viewable {
	private final MacroMakerView view;
	
	private Macro currentMakro = null;
	private Thread makroRunner = null;
	
	public MacroMakerController() {
		view = new MacroMakerView(this);
	}

	public void record() {
		currentMakro = new Macro();
		currentMakro.startRecording();
		view.setStatus("Recording...");
	}
	
	public void stop() {
		if (makroRunner != null && makroRunner.isAlive()) {
			makroRunner.interrupt();
		} else if (currentMakro != null) {
			currentMakro.stopRecording();
			view.setStatus("Idling...");
		}
	}
	
	public void play() {
		if (currentMakro == null) {
			view.showMessage("No makro recorded.");
		} else {
			view.setStatus("Playing...");
			final int repeats = view.getRepeats();
			
			makroRunner = new Thread(() -> {
				int i = 0;
				while (i < repeats && !Thread.interrupted()) {
					try {
						currentMakro.play(view::updateProgress);
						i++;
					} catch (InterruptedException e) {
						break;
					}
				}
				view.setStatus("Idling...");
			});
			makroRunner.start();
		}
	}
	
	@Override
	public JComponent getView() {
		return view.getView();
	}
}
