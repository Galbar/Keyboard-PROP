package classes;

import sharedClasses.*;
import classes.enumerations.*;
import static java.lang.Math.sqrt;
import java.util.*;
import classes.Position;

public class PositionsSet {
    private final TopologyType T;
    private Relation r;
    private float size;
    private final float MAX_ROW = 10;
    private Vector<Position> p;
    
    private void Calculate() {
        float lastRow = size%10;
        float totalRow = size - lastRow;
        Vector<Pair<Float, Float>> v = new Vector<Pair<Float, Float>>();
        r = new Relation((int)size);
        
        for (int i = 0; i < totalRow; ++i) {
            int aux = (int) (i/MAX_ROW);
            float x = aux;
            float y = i%MAX_ROW;
            v.add(new Pair<Float, Float>(x,y));
        }
        
        float mid = lastRow/2;
        float first = (MAX_ROW - lastRow)/2;
        for (int i = 0; i < lastRow; ++i) {
            int aux = (int) (size/10);
            float x = aux;
            float y = first + i;
            v.add(new Pair<Float,Float>(x,y));
        }
        
        for (int i = 0; i < size; ++i) {
            for (int j = i + 1; j < size; ++j) {
                float x = (float) Math.pow(v.get(j).second-v.get(i).second,2);
                float y = (float) Math.pow(v.get(j).first-v.get(i).first,2);
                float dist = (float) sqrt(x+y);
                //System.out.println("i: " + i + " j: " + j + " dist: " + dist);
                r.setRelation(i,j,dist);
                p.add(new Position(x,y));
            }
        }
    }
    
    public PositionsSet(TopologyType T, int n) {
        this.T = T;
        size = n;
        Vector<Position> p = new Vector<Position>();
        this.p = p;
        Calculate();
    }
    
   
    public float getDistance(int x, int y) {
        return r.getRelation(x,y);
    }
    
    public void setDistance(int x, int y, float k) {
        r.setRelation(x,y,k);
    }
    
    public Position[] getAllPositions() {
        Position ret[] = new Position[p.size()];
        for (int i = 0; i < p.size(); ++i)
        {
            ret[i] = p.get(i).clone();
        }
        return ret;
    }

    public float[][] getAllDistances() {
        return r.getRelations();
    }
    
   
    
}
