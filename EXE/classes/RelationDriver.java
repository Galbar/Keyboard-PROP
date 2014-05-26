package classes;

import java.util.Scanner;

/**
* Classe que comprova el funcionament de Relation.
*
* @author Marc Muntanyola
*/

public class RelationDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scaner = new Scanner(System.in);
        System.out.print("Introdueixi el nombre d'elements, 2 enters menors a aquest i la relació desitjada entre els corresponents elementsn\n\b");
        int n = scaner.nextInt();
        classes.Relation rel = new classes.Relation(n);
        int x = scaner.nextInt();
        int y = scaner.nextInt();
        float r = scaner.nextFloat();
        rel.setRelation(x, y, r);
        System.out.print("S'ha creat un conjunt de relacions amb ");
        System.out.print(rel.getRelations().length);
        System.out.print(" relacions i amb relació ");
        System.out.print(rel.getRelation(x, y));
        System.out.print(" entre ");
        System.out.print(x);
        System.out.print(" i ");
        System.out.print(y);
        System.out.print(" que proporcionalment és ");
        System.out.print(rel.getProportion(x, y));
        System.out.print("\n\b");
        System.out.print(rel.getProportion(x, y) == rel.getProportions()[x][y] && rel.getRelation(x, y) == rel.getRelations()[x][y]);
    }

}
