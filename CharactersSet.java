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
        /*
        for (int i = 0; i < characters.lenght() ; ++i) {
            char c = text.charAt(i);
            int id = getId(c);

            // d = 1
            [getId(text.charAt(i+1))];
            // d = 2
            next = text.charAt(i+2);
        */

        // Creo Vector on aniré fent push_backs dels caracters que trobi al text,
        // per despres mirar quins estan abans i despres del caracter trobat.
        // ara esta mal fet sintacticament
        vector<Character> v(0);

        // Divideix el text en caracters
        for (int i = 0; int increment = 0; i < text.length(); i += increment) {
            boolean found = false;
            int j = 0;
            while (not found and j < characters.length) {
                string s = characters[j].getCharacter();
                if (text.substring(i, s.length()) == s) {
                    // S'ha trobat el caracter
                    found = true;
                    increment = s.length();
                    v.add(characters[j]);

                    // d = 1
                    r.setRelation(j, v[v.size() - 2].getId()), r.getRelation(j, v[v.size() - 2].getId()) + 1);

                    // d = 2
                }
                else ++j;
            }
            if (not found) increment = 1;
        }

        // Recorre el text ja dividit en caracters i calcula les freqüències
        for (int i = 0; i < v.size())

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
