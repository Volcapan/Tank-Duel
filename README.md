# Personal Project: *Tank Duel*

## A *hopefully* entertaining video game

I plan on creating a little game called *Tank Duel*. To be more specific about the game, two players will both select
a tank to fight against each other. There will be different classes of tanks with different attributes to choose from.
The classes are:

- Light tanks: tanks that are fast and mobile but are lacking in terms of armor
- Heavy tanks: tanks that trade speed for armor and firepower
- Medium tanks: tanks that are in-between light and heavy in terms of armor, firepower, and mobility
- Tank destroyers: tanks prioritizing strong firepower; however, some lack turrets

While in the game, players will have actions (ex. shoot cannon) to choose
from in order to defeat the other. The type of tank chosen will affect how the actions turn out. I can see anyone
looking to kill some time or have some fun with another friend to play the game I set out to create. When I was younger,
I became very interested of World War II-era tanks. This led me to extensively play games such as *World of Tanks*
and *War Thunder* that feature such vehicles. Because of my interest in tanks and video games, I am looking forward
to developing the game.

## User Stories

- As a user, I want to be able to start a game and select a tank from the list of tanks to play as
- As a user, I want the other player to be able to select a tank from the list of tanks to play as
- As a user, I want to be able to shoot my tank's cannon during a game
- As a user, I want to be able to read the rules of the game
- As a user, I want to be able to add a new playable tank to the list of tanks
- As a user, I want to be able to remove a tank in the list of tanks
- As a user, I want to be able to select a tank from the list of tanks and edit its characteristics
- As a user, I want to be able to view the entire list of tanks
- As a user, I want to be able to pause a game
- As a user, I want to be able to resume a paused game
- As a user, I want to be able to save my current list of tanks and a paused game to file
- As a user, I want to be able to load my saved list of tanks and a paused game from file

## Phase 4: Task 2

Wed Nov 24 23:50:21 PST 2021
Added new Light Tank called M24 Chaffee to TankList

Wed Nov 24 23:50:21 PST 2021
Added new Medium Tank called Comet to TankList

Wed Nov 24 23:50:21 PST 2021
Added new Heavy Tank called Tiger II to TankList

Wed Nov 24 23:50:21 PST 2021
Added new Tank Destroyer called SU-100 to TankList

Wed Nov 24 23:50:33 PST 2021
Removed Light Tank M24 Chaffee from TankList

Wed Nov 24 23:50:33 PST 2021
Removed Medium Tank Comet from TankList

Wed Nov 24 23:50:33 PST 2021
Removed Heavy Tank Tiger II from TankList

Wed Nov 24 23:50:33 PST 2021
Removed Tank Destroyer SU-100 from TankList

Wed Nov 24 23:50:33 PST 2021
Added new Medium Tank called Comet to TankList

Wed Nov 24 23:50:33 PST 2021
Added new Heavy Tank called Tiger II to TankList

Wed Nov 24 23:50:33 PST 2021
Added new Tank Destroyer called SU-100 to TankList

Wed Nov 24 23:50:33 PST 2021
Added new Tank Destroyer called TheDestroyerOfWorlds to TankList

Wed Nov 24 23:50:33 PST 2021
Added new Heavy Tank called A to TankList

Wed Nov 24 23:50:45 PST 2021
Added new Light Tank called Tank to TankList

Wed Nov 24 23:51:06 PST 2021
Edited Tank #5 in TankList

Wed Nov 24 23:51:12 PST 2021
Removed Tank Destroyer SU-100 from TankList

## Phase 4: Task 3

The classes located in the model folder are structured in a satisfactory manner; I have no issues with how the classes
are built. In the persistence folder, I would like to have created a "JsonReader" superclass in order to reduce
semantic coupling and have only one single point of control for commonly held behaviors between all the JsonReaders.
TankDuelGUI in the ui folder suffers from poor cohesion; as a result, I would have split it into smaller, more
manageable classes (for example, one class represents the main menu and another represents the menu where you can view 
and edit the tank list). The same goes for TankDuelBackend, where I would like to split it into smaller classes, each 
handling a specific part of the backend, in order to improve cohesion.
In point form:
- Create a JsonReader superclass to improve coupling and create a single point of control for Json readers
- Split TankDuelGUI into smaller classes to improve cohesion; each new class will handle a specific part of the GUI
- Split TankDuelBackend into smaller classes to improve cohesion; each new class will handle a specific task