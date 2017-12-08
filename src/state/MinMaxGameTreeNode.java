package state;

import java.util.LinkedList;
import java.util.List;


public abstract class MinMaxGameTreeNode implements GameTreeNode {
	
	public static enum AdversaryType {
		MIN,
		MAX
	}
	
	/**
	 * Returns the opposite AdversaryType of current.
	 * e.g., if current is MAX, returns MIN, and vice-versa.
	 * 
	 * @param current the AdversaryType to be reversed
	 * @return the reversed AdversaryType of current
	 */
	public static AdversaryType reverseAdversaryType(AdversaryType current) {
		switch (current) {
			case MIN:
				return AdversaryType.MAX;
			case MAX:
				return AdversaryType.MIN;
		}
		return null;
	}
	
	private GameState state;
	private AdversaryType adversaryType;
	private List<GameTreeNode> children;
	
	public MinMaxGameTreeNode(GameState state, AdversaryType adversaryType) {
		this.state = state;
		this.adversaryType = adversaryType;
		children = new LinkedList<>();
	}
	
	@Override
	public String toString() {
		return String.format("GameTreeNode(%s, player=%s)", getState().toString(), getAdversaryType().toString());
	}

	public GameState getState() {
		return state;
	}

	public List<GameTreeNode> getChildren() {
		return children;
	}
	
	public void addChild(GameTreeNode child) {
		children.add(child);
	}

	public AdversaryType getAdversaryType() {
		return adversaryType;
	}
}
