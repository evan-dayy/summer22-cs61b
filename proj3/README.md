# CS61B: BYOW => THE DUNGEON

The primary idea and design of the game is "Dungeon". We want the player to 
find the shortest path from the start to the end. However, the player is 
constrained by the "energy" they have. The player need to pick "Energy" when it
is necessary to restore. The success of the game is defined as 
reaching the end with an energy >= 0. The failure of the game is defined as 
exhausting all the energy without reaching the end. 


## Classes and Data Structures
### Classes
* DistinctRooms : Create different types of rooms, including distinct rooms and merge rooms.
* HallWay: Create Hallway to connect distinct rooms or merge rooms.
* WorldGenerator: Create a 100 * 60 worlds with rooms and connected Hallway, and energy, coin and Avatar.
* User: Associate with the WorldGenerator Class, enable the function of user interactivity, including mouse Listener and KeyBoard input.
* Engineer: Two method to play the game: StringInput and KeyboardInput & mouseInput.
### Data Structure:
* Priority Quene
* HashSet
* ArrayList

## Persistence
* User \<WASD> movement behavior, recorded as a text file. (MovementRecord => move.txt)
* User \<WASD> movement behavior in the forest, recorded as a text file (MovementRecordForest => moveForest.txt) 
[It is important to notice that movement in forest require input directly in moveFrest.txt rather than edit in the run configuration]
* User \<Seed> recorded in a text file (seed.txt)
* User \<Click Series> Object (ArrayList) recorded in a text file (lightList => light.txt) 
[Again, important notice: StringInput does support click, "f" means turn off the light, "o" means turn on the light. However, the user should use an ArrayList.txt file to show the coordination]
  
## Main Element in the Game
* '@': User it self, Description: You!
* '·': Floor, user can move on the floor. There are two types of floor: Shining and non-shining floor. Shining floor is
created when the light is open. It will affect the normal floor in 2-tile distance.
* '█': Door. There are two type of door: Locked Door with red color and green door with green color. User can not directly
path the locked door. The door is unlocked when user gain enough coin.
* '©': coin, user can gain one coin
* 'œ': energy, user can gain 10 energy
* '♠': magical forest, it is a door connect to a forest where there are move chances to collect the coin
* '▲': light, click event will turn the light on and affect the floor.

## Features Chosen for the 12 Ambition Points
* Ability for light sources to affect how the world is rendered (Light Source);
* A system for “encounters”, where a new interface appears when the avatar interacts with entities in the world, returning the avatar to the original interface when the encounter ends
;
* Ability for the user to “replay” their most recent save, visually displaying all of the actions taken since the last time a new world was created.

## Extra Credits: Rules & Mechanisms
* Mechanism 0: Magical forest will only last 10 seconds or 10 coins. It will automatically return to the origin interface when either condition is met.
* Mechanism 1: Health Condition: HD is showing in the right-conor of the game. Each player will have a initial
200 HD. If user does not move, it will not affect the HD. However, each movement will cause user 1 HD. The user can choose use the
  'œ' to gain 10 energy. If user does have enough HD to reach the Unlocked door, then  the game is fail.
* Mechanism 2: Light Area: Avatar can change color during in the light.
* Mechanism 3: Unlock Door. The Door is not always opened. If user cannot get 10 coin, the door is never opened. When the user gain 10 or 10 more coin, it will cause the door
to green "unlocked" door. 
* Mechanism 4.1: User do not need to quit the entire program to start the new game. After the game is win or failed, or quit, the user can use 'R' to go back to the menu and start a new game
* Mechanism 4.2: If user keyboard input is wrong, the interface will show the correct one to guide the user.