package classes;
import java.util.Vector;


public class Alphabet{

	/*Atributs*/
	private String name;
	private ConjCharacters c;
	
	/*Inicialitzadora */
	public Alphabet() {}
	
	/* Pre: - */
	/* Post: Retorna el nom de l'alfabet */
	public String getName() {
            return name;
        }
	
	/* Pre: Rep un String */
        /* Post: Un nou nom ha sigut assignat a alphabet */
        public void setName(String newname) {
            this.name = newname;
        }
        
        /* Pre: - */
        /* Post: Retorna tots els caràcters */
        public Vector getCharacters() {
            return c.getAllCharacters();
        }
}
