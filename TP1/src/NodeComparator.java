

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node node1, Node node2) {
		int cost1 = node1.getG() /*+ node1.getH()*/;
		int cost2 = node2.getG() /*+ node2.getH()*/;

		if (cost1 > cost2)
			return 1;
		else if (cost1 == cost2)
			return 0;
		else
			return -1;
	}

}
