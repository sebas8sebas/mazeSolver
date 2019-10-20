<h5>
Maze Solver 
</h5>

This program takes the picture of a maze, and outputs a picture of 
with the solution of the maze.

To use the program go, run the Main.java program. It is going to ask
you for a file name, enter the name of your maze's file 
(which should be in the inputMazes directory), 
the program will solve the maze and output the solution
to the outputMazes directory.

For the program to work, your image has to be a .png file,
your maze has to be black and white, and
your starting and endpoints must not be black, white or gray. You
can only have 2 endpoints (start and end).
To see some examples for proper input files, see the examples I 
included in the inputMazes directory (which have their solutions on
the outputMazes directory).

If the program is not solving your maze, you can try adjusting the
tolerance parameters for the color detecting methods in the 
RGButils.java class.
