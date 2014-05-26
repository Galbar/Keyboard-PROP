package classes;

import java.util.*;

/**
* Guarda les relacions entre Characters (freqüència), i també entre Positions (distància)
*
* @author Marc Muntanyola
*/

public class Relation {
    private float[] relations;
    private float totalSum;
    private int n;

    /**
    * Constructora
    * @param n Int que representa el nombre d'elements dels que caldrà guardar relacions
    */
    public Relation(int n) {
        this.n = n;
        totalSum = 0;
        relations = new float[(n*n-n)/2];
        Arrays.fill(relations, 0f);
    }

    /**
    * Consulta
    * Pre: a != b
    * Post: Retorna la posicio al array
    */
    private int getArrayPosition(int a, int b) {
        if (a == b) {} // exception
        if (a > b) {
            int aux = a;
            a = b;
            b = aux;
        }
        int base = 0;
        for (int i = 1; i <= a; ++i) {
            base += n - i;
        }
        return base + b - 1 - a;
    }

    /**
    * Consulta la relació entre dos elements diferents.
    * @param a Id d'un dels elements que forma la relació.
    * @param b Id de l'altre element que forma la relació. b != a.
    */
    public float getRelation(int a, int b) {
        if (a != b)
            return relations[getArrayPosition(a, b)];
        else 
            return 0;
    }

    /**
    * Consulta la proporció que representa la relació entre dos elements diferents respecte
    * la suma total del valor de les altres relacions.
    * @param a Id d'un dels elements que forma la relació
    * @param b Id de l'altre element que forma la relació. b != a.
    * @return Float que conté el precentatge que representa aquesta relació respecte totes les relacions.
    */
    public float getProportion(int a, int b) {
        if (a != b) 
            return relations[getArrayPosition(a, b)]/totalSum;
        else
            return 0;
    }

    /**
    * Introdueix el valor d'una relació entre dos elements diferents.
    * @param a Id d'un dels elements que forma la relació.
    * @param b Id de l'altre element que forma la relació. b != a.
    * @param value Float que conté el valor de la relació. 
    */
    public void setRelation(int a, int b, float value) {
        if (a != b) {
            totalSum += value - relations[getArrayPosition(a, b)];
            relations[getArrayPosition(a, b)] = value;
        }
    }

    /**
    * Consulta totes les relacions entre tots els elements.
    * @return Matriu de floats que conté totes les relacions.
    */
    public float[][] getRelations() {
        float result[][] = new float[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                result[i][j] = result[j][i] = getRelation(i, j);
            }
        }
        return result;
    }

    /**
    * Consulta totes les relacions entre tots els elements. Relacions en forma de percentatge respecte la suma total del valor de totes les relacions.
    * @return Matriu de floats que conté els percentatges que representen les relacions respecte la suma de totes les altres relacions.
    */
    public float[][] getProportions() {
        float result[][] = new float[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                result[i][j] = result[j][i] = getProportion(i, j);
            }
        }
        return result;
    }

    /**
    * Afegeix una nova relació
    * @param a Id d'un dels elements que forma la relació.
    * @param b Id de l'altre element que forma la relació. b != a.
    * @param value Float que conté el valor de la relació. 
    */
    public void addToRelation(int a, int b, float value) {
        if (a != b) {
            relations[getArrayPosition(a, b)] += value;
            totalSum += value;
        }
    }
}
