import java.util.Random;

public class Vars {

	public static final int POPULATION_SIZE = 900;
	public static final int PARENTS_SIZE = (int) (POPULATION_SIZE * 0.10);
	public static final double MUTATION_RATE = 0.05;
	public static final int RESET_TRIGGER = 500;

	public static final Random RANDOM = new Random();

	public static int N;
	public static int NN;
	public static int TARGET;

	public static void INIT(int n) {
		N = n;
		NN = N * N;
		TARGET = (N * (NN + 1)) / 2;
	}
}
