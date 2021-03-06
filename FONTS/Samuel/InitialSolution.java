/*La solució inicial agafa els dos elements amb cost mínim entre ells i els 
 *assigna a les dues posicions que tenen la distancia mínima llavors va 
 *assignant el següent element amb cost mínim amb un dels anteriors a la següent
 *posició amb distancia mínima a una de les anteriors.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharedClasses;

/**
 *
 * @author Samuel.Bryan.Pierno
 */
public class InitialSolution {
/**
	 * Funció principal de la classe. Aquesta espera una matriu nxn de costos i
         * una de distancies i retorna les assignacions de caracters a posicions en
         * una array on el index identifica la posició i el enter que conté el caracter.
	 * @param  costs            matriu nxn amb els costos.
	 * @param  distances        matriu nxn amb les distancies.
	 * @return                  Array n on cada posició representa una assignació,
	 *                          el contingut és el caracter i l'index la posició.
	 */
    public static int[] compute(float[][] costs, float[][] distances) {
        boolean[] bo = new boolean[costs.length];
        int[] s = new int[costs.length];
        int a = 0, b = 1;
        for (int i = 0; i < s.length; ++i) {
            for (int j = i + 1; j < s.length; ++j) {
                if (costs[i][j] < costs[a][b]) {
                    a = i;
                    b = j;
                }
            }
        }
        if (a < bo.length) {
            bo[a] = true;
            s[0] = a;
            a = b;
        }
        for (int i = 1; i < s.length; ++i) {
            b = a;
            bo[a] = true;
            s[i] = a;
            for (int j = 0; j < costs[a].length; ++j) {
                if (!bo[j] && (costs[a][j] < costs[a][b] || bo[b])) {
                    b = j;
                }
            }
            a = b;
        }
        bo = new boolean[costs.length];
        int[] sf = new int[distances.length];
        a = 0;
        b = 1;
        for (int i = 0; i < sf.length; ++i) {
            for (int j = i + 1; j < sf.length; ++j) {
                if (distances[i][j] < distances[a][b]) {
                    a = i;
                    b = j;
                }
            }
        }
        bo[a] = true;
        sf[a] = s[0];
        a = b;
        for (int i = 1; i < sf.length; ++i) {
            b = a;
            bo[a] = true;
            sf[a] = s[i];
            for (int j = 0; j < distances[a].length; ++j) {
                if (!bo[j] && (distances[a][j] < distances[a][b] || bo[b])) {
                    b = j;
                }
            }
            a = b;
        }
        return sf;
    }
}
