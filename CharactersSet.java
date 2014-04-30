public class CharactersSet {
    private Character[] characters;
    private Relation r;

    public CharactersSet(int n) {
        characters = new Character[n];
        r = new Relation(n);
    }
    public void calculateFrequency(String text) {
        
    }

    public void calculateText(String text) {
        for (int i = 0; i < characters.lenght() ; ++i) {
            char c = text.charAt(i);
            int id = getId(c);

            // d = 1
            [getId(text.charAt(i+1))];
            // d = 2
            next = text.charAt(i+2);
    }

    public float getAffinity(int a, int b) {
        return r.getRelation(a, b);
    }


    public int getId(Character c) {
    // ???????
        String s = c.getName();
        return getId(s);
    }

    public int getId(String s) {
        boolean found = false;
        int i = 0;
        while (not found) {
            if (characters[i].getCharacter() == s) found = true;
            else ++i;
        }
        return i;
    }

    public Character[] getAllCharacters() {
        return characters;
    }

    public void addText(string path) {
        // Crida a HDDController ¿?
    }
