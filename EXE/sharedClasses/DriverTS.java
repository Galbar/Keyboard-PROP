package sharedClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Josep
 */
public class DriverTS {
    
    public static void main(String [ ] args) throws FileNotFoundException, IOException {
        int n = 0;
        float[][] costs = null;
        float[][] distances = null;
        
        Scanner in;
        in = new Scanner(System.in);
        
        
        System.out.print("Dado un numero de elementos n, la matriz de costes entre elementos y"
                + " la matriz de distancias,"
                + "se devolvera un vector solucion en el cual cada posicion correspondera"
                + "a un elemento (en el mismo orden en que se introdujo la matriz de costes) y que contendra"
                + "el numero de su posicion (de 0 a n-1) asignada, ademas del coste de la solucion.\n");
        
        System.out.print("\n1 - Mediante fichero\n");
        System.out.print("\n2 - Por consola\n");
        System.out.print("\nOpcion: ");
        int op = in.nextInt();
        
        switch(op) {
            case 1:
                System.out.print("\nIntroducir ruta fichero: ");
                String path = new File(in.next()).getCanonicalPath();
                File file = new File(path);
                in = new Scanner(file);
                n = in.nextInt();
                costs = new float[n][n];
                distances = new float[n][n];
                for (int i=0; i<n; i++){
                    for (int j=0; j<n; j++){
                        //System.out.print("coste entre"+i+" y "+j+":");
                        //float c = (float) Math.random() * 20;
                        float c = in.nextFloat();
                        costs[i][j] = c;
                    }
                }
                for (int i=0; i<n; i++){
                    for (int j=0; j<n; j++){
                        //System.out.print("coste entre"+i+" y "+j+":");
                        //float c = (float) Math.random() * 20;
                        float c = in.nextFloat();
                        distances[i][j] = c;
                    }
                }
                
            break;
            case 2:
                System.out.print("\n Numero de elementos: "); n = in.nextInt();
                costs = new float[n][n];
                distances = new float[n][n];
                System.out.print("\n Matriz de costes: ");
                for (int i=0; i<n; i++){
                     for (int j=0; j<n; j++){
                    //System.out.print("coste entre"+i+" y "+j+":");
                    //float c = (float) Math.random() * 20;
                    float c = in.nextFloat();
                    costs[i][j] = c;
                    }
                }
                System.out.print("\n Matriz de distancias: ");
                for (int i=0; i<n; i++){
                    for (int j=0; j<n; j++){
                        //System.out.print("coste entre"+i+" y "+j+":");
                        //float c = (float) Math.random() * 20;
                        float c = in.nextFloat();
                        distances[i][j] = c;
                    }
                }
            break;
               
        }
        
        
        in.close();
        
        int[] solucion = InitialSolution.compute(costs, distances);
        
        System.out.print("\n Calculando... \n\n");
        solucion = TS.solve(solucion, distances, costs);
        
        System.out.print("Valor: "+TS.funcionObjetivo(solucion,distances,costs)+"\n");
        System.out.print("Solucion: ");
        for (int i=0; i<n; i++) System.out.print(solucion[i]+" ");
    }
}
