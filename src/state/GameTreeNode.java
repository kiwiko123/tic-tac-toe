package state;

import java.util.List;

public interface GameTreeNode {
	
	/**
	 * Returns the GameState held by the current node.
	 * 
	 * @return GameState
	 */
	public GameState getState();
	
	/**
	 * Returns the immediate children of the current node.
	 * 
	 * @return List<GameTreeNode>
	 */
	public List<GameTreeNode> getChildren();
	
	/**
	 * Adds a new child to the current node.
	 * 
	 * @param child
	 */
	public void addChild(GameTreeNode child);
}
