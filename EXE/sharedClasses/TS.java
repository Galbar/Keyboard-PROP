package sharedClasses;


 
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 
/**
 *
 * @author Josep & Gerard
 */
public class TS {
    /*
        DEFINICIONES:
        - Función objetivo: minimizar el sumatorio sum[0..n](sum[0..n](Distancias*Costes)).
       
        - Estructura de solución: int[], donde cada posición del vector corresponde a una posición i el número
        escrito en ella el elemento asignado.
       
        - Movimiento: Intercambio de dos elementos entre sus posiciones.
       
        - Vecindario de la solución: Todas las soluciones a las que se llega desde la actual al hacer un solo
        movimiento. Es decir, todas las posibles combinaciones de n elementos tomados de 2 en 2. Si n=30, tenemos
        (30*29)/2 = 435 combinaciones, n=50, 1225. Así que habrá que acotar las combinaciones a un número máximo
        por definir.
   
        - Iteraciones máximas: por definir.
   
        - Función aspiración: se ignorará el estado taboo de un movimiento si este nos lleva a una solución
        mejor que la mejor encontrada hasta el momento.
        */
   
    private static boolean[][] costesItActualB;//matriz costes modificada/no modificada
    private static boolean[][] costesItAnteriorB;
    private static float[][] costesItActual;//costes
    private static float[][] costesItAnterior;

    private static int ultimoElem1, ultimoElem2;
   
    private static final int MAX_ITER = 10000;
    private static final int MAX_MOVS = 400; //número máximo de movimientos dentro de una iteración.

    static void solve() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    static class Movimiento {
        public int elem1;
        public int elem2;
        public float coste;
        public boolean isTaboo;
        public void rellenar(int e1, int e2, float c, boolean isT) {
            elem1 = e1;
            elem2 = e2;
            coste = c;
            isTaboo = isT;
        }
    }
    
    /*
    Pre: -.
    Post: Se devuelve el coste de la permutacion.
    */
    public static float funcionObjetivo(int[] permutacion, float[][] distancias, float[][] costes) {
        float valor = 0;
        for (int i = 0; i < permutacion.length; ++i) {
            for (int j = 0; j < permutacion.length; ++j){
                if(i!=j) valor = valor + costes[i][j]*distancias[permutacion[i]][permutacion[j]];
            }
        }
        return valor;
    }
   
    //Devolvemos el coste de un movimiento: a menor coste, mejor movimiento.
    private static float costeMovimiento(int[] permutacion, int elem1, int elem2, float[][] distancias, float[][] costes, boolean primeraIt) {
        float valor = 0;
        int pose1 = permutacion[elem1];
        int pose2 = permutacion[elem2];
        if (primeraIt || !costesItAnteriorB[elem1][elem2] || elem1==ultimoElem1
                                                               || elem1==ultimoElem2
                                                               || elem2==ultimoElem1
                                                               || elem2==ultimoElem2) {
            for (int k = 0; k < permutacion.length; ++k) { //coste O(N)
                if (k != elem1 && k != elem2) {
                    int posk = permutacion[k];
                    valor = valor + (costes[elem1][k] - costes[elem2][k])*(distancias[posk][pose2]-distancias[posk][pose1]);
                }
            }
            valor = 2*valor;
        }
        else { //coste O(1)
            int posUlte1 = permutacion[ultimoElem1];
            int posUlte2 = permutacion[ultimoElem2];
            valor = costesItAnterior[elem1][elem2] +
                    (costes[ultimoElem1][elem1] - costes[ultimoElem1][elem2]
                    + costes[ultimoElem2][elem2] - costes[ultimoElem2][elem1])
                    * (distancias[posUlte2][pose1] - distancias[posUlte2][pose2]
                    + distancias[posUlte1][pose2] - distancias[posUlte1][pose1]);
            costesItActualB[elem1][elem2] = true; costesItActual[elem1][elem2] = valor;
            costesItActualB[elem2][elem1] = true; costesItActual[elem2][elem1] = valor;
        }
       
        return valor;
    }
        /*
        Pre: 0 <= elem1 < n, 0 <= elem2 < n.
        Post: mejorMovIt es el movimiento más óptimo entre mejorMovIt y el movimiento entre elem1 y elem2
        */
 
