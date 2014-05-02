package sharedClasses;

import java.util.*;

public class BranchAndBound {
	
	
	/**
	 * @param solution
	 * @param distances
	 * @param costs
	 */
	public static void solve(int[] solution, float[][] distances, float[][] costs) {
		//First Incumbent
		float incumbent = Bound.bound(solution, distances, costs);
		System.out.println("Incumbent: " + incumbent);
		
		//Search Tree
		int[] partialSolution = new int[solution.length];		
		Arrays.fill(partialSolution, -1);
		float lowerBound = Bound.bound(partialSolution, distances, costs);
		boolean[] assigned = new boolean[solution.length];		//Defines the fixed positions of the current solution
		Arrays.fill(assigned, false);
		
		Node root = new Node(0, partialSolution, assigned, 0f);
		Comparator<Node> comp = new NodeComparator();
		PriorityQueue<Node> q = new PriorityQueue<Node>(1 , comp);
		q.add(root);
		
		while(!q.isEmpty()) {
			Node bestNode = q.poll();
			int pos = bestNode.getPackage();
			System.out.println(Arrays.toString(bestNode.getPartialSolution())+ "--> Bound: " + bestNode.getNodeBound() + " Paquet a assignar: " + pos);
			for (int i = 0; i < solution.length; ++i) {
				System.out.println("Entrem en el for" + i);
				Node nextNode = new Node(bestNode);
				
				if (nextNode.getIAssigned(i) == false) {
					nextNode.setIAssigned(i, true);
					//we assigned the package pos to the planet i
					nextNode.setIPartialSolution(pos, i);
					++pos;
					float nodeBound = Bound.bound(nextNode.getPartialSolution(), distances, costs);
					System.out.println(Arrays.toString(nextNode.getPartialSolution())+ "--> Bound: " + nodeBound);
					if (pos == solution.length) {
						//we're in a leave of the Search Tree --> bound == mark
						if (nodeBound < incumbent) {
							incumbent = nodeBound;
							solution = nextNode.getPartialSolution();
							System.out.println("Solucio en eix moment:" + Arrays.toString(solution));
						}
					}
					
					if (nodeBound >= lowerBound) {
						nextNode.setPlanet(pos);
						nextNode.setNodeBound(nodeBound);
						q.add(nextNode);							
					}
					--pos;
				}
			}
		}
	}

}
