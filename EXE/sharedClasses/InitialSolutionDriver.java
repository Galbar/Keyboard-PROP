/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sharedClasses;

/**
 *
 * @author Sam
 */
import java.util.Scanner;

public class InitialSolutionDriver {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scaner = new Scanner(System.in);
        int n = 0;
        while(n<=0){
            System.out.print("Introdueix el nombre d'elements(n>0)\n\b");
            n=scaner.nextInt();
        }
        float[][] distances = new float[n][n];
        System.out.print("Introdueix la matriu(de mida nxn) de distancies entre posicions\n\b");
        for(int i = 0; i < n; ++i){
            for(int j = 0; j < n; ++j){
               distances[i][j]=scaner.nextInt();
            }
        }
        float[][] costs = new float[n][n];
        System.out.print("Introdueix la matriu(de mida nxn) de costos entre elements\n\b");
        for(int i = 0; i < n; ++i){
            for(int j = 0; j < n; ++j){
               costs[i][j]=scaner.nextInt();
            }
        }
        int[] sol= InitialSolution.compute(costs, distances);
        System.out.print("La solució inicial és:\n\b"); 
        for(int i = 0; i < n; ++i){
            System.out.print(sol[i]);
            System.out.print(" ");
        }
        System.out.print("\n\b");
    }
    
}
