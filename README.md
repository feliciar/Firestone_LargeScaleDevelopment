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
* Twilight Guardian (http://hearthstone.gamepedia.com/Twilight_Guardian)
* Frostbolt (http://hearthstone.gamepedia.com/Frostbolt)
* Shadowstep (http://hearthstone.gamepedia.com/Shadowstep)
* Backstab (http://hearthstone.gamepedia.com/Backstab)
* Equality (http://hearthstone.gamepedia.com/Equality)
* Wild Pyromancer (http://hearthstone.gamepedia.com/Wild_Pyromancer)



