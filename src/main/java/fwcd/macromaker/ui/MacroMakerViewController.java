package fwcd.macromaker.ui;

import fwcd.macromaker.model.Macro;

public class MacroMakerViewController implements MacroMakerResponder {
	private final MacroMakerView view;
	
	private Macro currentMacro = null;
	private Thread macroRunner = null;
	
	public MacroMakerViewController() {
		view = new MacroMakerView(this);
	}
	
	@Override
	public void record() {
		currentMacro = new Macro();
		currentMacro.startRecording();
		view.setStatus("Recording...");
	}
	
	@Override
	public void stop() {
		if (macroRunner != null && macroRunner.isAlive()) {
			macroRunner.interrupt();
		} else if (currentMacro != null) {
			currentMacro.stopRecording();
			view.setStatus("Idling...");
		}
	}
	
	@Override
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
	
	public MacroMakerView getView() {
		return view;
	}
}
