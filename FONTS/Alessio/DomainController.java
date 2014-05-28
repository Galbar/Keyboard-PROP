package classes;

import org.json.JSONException;
import org.json.JSONObject;

public class DomainController {
	static private DomainController instance;

	public DomainController() {}
	static public DomainController i() {
		if (instance == null) {
			instance = new DomainController();
		}
		return instance;
	}

	public String loadAlphabet(String path) {
		return PersistanceController.i().Alphabet(path);
	}

	public void saveAlphabet(String json) {
		PersistanceController.i().saveAlphabet(json);
	}

	public String loadText(String path) {
		return PersistanceController.i().loadText(path);
	}

	public String loadFrequency(String path) {
		return PersistanceController.i().loadText(path);
	}

	public void saveText(String json) {
		PersistanceController.i().saveText(json);
	}

	public String loadKeyboard(String path) {
		return PersistanceController.i().loadKeyboard(path);
	}

	public void saveKeyboard(String json) {
		PersistanceController.i().saveKeyboard(json);
	}
}