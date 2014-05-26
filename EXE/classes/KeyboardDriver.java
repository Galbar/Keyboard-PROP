/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.util.Scanner;
import classes.enumerations.*;
/**
 *
 * @author Samuel.Bryan.Pierno
 */
public class KeyboardDriver {
    public static void main(String[] args){
        Scanner scaner = new Scanner(System.in);
        System.out.print("introdueixi el nom del nou keyboard\n\b");
        String nom = scaner.next();
        System.out.print("introdueixi el nombre de tecles\n\b");
        int n = scaner.nextInt();
        classes.Position[] p =new classes.Position[n];
        classes.Character[] c =new classes.Character[n];
        int[] ass = new int[n];
        if(n%3 != 0) n+=2;
        classes.Keyboard k = new classes.Keyboard<classes.Character, Position>(nom,classes.enumerations.TopologyType.Squared,classes.enumerations.UsageMode.Default,n/3,3);
        classes.Keyboard k1 =new classes.Keyboard<classes.Character, Position>(nom,classes.enumerations.TopologyType.Squared,classes.enumerations.UsageMode.Default,n/3,3,c,p,ass);
        System.out.print("introdueixi el nombre de referencies i aquestes\n\b");
        n = scaner.nextInt();
        for(int i=0;i<n;++i)k.addReference(scaner.next());
        System.out.print("S'ha creat el keyboard ");
        System.out.print(k.getName());
        System.out.print(" amb ");
        System.out.print(k.getHeight());
        System.out.print(" tecles d'alçada i ");
        System.out.print(k.getWidth());
        System.out.print(" d'amplada, la topologia per defecte: ");
        System.out.print(k.getTopology());
        System.out.print(", el mode d'us per defecte: ");
        System.out.print(k.getUsageMode());
        System.out.print(" i les següents referencies:\n\b");
        String[] r = k.getReferences();
        for(int i = 0; i < r.length;++i){
            System.out.print(r[i]);
            System.out.print("\n\b");
        }
    }
}
