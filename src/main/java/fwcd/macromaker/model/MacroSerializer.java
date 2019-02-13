package fwcd.macromaker.model;

import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import fwcd.macromaker.model.action.KeyPressAction;
import fwcd.macromaker.model.action.KeyReleaseAction;
import fwcd.macromaker.model.action.MacroAction;
import fwcd.macromaker.model.action.MouseMoveAction;
import fwcd.macromaker.model.action.MousePressAction;
import fwcd.macromaker.model.action.MouseReleaseAction;
import fwcd.macromaker.model.action.MouseScrollAction;
import fwcd.macromaker.model.action.TimedAction;

/**
 * Serializes and deserializes macros.
 */
public class MacroSerializer {
	private final Gson gson;
	
	public MacroSerializer() {
		RuntimeTypeAdapterFactory<MacroAction> taf = RuntimeTypeAdapterFactory.of(MacroAction.class, "ac");
		taf.registerSubtype(KeyPressAction.class, "kp");
		taf.registerSubtype(KeyReleaseAction.class, "kr");
		taf.registerSubtype(MouseMoveAction.class, "mm");
		taf.registerSubtype(MousePressAction.class, "mp");
		taf.registerSubtype(MouseReleaseAction.class, "mr");
		taf.registerSubtype(MouseScrollAction.class, "ms");
		taf.registerSubtype(TimedAction.class, "t");
		gson = new GsonBuilder()
			.registerTypeAdapterFactory(taf)
			.create();
	}
	
	public void serialize(Macro macro, Appendable writer) {
		gson.toJson(macro, writer);
	}
	
	public Macro deserialize(Reader reader) {
		return gson.fromJson(reader, Macro.class);
	}
}
