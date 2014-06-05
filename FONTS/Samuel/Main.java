import classes.*;
import classes.enumerations.*;
import sharedClasses.*;
import java.util.*;

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
		{/*             
			TopologyType topology_type;
			UsageMode usage_mode;
			Alphabet alphabet = new Alphabet();
			PositionsSet positionsSet;
			int num_keys;
			QAP qap;
       

			// Introdueix nom de l'alfabet
			String alphabetName = readNextLine();

			// Introdueix Alfabet
			num_keys = Integer.parseInt(readNextLine());
			String chars[] = new String[num_keys];
			for (int i = 0; i < num_keys; ++i)
				chars[i] = readNextLine();

			// Introdueix topologia
			String topology = readNextLine();

			// Introdueix mode d'us
			String usage = readNextLine();
			// Introdueix amplada del teclat
			int width = Integer.parseInt(readNextLine());
			// Introdueix alçada del teclat
			int height = Integer.parseInt(readNextLine());

			// String alphabetName, String topology, String usageMode, int width, int height, String chars[]
			domain.createKeyboard(alphabetName, topology, usage, width, height, chars);

			// Introdueix Textos

			int num_texts = Integer.parseInt(readNextLine());
			for (int i = 0; i < num_texts; ++i) {
				String text = "";
				String line = readNextLine();
				while (!line.equals("\\end")) {
					text += line;
					line = readNextLine();
				}
				domain.addText(text);
			}

			domain.process();

			print("Solution cost: "+domain.getCost());
		*/
    		//MainWindow.getInstance();
    		
        	
        	Vector<String> new_chars = new Vector<String>(0);
        	Vector<Integer> new_rels = new Vector<Integer>(0);
        	Vector<Position> new_coords = new Vector<Position>(0);

            new_chars.addElement("a");
            new_chars.addElement("b");
            new_chars.addElement("c");

            new_rels.addElement(0);
            new_rels.addElement(1);
            new_rels.addElement(2);

            Position p1 = new Position(10.0f, 10.0f);
            Position p2 = new Position(80.0f, 10.0f);
            Position p3 = new Position(70.0f, 50.0f);
            new_coords.addElement(p1);
            new_coords.addElement(p2);
            new_coords.addElement(p3);

            for (int i = 0; i < new_coords.size(); ++i) {
                System.out.println(new_coords.get(i).x);
                System.out.println(new_coords.get(i).y);
                System.out.println("---");
            }
            System.out.println("Start");

            //SolutionView sI = new SolutionView.getInstance(new_chars, new_rels, new_coords);
            SolutionView sI = SolutionView.getInstance(new_chars, new_rels, new_coords);

		}
    }
}