# Overview of our game!
	
 Please! Make sure you check out DYN4J, which is the engine our game uses in order to run, you can find it here: https://dyn4j.org

 
Welcome to Fruitless Conflict, your premiere 2010 era early flash game fruit murder simulator! That’s a lot of words! But don’t worry, our game is very simple.
 
 ![PlatformExamples](media/BetterPlatformer.png?raw=true "Platform sample")
	
 You, and up to 3 friends, can assume the role of one of these 4 fruits playing on a couple different maps. Your objective is to knock the other fruits off the map and be the last player standing. 
 
 You do this by moving your characters around and using your character's speed and mass to knock other players back. A player must be knocked off the map in order to lose a life, and once they lose all their lives they are out of the game.
 
  You control the game with one keyboard, using the arrow keys, wasd, tfgh, and ijkl keys for controlling player characters. And that’s the extent of controls!
  
![CharacterMenuExamples](media/DoubleMenu.png?raw=true "Char sample")


When you first enter the game, you will see our very simple menu. To navigate to our play, select the play button and then start a game. You will be presented with how many people you wish to play with. Please note there is no AI opponents!  
You will get a selection between 2 maps, dinner plate and kitchen sink. You can set up how many lives, players, and what fruit each player wants to be. Once you're done, click ready and begin playing! If you're confused, make sure to read the how to play!

![MenuExamples](media/CharacterSelect.png?raw=true "Menu sample")

  This whole game is meant to emulate early 2010s flash games, with an emphasis on simple controls and easy to understand gameplay that is engaging and repeatable. We also wanted to add in an element of community, being able to share a single keyboard to play with others making the game far more fun, and invoking memories of times when kids would play flash games in the school computer labs once they where done with work.


  
# Features not yet implimented:
-Power ups

  -These we where close to implementing, We have things set up and ready for use, including some variables in player class, a timer in simulate frame that platformer extends, and ideas written down in our documentation. 

  --
  
-More maps

  -If you want to make your own map, you can! All you have to do is make a text file and put it into the maps folder, then add details
  
  -any LINE starting with a # is treated as a comment
  
  -current map format: SHAPE; SIZE X; SIZE Y; TRANSLATE X; TRANSLATE Y; ROTATION; MASS; USER DATA
  
  -SHAPES: RECTANGLE, ELLIPSE, HALF-ELLIPSE, TRIANGLE
  
  -Above is some documentation on maps, feel free to use it if you wish

  --
  
-Ground pound

  -This was a though up ability for players to be able to slam into the ground after a certain amount of time in the air, this could be used as an attack and a way to stop yourself from flying off stage.

  --
  
-More modes

  -In our initial design, we wanted to implement a 2v2 mode where the players would be on teams, and players on the same team could not interact with each other but could with the enemy. We do have a way to implement more game   
    modes in our platformer class.
    
  -In our main code, we call line up GameRulesStock() to be activated when play is clicked, so all we would need to do is create a different list of rules to be called when another mode like 2v2 is selected.

--

-Moving obstacles

  -This was the final thing and was going to be implemented with the maps, however we just didn’t have the time to create obsticles

 
# How to play our game:
Extract from GitHub repository

Open eclipse, you can download eclipse if you don’t already have it at https://www.eclipse.org/downloads/

Click file in the top left corner

Click on import in the drop down menu

Under general click ‘existing projects into workspace’

Find the zipped game file and select it

Make sure under the project all files are selected

Click finish

Click on main menu in the project filed under src/main/java > (default package) > MainMenu.java

Click run
Enjoy
