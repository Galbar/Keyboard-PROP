
package classes;
import sharedClasses.*;
import classes.enumerations.*;
import java.util.*;

public class Keyboard <c1, c2> extends C <c1, c2>
{
	private TopologyType topology;
	private UsageMode usageMode;
	private boolean specialChars;
	private int width;
	private int height;
	private Vector<String> references;

	/* Constructora */
	/*
	Pre: -
	Post: New Instance
	*/
    public Keyboard(String name, TopologyType topology, UsageMode usageMode, int width, int height)
    {
    	super(name);
        this.topology = topology;
        this.usageMode = usageMode;
        this.width = width;
        this.height = height;
    }

    public Keyboard(String name, TopologyType topology, UsageMode usageMode, int width, int height, c1[ ] characters, c2[ ] positions, int[ ] assignments)
    {
    	super(name, characters, positions, assignments);
        this.topology = topology;
        this.usageMode = usageMode;
        this.width = width;
        this.height = height;
    }

	public String getName()
	{
		return id;
	}

	/*
	Pre: -
	Post: Returns the topology of the keyboard
	*/
	public TopologyType getTopology() {
		return topology;
	}

    /*
	Pre: -
	Post: Returns the usage mode of the keyboard
	*/
	public UsageMode getUsageMode() {
		return usageMode;
	}

    /*
	Pre: -
	Post: Returns the width of the keyboard
	*/
	public int getWidth() {
		return width;
	}

    /*
	Pre: -
	Post: Returns the height of the keyboard
	*/
	public int getHeight() {
		return height;
	}

	public void addReference(String r) {
     	references.add(r);
	}

	public String[] getReferences()
	{
		String s[] = new String[references.size()];
		for (int i = 0; i < references.size(); ++i)
			s[i] = references.get(i);
		return s;
	}
}