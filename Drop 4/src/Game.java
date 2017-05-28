

import java.util.Random;
import java.util.Scanner;

public class Game {

	private final int N = 4;
	private final int NX = 3;
	private final int iBLOCK = 5;
	private final int iBROKEN = 6;
	private final int iLOST = -100;

	private final Random RANDOM = new Random();

	private int[][] board;
	private int currentNumber;

	public Game() {
		board = new int[N + 1][N];
		currentNumber = nextTile();
	}

	private int nextTile() {
		int result = RANDOM.nextInt(N + NX) + 1;
		if (result > iBLOCK)
			result = iBLOCK;
		return result;
	}

	private void destroyTile(int y, int x) {
		board[y][x] = 0;

		if (y != 0 && board[y - 1][x] == iBLOCK)
			board[y - 1][x] = iBROKEN;
		if (y != N - 1 && board[y + 1][x] == iBLOCK)
			board[y + 1][x] = iBROKEN;
		if (x != N - 1 && board[y][x + 1] == iBLOCK)
			board[y][x + 1] = iBROKEN;
		if (x != 0 && board[y][x - 1] == iBLOCK)
			board[y][x - 1] = iBROKEN;
	}

	private void updateGravety(int col) {
		int emptyCount = 0;
		for (int i = 0; i < N + 1; i++)
			if (board[i][col] == 0)
				emptyCount++;
			else if (emptyCount != 0) {
				board[i - emptyCount][col] = board[i][col];
				board[i][col] = 0;
			}
	}

	private void updateGravety() {
		for (int i = 0; i < N; i++)
			updateGravety(i);
	}

	private int updateTiles() {
		int score = 0;

		// size of cols
		int[] colsSize = new int[N];
		for (int i = 0; i < N; i++)
			while (board[colsSize[i]][i] != 0)
				colsSize[i]++;

		// size lines and remove numbers
		for (int y = 0; y < N; y++) {
			int x = 0;
			while (x < N) {
				int size = 0;
				while (x < N) {
					if (board[y][x] != 0)
						size++;
					else if (size != 0)
						break;
					x++;
				}

				for (int i = 0; i < size; i++)
					if (board[y][x - i - 1] == size) {
						destroyTile(y, x - i - 1);
						score++;
					}
			}
		}

		// remove from cols
		for (int x = 0; x < N; x++)
			for (int y = 0; y < colsSize[x]; y++)
				if (board[y][x] == colsSize[x]) {
					destroyTile(y, x);
					score++;
				}

		for (int x = 0; x < N; x++)
			for (int y = 0; y < colsSize[x]; y++)
				if (board[y][x] == iBROKEN)
					board[y][x] = nextTile();

		return score;
	}

	public int play(int col) {

		board[N][col] = currentNumber;
		updateGravety();

		// check if lost
		for (int i = 0; i < N; i++)
			if (board[N][i] != 0)
				return iLOST;

		// update game
		int score = 0;
		int stepScore;
		while ((stepScore = updateTiles()) != 0) {
			score += stepScore;
			updateGravety();
		}

		currentNumber = nextTile();
		return score;
	}

	public long getState() {
		return State.calculate(board, currentNumber);
	}

	public String toString() {

		String result = "     " + (currentNumber == iBLOCK ? "@" : currentNumber) + "\n";

		for (int i = N - 1; i >= 0; i--) {
			result += "   |";
			for (int j = 0; j < N; j++)
				if (board[i][j] == 0)
					result += " ";
				else
					result += board[i][j] == iBLOCK ? "@" : board[i][j];

			result += "|\n";
		}

		result += "   ======";

		return result;
	}

	public static void main(String[] args) {
		Game game = new Game();
		Scanner in = new Scanner(System.in);

		System.out.println(game);

		int score = 0;
		while (true) {
			int roundScore = game.play(in.nextInt() - 1);
			if (roundScore == game.iLOST)
				break;
			else
				score += roundScore;

			System.out.println("Score: " + score + " roundScore: " + roundScore + "\n");
			System.out.println(game);
		}

		System.out.println("Final Score: " + score);
		in.close();
	}
}
