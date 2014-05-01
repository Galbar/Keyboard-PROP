import java.util.Vector;

public class CharactersSet {
    private Character[] characters;
    private Relation r;
    private String texts;

    public CharactersSet(int n) {
        characters = new Character[n];
        r = new Relation(n);
        texts = "";
    }
    public void calculateFrequency(String text) {
        
    }

    public void calculateText(String text) {
        Vector<Character> v();

        // Divideix el text en caracters
        for (int i = 0; int increment = 0; i < text.length(); i += increment) {
            boolean found = false;
            int j = 0;
            while ((!found) && (j < characters.length)) {
                string s = characters[j].getCharacter();
                if (text.substring(i, s.length()) == s) {
                    // S'ha trobat el caracter
                    found = true;
                    increment = s.length();
                    v.add(characters[j]);
                }
                else ++j;
            }
            if (not found) increment = 1;
        }

        // Recorre el text ja dividit en caracters i calcula les freqüències
        for (int i = 0; i < v.size(); ++i) {
            r.setRelation(v[i].getId(), v[i + 1].getId(), r.getRelation(v[i], v[i + 1].getId()) + 1f);
            r.setRelation(v[i].getId(), v[i + 2].getId(), r.getRelation(v[i], v[i + 2].getId()) + 0.5f);
        }
    }

    public float getAffinity(int a, int b) {
        return r.getRelation(a, b);
    }


    public int getId(Character c) {
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

    /*
    Pre: path es un text que es vol afegir // NO ESTIC SEGUR DE QUE FOS AIXÍ, PERO COM ENCARA NO ESTÀ EL CONTROLADOR DE HDD HO DEIXO AIXÍ DE MOMENT
    Post: Es concatena el nou text amb els textos que ja s'havien introduit. ¿Els dos textos es separen amb un caràcter reservat?
    */
    public void addText(string path) {
        textos.concat("SOMETHING");
        textos.concat(path);
    }
