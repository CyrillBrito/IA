import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class GeneticAlgorithm {

	ArrayList<MagicSquare> population;
	int resetCounter;
	int absMinCost;

	// ========== Constructor ==============
	public GeneticAlgorithm(int n) {
		Vars.INIT(n);

		population = new ArrayList<>();
		createNewPopulation();
	}

	public void createNewPopulation() {
		for (int i = 0; i < Vars.POPULATION_SIZE; i++)
			population.add(new MagicSquare());

		evaluate();

		resetCounter = Vars.RESET_TRIGGER;
		absMinCost = population.get(0).cost;
	}

	// ========== Genetic functions ========
	public void evaluate() {
		for (int i = 0; i < Vars.POPULATION_SIZE; i++)
			population.get(i).evaluate();

		Collections.sort(population);
	}

	public MagicSquare selectParent() {
		return population.get(Vars.RANDOM.nextInt(Vars.PARENTS_SIZE));
	}

	public void evolve() {
		resetUpdate();

		HashSet<MagicSquare> hashPopulation = new HashSet<>();

		for (int i = 0; i < Vars.POPULATION_SIZE; i++) {
			MagicSquare parent1 = selectParent();
			MagicSquare parent2 = selectParent();
			MagicSquare child = parent1.crossover(parent2);
			child.mutate(Vars.MUTATION_RATE);

			while (!hashPopulation.add(child))
				child.mutate(1);
		}

		population = new ArrayList<>(hashPopulation);
		evaluate();
	}

	public void resetUpdate() {
		if (population.get(0).cost >= absMinCost) {
			resetCounter--;
			if (resetCounter == 0) {
				population.clear();
				createNewPopulation();
			}
		} else {
			resetCounter = Vars.RESET_TRIGGER;
			absMinCost = population.get(0).cost;
		}
	}
}
