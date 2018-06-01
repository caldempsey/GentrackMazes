Gentrack Maze Solution Overview
------------------------------

A jUnit4 testing class has been provided to execute the code. Simply run the tests (will require a jUnit4 installation) or use the helper methods to provide your own. 

Using the Maze class the overall execution process looks like this...
 
 1. Construct a new GenTrackMaze object and its contained passages (on the maze) from a GenTrackMaze file.
 2. Solve the maze using a depth first search algorithm through passages which hold information about their neighbours and carry signposting information (the GenTrackMaze.solve() method).
 3. Identify the endpoint of the maze and trace the route back to the starting position to save time rather than completing the whole depth first search (the GentrackMaze.tracePath() method).
 4. Use the list metadata to redraw the graph which encourages looser coupling from the ASCII-looking maze format (the GenTrackMaze.solutionToString() method).

Unfortunately being set time constraints provided to get a solution in whilst working a full time position (9-6) during the week have prevented further modulation of the maze, passages on its topology, the search algorithm, and so on, into more modular classes and interfaces. Also, I did not want to hand over a Google lookup solution and demonstrate more what I'm capable of when it comes to writing algorithms (rather than directly translating an A* algorithm or so on) so the solution is unique in its approach.

With that in mind the solution was fully coded the night of 08/05/2018 after work and utilises a depth first search algorithm on passages and their adjacent passages, the information of which is cached to memory on first read of a GenTrackMaze compliant txt file.
 
Whilst I recognise there is a lot I want to improve with the solution, in particular class design since the whole thing was speed-coded (and I would be keen for resubmission), I hope you enjoy the approach as much as I had fun writing it. Happy Coding!
