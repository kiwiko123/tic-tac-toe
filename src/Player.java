
public abstract class Player {
	private final String id;
	
	public Player(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return String.format("Player(%s)", getId());
	}

	/**
	 * Represents an identifying trait of the player;
	 * e.g., "X" or "O".
	 * 
	 * @return the player's ID
	 */
	public String getId() {
		return id;
	}
}
