package game;

import state.GameState;
import state.MinMaxGameTreeNode;

public class TicTacToeGameTreeNode extends MinMaxGameTreeNode {

	private Pair move;
	private int rank;
	
	public TicTacToeGameTreeNode(GameState state, AdversaryType adversaryType) {
		super(state, adversaryType);
		move = new Pair(-1, -1);
		rank = 0;
	}
	
	@Override
	public String toString() {
		return String.format("GameTreeNode(%s, rank=%d, player=%s)", getState().toString(), getRank(), getAdversaryType().toString());
	}
	
	public Pair getMove() {
		return move;
	}
	
	public void setMove(int row, int col) {
		move.setFirst(row);
		move.setSecond(col);
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}