    private static void valorarMovimiento(Movimiento mejorMovIt, int[] permutacion, float[][] distances, 
            float[][] costs, Integer[][] tabooList, int elem1, int elem2, boolean primeraIT, int k, int s,
            float costeActual, float mejorCoste){

            float coste = costeMovimiento(permutacion, elem1, elem2, distances, costs, primeraIT);

            int tabooValue1 = tabooList[elem1][permutacion[elem2]];
            int tabooValue2 = tabooList[elem2][permutacion[elem1]];

            boolean isTaboo = ((tabooValue1 != -1 || (k - tabooValue1) <= s) &&
                               (tabooValue2 != -1 || (k - tabooValue2) <= s) &&
                                costeActual+coste >= mejorCoste); //funcion aspiracion
            if (primeraIT){
                mejorMovIt.rellenar(elem1, elem2, coste, isTaboo);
            }
            else {
                if (mejorMovIt.isTaboo){  //por la razón que fuere
                    if ( (isTaboo && coste<mejorMovIt.coste) || (!isTaboo) )
                        mejorMovIt.rellenar(elem1, elem2, coste, isTaboo);
                }
                else if (!isTaboo && coste<mejorMovIt.coste)
                        mejorMovIt.rellenar(elem1, elem2, coste, isTaboo);
            }
    }
   
    /*
    Pre: distancies i relacions són matrius nxn. n és el nombre d’elements i de posicions a organitzar.
    Post: Es retorna a solució el conjunt dels elements en ordre.
    */
public static int[] solve(int[] solution, float[][] distances, float[][] costs) {
        int n = solution.length;
        int smin = (int)0.9*n; //tabooSizeMin: según Taillard, 0.9N. Ver funcionamiento tabooList.
        int smax = (int)1.1*n; //tabooSizeMax.
        int s; //será un valor entre smin y smax que se cambiará cada 2*smax iteraciones (Taillard).

        Integer[][] tabooList = new Integer[n][n]; //1ro: elementos, 2o: posiciones.
        //iniciar tabooList a -1
        for (int a = 0; a < tabooList.length; ++a) {
                    for (int b = 0; b < tabooList.length; ++b) {
                    tabooList[a][b] = -1;
                    }
        }
        

        float costeActual = funcionObjetivo(solution, distances, costs);
        float mejorCoste = costeActual; //necesario para funcion aspiracion

        int[] permutacion = solution;
        s = smin + (int)(Math.random() * ((smax - smin) + 1)); //valor entre smin y smax
        
        for (int k = 0; k < MAX_ITER; ++k) {
            
            Movimiento mejorMovIt = new Movimiento();
            costesItActual = new float[n][n];
            costesItActualB = new boolean[n][n];
            if (k==0) {
                costesItAnterior = new float[n][n];
                costesItAnteriorB = new boolean[n][n];
            }

            if ( (k%(2*smax) == 0) )
                    s = smin + (int)(Math.random() * ((smax - smin) + 1)); //valor entre smin y smax

            if (n <= 40){
                    for (int i=0; i<n; i++){
                            for (int j=i+1; j<n; j++){
                                valorarMovimiento(mejorMovIt, permutacion, distances, costs, tabooList, i, j, i==0, k, s, costeActual, mejorCoste);
                            }
                    }
            }
            else {
                    for (int i = 0; i < MAX_MOVS; ++i) {

                        int elem1;
                        int elem2; //elementos a "intentar" mover                                
                        do{
                            elem1 = (int)(Math.random() * (n));
                            elem2 = (int)(Math.random() * (n)); //elementos a "intentar" mover
                            while (elem2==elem1) elem2 = (int)(Math.random() * (n));    
                        } while(costesItActualB[elem1][elem2]);

                        valorarMovimiento(mejorMovIt, permutacion, distances, costs, tabooList, elem1, elem2, i==0, k, s, costeActual, mejorCoste);
                    }
            }
            //se tiene en mejorMovIt el movimiento óptimo.
            costesItAnterior = costesItActual;
            costesItAnteriorB = costesItActualB;

            ultimoElem1 = mejorMovIt.elem1;
            ultimoElem2 = mejorMovIt.elem2;

            
            int auxPos = permutacion[mejorMovIt.elem1];
            permutacion[mejorMovIt.elem1] = permutacion[mejorMovIt.elem2];
            permutacion[mejorMovIt.elem2] = auxPos;

            costeActual = costeActual+mejorMovIt.coste;

            if (costeActual < mejorCoste) mejorCoste = costeActual;

            //actualizar tabooList
            tabooList[mejorMovIt.elem1][permutacion[mejorMovIt.elem1]] = k;
            tabooList[mejorMovIt.elem2][permutacion[mejorMovIt.elem2]] = k;
        }
        return permutacion;
    }
}