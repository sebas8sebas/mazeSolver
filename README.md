<h5>
Maze Solver 
</h5>

This program takes the picture of a maze, and outputs a picture of the solution of the maze.

To use the program, run the Main.java program. It is going to ask
you for a file path, enter the path of your maze png picture, 
then program will solve the maze and output the solution
to your input directory.

For the program to work, your image has to be a .png file,
your maze has to be black and white, and
your starting and endpoints must not be black, white or gray. You
can only have 2 endpoints (start and end).
To see some examples for proper input files, see the examples I 
included in the examples directory.

If the program is not solving your maze, you can try adjusting the
tolerance parameters for the color detecting methods in the 
RGButils.java class.
