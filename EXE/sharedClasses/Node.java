package sharedClasses;

public class Node {
	
	//ATRIBUTS
	
	//First package with no planet assigned
	int pack;
	//Partial solution where partialSolution[package] = planet
	int[] partialSolution;
	//assigned[planet] = true if planet had a package assigned
	boolean[] assigned;
	//Bound of the node in the search tree
	float bound;
	
	//Create
	/**
	 * @param planet
	 * @param partialSolution
	 * @param assigned
	 * @param bound
	 */
	public Node(int planet, int[] partialSolution, boolean[] assigned,	float bound) {
		this.pack = planet;
		this.partialSolution = partialSolution;
		this.assigned = assigned;
		this.bound = bound;
	}
	
	//Create copy
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
}
