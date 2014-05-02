package classes;
import java.util.Vector;

public class Alphabet{

	/*Atributs*/
	private String name;
	private CharactersSet c;
	
	/*Inicialitzadora */
	public Alphabet() {
        c = new CharactersSet();
	}
	
	/* Pre: - */
	/* Post: Retorna el nom de l'alfabet */
	public String getName() {
        return name;
    }
	
	/* Pre: Rep un String */
    /* Post: Un nou nom ha sigut assignat a alphabet */
    public void setName(String name) {
        this.name = name;
    }
    
    /* Pre: - */
    /* Post: Retorna tots els caràcters */
    public classes.Character[] getCharacters() {
        return c.getAllCharacters();
    }

    public void addCharacter(String c)
    {
    	classes.Character ch = new classes.Character(c);
    	this.c.addCharacter(ch);
    }

    public float[][] getAllFrequencies() {
        return c.getAllAffinities();
    }
}
