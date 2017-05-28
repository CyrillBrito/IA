import java.util.Scanner;

public class CubeTest {

	public static void main(String[] args) {

		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);

		String[] cubeString = new String[5];
		cubeString[0] = "GGG";
		cubeString[1] = "WWWOYYYR";
		cubeString[2] = "WWWOYYYR";
		cubeString[3] = "WWWOYYYR";
		cubeString[4] = "BBB";

		Cube cube = new Cube(cubeString);

		int[][] matrix = cube.getMatrix();
		int correct = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (matrix[i][j] == (i * 3 + j + 1))
					correct++;
		System.out.println(correct);

		while (true) {
			System.out.println(cube + "\n");
			int input = in.nextInt();
			if (input == 9) {
				cube.rotate(1);
			} else if (input == 0) {
				cube.fixPosition();
			} else {
				cube = cube.rotateRow(input - 1);
			}
		}
	}
}
