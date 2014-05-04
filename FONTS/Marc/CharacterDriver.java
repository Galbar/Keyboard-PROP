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
public class CharacterDriver {
    public static void main(String[] args) {
        System.out.print("Introdueixi el character a crear\n\b");
        Scanner scaner = new Scanner(System.in);
        String cont= scaner.next();
        classes.Character c = new classes.Character(cont);
        classes.Character cc= c.clone();
        System.out.print("El caracter creat ha sigut clonat i és: ");
        System.out.print(cc.getCharacter());
        System.out.print("\n\b");
    }
    
}
