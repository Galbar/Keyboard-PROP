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
public class AlphabetDriver {
    public static void main(String[] args) {
        Alphabet a = new Alphabet();
        Scanner scaner = new Scanner(System.in);
        System.out.print("Introdueix el nom del alphabet a crear\n\b");
        String nom;
        nom = scaner.next();
        a.setName(nom);
        System.out.print("Alphabet ");
        System.out.print(nom);
        System.out.print(" creat, introdueixi el nombre de caracters i els caracters\n\b");
        int n;
        n=scaner.nextInt();
        for(int i = 0;i<n;++i){
            a.addCharacter(scaner.next());
        }
        classes.Character[] chars=a.getCharacters();
        for(int i = 0;i<n;++i){
            System.out.print(chars[i].getCharacter());
        }
        System.out.print("\n\b");
    }
    
}
