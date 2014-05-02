package sharedClasses;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jordi
 */
public class C <c1, c2> {
protected float qualificacio;
protected String id;
protected Vector<c1> elements;
protected Vector<c2> posicions;
protected Integer[ ] rel;

/*Pre: -
Post: Es crea una nova instància.
*/
public C(String id, c1[ ] elements, c2[ ] posicions, int[ ] rel){
   this.id=id;
   this.elements= new Vector<>(Arrays.asList(elements));
   this.posicions= new Vector<>(Arrays.asList(posicions));
   this.rel = new Integer[rel.length];
   for (int i = 0; i < rel.length; ++i) this.rel[i] = rel[i];   
}

/*
Pre: -
Post: Es crea una nova instancia amb id = id
*/
public C(String id){
    this.elements = new Vector<>();
    this.posicions = new Vector<>();
    this.rel = new Integer[0];
    this.id=id;
}

/*
Pre: -
Post: es crea una nova instancia buida.
*/
public C() {
    this.elements = new Vector<>();
    this.posicions = new Vector<>();
    this.rel = new Integer[0];
}

/*
Pre: -
Post: S’afegeix un element
*/
public void addElement(c1 element, c2 posicio) {
this.elements.add(element);
this.posicions.add(posicio);
ArrayList<Integer> relArray = new ArrayList(Arrays.asList(rel));
relArray.add(posicions.size()-1);
this.rel = relArray.toArray(new Integer[relArray.size()]);
}

/*
Pre: -.
Post: S’afegeix un element
*/
public void addElement(Pair<c1,c2> a) {
this.elements.add(a.first);
this.posicions.add(a.second);
c1 element = a.first; c2 posicio = a.second;
ArrayList<Integer> relArray = new ArrayList(Arrays.asList(rel));
relArray.add(posicions.size()-1);
this.rel = relArray.toArray(new Integer[relArray.size()]);
}

public String getID() {
    return id;
}

/*
Pre: -.
Post: El resultat de la funció és el conjunt d’elements c1 que conté C.
*/
public Vector<c1> getAllElements() {
    return elements;
}

/*
Pre: -.
Post: El resultat de la funció és el conjunt de posicions c2 que conté C.
*/
public Vector<c2> getAllPositions(){
    return posicions;
}

/*Pre: -.
Post: El resultat de la funció és el conjunt de relacions entre elements i posicions que conté C.
*/
public int[ ] getAllocations(){
    int n=rel.length;
    int[] allocate;
    allocate= new int[n];
    for(int i=0; i < rel.length;++i){
        allocate[i]=rel[i];
    }
    return allocate;
}

/*Pre: 0<=id<elements.length.
Post: Es retorna una parella amb l’element en qüestió (elements[id]) i la posició on està (posicions[rel[id]]).
*/
public Pair<c1,c2> getAllocation(int id){
   //Pair(L first, R second) {};
    Pair<c1,c2> res= new Pair<>(elements.get(id),posicions.get(rel[id]));
    return res;
}

/*Pre: 0<=id1,id2<elements.length.
Post: S’intercanvien les posicions de id1 i id2 (i.e., rel[id1] <=> rel[id2])*/
public void swap(int id1,int id2){
    Integer aux;
    aux=rel[id1];
    rel[id1]=rel[id2];
    rel[id2]=aux;   
}

/*Pre: -.
Post: Es retorna el cost actual del conjunt.*/
public float getScore(){
    return qualificacio;
}
}