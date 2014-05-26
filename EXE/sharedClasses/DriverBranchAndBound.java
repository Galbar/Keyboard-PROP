package sharedClasses;
import java.util.*;

public class DriverBranchAndBound {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//LLegim les entrades via fitxer
		System.out.println("Driver of BranchAndBound.java");
		System.out.println("Enter the length of the matrices:");
		Scanner scan = new Scanner(System.in);
		
		int length = Integer.parseInt(scan.nextLine());
		float distances[][] = new float[length][length];
		float costs[][] = new float[length][length];
		
		System.out.println("Distances matrix:\n");
		for (int i = 0; i < length; ++i) 
			for (int j = 0; j < length; ++j) 
				distances[i][j] = Float.parseFloat(scan.next());
		
		System.out.println("Costs matrix.\n");
		for (int i = 0; i < length; ++i) 
			for (int j = 0; j < length; ++j)
				costs[i][j] = Float.parseFloat(scan.next());
			
		int solution[] = new int[length];
		solution = InitialSolution.compute(distances, costs);
		scan.close();
		BranchAndBound.solve(solution, distances, costs);
		System.out.println("Assignment: " + Arrays.toString(solution));
	}
}
