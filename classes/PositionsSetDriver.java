/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.util.Scanner;

/**
 *
 * @author Sam
 */
public class PositionsSetDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scaner = new Scanner(System.in);
        System.out.print("Introdueixi el nombre d'elements, ");
        int n=scaner.nextInt();
        classes.PositionsSet ps = new classes.PositionsSet(classes.enumerations.TopologyType.Squared,n);
        System.out.print("dos enters menors a aquest i la distancia que desitja entre aquests\n\b");
        int x = scaner.nextInt();
        int y = scaner.nextInt();
        float dist= scaner.nextFloat();
        ps.setDistance(x, y, dist);
        System.out.print("S'ha creat un conjunt de posicions amb ");
        System.out.print(ps.getAllPositions().length);
        System.out.print(" posicions i amb distancia ");
        System.out.print(ps.getDistance(x, y));
        System.out.print(" entre ");
        System.out.print(x);
        System.out.print(" i ");
        System.out.print(y);
        System.out.print("\n\b");
    }
}
