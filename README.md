firestone-sprint-1
==================

The first sprint of the game.

In this sprint we'll focus on getting the core right. 
Keep in mind that we will extend the game in the next sprints, so keep your code simple.

Please open GitHub issues of this repo if you have any issues with the code provided.

### Some notes

* This video instruction shows the basic rules of the game https://www.youtube.com/watch?v=TevkeE-Qy9Y
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