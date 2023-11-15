# ReversiModel
## Overview:
The following codebase is an implementation of the 2-player game, Reversi. 
You can play the normal version of the game here: https://cardgames.io/reversi/ (scroll down on page to see rules).
Compared to the normal version, this implementation of the game (as depicted in ReversiModel)  
contains a hexagonal shaped board (flat-top shaped) instead of a square shaped board.
In addition, instead of square tiles, each tile will be hexagons (pointy-top). However,
since the view has not been fully-implemented, this version of the codebase will use a
textual implementation of the view, as shown below. 

![img_8.png](images/img_8.png)

Due to the hexagonal shape compared to the square shape of the board in the standard 
version of Reversi, a different coordinate system was chosen. The coordinate system chosen in this 
implementation is the axial coordinate system. You can learn about axial coordinates in more depth here:
https://www.redblobgames.com/grids/hexagons/. The picture below 
shows how the axial coordinate work.

![img_2.png](images/img_2.png)

Since we are unable to use negative coordinates in Java, the following array representation was used
store the board. __

![img_6.png](images/img_6.png)

We decided to do a 2d array over an array of arraylist to store the board since the size of the board does not change
during the game (and arrays cannot change in size). This would allow for retrieval of a specific 
hexagon using its q and r coordinates (this wouldn't work if we chose an array of arraylist). Using an array 
of array also allows for more efficient replacement of an item at index q, r (it would be O(n) for an array of arraylist 
compared to O(1) for an array of array). The issue with 
using an array of array is that due to the hexagonal shape, there will be unpopulated spots in the 
2D array. These unpopulated spots will be initialized as null and no methods will be able to modify 
these null spots (invariant). 

In the view, the coordinate system also changes. This is due to our logical coordinate system
(see transformLogicalToPhysical method in ReversiPanel) where
(0, 0) represents the center of the frame. As a result, we have a method, translateAxialCoords, that 
converts the coordinate system used in the model (where q and r are non-negative) to the coordinate system 
where (0, 0) is the center (still follows the axial coordinate system). All this involves is subtracting
q and r by length of the board array list / 2. 

The game is a two player game where player 1 is represented as the integer 1 and player 2 is
represented as the integer 2. Player 1 is the white color while player 2 is the black color. 



## Quick Start:

ReversiModel r = new ReversiModel(); //creates a model
ReversiTextualView rtv = new ReversiTextualView(r); //creates a view
ReversiGraphicsView rgv = new ReversiGraphicsView(r); //creates graphical view

r.startGame(4); //starts the game where the hexagonal board has a side length of 4
System.out.println(rtv.toString()); //prints the current state of the game
r.placeMove(2, 2, 0); //does a valid move
System.out.println(rtv.toString()); //prints the current state of the game
rgv.render(); //renders the GUI view

.
.
.

## Key Components
The code base follows the model, view, Controller framework (controller not yet implemented).
The model contains all rules and the actions that can be done in Reversi. To use the view, 
a copy of the model is passed into the view.

### Key Subcomponents
#### model
The model consists of a board, represented as a 2d array of Hexagon Objects, a turn counter, skip counter
and a HashMap of the colors corresponding to the players.

* A Hexagon object represents a single hex on the board, and has a DiscState field.

* The DiscState is an enum that can either be NONE, BLACK or WHITE

#### view 
The Reversi Graphical view consists

## Source Organization
Within the src directory, there are two packages, model, view, strategy, and commands.

1. model includes :

* DiscState.java : Enum DiscState used in the Hexagon object
* Hexagon.java : Hexagon object used in model
* ReadonlyIReversi.java : Interface for Reversi model where all methods are 
* observation methods (used by view). 
* IReversi: Extends ReadonlyIReversi. Contains all operations for Reversi game. 
* ReversiModel.java : model Implementation

2. view includes :

* IView.java : Interface for the view
* ReversiTextualView : Textual view Implementation
* ReversiGraphicalView : Graphical view implementation
* HexagonImage : Hexagon graphic used in graphical view. 

## Changes for part 2
We implemented the missing methods in the model that were 
recommended to add:
* Method that checks if a player
has any possible moves. If they don't, it throws an Exception
telling the player to pass. 
* Method for getting the score. Used in strategies
* Method to check the expected score of a move. Used in strategies
* Method to return a deep copy of the model for use in the expected score
method
* Method to get the side length of the model to be able to calculate distance. 
We also followed the feedback given to us from 
the last assignment. 

## View Tests
![1.png](hw6%2FviewTestImages%2F1.png)
![2.png](hw6%2FviewTestImages%2F2.png)
![3.png](hw6%2FviewTestImages%2F3.png)


## Keyboard Interactions 
Pressing 'm' calls a move command placeholder on the selected coordinates. 
Pressing 'p' calls a pass command placeholder. 

## Extra Credit
The Extra Credit strategies are in the Strategies module, with a Avoid Corners strategy, an only use corners
strategy, a simple highest score increase strategy and a modular strategy
to combine the different strategies in the order specified by the user. The 
mock results are available in [the transcript](strategy-transcript.txt)

