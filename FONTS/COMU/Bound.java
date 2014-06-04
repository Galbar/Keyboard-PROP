package sharedClasses;
import java.util.*;

/**
 * Classe que conté els mètodes necessaris per calcular la cota mínima de la
 * solució parcial d'un QAP(A, B) que se li passa.
 *
 * @author Alessio Linares
 */
public class Bound
{
	/**
	 * Funció principal de la classe. Aquesta rep la solució parcial i les
	 * matrius de costos.
	 * @param  partial_solution solució parcial per la qual s'ha de calcular
	 *                          la cota mínima.
	 * @param  distances        matriu de distancies entre localitzacions.
	 * @param  costs            matriu de pes entre facilitats.
	 * @return                  cota mínima del cost de la solució parcial donada.
	 */
	public static float bound (int[] partial_solution, float[][] distances, float[][] costs)
	{
		Vector<Integer> elements = new Vector<Integer>();
		Vector<Integer> positions = new Vector<Integer>();
		float cost = calculate(partial_solution, distances, costs, elements, positions);
		
		Integer n = elements.size();

		if ( n == 0 ) return cost;

		Float A[][] = new Float[n][n];
		A = removeUnneededRowsAndColumns(costs, elements);
		Float B[][] = new Float[n][n];
		B = removeUnneededRowsAndColumns(distances, positions);

		//Float A_diagonal[] = new Float[n];
		//Float B_diagonal[] = new Float[n];
		//Float ANoDiagonal[][] = new Float[n][n-1];
		//ANoDiagonal = removeDiagonal(A, A_diagonal);
		//Float BNoDiagonal[][] = new Float[n][n-1];
		//BNoDiagonal = removeDiagonal(B, B_diagonal);
		
		for (int i = 0; i < n; ++i)
		{
			Arrays.sort(A[i]);
			Arrays.sort(B[i], Collections.reverseOrder());
		}

		Float X[][] = new Float[n][n];		// minimum scalar product
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < n; ++j)
				X[i][j] = dotProduct(A[i], B[j]);

		Float Y[][] = new Float[n][n];
		Y = extendedSolution(costs, distances, elements, positions, partial_solution, cost);

		Float L[][] = new Float[n][n];
		L = add(X, Y);

		Float aux[][] = new Float[n][n];
		for (int i = 0; i < n; ++i)
			aux[i] = L[i].clone();
		int[][] assig = HungarianAlgorithm.computeAssignments(aux);

		for (int i = 0; i < n; ++i)
			cost += L[assig[i][0]][assig[i][1]];

