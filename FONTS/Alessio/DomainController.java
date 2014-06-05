package classes;

import org.json.*;
import classes.enumerations.*;

public class DomainController {
	static private DomainController instance;
	private Keyboard currentKeyboard;
	private CharactersSet charactersSet;
	private PositionsSet positionsSet;

	private DomainController() {}
	static public DomainController i() {
		if (instance == null) {
			instance = new DomainController();
		}
		return instance;
	}

	public String loadAlphabet(String path) throws PROPKeyboardException {
		return PersistanceController.i().loadAlphabet(path);
	}

	public void saveAlphabet(String json) throws PROPKeyboardException {
		PersistanceController.i().saveAlphabet(json);
	}

	public String loadText(String path) throws PROPKeyboardException {
		return PersistanceController.i().loadText(path);
	}

	public String loadFrequency(String path) throws PROPKeyboardException {
		return PersistanceController.i().loadText(path);
	}

	public void saveText(String json) throws PROPKeyboardException {
		PersistanceController.i().saveText(json);
	}

	public String loadKeyboard(String path) throws PROPKeyboardException {
		return PersistanceController.i().loadKeyboard(path);
	}

	public void saveKeyboard(String json) throws PROPKeyboardException {
		PersistanceController.i().saveKeyboard(json);
	}

	public void saveKeyboardImage(String json) throws PROPKeyboardException {
		PersistanceController.i().saveKeyboardImage(json);
	}

	public String swap(String json) throws PROPKeyboardException {
		if (currentKeyboard == null) throw new PROPKeyboardException("Keyboard not initialized");
		try {
			JSONObject jsob = new JSONObject(json);
			currentKeyboard.swap(jsob.getInt("id1"), jsob.getInt("id2"));
			currentKeyboard.setScore(Bound.bound(currentKeyboard.getAllocations(), charactersSet.getAllAffinities(), positionsSet.getAllDistances()));
		} catch (JSONException ex) {
			throw new PROPKeyboardException("Error: JSON string bad format");
		}
	}

	public String calculateKeyboard(String json) throws PROPKeyboardException {
		try {
			JSONObject j = new JSONObject(json);
			Alphabet alph = fromJSONObjectToAlphabet(JSONObject(loadAlphabet(j.getString("alphabet_path"))));
			TopologyType t;
			if (j.getString("topology").equals("Squared")) t = TopologyType.Squared;
			else if (j.getString("topology").equals("Circular")) t = TopologyType.Circular;
			else throw new PROPKeyboardException("Error: JSON string bad format");

			charactersSet = new CharactersSet(alph.getCharacters());
			JSONArray txtArr = j.getJSONArray("texts");
			for (int i = 0; i < txtArr.length(); ++i) {
				charactersSet.calculateText(loadText(txtArr.get(i)));
			}
			positionsSet = new PositionsSet(t, alph.getCharacters().length);

			QAP qap = new QAP(charactersSet.getAllAffinities() ,positionsSet.getAllDistances());
			qapSolution[] = qap.solve();

			currentKeyboard = new Keyboard(j.getString("name"), t, j.getInt("width"), j.getInt("height"), charactersSet.getAllCharacters(), positionsSet.getAllPositions(), qapSolution);
			currentKeyboard.setScore(Bound.bound(currentKeyboard.getAllocations(), charactersSet.getAllAffinities(), positionsSet.getAllDistances()));
			return fromKeyboardToJSONObject(currentKeyboard).toString();

		} catch (JSONException ex) {
			throw new PROPKeyboardException("Error: JSON string bad format");
		}
	}

	private Alphabet fromJSONObjectToAlphabet(JSONObject j) throws PROPKeyboardException {
		try {
			Alphabet alph = new Alphabet();
			alph.setName(j.getString("name"));
			JSONArray arr = j.getJSONArray("characters");
			int n = j.getInt("number");
			for (int i = 0; i < n; ++i) {
				alph.addCharacter(arr.getString(i));
			}
			return alph;
		} catch (JSONException ex) {
			throw new PROPKeyboardException("Error: JSON string bad format");
		}
	}

	private JSONObject fromAlphabetToJSONObject(Alphabet a) throws PROPKeyboardException {
		try {
			JSONObject j = new JSONObject();
			j.put("name", a.getName());
			JSONArray arr = new JSONArray();
			int n = a.getCharacters().length;
			j.put("number", n);
			for (int i = 0; i < n; ++i) {
				arr.put(a.getCharacters()[i].getCharacter());
			}
			j.put("characters", arr);
			return j;
		} catch (JSONException ex) {
			throw new PROPKeyboardException("Error: JSON string bad format");
		}
	}

	private Keyboard fromJSONObjectToKeyboard(JSONObject j) throws PROPKeyboardException {
		try {
			TopologyType t;
			if (j.getString("topology").equals("Squared")) t = TopologyType.Squared;
			else if (j.getString("topology").equals("Circular")) t = TopologyType.Circular;
			else throw new PROPKeyboardException("Error: JSON string bad format");
			JSONArray jchars = j.getJSONArray("characters");
			JSONArray jpos = j.getJSONArray("positions");
			JSONArray jassig = j.getJSONArray("assignments");
			classes.Character chars[] = new classes.Character[jchars.length()];
			Position pos[] = new Position[jpos.length()];
			int assig[] = new int[jassig.length()];
			for (int i = 0; i < jchars.length(); ++i) {
				chars[i] = new classes.Character(jchars.getString(i));
				pos[i] = new Position(new Float(jpos.getJSONObject(i).getDouble("x")), new Float(jpos.getJSONObject(i).getDouble("y")));
				assig[i] = jassig.getInt(i);
			}
			System.out.println("hais");
			return new Keyboard(j.getString("name"), t, j.getInt("width"), j.getInt("height"), chars, pos, assig);
		} catch (JSONException ex) {
			throw new PROPKeyboardException("Error: JSON string bad format");
		}
	}

	private JSONObject fromKeyboardToJSONObject(Keyboard k) throws PROPKeyboardException {
		try {
			JSONObject j = new JSONObject();
			if (k.getTopology() == TopologyType.Squared) j.put("topology", "Squared");
			else if (k.getTopology() == TopologyType.Circular) j.put("topology", "Circular");
			JSONArray jchars = new JSONArray();
			JSONArray jpos = new JSONArray();
			JSONArray jassig = new JSONArray();
			int n = k.getAllElements().size();
			for (int i = 0; i < n; ++i) {
				jchars.put(k.getAllElements().get(i).getCharacter());
				JSONObject p = new JSONObject();
				p.put("x", k.getAllPositions().get(i).x);
				p.put("y", k.getAllPositions().get(i).y);
				jpos.put(p);
				jassig.put(k.getAllocations()[i]);
			}
			j.put("characters", jchars);
			j.put("positions", jpos);
			j.put("assignments", jassig);
			j.put("name", k.getName());
			j.put("height", k.getHeight());
			j.put("width", k.getWidth());
			return j;
		} catch (JSONException ex) {
			throw new PROPKeyboardException("Error: JSON string bad format");
		}
	}






}