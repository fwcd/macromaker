package fwcd.macromaker.model;

public interface RobotProxy {
	void pressKey(int keyCode);
	
	void releaseKey(int keyCode);
	
	void pressMouse(int buttons);
	
	void releaseMouse(int buttons);
	
	void moveMouse(int x, int y);
	
	void scroll(int mouseWheelDelta);
}
