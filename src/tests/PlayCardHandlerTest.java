package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import kth.firestone.FirestoneObservable;
import kth.firestone.GameData;
import kth.firestone.buff.BuffHandler;
import kth.firestone.card.Card;
import kth.firestone.card.FirestoneCard;
import kth.firestone.card.PlayCardHandler;
import kth.firestone.hero.FirestoneHero;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;
import kth.firestone.minion.MinionRace;
import kth.firestone.minion.MinionState;
import kth.firestone.player.GamePlayer;

public class PlayCardHandlerTest {
	
	GameData gameData;
	
	@Mock
	GamePlayer player;
	
	@Mock
	FirestoneObservable observable;
	
	@Mock
	BuffHandler buffHandler;

	
	public PlayCardHandlerTest(){
		MockitoAnnotations.initMocks(this);
		gameData = new GameData();
	}

	@Test
	public void testPlaySpellCardPlayerCard() {
		PlayCardHandler pch = new PlayCardHandler(null, buffHandler, observable);
		//Mock hero
		FirestoneHero hero = new FirestoneHero(UUID.randomUUID().toString(), null, 30, null);
		int startMana = 10;
		hero.setMana(startMana);
		when(player.getHero()).thenReturn(hero);
		
		int manaCost = 5;
		Card card = new FirestoneCard(UUID.randomUUID().toString(), "Imp", "1", "1", ""+manaCost, "SPELL", "", "NONE");
		List<Card> hand = new ArrayList<Card>();
		hand.add(card);
		hand.add(new FirestoneCard(UUID.randomUUID().toString(), "Imp", "1", "1", ""+manaCost, "SPELL", "", "NONE"));
		when(player.getHand()).thenReturn(hand);
		
		ArrayList<Card> discardPile = new ArrayList<>();
		discardPile.add(new FirestoneCard(UUID.randomUUID().toString(), "Imp", "1", "1", ""+manaCost, "SPELL", "", "NONE"));
		when(player.getDiscardPileThisTurn()).thenReturn(discardPile);
		
		pch.playSpellCard(player, card);
		
		assertEquals(startMana-manaCost, hero.getMana());
		assertFalse(player.getHand().contains(card));
		assertTrue(player.getDiscardPileThisTurn().contains(card));
		assertEquals(2, player.getDiscardPileThisTurn().size());
		
	}

	@Test
	public void testPlayMinionCardPlayerCardInt() {
		//Create PlayCardHandler
		
		PlayCardHandler pch = new PlayCardHandler(gameData, buffHandler, observable);
		
		int manaCost = 5;
		Card card = new FirestoneCard(UUID.randomUUID().toString(), "Imp", "1", "1", ""+manaCost, "MINION", "", "NONE");
		List<Card> hand = new ArrayList<Card>();
		hand.add(card);
		when(player.getHand()).thenReturn(hand);
		
		//Mock hero
		int startHealth = 30;
		FirestoneHero hero = new FirestoneHero(UUID.randomUUID().toString(), null, startHealth, null);
		int startMana = 10;
		hero.setMana(startMana);
		when(player.getHero()).thenReturn(hero);
		
		
		//Mock player.getActiveMinions()
		List<Minion> minions = new ArrayList<Minion>();
		minions.add(mock(Minion.class));
		minions.add(mock(Minion.class));
		minions.add(mock(Minion.class));
		when(player.getActiveMinions()).thenReturn(minions);
		
		//Stimulate the function
		int index1 = 0;
		pch.playMinionCard(player, card, index1);
		
		//Assert
		MinionRace race = MinionRace.valueOf(gameData.getCards().get(card.getName()).get("race"));
		List<MinionState> states = getMinionStates(card);
		hand.remove(card);
		minions.add(index1, new FirestoneMinion(UUID.randomUUID().toString(), card.getName(), 
    			card.getHealth().get(), card.getOriginalHealth().get(), card.getOriginalAttack().get(), 
    			card.getAttack().get(), race, states, null, null));
		
		assertEquals(hand,player.getHand());
		assertEquals(minions, player.getActiveMinions());
		
		//Stimulate the function
		int index2 = 4;
		pch.playMinionCard(player, card, index2);
		
		//Assert
		hand.remove(card);
		minions.add(index2, new FirestoneMinion(UUID.randomUUID().toString(), card.getName(), 
    			card.getHealth().get(), card.getOriginalHealth().get(), card.getOriginalAttack().get(), 
    			card.getAttack().get(), race, states, null, null));
		assertEquals(hand,player.getHand());
		assertEquals(minions, player.getActiveMinions());
		assertEquals(player.getHero().getMana(), startMana-manaCost*2);
		
	}

	@Test
	public void testPlayWeaponCardPlayerCard() {
		// TODO When we have weapon cards
	}

	@Test
	public void testPlayWeaponCardPlayerCardString() {
		// TODO When we have weapon cards
	}
	
	public List<MinionState> getMinionStates(Card card) {
    	List<MinionState> states = new ArrayList<>();
    	String stateStrings = gameData.getCards().get(card.getName()).get("state");
		if(stateStrings!=null && !stateStrings.equals("")){
			for(String state : stateStrings.split(", ")){
				states.add(MinionState.valueOf(state));
			}
		}
		return states;
    }
}
