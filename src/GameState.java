
public interface GameState {

	/**
	 * Determines if the GameState is over.
	 * 
	 * @return true if the game is in a state that is over
	 */
	public boolean terminal();
	
	/**
	 * Returns the integer representation of the GameState's score.
	 * 
	 * @param player the player which the GameState will calculate its score relative to 
	 * @return the integer representation of the score
	 */
	public int utility(Player player);
}
