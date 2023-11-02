# Player Design

We want to be able to have a game played with either 2 players, a player and an
AI and 2 AIs.

We will have a player Interface, which can work with both a human and a computer. The Interface
will have to select the type of view used, GUI for a human and just fetching the board as data
for the computer.

The Player class would have to be an intermediate between the view and the controller. The controller
would be best separating the commands it passes to the model and the way it gets the user input. 
We would use the command pattern for the controller.

A player would have to initialize the view and controller it uses according to its type, and connect
those to the model. This would require having multiple views at the same time, and different views
in some cases. 

The Computer implementation of the Player interface would have built-in move functionality while
the Human Player interface would have to delegate to the GUI.