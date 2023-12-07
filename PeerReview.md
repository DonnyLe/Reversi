Overall, we found the interfaces flexible, but with some issues. 
One of our main issues was implementing the 
labels features. Each of the labels directly relied in their 
player interface, which meant that we had to simulate their
players by making a player adapter. We think a better approach
to the labels would have been to have the controller send information 
to the view instead of the view getting information from the controller.
For example, we could have a showLabel function in the view interface
that takes in the turn, and this function would be called by the 
controller (which has access to the model and can easily find whose turn 
it is). Ideally, extracting information from the player should be mediated by the controller
so that any player implementation can work with the view. In addition, we found issues in 
the ReversiReadOnly and the Reversi interfaces. There are some methods like getDiscAt, isCellEmpty,
getSize, getPossibleMoves which should be in the ReversiReadOnly 
interface. We had many issues with compatibility with these interfaces
in the strategy. For example, the ReversiFrame view is supposed to take 
in a ReversiReadOnly. However, in updateturnLabel, the function casts the ReversiReadOnly
board into a Reversi board which would return an error if the inputted board 
was only implemented ReadOnlyReversi. As a result, we also had to do similar 
casting in our StrategyAdapter. 

In terms of the capability of their views, the view renders nicely. 
However, we also noticed the provider's implementation of the view does not use an AffineTransform. 
The result of this is that the width and height does not resize if the window is stretched. 
In addition, we noticed that 
the selecting feature is slightly buggy; it might be an issue in our end or maybe the lag 
is due to the adapter. However, the labels are very nice additions, and it is nice that the
view shows the player's score. 

In terms of how convenient it was the reuse the code in the view, our main issue was with 
how the provider's view updated. The way they would update their view was by calling the function 
initializeHexagon which took in their model implementation. That means that simply calling repaint 
onto their view would not update it; we had to also call initializeHexagon. As a result, we 
had to find a way to get the model  from our controller, convert into their model, and then call this
other function. As a result, we had to make a function called getModel in the controller in order 
for the ViewAdapter to have access to the model (which probably isn't great design).

We think their code, especially in the BoardPanel and BoardRenderer, were very clean and well-organized. 
Howevever, I think the documentation could be improved, especially in the README. It was very hard 
to understand the control since our implementation was much more different, and I think 
some explanation in the README could have been helpful. In addition, it would've been nice to have 
the coordinate system documented in the README. For example, the origin of the axial coordinate system
was not documented, so we had to do some trial and error to figure out what the origin is. 

The only change that we asked our providers to do was to change a part of their strategies. In their 
original implementation, their strategies made a deep copy of their Reversi implementation. However, 
we did not have access to this implementation. As a result, we asked them to change it such that 
they make their copy inside their model implementation instead of inside the strategy. This resulted in 
a new method called checkMove inside the Reversi interface. This change was necessary since the strategies
would not compile if their strategy method continued to have an instance of their model/board implementation (which we
do not have).





