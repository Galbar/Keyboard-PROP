import classes.*;
import classes.enumerations.*;
import sharedClasses.*;

import java.util.*;

public class main {
	private static void print(String s)
	{
		System.out.println(s);
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
			print("BranchAndBound");
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
			usage();
		}
	}
}