//package classes;
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
        totalSum += value - relations[getArrayPosition(a, b)];
        relations[getArrayPosition(a, b)] = value;
    }

    public float[] getRelations() {
        return relations;
    }

    /*
    Pre: a != b
    Post:
    */
    public void addToRelation(int a, int b, float value) {
        /*
        System.out.print("\na = ");
        System.out.print(Integer.toString(a));
        System.out.print("\nb = ");
        System.out.print(Integer.toString(b));
        System.out.print("\n"); */
        System.out.print("\n" + getArrayPosition(a, b));
        System.out.print(" // ADD = " + value +"\n"); 
        relations[getArrayPosition(a, b)] += value;
        totalSum += value;
    }
}
