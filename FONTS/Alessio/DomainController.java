package classes;
import classes.enumerations.*;
import sharedClasses.*;
import java.util.*;

public class DomainController {
	private Alphabet alphabet;
	private PositionsSet positionsSet;
	private CharactersSet charactersSet;
	private Keyboard<classes.Character, Position> keyboard;
	private QAP qap;

	public DomainController() {}

	public void createKeyboard(String alphabetName, String topology, String usageMode, int width, int height, String chars[]) {
		TopologyType topology_type;
		if (topology.equals("Squared")) topology_type = TopologyType.Squared;
		else if (topology.equals("Circular")) topology_type = TopologyType.Circular;
		else return;

		UsageMode usage_mode;
		if (usageMode.equals("Right")) usage_mode = UsageMode.Right;
		else if (usageMode.equals("Left")) usage_mode = UsageMode.Left;
		else if (usageMode.equals("Both")) usage_mode = UsageMode.Both;
		else return;
		
		keyboard = new Keyboard<classes.Character, Position>(alphabetName, topology_type, usage_mode, width, height);
		alphabet = new Alphabet();
		alphabet.setName(alphabetName);
		for (int i = 0; i < chars.length; ++i) {
			alphabet.addCharacter(new classes.Character(chars[i]));
		}

		charactersSet = new CharactersSet(alphabet.getCharacters());
		positionsSet = new PositionsSet(topology_type, chars.length);
	}

	public void addText(String s) {
		charactersSet.calculateText(s);
	}

	public void processWordFrequency(String s) {
		charactersSet.calculateFrequency(s);
	}

	public void process() {
		qap = new QAP(charactersSet.getAllAffinities() ,positionsSet.getAllDistances());
		int qapSolution[] = qap.solve();
		for (int i = 0; i < qapSolution.length; ++i) {
			keyboard.addElement(charactersSet.getCharacter(i), positionsSet.getPosition(qapSolution[i]));
		}
		keyboard.setScore(Bound.bound(qapSolution, charactersSet.getAllAffinities(), positionsSet.getAllDistances()));
	}

	public Keyboard<classes.Character, Position> getKeyboard() {
		return keyboard;
	}

	public float getCost() {
		return keyboard.getScore();
	}
}