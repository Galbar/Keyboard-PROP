package classes;

/**
* Classe que comprova el funcionament de Hungarian Algorithm
*
* @author Marc Muntanyola
*/

public class Character {
    private String character;

    /**
     * Constructora
     * @param character Caràcter o conjunt de caràcters que formarà el Character
    */
    public Character(String character) {
        this.character = character;
    }

    /**
     * Consulta el caràcter o caràcters del Character
     * @return El caràcter o conjunt de caràcters que formen el Character. (String).
     */
    public String getCharacter() {
        return character;
    }

    public Character clone()
    {
        return new Character(this.character);
    }
}