		return cost;
	}

	/**
	 * Extreu la diagonal (i,i) de A i la guarda a v com a vector.
	 * @param  A matriu a la que treure la diagonal.
	 * @param  v array on es guarda la diagonal extreta.
	 * @return   matriu que queda després de treure la diagonal a A.
	 */
	private static Float[][] removeDiagonal(Float[][] A, Float[] v)
	{
		Integer n = A.length;
		Float ret[][] = new Float[n][n-1];
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < n; ++j)
				if (i != j)
					if (i > j)
						ret[i][j] = A[i][j];
					else
						ret[i][j-1] = A[i][j];
				else
					v[i] = A[i][j];
		return ret;
	}

	/**
	 * Producte escalar entre els vectors u i v
	 * @param  u vector
	 * @param  v vector
	 * @return   producte escalar dels vectors u i v
	 */
	private static Float dotProduct(Float[] u, Float[] v)
	{
		Float ret = new Float(0f);
		for (int i = 0; i < u.length; ++i)
			ret += u[i]*v[i];
		return ret;
	}

	/**
	 * Calcula una matriu on la posició i,j és el cost que s'afegiria al cost de
	 * la solució si s'afegis una nova assignació no assignada previament.
	 * @param  costs            Matriu de costs.
	 * @param  distances        Matriu de distancies.
	 * @param  elements         Vector amb els elements no assignats.
	 * @param  positions        Vector amb les posicions no assignades.
	 * @param  partial_solution Solució parcial.
	 * @param  parcial_cost     Cost de la solució parcial.
	 * @return                  Matriu amb el costs afegit de les assignacións.
	 */
	private static Float[][] extendedSolution(float[][] costs, float[][] distances, Vector<Integer> elements, Vector<Integer> positions, int[] partial_solution, float parcial_cost)
	{
		Integer n = elements.size();
		Float ret[][] = new Float[n][n];
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < n; ++j) {
				partial_solution[elements.get(i)] = positions.get(j);
				ret[i][j] = calculate(partial_solution, distances, costs)-parcial_cost;
				partial_solution[elements.get(i)] = -1;
			}
		return ret;
	}

	/**
	 * Suma de dos matrius.
	 * @param  A matriu.
	 * @param  B matriu.
	 * @return   matriu resultant de la suma de les dos matrius.
	 */
	private static Float[][] add(Float[][] A, Float[][] B)
	{
		Integer n = A.length;
		Float ret[][] = new Float[n][n];
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < n; ++j)
				ret[i][j] = A[i][j] + B[i][j];
		return ret;
	}

	/**
	 * Funció que calcula el cost de les assignacions a la solució parcial.
	 * @param  partial_solution solució parcial del QAP.
	 * @param  distances        matriu de distancies entre localitzacions.
	 * @param  costs            matriu de pes entre facilitats.
	 * @return                  cost de les assignacions de la solució parcial.
	 */
	private static float calculate (int[] partial_solution, float[][] distances, float[][] costs)
	{
		Integer n = partial_solution.length;
		float cost = 0f;
		for ( Integer i = 0; i < n; ++i )
		{
			if (partial_solution[i] < 0) {
				continue;
			}
			for ( Integer j = i; j < n; ++j )
			{
				if (partial_solution[j] < 0) continue;
				cost += costs[i][j] * distances[partial_solution[i]][partial_solution[j]];
			}
		}
		return 2*cost;
	}

	/**
	 * Funció que calcula el cost de les assignacions a la solució parcial i guarda
	 * a elements i positions les facilitats i localitzacions, respectivament, que no
	 * estan assignades.
	 * @param  partial_solution solució parcial del QAP.
	 * @param  distances        matriu de distancies entre localitzacions.
	 * @param  costs            matriu de pes entre facilitats.
	 * @param  elements         vector que, en acabar, guarda les facilitats que no
	 *                          estan assignades.
	 * @param  positions        vectors que, en acabar, guarda les localitzacions que
	 *                          no estan assignades.
	 * @return                  cost de les assignacions de la solució parcial.
	 */
	private static float calculate (int[] partial_solution, float[][] distances, float[][] costs, Vector<Integer> elements, Vector<Integer> positions)
	{
		Integer n = partial_solution.length;
		float cost = 0f;
		for (Integer i = 0; i < n; ++i)
		{
			positions.add(i);
		}
		for ( Integer i = 0; i < n; ++i )
		{
			if (partial_solution[i] < 0) {
				elements.add(i);
				continue;
			}
			positions.removeElement(partial_solution[i]);
			for ( Integer j = i; j < n; ++j )
			{
				if (partial_solution[j] < 0) continue;
				cost += costs[i][j] * distances[partial_solution[i]][partial_solution[j]];
			}
		}
		return 2*cost;
	}

	/**
	 * Retorna una matriu que contè de m només les files i columnes que indica v.
	 * @param  m matriu a reduir.
	 * @param  v vector que indica quines files i columnes mantenir.
	 * @return   matriu resultant de l'operació.
	 */
	private static Float[][] removeUnneededRowsAndColumns (float[][] m, Vector<Integer> v)
	{
		Integer n = v.size();
		Float r[][] = new Float[n][n];
		for (Integer i = 0; i < n; ++i)
		{
			for (Integer j = 0; j < n; ++j)
			{
				r[i][j] = m[v.get(i)][v.get(j)];
			}
		}
		return r;
	}
}