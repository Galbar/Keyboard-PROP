package classes;

import java.util.Comparator;
import java.util.*;

/**
* Calcula les freqüències amb que els Characters del alfabet tenen posicions
* contigues o separades per un altre Character en un text.
*
* @author Marc Muntanyola
*/

public class CharactersSet {

    /** Comparador per ordenar l'array de Characters, de més llargs a més curts,
     * i dintre dels de la mateixa llargada, per ordre alfabètic.
     */
    private class ComparatorCharacter implements Comparator {

        public int compare(Object arg0, Object arg1) {
            classes.Character char0 = (classes.Character) arg0;
            classes.Character char1 = (classes.Character) arg1;

            int flag;
            if (char0.getCharacter().length() == char1.getCharacter().length())
                flag = 0;
            else if (char0.getCharacter().length() < char1.getCharacter().length())
                flag = 1;
            else
                flag = -1;
            if (flag == 0) {
                return char0.getCharacter().compareTo(char1.getCharacter());
            } else {
                return flag;
            }
        }
    }

    private ArrayList<classes.Character> characters;
    private Relation r;

    /**
     * Constructor
     * @param cs Array dels Characters que formaran el CharacterSet.
    */
    public CharactersSet(classes.Character[] cs) {
        characters = new ArrayList<classes.Character>(Arrays.asList(cs));
        Collections.sort(characters, new ComparatorCharacter());
        r = new Relation(cs.length);
    }
    
    public void calculateFrequency(String text) {
        // TO DO !!!
    }

    /**
    * Calcula les freqüències amb que els Characters tenen posicions
    * contigues o separades per un altre Character en el text introduit.
    * @param text String que conté el text a analitzar.
    */
    public void calculateText(String text) {
        Vector <classes.Character> v = new Vector <classes.Character>();
        // Divideix el text en caracters
        int increment = 0;
        for (int i = 0; i < text.length(); i += increment) {
            boolean found = false;
            int j = 0;
            while ((!found) && (j < characters.size())) {
                String s = characters.get(j).getCharacter();

                if (i + s.length() <= text.length()) {
                    if (text.substring(i, i + s.length()).equals(s)) {
                        // S'ha trobat el caracter
                        found = true;
                        increment = s.length();
                        v.add(characters.get(j));
                    }
                    else 
                        ++j;
                }
                else
                    ++j;
            }
            if (!found) increment = 1;
        }

        // Recorre el text ja dividit en caracters i calcula les freqüències
        for (int i = 0; i < v.size() - 1; ++i) {
            r.addToRelation(getId(v.elementAt(i)), getId(v.elementAt(i + 1)), 1f);
            if (i != v.size() - 2)
                r.addToRelation(getId(v.elementAt(i)), getId(v.elementAt(i + 2)), 0.5f);
        }
    }

    /**
    * Consulta la probabilitat que dos Characters estiguin junts en un text.
    * @param a 
    * @param b
    * @return Float que representa el percentatge de vegades que els Characters amb id a i b han aparegut junts en els texts calculats.
    */
    public float getAffinity(int a, int b) {
        return r.getProportion(a, b);
    }

    /**
    * Consulta la probabilitat que dos Characters estiguin junts en un text, per tots els Characters de CharacterSet.
    * @result Matriu de floats que conté els percentatges.
    */
    public float[][] getAllAffinities() {
        return r.getProportions();
    }

    /**
    * Consulta l'id d'un Character de CharacterSet.
    * @param c Character del que es vol consultar l'id.
    * @return La id del Character c. (int).
    */
    public int getId(classes.Character c) {
        String s = c.getCharacter();
        return getId(s);
    }

    /**
    * Consulta l'id d'un Character format per una string determinada
    * @param s String que forma el Character del que es vol saber la id.
    * @return La id del Character format per la String s.(int).
    */
    public int getId(String s) {
        boolean found = false;
        int i = 0;
        while (!found) {
            if (characters.get(i).getCharacter() == s) found = true;
            else ++i;
        }
        return i;
    }

    /**
    * Consulta el caràcter o caràcters d'un Character de CharactersSet
    * @param id Int que correspon al id del Character que es vol consultar. 
    * @return String que conté el caràcter o caràcters del Character amb id introduida
    */
    public String getCharacterContent(int id) {
        return characters.get(id).getCharacter();
    }

    /**
    * Consulta els Characters que formen CharacterSet
    * @return Un Array de Characters
    */
    public classes.Character[] getAllCharacters() {
        classes.Character ret[] = new classes.Character[characters.size()];
        for (int i = 0; i < characters.size(); ++i)
        {
            ret[i] = characters.get(i).clone();
        }
        return ret;
    }

    /**
    * Afegeix un Character
    * @param c Character a afegir
    */
    public void addCharacter(classes.Character c) {
        characters.add(c);
        Collections.sort(characters, new ComparatorCharacter());
        r = new Relation(characters.size());
    }
}