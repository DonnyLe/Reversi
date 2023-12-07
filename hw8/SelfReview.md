The main thing I think we could've done differently in our implementation is we could have used more enums 
to represent certain binary data like whose turn it is, or the game state. I also think 
we could have done a better job documenting our code, especially with the Java docs for the classes. 
I also think we could have documented more about the control flow in README (however, our customers did 
not have any questions regarding that). The only request from our customers was the copyBoard method 
which was frankly just a simple mistake of ours
(that method was accidentally in an interface). In terms about what we learned from this experience
I feel like we learned a lot about 
how important decoupling was when developing code after working with our provider's code. 
In our provider's code, the view was very coupled with the player interface and since our player interface
was much different compared to their player interface, it was very hard to adapt the two interfaces. 

When communicating with our providers, they were extremely responses in clarifying things that seemed
to be unclear with the documentation. When we asked for an update to their strategies, they responded 
in a timely manner and repeatedly asked us if their new method in the interface satisfied our needs. 
