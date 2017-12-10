# tic-tac-toe
## Abstract
This repository holds the source code for a console-based game of Tic-Tac-Toe on a 3x3 board against an artificially intelligent computer player that never loses.

## Background
This was the final project in ICS 45J (Introduction to Java) at UC Irvine, Fall 2017. It was very open-ended; you could implement it however you want. The professor recommended a split between an offensive and defensive computer player, depending on the current state of the game. I chose to implement a Min-Max game search algorithm to ensure that the AI always selects the best move for itself. I also happened to be taking CS 171 (Introduction to Artificial Intelligence) at the same time, so it was the perfect chance for me to apply my knowledge across courses.

## The Algorithm
### How does it work?
A [min-max](https://en.wikipedia.org/wiki/Minimax) (or minimax) algorithm applies most relevantly to turn-based games between two optimal players.
One player, *max*, seeks to maximize his/her score by choosing the move which will yield the highest value result after the opponent, *min*, has moved. Similarly, *min* is trying to minimize *max*'s score.

### Applying Min-Max to Tic-Tac-Toe
Let the AI be *max*, and the user be *min*. After every move, *max* will internally play the game against itself to completion by generating a tree of all possible moves. Each node's children consist of the game board with every possible next move. Upon finding a path in which *max* wins (or ties if it can't win), it will back-track up the tree until it reaches the first move that it made in that path.

### Implementation
I implemented the game tree as a digital tree. Each node stores the current game board, and each child contains the game board with the next possible move of the opposing player. As such, the root will always be a *max* node, the next level will be *min* nodes, the next level *max*, and so on.

### Analysis
Since this min-max implementation generates a complete tree on each turn of the AI, it has significant overhead on every other turn:

* *O(b<sup>d</sup>)* time to generate a complete tree.
	* *b* is the branching factor of the tree (the number of possible next moves from any given state).
	* *d* is the depth of the tree (the total number of concrete moves that will be played; equivalent to the number of turns that will be taken).
* *Θ(d/2)* turns taken by the AI against an optimal opponent.
	* the tree is generated each time *max* makes a turn.
* **O(db<sup>d</sup>)** total running time.

This is of course feasible on a 3x3 game board, but would be extremely inefficient on larger sizes.

#### Possible Optimizations
One way to significantly reduce the running time would be to generate the complete tree only once, at the beginning. Let *v* be the node in the tree representing the state of *max*'s current turn. After *min* makes a move, scan *v*'s children in *O(b)* time to find the correct node matching the updated board's state. Use this node as the new root.

The updated analysis:

* *Θ(b<sup>d</sup>)* time to generate a complete game tree from the initial, empty game state.
* *O(b)* time to scan *b* children of *v*.
	* with optimal moves, *min* will have *Θ(d/2)* total turns.
* **O(b<sup>d</sup> + bd)** total running time.