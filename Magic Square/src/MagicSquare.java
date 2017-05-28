import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MagicSquare implements Comparable<MagicSquare> {

	int[] numbers;
	int cost;

	// ========== Constructors =============
	public MagicSquare() {

		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < Vars.NN; i++)
			list.add(i + 1);

		Collections.shuffle(list, Vars.RANDOM);

		this.numbers = new int[Vars.NN];
		for (int i = 0; i < Vars.NN; i++)
			this.numbers[i] = list.get(i);
	}

	public MagicSquare(int[] numbers) {
		this.numbers = numbers;
	}

	// ========== Other Stuff ==============
	public int matrix(int y, int x) {
		return numbers[y * Vars.N + x];
	}

	private static boolean addNumber(int[] array, int pos, int value, boolean[] usedNumbers) {
		if (array[pos] != 0)
			return false;
		if (usedNumbers[value - 1])
			return false;

		array[pos] = value;
		usedNumbers[array[pos] - 1] = true;
		return true;
	}

	// ========== Genetic functions ========
	public void evaluate() {
		int diagonal1 = 0;
		int diagonal2 = 0;

		for (int i = 0; i < Vars.N; i++) {
			int line = 0;
			int col = 0;
			for (int j = 0; j < Vars.N; j++) {
				line += matrix(i, j);
				col += matrix(j, i);
			}
			diagonal1 += matrix(i, i);
			diagonal2 += matrix(i, Vars.N - 1 - i);

			this.cost += Math.abs(Vars.TARGET - line);
			this.cost += Math.abs(Vars.TARGET - col);
		}

		this.cost += Math.abs(Vars.TARGET - diagonal1);
		this.cost += Math.abs(Vars.TARGET - diagonal2);
	}

	public MagicSquare crossover(MagicSquare partner) {
		int[] parent1 = numbers;
		int[] parent2 = partner.numbers;
		int[] child = new int[Vars.NN];

		boolean[] usedNumbers = new boolean[Vars.NN];

		// copy 1 by 1
		int i1 = 0;
		int i2 = 0;
		while (i1 < Vars.NN || i2 < Vars.NN) {
			while (i1 < Vars.NN && !addNumber(child, i1, parent1[i1], usedNumbers))
				i1++;

			while (i2 < Vars.NN && !addNumber(child, i2, parent2[i2], usedNumbers))
				i2++;
		}

		// find missing numbers
		ArrayList<Integer> missing = new ArrayList<>();
		for (int i = 0; i < Vars.NN; i++)
			if (!usedNumbers[i])
				missing.add(i + 1);

		// shuffle the missing
		Collections.shuffle(missing, Vars.RANDOM);

		// fill child with missing numbers
		int index = 0;
		for (int i = 0; i < missing.size(); i++) {
			while (child[index] != 0)
				index++;
			child[index] = missing.get(i);
		}

		return new MagicSquare(child);
	}

	public void mutate(double mutationRate) {

		if (Vars.RANDOM.nextDouble() < mutationRate) {

			int i1 = Vars.RANDOM.nextInt(Vars.NN);
			int i2 = Vars.RANDOM.nextInt(Vars.NN);

			int temp = numbers[i1];
			numbers[i1] = numbers[i2];
			numbers[i2] = temp;
		}
	}

	// ========== Override functions =======
	@Override
	public String toString() {
		String result = "";

		int diagonal1 = 0;
		int diagonal2 = 0;
		int[] cols = new int[Vars.N];

		for (int i = 0; i < Vars.N; i++) {
			int line = 0;
			cols[i] = 0;
			for (int j = 0; j < Vars.N; j++) {
				result += "\t" + matrix(i, j);
				line += matrix(i, j);
				cols[i] += matrix(j, i);
			}
			diagonal1 += matrix(i, i);
			diagonal2 += matrix(i, Vars.N - 1 - i);

			result += "\t| " + line + "\n";
		}

		// horizontal line
		for (int i = 0; i < Vars.N + 2; i++)
			result += "_____\t";

		// sums
		result += "\n" + diagonal1;
		for (int i = 0; i < Vars.N; i++)
			result += "\t" + cols[i];
		result += "\t" + diagonal2;

		return result;
	}

	@Override
	public boolean equals(Object object) {
		return Arrays.equals(numbers, ((MagicSquare) object).numbers);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(numbers);
	}

	@Override
	public int compareTo(MagicSquare other) {
		if (cost > other.cost)
			return 1;
		else if (cost == other.cost)
			return 0;
		else
			return -1;
	}
}
