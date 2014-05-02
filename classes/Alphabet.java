package classes;
import java.util.ArrayList;

public class Alphabet{

	/*Atributs*/
	private String name;
	private ArrayList<Character> c;
	
	/*Inicialitzadora */
	public Alphabet() {
        c = new ArrayList<Character>();
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
        classes.Character ret[] = new classes.Character[c.size()];
        for (int i = 0; i < this.c.size(); ++i)
        {
            ret[i] = this.c.get(i).clone();
        }
        return ret;
    }

    public void addCharacter(String c)
    {
    	classes.Character ch = new classes.Character(c);
    	this.c.add(ch);
    }
}
