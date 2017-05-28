
public class Main {

	public static void main(String[] args) {

		int n = 5;
		double startTime = System.currentTimeMillis();
		double absolute = 0;
		while (absolute < 120) {
			double time = System.currentTimeMillis();

			GeneticAlgorithm ga = new GeneticAlgorithm(n);
			while (ga.population.get(0).cost > 0)
				ga.evolve();

			double elapsed = (System.currentTimeMillis() - time) / 1000;
			absolute = (System.currentTimeMillis() - startTime) / 1000;

			System.out.println("Elapsed time: " + elapsed + " secs. Absolute time: " + absolute
					+ " secs. Magic square of size: " + n);
			System.out.println(ga.population.get(0) + "\n");

			n++;
		}
	}
}
