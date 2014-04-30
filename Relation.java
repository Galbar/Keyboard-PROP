public class Relation {
    private Float[] relations;

    public Relation(int n) {
        relations = new Float[(n*n-n)/2];
        for (int i = 0; i < relations.length(); ++i)
                relations[i] = 0;
        
    }

    public Float[] getRelations() {
        return relations;
    }

    private int getArrayPosition(int a, int b) {
        if (a == b) EXCEPTION!!!
        if (a > b) {
            int aux = a;
            a = b;
            b = aux;
        }
        return (relations.length() - 1) * a + b - 1;
    }

    public float getRelation(int a, int b) {
        return relations[getArrayPosition(a, b)];
    }

    public void setRelation(int a, int b, float value) {
        relations[getArrayPosition(a, b)] = value;
    }
