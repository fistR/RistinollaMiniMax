# Definition

## Used data structures and algorithms

### Data Structures
I will only need to implement a Doubly Linked List for use other
than the basic arrays and strings for this project.

### Algorithms
I will be using the MiniMax algorithm with AB pruning for the decision making of
the Tic-Tac-Toe AI. For iterating the list and basic arrays, a
simple linear search will suffice.

## The problem to solve
The target problem is making a worthy AI opponent in the game of Tic-Tac-Toe.
The MiniMax algorithm is perfectly suited for this since it is a turn based game
which is exactly what MiniMax solves. The Doubly Linked List is needed mostly for convenience of implementation.

## Inputs
The program will accept keyboard inputs of integers to choose game parameters
and for the player to select row/column of next move, as well as y/n to select
whether Alpha-Beta pruning is enabled or not. The inputs will most likely not be error tolerant as the algorithm is the sole focus of this project.

## Target time and space complexity
The algorithm is supposed to have a worst case time requirement of O(b^d) and
space requirement of O(b*d) where b is the branching factor and d is the search depth. The algorithm is a recursive call to itself and the branching factor is how many available moves there are to make. In tic-tac-toe B will decrease as it goes deeper though. The time complexity is how big a tree will be total in the worst case, but the space complexity only is one branch to the end since it is a depth search algorithm it does not need to keep the entire tree in memory at once.

## Sources
https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning
https://en.wikipedia.org/wiki/Minimax