

public class State {

	public static long calculate(int[][] board, int currentNumber) {
		long state = 0;
		int pos = 0;
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				state += board[i][j] * (long) Math.pow(10, pos++);

		state += currentNumber * (long) Math.pow(10, pos);
		return state;
	}

	// test State.calculate()
	public static void main(String[] args) {
		int[][] board = new int[5][4];
		int pos = 0;
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 4; j++, pos++)
				board[i][j] = pos % 10;

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++, pos++)
				System.out.print(board[i][j]);
			System.out.println();
		}

		int currentNumber = 4;
		System.out.println(currentNumber);

		long state = State.calculate(board, currentNumber);
		System.out.println(state);
	}
}
