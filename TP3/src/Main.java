

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Main {

	public static QLearning loadSmall() {
		System.out.println("Loading... this will take around 4 seconds");
		return read("SmallBot.ser");
	}

	public static QLearning loadBig() {
		System.out.println("Loading... this will take around 1 minute");
		return read("BigBot.ser");
	}

	public static QLearning learnSmall() {
		System.out.println("Learning... this will take around 12 seconds");
		QLearning bot = new QLearning();
		bot.learn(10000000);
		write(bot, "SmallBot.ser");
		return bot;
	}

	public static QLearning learnBig() {
		System.out.println("Learning... this will take around 16 minutes");
		QLearning bot = new QLearning();
		bot.learn(1500000000);
		write(bot, "BigBot.ser");
		return bot;
	}

	public static void playNGames(QLearning bot, int n) {

		int sum = 0;
		for (int i = 0; i < n; i++) {
			int score = bot.runGame(false);
			System.out.println("Game: " + (i + 1) + " \tScore: " + score);
			sum += score;
		}
		System.out.println("Games average: " + sum / n);

	}

	public static void play1(QLearning bot) {
		bot.runGame(true);
	}

	public static void write(QLearning bot, String fileName) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(bot.qtable);
			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static QLearning read(String fileName) {
		try {
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			@SuppressWarnings("unchecked")
			QLearning bot = new QLearning((HashMap<Long, byte[]>) ois.readObject());
			ois.close();
			fis.close();
			return bot;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws InterruptedException, IOException {

		// Choose 1 of the 5
		// QLearning bot = new QLearning(); // bot without learning, plays randomly
		// QLearning bot = learnSmall(); // bot learns for 12 seconds and saves the object to a file
		// QLearning bot = learnBig(); // bot learns for 16 minutes and saves the object to a file
		// QLearning bot = loadSmall(); // bot loads object from file, it takes 4 seconds
		 QLearning bot = loadBig(); // bot loads object from file, it takes 1 minute

		 playNGames(bot, 1000);
		// play1(bot);
	}
}
