package fwcd.macromaker.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import fwcd.macromaker.model.Macro;
import fwcd.macromaker.model.MacroSerializer;
import fwcd.macromaker.model.RobotProxy;
import fwcd.macromaker.model.action.MacroAction;

public class MacroMakerViewController implements MacroMakerResponder, AutoCloseable {
	private final MacroMakerView view;
	
	private RobotProxy robot = new AwtRobotProxy();
	private MacroRecorder recorder = new MacroRecorder();
	private MacroSerializer serializer = new MacroSerializer();
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
			long timeStamp = action.getTimeStamp();
			Thread.sleep(timeStamp - elapsed);
			
			action.run(robot);
			
			elapsed = timeStamp;
			elapsedPercent = elapsed / duration;
			view.updateProgress(elapsedPercent);
		}
	}
	
	@Override
	public void newMacro() {
		macro = null;
	}
	
	@Override
	public void open(Path path) {
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			macro = serializer.deserialize(reader);
		} catch (IOException e) {
			view.showMessage("Could not open file: " + e.getMessage());
		}
	}
	
	@Override
	public void save(Path path) {
		if (macro == null) {
			view.showMessage("No macro recorded.");
		} else {
			try (BufferedWriter writer = Files.newBufferedWriter(path)) {
				serializer.serialize(macro, writer);
			} catch (IOException e) {
				view.showMessage("Could not save to file: " + e.getMessage());
			}
		}
	}
	
	public MacroMakerView getView() {
		return view;
	}
	
	@Override
	public void close() {
		recorder.close();
	}
}
