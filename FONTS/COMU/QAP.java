package sharedClasses;

/**
 * @author Alvaro Mateo
 * 
 */
public class QAP {
  private float[][] costs;
  private float[][] distances;

  /**
   * @param costs
   * @param distances
   */
  public QAP(float[][] costs, float[][] distances) {
    this.costs = costs;
    this.distances = distances;
  }


  /**
   * @param force_branch_and_bound
   * @return
   */
  public int[] solve(boolean forceBranchAndBound) {
    int[] res;
    int x = distances.length;
    if (forceBranchAndBound || x < 12)
      res = callBranch(this.distances, this.costs);
    else
      res = callAprox(this.distances, this.costs);
    return res;
  }

  /**
   * @return
   */
  public int[] solve() {
    return this.solve(false);
  }

  /**
   * @param distances
   * @param costs
   * @return
   */
  private static int[] callBranch(float[][] distances, float[][] costs) {
    int[] aux;
    aux = InitialSolution.compute(costs, distances);

    BranchAndBound.solve(aux, distances, costs);
    return aux;
  }

  /**
   * @param distances
   * @param costs
   * @return
   */
  private static int[] callAprox(float[][] distances, float[][] costs) {
    int[] aux;
    aux = InitialSolution.compute(costs, distances);

    TS.solve(aux, distances, costs);
    return aux;
  }

}
