import java.util.ArrayList;
import java.util.List;

public class Board implements GameState {
	public static final String EMPTY = " ";
	public static final String PLAYER1 = "X";
	public static final String PLAYER2 = "O";
	
	public static String getOtherPlayer(String currentPlayer) {
		return currentPlayer.equals(PLAYER1) ? PLAYER2 : PLAYER1;
	}
	
	private List<List<String>> board;
	private final int rows;
	private final int cols;
	private int length;
	
	private Board(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		length = 0;
		board = new ArrayList<>();
		
		for (int r = 0; r < rows; ++r) {
			board.add(new ArrayList<>());
			for (int c = 0; c < cols; ++c) {
				board.get(r).add(EMPTY);
			}
		}
	}
	
	public Board() {
		this(3, 3);
	}
	
	/**
	 * Copy constructor - returns a deep copy of other.
	 * 
	 * @param other the board to copy
	 */
	public Board(Board other) {
		rows = other.getRows();
		cols = other.getCols();
		length = other.size();
		board = new ArrayList<>();
		for (List<String> row : other.getBoard()) {
			board.add(new ArrayList<>(row));
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Board) {
			Board otherBoard = (Board)other;
			return getRows() == otherBoard.getRows() && getCols() == otherBoard.getCols() && board.equals(otherBoard.getBoard());
		}
		return false;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return board.toString();
	}
	
	/**
	 * @see GameState#terminal()
	 */
	@Override
	public boolean terminal() {
		for (int r = 0; r < getRows(); ++r) {
			if (rowHasStreak(r, PLAYER1) || rowHasStreak(r, PLAYER2)) {
				return true;
			}
		}
		for (int c = 0; c < getCols(); ++c) {
			if (colHasStreak(c, PLAYER1) || colHasStreak(c, PLAYER2)) {
				return true;
			}
		}
		if (diagonalHasStreak(PLAYER1) || diagonalHasStreak(PLAYER2)) {
			return true;
		}
		if (size() == getRows() * getCols()) {
			return true;
		}
		return false;
	}

	/**
	 * @see GameState#utility(Player)
	 */
	@Override
	public int utility(Player player) {
		String me = player.getId();
		String enemy = getOtherPlayer(me);
		
		for (int r = 0; r < getRows(); ++r) {
			if (rowHasStreak(r, me)) {
				return 1;
			}
			else if (rowHasStreak(r, enemy)) {
				return -1;
			}
		}
		for (int c = 0; c < getCols(); ++c) {
			if (colHasStreak(c, me)) {
				return 1;
			}
			else if (colHasStreak(c, enemy)) {
				return -1;
			}
		}
		if (diagonalHasStreak(me)) {
			return 1;
		}
		else if (diagonalHasStreak(enemy)) {
			return -1;
		}
		
		// tie
		return 0;
	}
	
	/**
	 * Prints a human-readable representation of the board to standard-out.
	 */
	public void printBoard() {
		System.out.print("  ");
		for (int c = 0; c < cols; ++c) {
			System.out.printf("%d ", c + 1);
		}
		System.out.println();
		
		for (int r = 0; r < rows; ++r) {
			System.out.printf("%d ", r + 1);
			for (int c = 0; c < cols; ++c) {
				System.out.printf("%s ", board.get(r).get(c));
			}
			System.out.println();
		}
	}
	
	/**
	 * Represents the number of moves that have currently been made on the board.
	 * 
	 * @return the number of moves made
	 */
	public int size() {
		return length;
	}
	
	/**
	 * Places the move at (row, col) on the board, marking it with player's icon.
	 * Only places the move if it is valid.
	 * 
	 * @param row
	 * @param col
	 * @param player
	 * @return true if the move was valid
	 */
	public boolean place(int row, int col, String player) {
		boolean result = isValidMove(row, col);
		if (result) {
			board.get(row).set(col, player);
			++length;
		}
		return result;
	}
	
	/**
	 * Determines if the space at (row, col) is a valid move.
	 * A valid move is within the boundaries of the board, and is empty.
	 * 
	 * @param row row to place a move in 
	 * @param col column to place a move in
	 * @return true if the move is valid
	 */
	public boolean isValidMove(int row, int col) {
		return row >= 0 && row < rows && col >= 0 && col < cols && board.get(row).get(col).equals(EMPTY);
	}

	public List<List<String>> getBoard() {
		return board;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}
	
	/**
	 * Determines if the given row has a winning streak by the given player.
	 * 
	 * @param row
	 * @param player
	 * @return true if player has a streak in row
	 */
	private boolean rowHasStreak(int row, String player) {
		boolean hasStreak = !player.equals(EMPTY);
		int col = 0;
		
		while (col < getCols() && hasStreak) {
			String value = board.get(row).get(col++);
			hasStreak = hasStreak && value.equals(player);
		}
		
		return hasStreak;
	}
	
	/**
	 * Determines if the given column has a winning streak by the given player.
	 * 
	 * @param col
	 * @param player
	 * @return true if player has a streak in col
	 */
	private boolean colHasStreak(int col, String player) {
		boolean hasStreak = !player.equals(EMPTY);
		int row = 0;
		
		while (row < getRows() && hasStreak) {
			String value = board.get(row++).get(col);
			hasStreak = hasStreak && value.equals(player);
		}
		
		return hasStreak;
	}
	
	/**
	 * Determines if either diagonal has a winning streak by the given player.
	 * 
	 * @param player
	 * @return true if player has a diagonal streak
	 */
	private boolean diagonalHasStreak(String player) {
		int row = 0;
		int col = 0;
		boolean leftHasStreak = !player.equals(EMPTY);
		
		while (row < getRows() && col < getCols() && leftHasStreak) {
			String value = board.get(row++).get(col++);
			leftHasStreak = leftHasStreak && value.equals(player);
		}
		
		row = 0;
		col = getCols() - 1;
		boolean rightHasStreak = !player.equals(EMPTY);
		while (row < getRows() && col >= 0 && rightHasStreak) {
			String value = board.get(row++).get(col--);
			rightHasStreak = rightHasStreak && value.equals(player);
		}
		
		return leftHasStreak || rightHasStreak;
	}
}
