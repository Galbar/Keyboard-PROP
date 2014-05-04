package classes;
import java.util.*;

public class Relation {
    private float[] relations;
    private float totalSum;
    int n;

    public Relation(int n) {
        this.n = n;
        totalSum = 0;
        relations = new float[(n*n-n)/2];
        Arrays.fill(relations, 0f);
    }

    /*
    Pre: a != b
    Post: Retorna la posicio al vector
    */
    private int getArrayPosition(int a, int b) {
        if (a == b) {} // exception To Do 
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

    public float getRelation(int a, int b) {
        return relations[getArrayPosition(a, b)]/totalSum;
    }

    public void setRelation(int a, int b, float value) {
        //System.out.println("setRelation: a="+a+", b="+b+", value="+value);
        //System.out.println("pos: "+getArrayPosition(a, b));
        totalSum += value - relations[getArrayPosition(a, b)];
        relations[getArrayPosition(a, b)] = value;
    }

    public float[][] getRelations() {
        float result[][] = new float[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                result[i][j] = result[j][i] = getRelation(i, j);
            }
        }
        return result;
    }

    /*
    Pre: a != b
    Post:
    */
    public void addToRelation(int a, int b, float value) {
        relations[getArrayPosition(a, b)] += value;
        totalSum += value;
    }
}
