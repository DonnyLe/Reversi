# ReversiModel
## Overview:
The following codebase is an implementation of the 2-player game, Reversi. 
You can play the normal version of the game here: https://cardgames.io/reversi/ (scroll down on page to see rules).
Compared to the normal version, this implementation of the game (as depicted in ReversiModel)  
contains a hexagonal shaped board (flat-top shaped) instead of a square shaped board.
In addition, instead of square tiles, each tile will be hexagons (pointy-top). However,
since the view has not be fully-implemented, this version of the codebase will use a
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

We decided to do an array of array over an array of arraylist to store the board since the size of the board does not change
during the game (and arrays cannot change in size). This would allow for retrieval of a specific 
hexagon using its q and r coordinates (this wouldn't work if we chose an array of arraylist). Using an array 
of array also allows for more efficient replacement of an item at index q, r (it would be O(n) for an array of arraylist 
compared to O(1) for an array of array). The issue with 
using an array of array is that due to the hexagonal shape, there will be unpopulated spots in the 
2D array. These unpopulated spots will be initialized as null and no methods will be able to modify 
these null spots (invariant). 

The game is a two player game where player 1 is represented as the integer 1 and player 2 is
represented as the integer 2. Player 1 is the white color while player 2 is the black color. 

## Quick Start:
The following code shows how one would start the game. 

ReversiModel r = new ReversiModel(); //creates a model
ReversiTextualView rtv = new ReversiTextualView(r); //creates a view 
r.startGame(4); //starts the game where the hexagonal board has a side length of 4
System.out.println(rtv.toString()); //prints the current state of the game
r.placeMove(2, 2, 0); //does a valid move
System.out.println(rtv.toString()); //prints the current state of the game
.
.
.

## Key Components
The code base follows the Model, View, Controller framework (controller not yet implemented).
The model contains all rules and the actions that can be done in Reversi. To use the view, 
the model is passed into the view. 









