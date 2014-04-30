import java.util.*;
import HungarianAlgorithm;

public class Bound
{
	private static Float[][] removeDiagonal(Float[][] A)
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
		return ret;
	}

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

	private static Float dotProduct(Float[] u, Float[] v)
	{
		Float ret = new Float(0f);
		for (int i = 0; i < u.length; ++i)
			ret += u[i]*v[i];
		return ret;
	}

	private static Float[][] diagonalsProduct(Float[] u, Float[] v)
	{
		Integer n = u.length;
		Float ret[][] = new Float[n][n];
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < n; ++j)
				ret[i][j] = u[i]*v[j];
		return ret;
	}

	private static Float[][] add(Float[][] A, Float[][] B)
	{
		Integer n = A.length;
		Float ret[][] = new Float[n][n];
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < n; ++j)
				ret[i][j] = A[i][j] + B[i][j];
		return ret;
	}

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

		Float A_diagonal[] = new Float[n];
		Float B_diagonal[] = new Float[n];
		Float ANoDiagonal[][] = new Float[n][n-1];
		ANoDiagonal = removeDiagonal(A, A_diagonal);
		Float BNoDiagonal[][] = new Float[n][n-1];
		BNoDiagonal = removeDiagonal(B, B_diagonal);
		
		for (int i = 0; i < n; ++i)
		{
			Arrays.sort(ANoDiagonal[i]);
			Arrays.sort(BNoDiagonal[i], Collections.reverseOrder());
		}

		Float X[][] = new Float[n][n];		// minimum scalar product
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < n; ++j)
				X[i][j] = dotProduct(ANoDiagonal[i], BNoDiagonal[j]);

		Float Y[][] = new Float[n][n];
		Y = diagonalsProduct(A_diagonal, B_diagonal);

		Float L[][] = new Float[n][n];
		L = add(X, Y);

		HungarianAlgorithm ha = new HungarianAlgorithm();
		Float aux[][] = new Float[n][n];
		for (int i = 0; i < n; ++i)
			aux[i] = L[i].clone();
		int[][] assig = ha.computeAssignments(aux);

		for (int i = 0; i < n; ++i)
			cost += L[assig[i][0]][assig[i][1]];

		return cost;
	}
}