public class Cube {

	private int[][] matrix;

	public Cube(String[] input) {
		matrix = new int[3][3];

		matrix[0][1] = getTileNumber(input[0].charAt(1));
		matrix[1][0] = getTileNumber(input[2].charAt(7));
		matrix[1][2] = getTileNumber(input[2].charAt(3));
		matrix[2][1] = getTileNumber(input[4].charAt(1));

		matrix[1][1] = 5;

		matrix[0][0] = getTileNumber(input[0].charAt(0), input[1].charAt(7));
		matrix[0][2] = getTileNumber(input[0].charAt(2), input[1].charAt(3));
		matrix[2][0] = getTileNumber(input[4].charAt(0), input[3].charAt(7));
		matrix[2][2] = getTileNumber(input[4].charAt(2), input[3].charAt(3));

		for (int i = 1; i < 4; i++)
			for (int j = 0; j < 3; j++)
				if (input[i].charAt(j) == 'Y')
					matrix[i - 1][j] += 10;

		fixPosition();
	}

	public Cube(Cube cube) {
		int[][] matrix2 = cube.getMatrix();
		matrix = new int[3][3];
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				matrix[i][j] = matrix2[i][j];
	}

	private int getTileNumber(char color) {
		if (color == 'G')
			return 2;
		else if (color == 'R')
			return 4;
		else if (color == 'O')
			return 6;
		else
			return 8;
	}

	private int getTileNumber(char color1, char color2) {
		if ((color1 == 'G' || color1 == 'R') && (color2 == 'G' || color2 == 'R'))
			return 1;
		else if ((color1 == 'G' || color1 == 'O') && (color2 == 'G' || color2 == 'O'))
			return 3;
		else if ((color1 == 'B' || color1 == 'R') && (color2 == 'B' || color2 == 'R'))
			return 7;
		else
			return 9;
	}

	public void fixPosition() {
		if (matrix[0][0] == 11 || matrix[0][2] == 11 || matrix[2][0] == 11 || matrix[2][2] == 11) {
			rotateRow(0, 0, 0, 1, 0, 2);
			rotateRow(1, 0, 1, 1, 1, 2);
			rotateRow(2, 0, 2, 1, 2, 2);
		}

		if (matrix[0][2] == 1)
			rotate(3);
		else if (matrix[2][2] == 1)
			rotate(2);
		else if (matrix[2][0] == 1)
			rotate(1);
	}

	public void rotate(int n) {
		if (n >= 1) {
			// corners
			swapTile(0, 0, 0, 2);
			swapTile(0, 0, 2, 2);
			swapTile(0, 0, 2, 0);
			// middles
			swapTile(0, 1, 1, 2);
			swapTile(0, 1, 2, 1);
			swapTile(0, 1, 1, 0);

			if (n > 1)
				rotate(n - 1);
		}
	}

	public Cube rotateRow(int direction) {
		Cube result = new Cube(this);

		if (direction == 0) { // top line
			result.rotateRow(1, 0, 1, 1, 1, 2);
			result.rotateRow(2, 0, 2, 1, 2, 2);
		} else if (direction == 1) { // middle line
			result.rotateRow(1, 0, 1, 1, 1, 2);
		} else if (direction == 2) { // bottom line
			result.rotateRow(2, 0, 2, 1, 2, 2);
		} else if (direction == 3) { // left row
			result.rotateRow(0, 1, 1, 1, 2, 1);
			result.rotateRow(0, 2, 1, 2, 2, 2);
		} else if (direction == 4) { // middle row
			result.rotateRow(0, 1, 1, 1, 2, 1);
		} else if (direction == 5) // right row
			result.rotateRow(0, 2, 1, 2, 2, 2);
		else
			System.out.println("Unknown direction");

		return result;
	}

	public void rotateRow(int x1, int y1, int x2, int y2, int x3, int y3) {
		swapTile(x1, y1, x3, y3);

		matrix[x1][y1] = (matrix[x1][y1] + 10) % 20;
		matrix[x2][y2] = (matrix[x2][y2] + 10) % 20;
		matrix[x3][y3] = (matrix[x3][y3] + 10) % 20;
	}

	private void swapTile(int x1, int y1, int x2, int y2) {
		int tmp = matrix[x1][y1];
		matrix[x1][y1] = matrix[x2][y2];
		matrix[x2][y2] = tmp;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Cube) {
			int[][] matrix2 = ((Cube) obj).getMatrix();
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++)
					if (matrix[i][j] != matrix2[i][j])
						return false;

			return true;
		} else
			return false;
	}

	public String toString() {
		String result = "";
		result += "" + matrix[0][0] + "\t" + matrix[0][1] + "\t" + matrix[0][2] + '\n';
		result += "" + matrix[1][0] + "\t" + matrix[1][1] + "\t" + matrix[1][2] + '\n';
		result += "" + matrix[2][0] + "\t" + matrix[2][1] + "\t" + matrix[2][2];
		return result;
	}
}
