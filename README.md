# tic-tac-toe
## Abstract
This repository holds the source code for a console-based game of Tic-Tac-Toe against an artificially intelligent computer player that never loses.

## Background
This was the final project in ICS 45J (Introduction to Java) at UC Irvine, Fall 2017. It was very open-ended; you could implement it however you want. The professor recommended a split between an offensive and defensive computer player, depending on the current state of the game. I chose to implement a Min-Max game search algorithm to ensure that the AI always selects the best move for itself. I also happened to be taking CS 171 (Introduction to Artificial Intelligence) at the same time, so it was the perfect chance for me to apply my knowledge across courses.

## The Algorithm
### The Basics
A min-max (or minimax) algorithm applies most relevantly to turn-based games between two players.
One player, *max*, seeks to maximize his/her score by choosing the move which will yield the highest value result after the opponent, *min*, has moved. The algorithm assumes that *min* is trying to minimize *max*'s score.

### Applying Min-Max to Tic-Tac-Toe
Let the AI be *max*, and the user be *min*. After every move, *max* will internally play the entire game against itself to completion by generating a tree whose children consist of the game board with every possible next move. Upon finding a path in which *max* wins (or ties if it can't win), it will back-track up the tree until it reaches the first move that it made in that path.

### Implementation
I implemented a GameTree as a digital tree. Each node stores the current game board, and each child contains the game board with the next possible move of the opposing player. As such, the root will always be a *max* node, the next level will be *min* nodes, the next level *max*, and so on.

### Analysis
Since this min-max implementation generates a complete tree on each turn of the AI, it has significant overhead on every other turn. Generating a complete tree incurs *O(b<sup>d</sup>)* time, where *b* is the branching factor of the tree (the number of possible moves from any given state), and *d* is the depth of the tree (the total number of turns that will be taken). Trivially, it requires the same amount of space. By generating this tree on every turn, with *Θ(d/2)* turns by the AI, the total running time is *O(db<sup>d</sup>)*.
One way to reduce the running time by a factor of *d* is by generating the complete tree only once (at the beginning), then storing the node representing the state of *max*'s current turn, using that node as the root, and descending the tree from that node at every turn.