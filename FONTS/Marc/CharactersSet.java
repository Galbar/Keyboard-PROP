package classes;

import java.util.Comparator;
import java.util.*;

public class CharactersSet {

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

    public CharactersSet(classes.Character[] cs) {
        characters = new ArrayList<classes.Character>(Arrays.asList(cs));
        Collections.sort(characters, new ComparatorCharacter());
        r = new Relation(cs.length);
    }
    
    public void calculateFrequency(String text) {
        // TO DO !!!
    }

    public void calculateText(String text) {
        Vector <classes.Character> v = new Vector <classes.Character>();
        // Divideix el text en caracters
        int increment = 0;
        for (int i = 0; i < text.length(); i += increment) {
            //System.out.print("For");
            boolean found = false;
            int j = 0;
            while ((!found) && (j < characters.size())) {
                //System.out.print("While");
                String s = characters.get(j).getCharacter();
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
                        v.add(characters.get(j));
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
        return r.getProportion(a, b);
    }

    public float[][] getAllAffinities() {
        return r.getProportions();
    }

    public int getId(classes.Character c) {
        String s = c.getCharacter();
        return getId(s);
    }

    public int getId(String s) {
        boolean found = false;
        int i = 0;
        while (!found) {
            if (characters.get(i).getCharacter() == s) found = true;
            else ++i;
        }
        return i;
    }

    public String getCharacterContent(int id) {
        return characters.get(id).getCharacter();
    }

    public classes.Character[] getAllCharacters() {
        classes.Character ret[] = new classes.Character[characters.size()];
        for (int i = 0; i < characters.size(); ++i)
        {
            ret[i] = characters.get(i).clone();
        }
        return ret;
    }

    public void addCharacter(classes.Character c) {
        characters.add(c);
        Collections.sort(characters, new ComparatorCharacter());
        r = new Relation(characters.size());
    }
}
