package classes;

import org.json.*;
import classes.enumerations.*;
import sharedClasses.*;

public class DomainController {
	static private DomainController instance;
	private Keyboard currentKeyboard;
	private float[][] charactersSetValues;
	private float[][] positionsSetValues;

	private DomainController() {}
	static public DomainController getInstance() {
		if (instance == null) {
			instance = new DomainController();
		}
		return instance;
	}

	public String loadAlphabet(String path) throws PROPKeyboardException {
		return PersistanceController.getInstance().loadAlphabet(path);
	}

	public void saveAlphabet(String json) throws PROPKeyboardException {
		PersistanceController.getInstance().saveAlphabet(json);
	}

	public String loadText(String path) throws PROPKeyboardException {
		return PersistanceController.getInstance().loadText(path);
	}

	public String loadFrequency(String path) throws PROPKeyboardException {
		return PersistanceController.getInstance().loadText(path);
	}

	public void saveText(String json) throws PROPKeyboardException {
		PersistanceController.getInstance().saveText(json);
	}

	public String loadKeyboard(String path) throws PROPKeyboardException {
		try {
			String json = PersistanceController.getInstance().loadKeyboard(path);
			JSONObject j = new JSONObject(json);
			currentKeyboard = fromJSONObjectToKeyboard(j.getJSONObject("keyboard"));
			charactersSetValues = fromJSONArrayToFloatMatrix(j.getJSONArray("affinities"));
			positionsSetValues = fromJSONArrayToFloatMatrix(j.getJSONArray("distances"));
			return json;
		} catch (JSONException e) {
			throw new PROPKeyboardException("Error: JSON string bad format");
		}
	}

	public void saveKeyboard(String path) throws PROPKeyboardException {
		try {	
			JSONObject jret = new JSONObject();
			jret.put("keyboard", fromKeyboardToJSONObject(currentKeyboard));
			jret.put("affinities", fromFloatMatrixToJSONArray(charactersSetValues));
			jret.put("distances", fromFloatMatrixToJSONArray(positionsSetValues));
			jret.put("path", path);
			PersistanceController.getInstance().saveKeyboard(jret.toString());
		} catch (JSONException ex) {
            throw new PROPKeyboardException("Error: JSON string bad format");
        }
	}

	public void saveKeyboardImage(String json) throws PROPKeyboardException {
		PersistanceController.getInstance().saveKeyboardImage(json);
	}


	public String swap(String json) throws PROPKeyboardException {
		if (currentKeyboard == null) throw new PROPKeyboardException("Keyboard not initialized");
		try {
			JSONObject jsob = new JSONObject(json);
			currentKeyboard.swap(jsob.getInt("id1"), jsob.getInt("id2"));
			currentKeyboard.setScore(Bound.bound(currentKeyboard.getAllocations(), charactersSetValues, positionsSetValues));
			return fromKeyboardToJSONObject(currentKeyboard).toString();
		} catch (JSONException ex) {
			throw new PROPKeyboardException("Error: JSON string bad format");
		}
	}

