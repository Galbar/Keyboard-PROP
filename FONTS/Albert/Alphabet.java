package classes;
import java.util.ArrayList;

/**
 * Conté tots els caràcters de l'alfabet.
 *
 * @author Albert Trelis
 */
public class Alphabet{

    /*Atributs*/
    private String name;
    private ArrayList<Character> c;

    /**
     * Constructora.
     * 
     */
    public Alphabet() {
    c = new ArrayList<Character>();
    }

    /**
     * Consulta el nom de l'alfabet.
     * @return El nom de l'alfabet.
     */
    public String getName() {
    return name;
    }
	
    /**
     * Modifica el nom de l'alfabet.
     * @param  x  String amb el nou nom.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
	 * Consulta els caràcters de l'alfabet.
	 * @return Tots els caràcters que conté l'alfabet.
	 */
    public classes.Character[] getCharacters() {
        classes.Character ret[] = new classes.Character[c.size()];
        for (int i = 0; i < this.c.size(); ++i)
        {
            ret[i] = this.c.get(i).clone();
        }
        return ret;
    }

    /**
	 * Afageig un nou caràcter
	 * @param  x  Nou caràcter.
	 */
    public void addCharacter(classes.Character c)
    {
    	classes.Character ch = new classes.Character(c.getCharacter());
    	this.c.add(ch);
    }
}
