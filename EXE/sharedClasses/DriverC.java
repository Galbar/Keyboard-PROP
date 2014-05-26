package sharedClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import main.Llibre;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jordi
 */
public class DriverC {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;   
        if (args.length > 1) Usage();
        if (args.length == 0) in = new Scanner(System.in);
        else {
            File file = new File(args[0]);
            in = new Scanner(file);
        }        
        System.out.print("\n Driver de la classe C\n");
        C prova=new C();
        Llibre element;
        Integer posicio;
        ArrayList<Llibre>elements;
        ArrayList<Integer>posicions;
        int[ ] rel;
        elements=prova.getAllElements();
        posicions=prova.getAllPositions();
                
        int op;
        System.out.print("1 - Afegir Element\n");
        System.out.print("2 - Afegir Element en forma de Pair \n");
        System.out.print("3 - Fer swap de la relacio\n");
        System.out.print("4 - Estat actual\n");
        System.out.print("5 - Sortir \n");
        System.out.print("\n Introduir Opcio: ");
        op = in.nextInt();

        while (op != 5) {
            switch(op) {
                case 1:
                    System.out.print("Introduir titol:\n");
                    String titol;
                    titol = in.next();    
                    element = new Llibre(titol);
                    System.out.print("\n Introduir posicio:\n");
                    posicio=in.nextInt();        
                    prova.addElement(element,posicio);
                    System.out.print("\n Comprovar que s'ha afegit l'element:\n");
                    posicions=prova.getAllPositions();
                    
                    System.out.print("\n Escriure Elements:\n");                   
                    int n=elements.size();
                    for (int i=0; i<n; i++){
                        System.out.print(elements.get(i).getTitol()+" ");
                    }
                    
                    System.out.print("\n Escriure Posicions.\n");
                    n=posicions.size();
                    for (int i=0; i<n; i++){
                        System.out.print(posicions.get(i)+" ");
                    }
                    break;
                case 2:
                    System.out.print("\nAfegir Pair:");
                    System.out.print("\nIntroduir Titol:");
                    titol = in.next();  
                    element = new Llibre(titol);

                    System.out.print("\nIntroduir Posicio:");                
                    posicio= in.nextInt();
                    Pair<Llibre,Integer> a=new Pair(element,posicio);
                    prova.addElement(a);

                    System.out.print("\n Comprovar que s'ha afegit el pair.\n");
                    posicions=prova.getAllPositions();
                    System.out.print("\nEscriure Elements.\n");
                    n=elements.size();
                    for (int i=0; i<n; i++){
                        System.out.print(elements.get(i).getTitol()+" ");
                    }
                    System.out.print("\n Escriure Posicions.\n");
                    n=posicions.size();
                    for (int i=0; i<n; i++){
                        System.out.print(posicions.get(i)+" ");
                    }
                    break;
                case 3:
                    System.out.print("\n Fer swap.\n");
                    rel=prova.getAllocations();
                    System.out.print("\nEntrar dues posicions valides.\n");
                    int n1,n2;
                    n1=in.nextInt();
                    n2=in.nextInt();
                    prova.swap(n1, n2);
                    n=rel.length;
                    System.out.print("Escriure rel despres de fer el swap.\n");
                    for (int i=0; i<n; i++){
                        System.out.print(rel[i]+" ");
                    }
                 break;
                case 4:
                    System.out.print("\n Estat actual:\n");
                    n = elements.size();
                    for (int i=0; i<n; i++){
                        System.out.print(elements.get(i).getTitol()+" ");
                    }
                    System.out.print("Escriure com estar posicions.\n");
                    n=posicions.size();
                    for (int i=0; i<n; i++){
                        System.out.print(posicions.get(i)+" ");
                    }
                    break;
            }   
            System.out.print("\n Introduir opció:");
            System.out.print("1 - Afegir Element\n");
            System.out.print("2 - Afegir Element en forma de Pair \n");
            System.out.print("3 - Fer swap de la relacio\n");
            System.out.print("4 - Estat actual\n");
            System.out.print("5 - Sortir \n");
            op = in.nextInt();
        }
        in.close();
    }
        

    private static void Usage() {
      System.out.print("USAGE: args.length == 1: archivo desde donde leer los datos (numero elementos)");
      System.exit(0);
    }
}
