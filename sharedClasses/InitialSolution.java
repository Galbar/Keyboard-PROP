//AQUESTA VERSIO ES LA QUE INDUEIX EL NOM DE COST A LA MATRIU DE RELACIONS I ES LA QUE PITJORS RESULTATS DONA POSA ELS COSTOS BAIXOS A DISTANCIES BAIXES
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package initial.solution;

/**
 *
 * @author Sam
 */
public class InitialSolution {

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