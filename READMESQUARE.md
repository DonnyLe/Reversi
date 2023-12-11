Level 0: Not complete

Level 1: We reimplemented our IReversi interface in 
the SquareModel class. We did not need to change anything 
in the interface. We added two more directions in the 
getListDirectionsToSearch helper method to account for 
the 8 directions instead of 6 for Hexagonal Reversi.
We also made sure getBoardArrayLength and getSideLength
return the same value (since these values should be equal).
We also changed how the board was initialized and removed
the invariant of the hexagon board having null spaces.

Note: We did not change the variable names for q and r 
to be x and y, but in this case since we are not using
the axial coordinate system, q = x and r = y. 

Level 2: We reimplemented the IView interface in SquareReversiGraphicsView
and the IPanel interface (we just separated the methods in ReversiPanel
and added it to a new interface, does not change anything) in SquareReversiPanel.
We also made a new class called SquareImage which makes a square
using the Path2D class. All logic is similar between SquareReversiPanel 
and ReversiPanel, but some of the translations and transformations were modified/removed.

Level 3: Our controller was able to work with the SquareReversi and Hexagonal Reversi
without issues. As a result, we did not modify the controller at all. 

Level 4: The strategies ModularStrategy and MostCapturesStrategy worked without changes for the square
model. The strategies AvoidBeforeCornersStrategy and CornersStrategy required a way to check if the model
is square. This was achieved by checking whether the 0, 0 coordinate of the board is null, as it is 
an invariant that it is null in a hexagonal board. The strategies chooses the appropriate coordinate according 
to the result of that check.

