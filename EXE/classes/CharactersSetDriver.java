package classes;

import java.util.Scanner;
import java.lang.StringBuilder;

/**
* Classe que comprova el funcionament de CharactersSet.
*
* @author Marc Muntanyola
*/

public class CharactersSetDriver {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Introdueix el numero de tecles del teclat (n > 1): ");
		
		int n = 0;
		while (n <= 1) n = scanner.nextInt();
                classes.Character[] cs = new classes.Character[n];

		System.out.print("Introdueixi els caracters de cada tecla:\n");

		for (int i = 0; i < n ; ++i) {
			StringBuilder outputString = new StringBuilder();
			outputString.append("Introdueixi els caracters de la tecla ").append(Integer.toString(i)).append(":");
			System.out.print(outputString);
			String s = scanner.next();
                        cs[i]=new classes.Character(s);
		}
		CharactersSet charactersSet = new CharactersSet(cs);
		
                /**************/
                charactersSet.calculateFrequency("56 hola 86 adeu");
                
                System.out.print("Introdueixi el text a analitzar acabat amb 'FiNaL':\n");
                StringBuilder textf = new StringBuilder();
                String text=scanner.next();
                while(!"FiNaL".equals(text)){
                    textf.append(text);
                    text = scanner.next();
                }
                
		charactersSet.calculateText(textf.toString());
                
		for (int i = 0; i < n; ++i) {
			for ( int j = i + 1; j < n; ++j) {
                                System.out.print(charactersSet.getCharacterContent(i));
                                System.out.print(" ");
                                System.out.print(charactersSet.getCharacterContent(j));
                                System.out.print(" ");
				System.out.print(charactersSet.getAffinity(i, j));
                                System.out.print("\n\b");
			}
		}
                System.out.print(charactersSet.getAffinity(0,1)==charactersSet.getAllAffinities()[0][1] && charactersSet.getId(cs[0]) == charactersSet.getId(cs[0].getCharacter()) && charactersSet.getCharacterContent(0) == charactersSet.getAllCharacters()[0].getCharacter());
                
	}
}
