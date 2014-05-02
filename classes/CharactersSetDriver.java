package classes;

import java.util.Scanner;
import java.lang.StringBuilder;

public class CharactersSetDriver {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		//System.out.print("Introdueix el numero de tecles del teclat (n > 0): ");
		
		int n = 0;
		//while (n <= 0) n = scanner.nextInt();
		n = 4; // ONLY FOR THIS TEST
		CharactersSet charactersSet = new CharactersSet(n);

		//System.out.print("Introdueixi els caracters de cada tecla:\n");

		/*
		for (int i = 0; i < n ; ++i) {
			StringBuilder outputString = new StringBuilder();
			outputString.append("Introdueixi els caracters de la tecla ").append(Integer.toString(i)).append(":");
			System.out.print(outputString);
			String s = scanner.nextLine();
			s = scanner.nextLine();
			s = scanner.nextLine();


			Character c = new Character(s);
			charactersSet.addCharacter(c, i);
		}
		*/
		Character c = new Character("asp");
		charactersSet.addCharacter(c, 0);

		c = new Character("we");
		charactersSet.addCharacter(c, 1);

		c = new Character("q");
		charactersSet.addCharacter(c, 2);

		c = new Character("d");
		charactersSet.addCharacter(c, 3);

		//System.out.print("Introdueixi el text a analitzar:\n");
		//String text = scanner.nextLine();
		String text = "aspdqwe"; // ONLY FOR THIS TEST
		charactersSet.calculateText(text);

        System.out.print("LALALAL");

		for (int i = 0; i < n; ++i) {
			for ( int j = i + 1; j < n; ++j) {
				StringBuilder outputString = new StringBuilder();
				outputString.append(charactersSet.getCharacterContent(i)).append(" - ").append(charactersSet.getCharacterContent(j)).append(" = ").append(Float.toString(charactersSet.getAffinity(i, j))).append("\n");
				System.out.print(outputString);
			}
		}
	}
}
