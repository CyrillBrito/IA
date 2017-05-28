import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(System.in);

		// Reads the cube from input
		String[] cubeString = new String[5];
		for (int i = 0; i < 5; i++)
			cubeString[i] = in.nextLine();
		Cube cube = new Cube(cubeString);

		in.close();

		// Solves the cube
		AStar aStar = new AStar(cube);
		System.out.println(aStar.solve() + "\nOpen nodes: " + aStar.closed.size());
		
	}
}
