
package classes;
import sharedClasses.*;
import classes.enumerations.*;
import java.util.*;

/**
 *
 * @author Samuel.Bryan.Pierno
 */

public class Keyboard extends C <classes.Character, Position>
{
	private TopologyType topology;
	private boolean specialChars;
	private int width;
	private int height;
	private Vector<String> references = new Vector<String>();

	/* Constructora */
	/*
	Pre: -
	Post: New Instance with name=name,topology=topology, width=width, height=height
	*/
    public Keyboard(String name, TopologyType topology, int width, int height)
    {
    	super(name);
        this.topology = topology;
        this.width = width;
        this.height = height;
    }

        /* Constructora */
	/*
	Pre: -
	Post: New Instance with the parameters set as passed
	*/
    public Keyboard(String name, TopologyType topology, int width, int height, classes.Character[ ] characters, Position[ ] positions, int[ ] assignments)
    {
    	super(name, characters, positions, assignments);
        this.topology = topology;
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

	public void setScore(float s) {
		qualificacio = s;
	}
}