package sharedClasses;

/**
 * This class is the driver for the part of the QAP problem. It has as an attribute the two matrices
 * needed to compute everything: the costs and distances matrices. Both calculated by the
 * CtrlTerraform class.
 * 
 * @author Alvaro Mateo
 * 
 */
public class QAP {
  private float[][] costs;
  private float[][] distances;

  /**
   * Class constructor providing the costs and matrices distances.
   * 
   * @param costs the matrix with the values assigned to each pair of packages according to their
   *        compatibility.
   * @param distances the matrix with the values of the distances between each pair of planets.
   */
  public QAP(float[][] costs, float[][] distances) {
    this.costs = costs;
    this.distances = distances;
  }


  /**
   * This function is called to get a solution for the QAP problem forcing the BranchAndBound
   * solution (boolean = true) or the approximate solution (boolean = false).
   * 
   * @param force_branch_and_bound boolean which forces to use BranchAndBound or an approximation.
   *        If forceBranchAndBound is true then the solution will be get by the BranchAndBound
   *        method and if not then it will be get by the Taboo Search method.
   * @return an array with the solution of the problem. In each position of the array it is assigned
   *         the number of the package that should go to the planet that is in that position of the
   *         array.
   */
  public int[] solve(boolean forceBranchAndBound) {
    int[] res;
    if (forceBranchAndBound)
      res = callBranch(this.distances, this.costs);
    else
      res = callAprox(this.distances, this.costs);
    return res;
  }

  /**
   * Function to get a solution for the QAP problem but without forcing the function to do anything.
   * 
   * @return an array with the solution of the problem.
   */
  public int[] solve() {
    int[] res;
    int x = distances.length;
    if (x < 12)
      res = callBranch(this.distances, this.costs);
    else
      res = callAprox(this.distances, this.costs);
    return res;
  }

  /**
   * This functions is in charge of making the correct call to BranchAndBound, giving it a good
   * initial solution.
   * 
   * @param distances this matrix of QAP's instance.
   * @param costs this matrix of QAP's instance.
   * @return an array with the solution of the problem.
   */
  private static int[] callBranch(float[][] distances, float[][] costs) {
    int[] aux;
    aux = InitialSolution.compute(costs, distances);

    BranchAndBound.solve(aux, distances, costs);
    return aux;
  }

  /**
   * This function is the one that makes the call to Taboo Search (the approximated solution),
   * giving it a good initial solution.
   * 
   * @param distances this matrix of QAP's instance.
   * @param costs this matrix of QAP's instance
   * @return an array with an approximate solution of the problem.
   */
  private static int[] callAprox(float[][] distances, float[][] costs) {
    int[] aux;
    aux = InitialSolution.compute(costs, distances);

    TS.solve(aux, distances, costs);
    return aux;
  }

}
