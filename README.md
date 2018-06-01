Gentrack Maze Solution Overview
------------------------------

Problem Statement
---

The idea here is to write a program to solve simple mazes. The mazes are given in 
a file and the program must read in the file, solve the maze and output the solution.
If no solution is possible the output should indicate this somehow. The program 
should be written to the following specification:
  
  - Arbitrary sized mazes should be handled
  - Valid moves are N, S, E, W (not diagonally)
  - All input will be clean, no validation is necessary
  - Any suitable language can be used although one of Java, C#, Python is preferred
  - The maze file format is described below with an example
  - The program should be tested on the sample mazes provided
  - Output should be written to the Standard Output/Console


Maze file format
================

The input is a maze description file in plain text.  
 1 - denotes walls
 0 - traversable passage way

INPUT:
<WIDTH> <HEIGHT><CR>
<START_X> <START_Y><CR>		(x,y) location of the start. (0,0) is upper left and (width-1,height-1) is lower right
<END_X> <END_Y><CR>		(x,y) location of the end
<HEIGHT> rows where each row has <WIDTH> {0,1} integers space delimited

OUTPUT:
 the maze with a path from start to end
 walls marked by '#', passages marked by ' ', path marked by 'X', start/end marked by 'S'/'E'

Example file:
```
10 10
1 1
8 8
1 1 1 1 1 1 1 1 1 1
1 0 0 0 0 0 0 0 0 1
1 0 1 0 1 1 1 1 1 1
1 0 1 0 0 0 0 0 0 1
1 0 1 1 0 1 0 1 1 1
1 0 1 0 0 1 0 1 0 1
1 0 1 0 0 0 0 0 0 1
1 0 1 1 1 0 1 1 1 1
1 0 1 0 0 0 0 0 0 1
1 1 1 1 1 1 1 1 1 1
```

OUTPUT:
```
##########
#SXX     #
# #X######
# #XX    #
# ##X# ###
# # X# # #
# # XX   #
# ###X####
# #  XXXE#
##########
```


How to run 
---

A jUnit4 testing class has been provided to execute the code. Simply run the tests (will require a jUnit4 installation) or use the helper methods to provide your own. 

Using the Maze class the overall execution process looks like this...
 
 1. Construct a new GenTrackMaze object and its contained passages (on the maze) from a GenTrackMaze file.
 2. Solve the maze using a depth first search algorithm through passages which hold information about their neighbours and carry signposting information (the GenTrackMaze.solve() method).
 3. Identify the endpoint of the maze and trace the route back to the starting position to save time rather than completing the whole depth first search (the GentrackMaze.tracePath() method).
 4. Use the list metadata to redraw the graph which encourages looser coupling from the ASCII-looking maze format (the GenTrackMaze.solutionToString() method).

Unfortunately being set time constraints provided to get a solution in whilst working a full time position (9-6) during the week have prevented further modulation of the maze, passages on its topology, the search algorithm, and so on, into more modular classes and interfaces. Also, I did not want to hand over a Google lookup solution and demonstrate more what I'm capable of when it comes to writing algorithms (rather than directly translating an A* algorithm or so on) so the solution is unique in its approach.

With that in mind the solution was fully coded the night of 08/05/2018 after work and utilises a depth first search algorithm on passages and their adjacent passages, the information of which is cached to memory on first read of a GenTrackMaze compliant txt file.
 
Whilst I recognise there is a lot I want to improve with the solution, in particular class design since the whole thing was speed-coded (and I would be keen for resubmission), I hope you enjoy the approach as much as I had fun writing it. Happy Coding!


Application Output
----

Example output ran by this application are provided below. 

Example 1 
---

```
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 0 1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 1 0 1 0 1
1 0 1 0 1 0 1 0 1 1 1 1 1 1 1 0 1 0 1 0 1 0 1
1 0 1 0 1 0 1 0 0 0 0 0 0 0 1 0 1 0 1 0 1 0 1
1 0 1 0 1 0 1 1 1 1 1 1 1 1 1 0 1 0 1 0 1 0 1
1 0 1 0 1 0 1 0 0 0 0 0 0 0 1 0 1 0 0 0 1 0 1
1 0 1 0 1 0 1 0 1 1 1 1 1 0 1 0 1 1 1 1 1 0 1
1 0 1 0 1 0 1 0 0 0 0 0 1 0 1 0 1 0 0 0 1 0 1
1 0 1 1 1 0 1 1 1 0 1 0 1 0 1 0 1 0 1 0 1 0 1
1 0 0 0 0 0 1 0 0 0 1 0 1 0 1 0 1 0 1 0 1 0 1
1 1 1 0 1 1 1 1 1 1 1 0 1 0 1 0 1 1 1 0 1 0 1
1 0 0 0 0 0 1 0 0 0 0 0 1 0 1 0 0 0 1 0 1 0 1
1 1 1 1 1 0 1 0 1 1 1 1 1 0 1 1 1 0 1 0 0 0 1
1 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 1 0 1 0 1 0 1
1 0 1 0 1 1 1 1 1 0 1 1 1 0 1 0 1 0 1 0 1 0 1
1 0 1 0 0 0 0 0 1 0 1 0 0 0 1 0 0 0 1 0 1 0 1
1 0 1 1 1 1 1 0 1 0 1 0 1 1 1 0 1 1 1 0 1 0 1
1 0 1 0 0 0 1 0 1 0 1 0 0 0 1 0 1 0 0 0 1 0 1
1 0 1 0 1 0 1 0 1 1 1 1 1 0 1 0 1 0 1 0 1 0 1
1 0 0 0 1 0 1 0 0 0 0 0 0 0 1 0 0 0 1 0 1 0 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
```

Solves to...
```
#######################
#S#   #           # # #
#X# # # ####### # # # #
#X# # #       # # # # #
#X# # ######### # # # #
#X# # #XXXXXXX# #   # #
#X# # #X#####X# ##### #
#X# # #XXXXX#X# #   # #
#X### ### #X#X# # # # #
#XXX  #   #X#X# # # # #
###X#######X#X# ### # #
#  XXX#XXXXX#X#   # # #
#####X#X#####X### #XXX#
#   #XXX  #  XXX# #X#X#
# # ##### ### #X# #X#X#
# #     # #   #X  #X#X#
# ##### # # ###X###X#X#
# #   # # #   #X#XXX#X#
# # # # ##### #X#X# #X#
#   # #       #XXX# #E#
#######################
```

Example 2
---

```
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
```

Solves to... 

```
#####################
#S                  #
#X                  #
#X                  #
#X                  #
#X                  #
#X                  #
#X                  #
#X                  #
#X                  #
#X                  #
#X                  #
#X                  #
#X                  #
#X                  #
#XX                 #
# XX                #
#  XX               #
#   XX              #
#    XXXXXXXXXXXXXXE#
#####################
```
