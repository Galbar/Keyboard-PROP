package sharedClasses;
import java.util.Scanner;

/**
 * Classe que comprova el funcionament de Bound
 * 
 * @author Alessio Linares
 */
public class BoundDriver {
    public static void main(String[] args){
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
        for(int i = n/2; i < n; ++i)  sol[i]=-1;
        float  cota = Bound.bound(sol, distances, costs);
        System.out.print("La cota calculada per la meitat d'una solució inicial és:\n\b");
        System.out.print(cota);
        System.out.print("\n\b");        
    }
    
}
