import java.util.ArrayList;

public class Node {

	Node father;
	Cube cube;
	private int cost;

	public Node(Node father, Cube cube, int cost) {
		this.father = father;
		this.cube = cube;
		this.cost = cost;
	}

	public ArrayList<Node> children() {
		ArrayList<Node> list = new ArrayList<>();
		for (int i = 0; i < 6; i++)
			list.add(new Node(this, cube.rotateRow(i), 1));

		return list;
	}

	public int getH() {
		if (cube.getMatrix()[1][1] == 15) {
			Cube cube1 = new Cube(cube);
			cube1.rotateRow(0, 0, 0, 1, 0, 2);
			cube1.rotateRow(1, 0, 1, 1, 1, 2);
			cube1.rotateRow(2, 0, 2, 1, 2, 2);
			int result1 = getH(cube1.getMatrix());

			cube1.rotate(2);
			int result2 = getH(cube1.getMatrix());
			return result1 < result2 ? result1 : result2;
		} else
			return getH(cube.getMatrix());
	}

	private int getH(int[][] matrix) {
		int wrong = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (matrix[i][j] != (i * 3 + j + 1))
					wrong++;
		return wrong / 2;
	}

	public int getG() {
		return father != null ? cost + father.getG() : cost;
	}

	public boolean equals(Object obj) {
		return cube.equals(obj);
	}
}
