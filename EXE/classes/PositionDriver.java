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
public class PositionDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scaner = new Scanner(System.in);
        System.out.print("Introdueixi una x seguida de una y per la posició\n\b");
        classes.Position XY = new classes.Position(scaner.nextFloat(), scaner.nextFloat());
        classes.Position xy = XY.clone();
        System.out.print("S'ha creat i clonat una posició amb x=");
        System.out.print(xy.x);
        System.out.print(" i y=");
        System.out.print(xy.y);
        System.out.print("\n\b");
    }
    
}
