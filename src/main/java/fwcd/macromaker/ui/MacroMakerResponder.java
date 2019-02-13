package fwcd.macromaker.ui;

import java.nio.file.Path;

public interface MacroMakerResponder {
	void newMacro();
	
	void open(Path path);
	
	void save(Path path);
	
	void record();
	
	void play();
	
	void stop();
}
