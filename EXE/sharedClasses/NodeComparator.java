package sharedClasses;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
	
	@Override
	public int compare(Node x, Node y) {
		return Float.valueOf(x.getNodeBound()).compareTo(Float.valueOf(y.getNodeBound()));	
	}

}
