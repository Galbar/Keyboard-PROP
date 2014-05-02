package classes;

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
        // TO DO !!!
        int a = 3;
    }

    public void calculateText(String text) {
        Vector <Character> v = new Vector <Character>();

        // Divideix el text en caracters
        int increment = 0;
        for (int i = 0; i < text.length(); i += increment) {
            //System.out.print("For");
            boolean found = false;
            int j = 0;
            while ((!found) && (j < characters.length)) {
                //System.out.print("While");
                String s = characters[j].getCharacter();
                System.out.print(s);
                System.out.print(" - ");
                //System.out.print(text.substring(i, i + s.length()));
                System.out.print(" = ");

                if (i + s.length() <= text.length()) {
                    System.out.print("Suficient llargada\n");
                    if (text.substring(i, i + s.length()).equals(s)) {
                        System.out.print("Son iguals\n");

                        //System.out.print(s);
                        // S'ha trobat el caracter
                        found = true;
                        increment = s.length();
                        v.add(characters[j]);
                    }
                    else {
                       System.out.print("No son iguals\n");
                        ++j;
                    }
                }
                else {
                    System.out.print("Massa Llarg - S'acaba el text\n");
                    ++j;
                }
            }
            if (!found) increment = 1;
        }

        // Recorre el text ja dividit en caracters i calcula les freqüències
        for (int i = 0; i < v.size() - 1; ++i) {
            System.out.print("\n" + v.elementAt(i).getCharacter() + " - " + v.elementAt(i+1).getCharacter());
            r.addToRelation(getId(v.elementAt(i)), getId(v.elementAt(i + 1)), 1f);
            if (i != v.size() - 2) {
                System.out.print("\n" + v.elementAt(i).getCharacter() + " - " + v.elementAt(i+2).getCharacter());
                r.addToRelation(getId(v.elementAt(i)), getId(v.elementAt(i + 2)), 0.5f);
            }
        }
    }

    public float getAffinity(int a, int b) {
        return r.getRelation(a, b);
    }

    public int getId(Character c) {
        String s = c.getCharacter();
        return getId(s);
    }

    public int getId(String s) {
        boolean found = false;
        int i = 0;
        while (!found) {
            if (characters[i].getCharacter() == s) found = true;
            else ++i;
        }
        return i;
    }

    public String getCharacterContent(int id) {
        return characters[id].getCharacter();
    }

    public Character[] getAllCharacters() {
        return characters;
    }

    public void addCharacter(Character c, int pos) {
        characters[pos] = c;
    }

    /*
    Pre: path es un text que es vol afegir // NO ESTIC SEGUR DE QUE FOS AIXÍ, PERO COM ENCARA NO ESTÀ EL CONTROLADOR DE HDD HO DEIXO AIXÍ DE MOMENT
    Post: Es concatena el nou text amb els textos que ja s'havien introduit. ¿Els dos textos es separen amb un caràcter reservat?
    */
    public void addText(String path) {
        texts.concat("SOMETHING");
        texts.concat(path);
    }
}
