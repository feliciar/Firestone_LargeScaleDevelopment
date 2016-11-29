firestone
==================

The first sprint of the game.

In this sprint we'll focus on getting the core right. 
Keep in mind that we will extend the game in the next sprints, so keep your code simple.

Please open GitHub issues of this repo if you have any issues with the code provided.

### Some notes

* This video instruction shows the basic rules of the game https://www.youtube.com/watch?v=XEXLe98bj8I
* When the game is started, the starting player gets three cards. The other players gets four cards. This process is called mulligan. In the real game this can be chosen by the client, but our implementation simply takes the `x` first cards of the deck. Also, we do not have any coin card.
* Keep in mind that the player hero takes damage if the deck is empty and the player tries to draw a card. This is called fatigue. This can be useful to know while constructing tests.
* Some methods of the game API returns a list of events. This is not used in this sprint, so simply return an empty list.
* Create tests that assures that the functionality works.

### Goals for the sprint 1

* Be able to create the state of the game.
* Be able to play minion cards.
* A minion should be able to attack another minion.
* A minion should be able to attack the other hero.

Observe that hero powers and specific heroes, spells and weapons are not included in sprint 1.

#### Minions for the first sprint are: 
* Imp (http://hearthstone.gamepedia.com/Imp)
* War Golem (http://hearthstone.gamepedia.com/War_Golem)
* Boulderfist Ogre (http://hearthstone.gamepedia.com/Boulderfist_Ogre)
* Ironforge Rifleman (http://hearthstone.gamepedia.com/Ironforge_Rifleman)
* Blackwing Corruptor (http://hearthstone.gamepedia.com/Blackwing_Corruptor)
* Twilight Drake (http://hearthstone.gamepedia.com/Twilight_Drake)


# firestone-sprint-2

Sprint 2 should contain all features requested in sprint 1, and the following new additions.

* Be able to end the turn.
* Be able to play spell cards.

Note that we expect the basic rules of the game to function (such as minions not being able to attack twice, sleepyness, etc.). It is also expected that all functionality of the cards given to you should work in the same way as in the original game. For instance, playing a "Blackwing Corruptor" should deal 3 damage to a specified target if the player is holding a dragon. We also want tests that show that the cards are implemented correctly. For instance, there should be tests showing that "Blackwing Corruptor" works as expected in different situations. If you are unsure about the functionality of the original game, you can watch gameplay videos on youtube, ask us, or read at http://hearthstone.gamepedia.com/Advanced_rulebook. 

#### Additional cards

* Edwin VanCleef (http://hearthstone.gamepedia.com/Edwin_VanCleef)
* Midnight Drake (http://hearthstone.gamepedia.com/Midnight_Drake)
* Frostbolt (http://hearthstone.gamepedia.com/Frostbolt)
* Shadowstep (http://hearthstone.gamepedia.com/Shadowstep)
* Backstab (http://hearthstone.gamepedia.com/Backstab)
* Equality (http://hearthstone.gamepedia.com/Equality)
* Wild Pyromancer (http://hearthstone.gamepedia.com/Wild_Pyromancer)


# firestone-sprint-3

Sprint 3 should contain all features requested in sprint 2 and 1, and the following new additions. In this sprint you’ll be able to try out your implementation with our view. Observe all bugs you didn't catch with your tests, type checks or compilation steps. You have been added to another repo called `firestone-view` which contains the compiled web view code including instructions how to run it (in the README.md of that repo). You will need to integrate your code with our game server. Please report issues in this repo if you run into troubles.

### SAD

Your team needs to write a software architecture design. You need to guide other people your strategies and how to maintain your code. Not more than six A4 pages.

## New Cards

* Shadowform (http://hearthstone.gamepedia.com/Shadowform)
* Alexstrasza (http://hearthstone.gamepedia.com/Alexstrasza)
* Acolyte of Pain (http://hearthstone.gamepedia.com/Acolyte_of_Pain)
* Lorewalker Cho (http://hearthstone.gamepedia.com/Lorewalker_Cho)

## New Hero Powers

* Life Tap ( http://hearthstone.gamepedia.com/Life_Tap)
* Fireblast (http://hearthstone.gamepedia.com/Fireblast)
* Lesser Heal (http://hearthstone.gamepedia.com/Lesser_Heal)
* Steady Shot (http://hearthstone.gamepedia.com/Steady_Shot)

## Implementing the server in Java

1. Include the `server.jar` file as a dependency.
2. Include the `gson-2.3.1.jar` file as a dependency.
3. Create an implementation of `kth.firestone.server.GameFactory`.
4. Create a main method that creates an instance of your `GameFactory` implementation and create a `ServerFactory` instance with it and then call `createServer` followed by `start`.




