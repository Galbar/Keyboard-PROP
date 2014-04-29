public class Relation {
    private float[][] relations;

    public Relation() {
        for (int i = 0; i < relations.length(); ++i) {
            for (int j = 0; j < relations.length(); ++j) {
                relations[i][j] = 0;
            }
        }
    }

    public float[][] getRelations() {
        return relations;
    }

    public float getRelation(int a, int b) {
        return relations[a][b];
    }

    public void setRelation(int a, int b, float value) {
        relations[a][b] = value;
    }
