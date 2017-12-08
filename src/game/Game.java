package game;
import java.util.Scanner;

public class Game {
	
	private static final Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		String playerIcon = Board.PLAYER1;
		do {
			playerIcon = promptForString("Enter the player's icon (X/O)").toUpperCase();
		} while (!(playerIcon.equalsIgnoreCase(Board.PLAYER1) || playerIcon.equalsIgnoreCase(Board.PLAYER2)));
		
		String whoFirst = "";
		do {
			whoFirst = promptForString("Which player will go first (X/O)?").toUpperCase();
		} while (!(whoFirst.equalsIgnoreCase(Board.PLAYER1) || whoFirst.equalsIgnoreCase(Board.PLAYER2)));
		
		Game game = new Game(playerIcon, whoFirst.equals(playerIcon));
		game.play();
	}
	
	/**
	 * Displays message and prompts for input from standard-in.
	 * 
	 * @param message String message to be displayed at prompt
	 * @return String that is entered into standard-in 
	 */
	private static String promptForString(String message) {
		System.out.printf("%s: ", message);
		while (!in.hasNext()) {
			String invalid = in.next();
			System.out.printf("  \")%s\" is not a valid string.\n", invalid);
			System.out.printf("%s: ", message);
		}
		return in.next();
	}
	
	/**
	 * Displays message and prompts for integer input from standard-in.
	 * Repeatedly prompts if a non-integer is entered.
	 * 
	 * @param message String message to be displayed at prompt
	 * @return integer that is entered into standard-in.
	 */
	private static int promptForInt(String message) {
		System.out.printf("%s: ", message);
		while (!in.hasNextInt()) {
			String invalid = in.next();
			System.out.printf("  \")%s\" is not a valid integer.\n", invalid);
			System.out.printf("%s: ", message);
		}
		return in.nextInt();
	}
	
	
	private final String playerIcon;
	private final String opponentIcon;
	private final boolean playerGoesFirst;
	private ComputerPlayer opponent;
	private Board board;
	
	/**
	 * Constructs a new Game object.
	 * 
	 * @param playerIcon
	 * @param playerGoesFirst
	 */
	public Game(String playerIcon, boolean playerGoesFirst) {
		this.playerIcon = playerIcon;
		this.playerGoesFirst = playerGoesFirst;
		opponentIcon = playerIcon.equals(Board.PLAYER1) ? Board.PLAYER2 : Board.PLAYER1;
		board = new Board();
		opponent = new ComputerPlayer(opponentIcon, playerIcon, board);
	}
	
	/**
	 * Starts the game. Prints the board, allows for user input, and allows computer to play.
	 * Ends the game when someone has won, or it's a tie.
	 */
	public void play() {
		printGame();
		
		while (true) {
			try {
				if (playerGoesFirst) {
					playerMakesMove();
					opponentMakesMove();
				}
				else {
					opponentMakesMove();
					playerMakesMove();
				}
			}
			catch (GameOverException e) {
				System.out.println(e.getMessage());
				break;
			}
		}
	}
	
	/**
	 * Prompts the user to enter in a row, followed by a column number to standard-in.
	 * This represents the user's next move.
	 * Repeatedly prompts until valid integers that are currently empty in the game board are inputted.
	 * 
	 * @return validated Pair representing (row, column)
	 */
	private Pair getPlayerMove() {
		boolean validMove;
		int row = -1;
		int col = -1;
		
		do {
			System.out.println("Your turn -");
			row = promptForInt("  Enter a row (1-indexed)") - 1;
			col = promptForInt("  Enter a column (1-indexed)") - 1;
			validMove = board.isValidMove(row, col);
			if (!validMove) {
				System.out.printf("(%d, %d) is not a valid move. Try again.\n", row + 1, col + 1);
			}
		} while (!validMove);
		
		return new Pair(row, col);
	}
	
	/**
	 * Generic helper method to place a validated move on the board.
	 * Prints the game board after the move has been made.
	 * 
	 * @param player the String "icon" of which player's turn it is (i.e., "X" or "O")
	 * @param move the validated move to be placed on the board, represented as (row, column)
	 * @return true if the game is over
	 */
	private boolean makeMove(String player, Pair move) {
		int row = move.getFirst();
		int col = move.getSecond();
		board.place(row,  col,  player);
		
		System.out.println();
		printGame();
		
		return board.terminal();
	}
	
	/**
	 * Helper method that allows the computer to make its next move.
	 * Prints out input similar to the player's prompts for convenience.
	 * 
	 * @throws GameOverException if the game is over
	 */
	private void opponentMakesMove() throws GameOverException {
		System.out.println("Opponent's turn:");
		Pair opponentMove = opponent.getNextMove(board);
		System.out.printf("  Enter a row (1-indexed): %d\n", opponentMove.getFirst() + 1);
		System.out.printf("  Enter a column (1-indexed): %d\n", opponentMove.getSecond() + 1);
		if (makeMove(opponentIcon, opponentMove)) {
			String message = generateWinningMessage();
			throw new GameOverException(message);
		}
	}
	
	/**
	 * Helper method that allows the player to make his/her next move.
	 * 
	 * @throws GameOverException if the game is over
	 */
	private void playerMakesMove() throws GameOverException {
		Pair playerMove = getPlayerMove();
		if (makeMove(playerIcon, playerMove)) {
			String message = generateWinningMessage();
			throw new GameOverException(message);
		}
		
	}
	
	/**
	 * Generates the winning message based on the board's state relative to the computer player.
	 * Assumes that the game has ended.
	 * One of:
	 *   "X wins!"
	 *   "O wins!"
	 *   "Tie!"
	 *  
	 * @return String message to be printed at the end of the game
	 */
	private String generateWinningMessage() {
		int score = board.utility(opponent);
		String result = "";
		switch (score) {
			case 1:
				result = String.format("%s wins!", opponentIcon);
				break;
			case -1:
				result = String.format("%s wins!", playerIcon);
				break;
			default:
				result = "Tie!";
		}
		return result;
	}
	
	/**
	 * Prints the icon information and board in a human-readable format.
	 */
	private void printGame() {
		System.out.printf("PLAYER: %s --- COMPUTER: %s\n", playerIcon, opponentIcon);
		board.printBoard();
		System.out.println();
	}
}
