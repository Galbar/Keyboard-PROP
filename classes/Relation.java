package classes;
import sharedClasses.*;
import java.util.*;

public class Relation {
    private float[] relations;
    private float totalSum;

    public Relation(int n) {
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
        return (relations.length - 1) * a + b - 1;
    }

    public float getRelation(int a, int b) {
        return relations[getArrayPosition(a, b)]/totalSum;
    }

    public void setRelation(int a, int b, float value) {
        totalSum += value - relations[getArrayPosition(a, b)];
        relations[getArrayPosition(a, b)] = value;
    }

    public void addToRelation(int a, int b, float value) {
        relations[getArrayPosition(a, b)] += value;
        totalSum += value;
    }
}
