import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author Marc Garnica i Pol Verdaguer
 * 
 */
public class BranchAndBound {

  /**
   * @param solution
   * @param distances
   * @param costs
   */
  public static void solve(int[] solution, float[][] distances, float[][] costs) {
    // First Incumbent
    float incumbent = Bound.bound(solution, distances, costs);

    // Search Tree
    int[] partialSolution = new int[solution.length];
    Arrays.fill(partialSolution, -1);
    // Defines the fixed positions of the current solution
    boolean[] assigned = new boolean[solution.length];
    Arrays.fill(assigned, false);

    BranchAndBound b = new BranchAndBound();
    Node root = b.new Node(0, partialSolution, assigned, 0f);
    PriorityQueue<Node> q = new PriorityQueue<Node>();
    q.add(root);

    while (!q.isEmpty()) {
      Node bestNode = q.poll();
      int pack = bestNode.getPackage();
      // Branch
      for (int i = 0; i < solution.length; ++i) {
        Node nextNode = b.new Node(bestNode);

        if (nextNode.getIAssigned(i) == false) {
          nextNode.setIAssigned(i, true);
          // we assigned the package pack to the planet i
          nextNode.setIPartialSolution(pack, i);
          ++pack;
          float nodeLowerBound = Bound.bound(nextNode.getPartialSolution(), distances, costs);

          // If the current solution is a feasible solution
          if (pack == solution.length) {
            // we're in a leave of the Search Tree --> bound == mark
            // we check if our feasible solution is better that the previous incumbent
            if (nodeLowerBound < incumbent) {
              incumbent = nodeLowerBound;
              System.arraycopy(nextNode.getPartialSolution(), 0, solution, 0, solution.length);
            }
          }

          // We discard the node if the bound >= incumbent because our current
          // solution can't be better than our current incumbent
          else if (nodeLowerBound < incumbent) {
            nextNode.setPlanet(pack);
            nextNode.setNodeBound(nodeLowerBound);
            q.add(nextNode);
          }
          --pack;
        }
      }
    }
  }

  private class Node implements Comparable<Node> {

    // ATRIBUTS

    // First package with no planet assigned
    int pack;
    // Partial solution where partialSolution[package] = planet
    int[] partialSolution;
    // assigned[planet] = true if planet had a package assigned
    boolean[] assigned;
    // Bound of the node in the search tree
    float bound;

    // Create
    /**
     * @param planet
     * @param partialSolution
     * @param assigned
     * @param bound
     */
    public Node(int pack, int[] partialSolution, boolean[] assigned, float bound) {
      this.pack = pack;
      this.partialSolution = partialSolution;
      this.assigned = assigned;
      this.bound = bound;
    }

    // Create copy
    /**
     * @param copyFrom
     */
    public Node(Node copyFrom) {
      this.pack = copyFrom.pack;
      this.bound = copyFrom.bound;
      this.assigned = new boolean[copyFrom.assigned.length];
      this.partialSolution = new int[copyFrom.partialSolution.length];
      for (int i = 0; i < copyFrom.assigned.length; ++i) {
        this.partialSolution[i] = copyFrom.partialSolution[i];
        this.assigned[i] = copyFrom.assigned[i];
      }
    }

    /**
     * @return the first package to be allocated.
     */
    public int getPackage() {
      return pack;
    }

    /**
     * @param pack
     */
    public void setPlanet(int pack) {
      this.pack = pack;
    }

    /**
     * @return the partial solution of the node.
     */
    public int[] getPartialSolution() {
      return partialSolution;
    }

    /**
     * @param partialSolution
     */
    public void setPartialSolution(int[] partialSolution) {
      this.partialSolution = partialSolution;
    }

    /**
     * @param pos
     * @param i
     */
    public void setIPartialSolution(int pos, int i) {
      partialSolution[pos] = i;
    }

    /**
     * @return the vector with the state of the packages.
     */
    public boolean[] getAssigned() {
      return assigned;
    }

    /**
     * @param i
     * @return the state of the packet i (allocated->true, false otherwise)
     */
    public boolean getIAssigned(int i) {
      return assigned[i];
    }

    /**
     * @param i
     * @param b
     */
    public void setIAssigned(int i, boolean b) {
      assigned[i] = b;
    }

    /**
     * @param assigned
     */
    public void setAssigned(boolean[] assigned) {
      this.assigned = assigned;
    }

    /**
     * @return the bound of the node
     */
    public float getNodeBound() {
      return bound;
    }

    /**
     * @param i
     */
    public void setNodeBound(float i) {
      this.bound = i;
    }

    @Override
    public int compareTo(Node other) {
      return ((Float) bound).compareTo(other.bound);
    }
  }


}
