package game;

public class GameOverException extends Exception {
	
	private static final long serialVersionUID = 42L;		// arbitrary; silence warning
	
	public GameOverException(String message) {
		super(message);
	}
	
	public GameOverException() {
		this("");
	}
}
