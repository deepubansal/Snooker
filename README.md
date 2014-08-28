Snooker
=======

How to run:
-----------

1. Build the file using "mvn clean install".
2. From the Project's home directory run the Jar file in target folder using below command:
   <code>java -cp target/snooker-0.0.1-SNAPSHOT.jar pool.Pool</code>

About Snooker:
--------------

The project began with creating a Jpanel with an image background. Jpanel is a swings component that acts as a container for the application or a Jframe. This class is named ImagePanel class that provides a JPanel with an image as the background. It is used in two places. First, in displaying the board of the game. Second, for user input display like power stick setting and spin setting. Thus Board class and spinInput class extend this ImagePanel class. 

Pool is the actual JFrame which holds the whole interface of the game.  It contains the main() method and thus becomes the starting point of the game. It creates the objects of the two ImagePanels i.e. Board and SpinInput and adds them to the container panel. Background images for the board and spinInput imagepanels are provided here only. In Board class, the balls are added and their positions are updated according to the shots played and collisions. It contains a collection of balls, which are all objects of class Ball, and the board is refreshed from the thread method run(). In its initialize() method, the balls are put to the starting position and a thread refreshing is started to refresh the position of the balls. The run() method checks whether any ball’s postion is changed, If, yes, the board is repainted.
Ball is a super class of the balls with subclasses as CueBall(White), ColouredBall, and RedBall.  It contains all the information about a ball. Its image, its position, its last position, its velocity, its rotational velocity (w-omega) etc.  Its update() method plays the vital role which redefines the various vectors by calculating the change in them according to time. 



Concepts Used:
--------------
Multithreading, Physics Formulae for rolling and sliding motions, Java Swings.

