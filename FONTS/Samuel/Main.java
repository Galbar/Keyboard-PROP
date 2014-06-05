import classes.*;
import classes.enumerations.*;
import sharedClasses.*;
import java.util.*;
import org.json.JSONException;

import org.json.JSONObject;

/**
 *
 * @author Samuel.Bryan.Pierno
 */

public class Main {
	private final static Scanner sc = new Scanner(System.in);
	private static void print(String s)
	{
		System.out.println(s);
	}

	private static String readNextLine()
	{
		String s = "";
		do
		{
			s = sc.nextLine();
		} while (s.length() == 0 || s.charAt(0) == '#');
		return s;
	}

	private static void usage()
	{
		print("Usage: java main [OPTION]");
		print("Options:");
		print("-t, --test [-h|--help]      Enter class testing mode");
		print("           [class name]     (run the corresponding driver).");
		print("-h, --help                  View this text.");
		print("-c, --commandline           Run without GUI and input through standard input");
	}

	private static void usage(String s)
	{
		if( s.equals("-t") || s.equals("--help"))
		{
			print("Classes available for testing:");
			print("    Alphabet");
			print("    Character");
			print("    CharactersSet");
			print("    Keyboard");
			print("    Position");
			print("    PositionsSet");
			print("    Relation");
			print("# Shared Classes");
			print("    Bound");
			print("    InitialSolution");
		}
	}

	public static void main(String [ ] args)
	{
		if ( args.length > 0 )
		{
			String option = args[0];
			if (option.equals("-h") || option.equals("--help"))
			{
				usage();
			}
			else if ( args.length > 1 && (args[1].equals("-h") || args[1].equals("--help")) )
			{
				usage(args[0]);
			}
			else if ( option.equals("-c") || option.equals("--commandline")){
				TopologyType topology_type;
				UsageMode usage_mode;
				PositionsSet positionsSet;
				CharactersSet charactersSet;
				int num_keys;

				// Introdueix nom de l'alfabet
				String alphabetName = readNextLine();

				// Introdueix Alfabet
				num_keys = Integer.parseInt(readNextLine());
				String chars[] = new String[num_keys];
				for (int i = 0; i < num_keys; ++i)
					chars[i] = readNextLine();

				classes.Character characters[] = new classes.Character[num_keys];
				for (int i = 0; i < num_keys; ++i) {
					characters[i] = new classes.Character(chars[i]);
				}
				charactersSet = new CharactersSet(characters);
				
				// Introdueix topologia
				String topology = readNextLine();
				TopologyType t;
				if (topology.equals("Squared")) t = TopologyType.Squared;
				else t = TopologyType.Circular;

				positionsSet = new PositionsSet(t, num_keys);

				// Introdueix amplada del teclat
				int width = Integer.parseInt(readNextLine());
				// Introdueix alçada del teclat
				int height = Integer.parseInt(readNextLine());


				// Introdueix Textos
				int num_texts = Integer.parseInt(readNextLine());
				for (int i = 0; i < num_texts; ++i) {
					String text = "";
					String line = readNextLine();
					while (!line.equals("\\end")) {
						text += line;
						line = readNextLine();
					}
					charactersSet.calculateText(text);
				}

				QAP qap = new QAP(charactersSet.getAllAffinities() ,positionsSet.getAllDistances());

				int qapSolution[] = qap.solve();

				Keyboard k = new Keyboard(alphabetName, t, width, height, charactersSet.getAllCharacters(), positionsSet.getAllPositions(), qapSolution);

				k.setScore(Bound.bound(k.getAllocations(), charactersSet.getAllAffinities(), positionsSet.getAllDistances()));

				print("Assignments:");
				for (int i = 0; i < num_keys;++i) {
					System.out.println(k.getAllocation(i).first.getCharacter()+"\t<->\t("+k.getAllocation(i).second.x+","+k.getAllocation(i).second.y+")");
				}
				print("Solution cost: "+k.getScore());

				System.out.print("id:\t");
				String a = "Cha:\t";
				String b = "Pos:\t";
				for (int i = 0; i < num_keys; ++i) {
					System.out.print(i+"\t\t");
					a += k.getAllElements().get(i).getCharacter()+"\t\t";
					b += "("+k.getAllPositions().get(i).x+","+k.getAllPositions().get(i).y+")\t";
				}
				print("");
				print(a);
				print(b);

				print("Relations between characters:");
				for (int i = 0; i < num_keys; ++i) {
					for (int j = 0; j < num_keys; ++j) {
						System.out.print(charactersSet.getAffinity(i, j)+"\t");
					}
					print("");
				}

				print("Distances between positions:");
				for (int i = 0; i < num_keys; ++i) {
					for (int j = 0; j < num_keys; ++j) {
						System.out.print(positionsSet.getDistance(i, j)+"\t");
					}
					print("");
				}
			}
			else if ( option.equals("-t") || option.equals("--test") && args.length > 1 )
			{
				if(args.length < 2)
				{
					usage("-t");
					return;
				}

				String s[] = new String[args.length-1];
				for (int i = 2; i < args.length; ++i)
				s[i-1] = args[i];


				if (args[1].equals("Alphabet"))
					AlphabetDriver.main(s);

				if (args[1].equals("Character"))
					CharacterDriver.main(s);

				if (args[1].equals("CharactersSet"))
					CharactersSetDriver.main(s);
				
				if (args[1].equals("Keyboard"))
					KeyboardDriver.main(s);
				
				if (args[1].equals("Position"))
					PositionDriver.main(s);
				
				if (args[1].equals("PositionsSet"))
					PositionsSetDriver.main(s);
				
				if (args[1].equals("Relation"))
					RelationDriver.main(s);
				
				if (args[1].equals("Bound"))
					BoundDriver.main(s);
				
				if (args[1].equals("HungarianAlgorithm"))
					HungarianAlgorithmDriver.main(s);

				if (args[1].equals("InitialSolution"))
					InitialSolutionDriver.main(s);

				if (args[1].equals("DriverBranchAndBound"))
					DriverBranchAndBound.main(s);
			}
		}
		else
		{
    		MainWindow.getInstance();
		}
    }
}