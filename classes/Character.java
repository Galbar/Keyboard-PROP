package classes;

public class Character {
    public String character;

    public Character(String character) {
        this.character = character;
    }

	/*
    public void setCharacter(String character) {
    	this.character = character;
    }
    */

    public String getCharacter() {
        return character;
    }

    public Character clone()
    {
        return new Character(this.character);
    }
}
