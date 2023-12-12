Notes:
1.
In the document of P2, it says “You can only skip to the next animal's turn
 if you do not want the current animal to move or use a spell. 
You cannot skip more than one animal's turn at a time.”

However, on the demo server, it seems that the player can't skip an animal move. For example, 
when page shows the current animal turn is Fox and the current animal type is Spell (which means Fox has already moved) 
and next animal turn is Deer, I can't skip Deer's turn and Im not sure what would happen in the test case like this.
And another issue related is that if an animal’s turn is skipped like said in the document, 
then the chain of animal's order may be distrupted.

My code submitted is like the behaviour of the demo server.

2.
When the game lost or game win happen, the client would still be able to get, get /game and post/reset from the server,
but for post/game to the server, it won't receive any feedback to make sure the board is frozen.

3.
The code passed all the automated tests except the style check....