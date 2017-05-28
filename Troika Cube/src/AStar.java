import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class AStar {

	private Queue<Node> open;
	public List<Node> closed;
	Node currentNode;

	public AStar(Cube cube) {
		open = new PriorityQueue<Node>(10, new NodeComparator());
		open.add(new Node(null, cube, 0));
		closed = new ArrayList<Node>();
	}

	public int solve() {

		// while are nodes to be opened
		while (!open.isEmpty()) {

			// takes a node from the queue of nodes not opened
			currentNode = open.poll();

			// if H is 0 it means the cube is solved
			if (currentNode.getH() == 0)
				return currentNode.getG();
			else {
				// adds the childs of the current node to the queue of nodes to be opened
				for (Node child : currentNode.children())
					if (!closed.contains(child))
						open.add(child);

				// adds the current node to the opened nodes
				closed.add(currentNode);
			}
		}
		return -1;
	}
}
