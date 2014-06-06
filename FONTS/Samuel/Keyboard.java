
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
	private Vector<String> references;
	private String alphabetName = new String();

	/* Constructora */
	/*
	Pre: -
	Post: New Instance with name=name,topology=topology, width=width, height=height
	*/
    public Keyboard(String name, TopologyType topology, String name2)
    {
    	super(name);
        this.topology = topology;
        this.alphabetName = name2;
        references = new Vector<String>();
    }

        /* Constructora */
	/*
	Pre: -
	Post: New Instance with the parameters set as passed
	*/
    public Keyboard(String name, TopologyType topology, String name2, classes.Character[ ] characters, Position[ ] positions, int[ ] assignments)
    {
    	super(name, characters, positions, assignments);
        this.topology = topology;
        this.alphabetName = name2;
        references = new Vector<String>();
    }

    public Keyboard(Keyboard k, int[] assignments) {
    	super(k.id);
    	this.elements = k.elements;
    	this.posicions = k.posicions;
    	this.rel = k.rel;
    	this.topology = k.topology;
    	this.alphabetName = k.alphabetName;
    	this.references = k.references;
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

	public void addReference(String r) {
     	references.add(new String(r));
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

	public String getAlphabetName() {
		return alphabetName;
	}
}