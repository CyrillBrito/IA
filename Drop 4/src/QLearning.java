

import java.util.HashMap;
import java.util.Random;

public class QLearning {

	// Hyper parameter
	private final double LEARNING_RATE = 0.15;
	private final double DISCOUNT_FACTOR = 0.9;
	private final double CHOOSE_RANDOM = 0.2;
	private final double REWARD_MULTIPLIER = 14;
	private final int N = 4;

	public HashMap<Long, byte[]> qtable;
	private Random random;

	public QLearning() {
		qtable = new HashMap<>();
		random = new Random();
	}

	public QLearning(HashMap<Long, byte[]> map) {
		qtable = map;
		random = new Random();
	}

	public void update(long state, int action, int reward, long nextState) {
		reward *= REWARD_MULTIPLIER;

		initState(state);
		initState(nextState);

		double maxValue = max(nextState);
		double value = get(state, action);
		value += LEARNING_RATE * (reward + DISCOUNT_FACTOR * maxValue - value);

		set(state, action, value);
	}

	public double[] get(long state) {
		byte[] values = qtable.get(state);
		double[] result = new double[N];
		for (int i = 0; i < N; i++)
			result[i] = (double) values[i] + 127;
		return result;
	}

	public double get(long state, int action) {
		return (double) qtable.get(state)[action] + 127;
	}

	public void set(long state, int action, double value) {
		if (value > 250)
			try {
				throw new Exception("Grand valor");
			} catch (Exception e) {
				e.printStackTrace();
			}

		qtable.get(state)[action] = (byte) Math.round(value - 127);
	}

	private void initState(long state) {
		if (!qtable.containsKey(state)) {
			byte[] a = new byte[N];
			for (int i = 0; i < N; i++)
				a[i] = -127;
			qtable.put(state, a);
		}
	}

	public int argmax(long state) {
		byte[] values = qtable.get(state);

		int maxIndex = 0;
		for (int i = 1; i < values.length; i++)
			if (values[maxIndex] < values[i])
				maxIndex = i;
		return maxIndex;
	}

	public double max(long state) {
		double[] values = get(state);
		double maxValue = 0;
		for (int i = 0; i < N; i++)
			if (maxValue < values[i])
				maxValue = values[i];
		return maxValue;
	}

	public void learn(int nSteps) {
		Game game = new Game();
		long state = game.getState();

		for (int i = 0; i < nSteps; i++) {
			state = learningStep(game, state);
			if (state == 0) {
				game = new Game();
				state = game.getState();
			}
			if (i % 10000000 == 0 && i != 0) {
				int percent = (int) (((double) i / nSteps) * 100);
				System.out.println("Step: " + i / 1000000 + " million\tPercentage to completion: " + percent + "%");
			}
		}
	}

	public long learningStep(Game game, long state) {

		int action;
		if (CHOOSE_RANDOM < random.nextDouble() || !qtable.containsKey(state))
			action = random.nextInt(N);
		else
			action = argmax(state);

		int reward = game.play(action);
		if (reward == -100) {
			set(state, action, -1);
			return 0;
		} else {
			long nextState = game.getState();
			update(state, action, reward, nextState);
			return nextState;
		}
	}

	public int runGame(boolean showSteps) {
		Game game = new Game();

		int action;
		int score = 0;
		while (true) {
			long state = game.getState();

			if (!qtable.containsKey(state))
				action = random.nextInt(N);
			else
				action = argmax(state);

			if (showSteps)
				System.out.println(game + "\nplayed: " + (action + 1) + ", Score: " + score + "\n");

			// play round
			int roundScore = game.play(action);
			if (roundScore == -100)
				return score;
			else
				score += roundScore;
		}
	}

	public static void main(String[] args) {

		QLearning bot = new QLearning();
		bot.learn(10000000);

		for (int i = 0; i < 20; i++)
			System.out.println(bot.runGame(true));
	}
}
