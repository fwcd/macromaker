package fwcd.macromaker.model;

import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import fwcd.macromaker.model.action.MacroAction;

/**
 * Serializes and deserializes macros.
 */
public class MacroSerializer {
	private final Gson gson;
	
	public MacroSerializer() {
		RuntimeTypeAdapterFactory<MacroAction> taf = RuntimeTypeAdapterFactory.of(MacroAction.class, "action");
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
