package fwcd.macromaker.ui;

import fwcd.macromaker.model.Macro;
import fwcd.macromaker.model.RobotProxy;
import fwcd.macromaker.model.action.MacroAction;

public class MacroMakerViewController implements MacroMakerResponder {
	private final MacroMakerView view;
	
	private RobotProxy robot = new AwtRobotProxy();
	private MacroRecorder recorder = new MacroRecorder();
	private Macro macro = null;
	private Thread macroRunner = null;
	
	public MacroMakerViewController() {
		view = new MacroMakerView(this);
	}
	
	@Override
	public void record() {
		recorder.reset();
		recorder.startRecording();
		view.setStatus("Recording...");
	}
	
	@Override
	public void stop() {
		if (macroRunner != null && macroRunner.isAlive()) {
			macroRunner.interrupt();
		} else if (recorder != null) {
			recorder.stopRecording();
			macro = recorder.getRecordedMacro();
			view.setStatus("Idling...");
		}
	}
	
	@Override
	public void play() {
		if (macro == null) {
			view.showMessage("No macro recorded.");
		} else {
			view.setStatus("Playing...");
			final int repeats = view.getRepeats();
			
			macroRunner = new Thread(() -> {
				int i = 0;
				while (i < repeats && !Thread.interrupted()) {
					try {
						runMacroWithProgress();
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
	
	private void runMacroWithProgress() throws InterruptedException {
		if (macro == null) {
			throw new IllegalStateException("Tried to run non-present macro");
		}
		
		double elapsedPercent = 0;
		double duration = macro.getDurationMs();
		long elapsed = 0;
		
		for (MacroAction action : macro.getActions()) {
			action.run(robot);
			elapsed += action.getDelayMs();
			elapsedPercent = elapsed / duration;
			view.updateProgress(elapsedPercent);
		}
	}
	
	public MacroMakerView getView() {
		return view;
	}
}