	public String calculateKeyboard(String json) throws PROPKeyboardException {
		try {
			JSONObject j = new JSONObject(json);
			Alphabet alph = fromJSONObjectToAlphabet(new JSONObject(loadAlphabet(j.getString("alphabet_path"))));
			TopologyType t;
			if (j.getString("topology").equals("Rectangular")) {
				t = TopologyType.Squared;
			}
			else if (j.getString("topology").equals("Circular")) {
				t = TopologyType.Circular;
			}
			else {
				throw new PROPKeyboardException("Error: JSON string bad format");
			}

			CharactersSet charactersSet = new CharactersSet(alph.getCharacters());
			String freqPath = j.getString("frequency_file");
			if (!freqPath.equals("")) {
				charactersSet.calculateFrequency(loadText(freqPath));
			}
			JSONArray txtArr = j.getJSONArray("texts");
			for (int i = 0; i < txtArr.length(); ++i) {
				String s = txtArr.getString(i);
				charactersSet.calculateText(loadText(s));
			}
			charactersSetValues = charactersSet.getAllAffinities();
			PositionsSet positionsSet = new PositionsSet(t, alph.getCharacters().length);
			positionsSetValues = positionsSet.getAllDistances();
			QAP qap = new QAP(charactersSet.getAllAffinities() ,positionsSet.getAllDistances());
			int[] qapSolution = qap.solve(j.getBoolean("force_BB"));

			currentKeyboard = new Keyboard(j.getString("name"), t, alph.getName(), charactersSet.getAllCharacters(), positionsSet.getAllPositions(), qapSolution);
			currentKeyboard.setScore(Bound.bound(currentKeyboard.getAllocations(), charactersSet.getAllAffinities(), positionsSet.getAllDistances()));
			
			if (!freqPath.equals("")) {
				currentKeyboard.addReference(freqPath);
			}
			
			for (int i = 0; i < txtArr.length(); ++i) {
				String s = txtArr.getString(i);
				currentKeyboard.addReference(s);
			}
			JSONObject jret = new JSONObject();
			jret.put("keyboard", fromKeyboardToJSONObject(currentKeyboard));
			jret.put("affinities", fromFloatMatrixToJSONArray(charactersSet.getAllAffinities()));
			jret.put("distances", fromFloatMatrixToJSONArray(positionsSet.getAllDistances()));
			return jret.toString();

		} catch (JSONException ex) {
			throw new PROPKeyboardException("Error: JSON string bad format");
		}
	}

	public String recalculateCurrentKeyboard() throws PROPKeyboardException {
		try {
			QAP qap = new QAP(charactersSetValues ,positionsSetValues);
			int[] qapSolution = qap.solve();

			currentKeyboard = new Keyboard(currentKeyboard, qapSolution);
			currentKeyboard.setScore(Bound.bound(currentKeyboard.getAllocations(), charactersSetValues, positionsSetValues));
			JSONObject jret = new JSONObject();
			jret.put("keyboard", fromKeyboardToJSONObject(currentKeyboard));
			jret.put("affinities", fromFloatMatrixToJSONArray(charactersSetValues));
			jret.put("distances", fromFloatMatrixToJSONArray(positionsSetValues));
			return jret.toString();
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
			Keyboard k = new Keyboard(j.getString("name"), t, j.getString("alphabet_name"), chars, pos, assig);
			k.setScore((float)j.getDouble("score"));

			JSONArray jrefs = j.getJSONArray("references");
			for (int i = 0;i < jrefs.length(); ++i) {
				k.addReference(jrefs.getString(i));
			}
			return k;
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
			j.put("score", k.getScore());
			j.put("alphabet_name", k.getAlphabetName());
			JSONArray jrefs = new JSONArray();
			String[] refs = k.getReferences();
			for (int i = 0;i < refs.length; ++i) {
				jrefs.put(refs[i]);
			}
			j.put("references", jrefs);
			return j;
		} catch (JSONException ex) {
			throw new PROPKeyboardException("Error: JSON string bad format");
		}
	}

	private JSONArray fromFloatMatrixToJSONArray(float[][] mat) throws PROPKeyboardException {
		try {
			int n = mat.length;
			JSONArray jmat = new JSONArray();
			for (int i = 0; i < n; ++i) {
				jmat.put(new JSONArray());
				int l = jmat.length()-1;
				for (int j = 0; j < n; ++j) {
					jmat.getJSONArray(l).put(mat[i][j]);
				}
			}
			return jmat;
		} catch (JSONException ex) {
			throw new PROPKeyboardException("Error: JSON string bad format");
		}
	}

	private float[][] fromJSONArrayToFloatMatrix(JSONArray jmat) throws PROPKeyboardException {
		try {
			int n = jmat.length();
			float[][] mat = new float[n][n];
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < n; ++j) {
					mat[i][j] = (float)jmat.getJSONArray(i).getDouble(j);
				}
			}
			return mat;
		} catch (JSONException ex) {
			throw new PROPKeyboardException("Error: JSON string bad format");
		}
	}
}