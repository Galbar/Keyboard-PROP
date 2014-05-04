package sharedClasses;
import java.util.*;

/**
 * Implementa una solució pel problema d'assignació en temps polinómic.
 *
 * @author Alessio Linares
 */
public class HungarianAlgorithm 
{
	/**
	 * Funció principal de la classe. Aquesta espera una matriu nxn de costos i
	 * torna les assignacions (fila, columna) la suma de les quals és mínima.
	 * @param  matrix           matriu nxn amb els costos.
	 * @return                  matriu nx2 on cada fila representa una assignació,
	 *                          la primera columna és la fila i la segona la columna
	 *                          que formen aquesta assignació.
	 */
	static public int[][] computeAssignments(Float[][] matrix)
	{
		reduceMatrix(matrix);
		int[] starsByRow = new int[matrix.length]; Arrays.fill(starsByRow,-1);
		int[] starsByCol = new int[matrix[0].length]; Arrays.fill(starsByCol,-1);
		int[] primesByRow = new int[matrix.length]; Arrays.fill(primesByRow,-1);
		int[] coveredRows = new int[matrix.length];
		int[] coveredCols = new int[matrix[0].length];

		initStars(matrix, starsByRow, starsByCol);
		coverColumnsOfStarredZeroes(starsByCol,coveredCols);

		while (!allAreCovered(coveredCols)) {
			int[] primedZero = primeSomeUncoveredZero(matrix, primesByRow, coveredRows, coveredCols);
			while (primedZero == null) {
				makeMoreZeroes(matrix,coveredRows,coveredCols);
				primedZero = primeSomeUncoveredZero(matrix, primesByRow, coveredRows, coveredCols);
			}

			int columnIndex = starsByRow[primedZero[0]];
			if (-1 == columnIndex) {
				incrementSetOfStarredZeroes(primedZero, starsByRow, starsByCol, primesByRow);
				Arrays.fill(primesByRow,-1);
				Arrays.fill(coveredRows,0);
				Arrays.fill(coveredCols,0);
				coverColumnsOfStarredZeroes(starsByCol,coveredCols);
			}
			else {
				coveredRows[primedZero[0]] = 1;
				coveredCols[columnIndex] = 0;
			}
		}

		int[][] ret = new int[matrix.length][];
		for (int i = 0; i < starsByCol.length;  i++)
			ret[i] = new int[]{starsByCol[i],i};
		return ret;
	}

	/**
	 * Comprova si ja s'han tractat totes les columnes de la matriu.
	 * @param  coveredCols array que guarda quines columnes s'han tractat.
	 * @return             si totes les columnes han estat tractades torna true,
	 *                     sino false.
	 */
	static private boolean allAreCovered(int[] coveredCols) {
		for (int covered : coveredCols)
			if (0 == covered) return false;
		return true;
	}

	/**
	 * Per cada fila de la matriu, troba el valor mínim i li resta 
	 * als altres de la mateixa fila, fa el mateix per les columnes.
	 * @param matrix matriu nxn de costos
	 */
	static private void reduceMatrix(Float[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			Float minValInRow = Float.MAX_VALUE;
			for (int j = 0; j < matrix[i].length; j++)
				if (minValInRow > matrix[i][j])
					minValInRow = matrix[i][j];

			for (int j = 0; j < matrix[i].length; j++)
				matrix[i][j] -= minValInRow;
		}

		for (int i = 0; i < matrix[0].length; i++) {
			Float minValInCol = Float.MAX_VALUE;
			for (int j = 0; j < matrix.length; j++)
				if (minValInCol > matrix[j][i])
					minValInCol = matrix[j][i];

			for (int j = 0; j < matrix.length; j++)
				matrix[j][i] -= minValInCol;
		}
	}

	/**
	 * Guarda les files i columnes que al menys tenen un zero, si 
	 * una columna/fila té un zero, pero la fila/columna on es troba
	 * ja en té un, s'ignora.
	 * @param costMatrix[][] matriu de costos.
	 * @param starsByRow     guarda la columna en la que està el zero per cada fila.
	 * @param starsByCol     guarda la fila en la que està el zero per cada columna.
	 */
	static private void initStars(Float costMatrix[][], int[] starsByRow, int[] starsByCol) {
		int [] rowHasStarredZero = new int[costMatrix.length];
		int [] colHasStarredZero = new int[costMatrix[0].length];

		for (int i = 0; i < costMatrix.length; i++)
			for (int j = 0; j < costMatrix[i].length; j++)
				if (0 == costMatrix[i][j] && 0 == rowHasStarredZero[i] && 0 == colHasStarredZero[j]) {
					starsByRow[i] = j;
					starsByCol[j] = i;
					rowHasStarredZero[i] = 1;
					colHasStarredZero[j] = 1;
					break;
				}
	}

