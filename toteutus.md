# Implementation

## General Structure
### GameController
The high level logic is controlled by the GameController class. It contains
the implementation of the different game loops: Person vs Person, Person vs AI and AI vs AI. It is handled as a singleton class, and all its methods are static.

### Ristinolla
This class is the blueprint for a game of Tic-Tac-Toe. Within is contained the board, the active player mark, and methods for getting/setting those as well as evaluating the gameboard for a win and placing/removing marks. It also has methods for calculating the available moves even though it keeps running record of them. This is because it seemed to be the simplest way to always have them in the correct order instead of sorting the DoubleLinkList each recursion by comparison of Moves.

### MiniMaxAI
This class unsurprisingly handles the AI algorithm. It consists of two methods,
the chooseMove method which is called when wanting an AI suggestion of the best next move, and the miniMaxAB2 method which is the actual algorithm chooseMove calls. I decided not to split the minimax algorithm into mini and max methods because personally I think the method is clearer as a whole, even though mini and max are quite similar.

### DoubleLinkList and Node
These are the classes implementing a Doubly Linked List which was necessary for convenience.

The other classes are very self explanatory

## Reached performance
The minimax algorithm with AB pruning will in the worst case consume O(b^d) time
which is to say the same as minimax without AB pruning. This means having to search the whole tree which means going through all the possible variations of the game. However this is not taking into account that in tic-tac-toe every level of the tree will contain -1 branch compared to the last. To be even more precise, this implementation will also have +n (remaining moves at node) added time complexity because at every node the remaining moves are found once.
In practice it seems most of the tree does not need to be searched as the next segment will reveal. The space complexity is generalized to O(b*d) when constants are discarded like the available moves.

## With vs Without AB Pruning
To see approximately how much AB pruning helps the minimax algorithm in this case I have run on my laptop a series of test runs. They were all run with depth set to 5. This is AI vs AI total time from first decision to last. Every result was a draw. More than 5x5 there is no point in measuring since without AB pruning it is too long for reason.

AB   3x3     4x4     5x5  
Yes  49ms   430ms    2724ms  
No   102ms  10766ms  369937ms  

This is a quadratic difference in performance as the size of the game board increases. A simple polynomial regression on the above data points yields the following curve: **y = -256 + 4.97x + 0.048x^2**

## Possible shortcomings and improvements to be made
Figuring out a better way to handle the list of available moves without
having to recalculate or re-order it in the recursion will save some performance power.

Implementing move-ordering would be the simplest improvement to this algorithm. I started it half-way, but it was inadequate and stopped for the time being. This means having the available moves in order of best likelihood of being a better start of a tree, this will give AB pruning more to prune off the tree. I may implement it for fun at a later date.

There is more to be done for minmax but for a simple game like tic-tac-toe it doesn't make a lot of sense. Another problem to solve instead.

