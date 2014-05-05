package classes;

import sharedClasses.*;
import classes.enumerations.*;
import static java.lang.Math.sqrt;
import java.util.*;

/**
 * Calcula les posicions i distàncies de les diferents tecles depenent de la
 * forma del teclat
 *
 * @author Albert Trelis
 */

public class PositionsSet {
    private final TopologyType T;
    private Relation r;
    private float size;
    private final float MAX_ROW = 10;
    private Vector<Position> p;
    
    /**
	 * Càlcul principal de la classe. Calcula les posicions i distàncies
         * de les tecles en el cas que sigui un teclat rectangular.
	 */
    private void CalculateSquared() {
        float lastRow = size%10;
        float totalRow = size - lastRow;
        //Vector<Pair<Float, Float>> v = new Vector<Pair<Float, Float>>();
        r = new Relation((int)size);
        
        for (int i = 0; i < totalRow; ++i) {
            int aux = (int) (i/MAX_ROW);
            float x = aux;
            float y = i%MAX_ROW;
            p.add(new Position(x,y));
        }
        
        float mid = lastRow/2;
        float first = (MAX_ROW - lastRow)/2;
        for (int i = 0; i < lastRow; ++i) {
            int aux = (int) (size/10);
            float x = aux;
            float y = first + i;
            p.add(new Position(x,y));
        }
        
        for (int i = 0; i < size; ++i) {
            for (int j = i + 1; j < size; ++j) {
                float x = (float) Math.pow(p.get(j).y-p.get(i).y,2);
                float y = (float) Math.pow(p.get(j).x-p.get(i).x,2);
                float dist = (float) sqrt(x+y);
                //System.out.println("i: " + i + " j: " + j + " dist: " + dist);
                r.setRelation(i,j,dist);
            }
        }
    }
    
     /**
	 * Càlcul principal de la classe. Calcula les posicions i distàncies
         * de les tecles en el cas que sigui un teclat circular.
	 */
    private void CalculateCircular() {
        float dist;
        float aux = (float)(sqrt(size));
        float n = (float)Math.ceil(aux);
        if (n%2 == 0) ++n;

        /* Declaració Matriu */
        Float m[][] = new Float[(int)n][(int)n];
        for (Float[] i : m) {
            Arrays.fill(i,(float)(-1));
        }
        Integer maux[][] = new Integer[(int)n][(int)n];
        for (Integer[] i : maux) {
            Arrays.fill(i,0);
        } 

        /* Declaració Queue */
        LinkedList<Position> q = new LinkedList<Position>();

        /* BFS */
        int x = m.length/2;
        int y = m[0].length/2;
        Position pos = new Position((float)(x),(float)(y));
        q.addLast(pos);
        maux[x][y] = 1;
        float cont = 0;

        while (!q.isEmpty() && cont!=size) {
            Position actual = q.poll();
            float posx = actual.x;
            float posy = actual.y;
            m[(int)(posx)][(int)(posy)] = cont;
            p.add(actual);
            if ((posx+1 >= 0) && (posy >= 0) && (m.length > posx+1) && (m[(int)(posx+1)].length > posy) && (maux[(int)(posx+1)][(int)(posy)] == 0)) {
                q.addLast(new Position((float)(posx+1),(float)(y)));
                maux[(int)(posx+1)][(int)(posy)] = 1;
            }
            if ((posx-1 >= 0) && (posy >= 0) && (m.length > posx-1) && (m[(int)(posx-1)].length > posy) && (maux[(int)(posx-1)][(int)(posy)] == 0)) {
                q.addLast(new Position((float)(posx-1),(float)(posy)));
                maux[(int)(posx)][(int)(posy)] = 1;
            }
            if ((posx >= 0) && (posy+1 >= 0) && (m.length > posx) && (m[(int)(posx)].length > posy+1) && (maux[(int)(posx)][(int)(posy+1)] == 0)) {
                q.addLast(new Position((float)(posx),(float)(posy+1)));
                maux[(int)(posx)][(int)(posy+1)] = 1;
            }
            if ((posx >= 0) && (posy-1 >= 0) && (m.length > posx) && (m[(int)(posx)].length > posy-1) && (maux[(int)(posx)][(int)(posy-1)] == 0)) {
                q.addLast(new Position((float)(posx),(float)(posy-1)));
                maux[(int)(posx)][(int)(posy-1)] = 1;
            }
            ++cont;
        }

        /* Càlcul distàncies */
        for (int i = 0; i < size; ++i) {
            for (int j = i + 1; j < size; ++j) {
                float xpos = (float) Math.pow(p.get(j).y-p.get(i).y,2);
                float ypos = (float) Math.pow(p.get(j).x-p.get(i).x,2);
                dist = (float) sqrt(xpos+ypos);
                r.setRelation(i,j,dist);
                //System.out.println("i: " + xpos + " j: " + ypos + " dist: " + dist);
            }
        }

        /* Prints */
        /*for (int i = 0; i < m.length; ++i) {
            for (int j = 0; j < m[i].length; ++j) {
                System.out.print(" "+m[i][j]+" ");
            }
            System.out.println();
        }

        for (int i = 0; i < p.size(); ++i) {
            System.out.println("i: "+i+" x: "+p.get(i).x+" y: "+p.get(i).y);
        }*/
    }
    
    /**
	 * Constructora.
	 * @param  T És el forma que tindrà el teclat.
         * @param  n Quantitat de tecles que contindrà el teclat.
	 */
    public PositionsSet(TopologyType T, int n) {
        this.T = T;
        size = n;
        Vector<Position> p = new Vector<Position>();
        this.p = p;
        r = new Relation(n);
        if (T == TopologyType.Squared) CalculateSquared();
        else if (T == TopologyType.Circular) CalculateCircular();
    }
    
   /**
	 * Consulta les distancies.
	 * @param  x  Element 1.
         * @param  y  Element 2.
	 * @return La distància entre ambdós elements.
	 */
    public float getDistance(int x, int y) {
        return r.getRelation(x,y);
    }
    
    /**
	 * Modifica les distancies.
	 * @param  x  Element 1.
         * @param  y  Element 2.
         * @param  k  Nova distància.
	 */
    public void setDistance(int x, int y, float k) {
        r.setRelation(x,y,k);
    }
    
    /**
	 * Consulta les posicions.
	 * @return El vector amb les posicions.
	 */
    public Position[] getAllPositions() {
        Position ret[] = new Position[p.size()];
        for (int i = 0; i < p.size(); ++i)
        {
            ret[i] = p.get(i).clone();
        }
        return ret;
    }

    /**
	 * Consulta les distancies.
	 * @return El vector amb les relacions.
	 */
    public float[][] getAllDistances() {
        return r.getRelations();
    }
    
    /**
    * Retorna la Position de PositionsSet amb id <id>
    * @param id Int que correspon al id del Position que es vol consultar. 
    * @return Position amb id introduida
    */
    public Position getPosition(int id) {
        return p.get(id);
    }
    
}