	/**
	 * Marca, a coveredCols, les columnes en les que ja s'ha trobat algun 0, guardades
	 * a starsByCol.
	 * @param starsByCol  columnes que tenen al menys un 0.
	 * @param coveredCols columnes ja tractades.
	 */
	static private void coverColumnsOfStarredZeroes(int[] starsByCol, int[] coveredCols) {
		for (int i = 0; i < starsByCol.length; i++)
			coveredCols[i] = -1 == starsByCol[i] ? 0 : 1;
	}

	/**
	 * Troba un 0 que no hagi sigut marcat encara.
	 * @param  matrix[][]  matriu nxn de costos.
	 * @param  primesByRow on es guarden els zeros que no havien estat marcats.
	 * @param  coveredRows files ja tractades.
	 * @param  coveredCols columnes ja tractades.
	 * @return             la posició a la matriu del 0 trobat.
	 */
	static private int[] primeSomeUncoveredZero(Float matrix[][], int[] primesByRow, int[] coveredRows, int[] coveredCols) {
		for (int i = 0; i < matrix.length; i++) {
			if (1 == coveredRows[i]) continue;
			for (int j = 0; j < matrix[i].length; j++)
				if (0 == matrix[i][j] && 0 == coveredCols[j]) {
					primesByRow[i] = j;
					return new int[]{i,j};
				}
		}
		return null;
	}

	/**
	 * Troba més 0s a les columnes que no han estat marcades encara.
	 * @param unpairedZeroPrime posició a la matriu on hi ha un 0.
	 * @param starsByRow        files que tenen al menys un 0.
	 * @param starsByCol        columnes que tenen al menys un 0.
	 * @param primesByRow       on es guarden els zeros que no havien estat marcats.
	 */
	static private void incrementSetOfStarredZeroes(int[] unpairedZeroPrime, int[] starsByRow, int[] starsByCol, int[] primesByRow) {
		int i, j = unpairedZeroPrime[1];

		Set<int[]> zeroSequence = new LinkedHashSet<int[]>();
		zeroSequence.add(unpairedZeroPrime);
		boolean paired = false;
		boolean first = true;
		while (first || paired) {
			if (first) first = false;
			i = starsByCol[j];
			paired = -1 != i && zeroSequence.add(new int[]{i,j});
			if (!paired) break;

			j = primesByRow[i];
			paired = -1 != j && zeroSequence.add(new int[]{ i, j });

		}

		for (int[] zero : zeroSequence) {
			if (starsByCol[zero[1]] == zero[0]) {
				starsByCol[zero[1]] = -1;
				starsByRow[zero[0]] = -1;
			}
			if (primesByRow[zero[0]] == zero[1]) {
				starsByRow[zero[0]] = zero[1];
				starsByCol[zero[1]] = zero[0];
			}
		}
	}

	/**
	 * Fa més 0s restant el següent valor més petit de les files/columnes.
	 * @param matrix      matriu nxn de costos.
	 * @param coveredRows files ja tractades.
	 * @param coveredCols columnes ja tractades.
	 */
	static private void makeMoreZeroes(Float[][] matrix, int[] coveredRows, int[] coveredCols) {
		Float minUncoveredValue = Float.MAX_VALUE;
		for (int i = 0; i < matrix.length; i++)
			if (0 == coveredRows[i])
				for (int j = 0; j < matrix[i].length; j++)
					if (0 == coveredCols[j] && matrix[i][j] < minUncoveredValue)
						minUncoveredValue = matrix[i][j];

		for (int i = 0; i < coveredRows.length; i++) {
			if (1 == coveredRows[i])
				for (int j = 0; j < matrix[i].length; j++)
					matrix[i][j] += minUncoveredValue;
		}

		for (int i = 0; i < coveredCols.length; i++) {
			if (0 == coveredCols[i])
				for (int j = 0; j < matrix.length; j++)
					matrix[j][i] -= minUncoveredValue;
		}
	}
}