/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sharedClasses;

import java.util.Scanner;

/**
 *
 * @author Sam
 */
public class HungarianAlgorithmDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scaner = new Scanner(System.in);
        System.out.print("Introdueix el nombre d'elements i la matriu de costos entre aquests\n\b");
        int n = scaner.nextInt();
        Float[][] costos = new Float[n][n];
        for (int i=0; i < n; ++i){
               for(int j=0; j < n; ++j){
                   costos[i][j]=scaner.nextFloat();
               }
        }
        int[][] res = sharedClasses.HungarianAlgorithm.computeAssignments(costos);
        System.out.print("les assignacions que sumen el cost mínim son:\n\b");
        for (int i = 0; i < res.length;++i){
            for (int j = 0; j < res[i].length;++j){
                System.out.print(res[i][j]);
                System.out.print(" ");
            }
            System.out.print("\n\b");
        }
    }
}
