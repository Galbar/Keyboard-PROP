/*
    TO DO:
        - Enumerations: TopolodyType & UsageMode
        - References: ??
*/

public class Keyboard {
    private string name;
    private TopologyType topology;
    private UsageMode usage_mode;
    private int width;
    private int height;

    
    /* Constructora */
    /*
    Pre: -
    Post: New Instance
    */
    public Keyboard(string name, TopologyType topology, UsageMode usage_mode, int widthm int height) {
        this.name = name;
        this.topology = topology;
        this.usage_mode = usage_mode;
        this.width = width;
        this.height = height;
    }

    /*
    Pre: -
    Post: Returns the name of the keyboard
    */
    public String getName() {
        return this.name;
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
        return usage_mode;
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

    public void addReference(Reference r) {
        /*
            TO DO
        */
    }
