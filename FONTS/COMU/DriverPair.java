/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sharedClasses;

import java.util.Scanner;

/**
 *
 * @author Josep
 */
public class DriverPair {
    public static void main(String[] args)  {
        System.out.print("\nDriver de la classe Pair<L,R>\n");
        System.out.print("\nObjecte primer element(L): \n\n1 - Integer\n2 - Float");
        System.out.print("\n\nOpcio: ");
        Scanner in = new Scanner(System.in);
        int obj1 = in.nextInt();
        Integer valor1, valor2;
        Float valor1f, valor2f;
        System.out.print("\nObjecte segon element(R): \n\n1 - Integer\n2 - Float");
        System.out.print("\n\nOpcio: ");
        int obj2 = in.nextInt();
        if (obj1 == 1 && obj2 == 1) {
            System.out.print("\nValor primer element: ");
            valor1 = in.nextInt();
            System.out.print("\nValor segon element: ");
            valor2 = in.nextInt();
            Pair<Integer,Integer> myPair = new Pair<>(valor1, valor2);
            System.out.print("El meu Pair<Integer,Integer> es igual a "+myPair.first+", "+myPair.second);
            
        }
        else if (obj1 == 2 && obj2 == 1) {
            System.out.print("\nValor primer element: ");
            valor1f = in.nextFloat();
            System.out.print("\nValor segon element: ");
            valor2 = in.nextInt();
            Pair<Float,Integer> myPair2 = new Pair<>(valor1f, valor2);
            System.out.print("El meu Pair<Float,Integer> es igual a "+myPair2.first+", "+myPair2.second);
        }
        else if (obj2 == 2 && obj1 == 1) {
            System.out.print("\nValor primer element: ");
            valor1 = in.nextInt();
            System.out.print("\nValor segon element: ");
            valor2f = in.nextFloat();
            Pair<Integer,Float> myPair3 = new Pair<>(valor1, valor2f);
            System.out.print("El meu Pair<Float,Integer> es igual a "+myPair3.first+", "+myPair3.second);
        }
        else {
            System.out.print("\nValor primer element: ");
            valor1f = in.nextFloat();
            System.out.print("\nValor segon element: ");
            valor2f = in.nextFloat();
            Pair<Float,Float> myPair4 = new Pair<>(valor1f, valor2f);
            System.out.print("El meu Pair<Float,Integer> es igual a "+myPair4.first+", "+myPair4.second);
        }
    }
}
