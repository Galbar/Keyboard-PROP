import classes.Keyboard;
import classes.CharactersSet;
import classes.Alphabet;
import classes.PositionsSet;
import classes.Position;
import classes.*;
import classes.enumerations.*;
import sharedClasses.*;

import java.util.*;

public class main {
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
	}

	private static void usage(String s)
	{
		if( s.equals("-t") || s.equals("--help"))
		{
			print("Classes available for testing:");
			print("Alphabet");
			print("Character");
			print("CharactersSet");
			print("Keyboard");
			print("Position");
			print("PositionsSet");
			print("Relation");
			print("# Shared Classes")
			print("Bound");
			print("BranchAndBound");
			print("C");
			print("InitialSolution");
			print("Pair");
			print("QAP");
			print("TabuSearch");
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
			else if ( option.equals("-t") || option.equals("--test") && args.length > 1 )
			{
				if (args[1].equals("BranchAndBound"))
				{
					String s[] = new String[args.length-1];
					for (int i = 2; i < args.length; ++i)
						s[i-1] = args[i];
					DriverBranchAndBound.main(s);
				}
			}
		}
		else
		{                    
			String name;
			TopologyType topology_type;
			UsageMode usage_mode;
			Alphabet alphabet = new Alphabet();
			PositionsSet positionsSet;
			int num_keys;
			QAP qap;

			// Introdueix nom
			name = readNextLine();

			// Introdueix topologia
			String topology = readNextLine();
			if (topology.equals("Squared")) topology_type = TopologyType.Squared;
			else if (topology.equals("Circular")) topology_type = TopologyType.Circular;
			else return;

			// Introdueix mode d'us
			String usage = readNextLine();
			if (usage.equals("Right")) usage_mode = UsageMode.Right;
			else if (usage.equals("Left")) usage_mode = UsageMode.Left;
			else if (usage.equals("Both")) usage_mode = UsageMode.Both;
			else return;

			// Introdueix Alfabet
			num_keys = Integer.parseInt(readNextLine());
			for (int i = 0; i < num_keys; ++i)
				alphabet.addCharacter(readNextLine());

			// PositionSet
			positionsSet = new PositionsSet(topology_type, num_keys);

			// Introdueix Textos

			CharactersSet cs = new CharactersSet(alphabet.getCharacters());
			classes.Character[] c = cs.getAllCharacters();
			for(int i = 0; i < c.length; ++i)
			{
				print(c[i].getCharacter());
			}

			int num_texts = Integer.parseInt(readNextLine());
			for (int i = 0; i < num_texts; ++i) {
				String text = "";
				String line = readNextLine();
				while (!line.equals("\\end")) {
					text += line;
					line = readNextLine();
				}
				cs.calculateText(text);
			}
			 

			qap = new QAP(cs.getAllAffinities() ,positionsSet.getAllDistances());
			int[] sol =  qap.solve();

			// Calcula
			Keyboard<classes.Character, Position>keyboard = new Keyboard<classes.Character, Position>(name,topology_type, usage_mode, 1, 1, cs.getAllCharacters(), positionsSet.getAllPositions(), sol);

			// Mostra els resultats

		}
	}
}