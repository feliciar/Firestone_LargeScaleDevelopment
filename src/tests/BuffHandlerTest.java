package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import kth.firestone.Action;
import kth.firestone.DamageHandler;
import kth.firestone.buff.BuffHandler;
import kth.firestone.buff.BuffMethods;
import kth.firestone.card.Card;
import kth.firestone.card.FirestoneCard;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;
import kth.firestone.minion.MinionRace;
import kth.firestone.player.GamePlayer;
import kth.firestone.player.Player;

public class BuffHandlerTest {

    @Mock
    DamageHandler damageHandler;

    @Mock
    BuffMethods buffMethods;

    @Mock
    GamePlayer p1, p2;

    Card spellCard, minionCard1, minionCard2;

    List<Player> players;
    List<Minion> minions1, minions2;

    BuffHandler buffHandler;
    ArrayList<Card> discardPile1, discardPile2;

    public BuffHandlerTest() {
        MockitoAnnotations.initMocks(this);
        players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        minions1 = new ArrayList<>();
        minions2 = new ArrayList<>();
        String buff = "After you cast a spell, deal 1 damage to ALL minions.";
        String buff2 = "Battlecry: Deal 1 damage.";
        minions1.add(new FirestoneMinion("uniqueId", "Wild Pyromancer", 2, 2, 0, 0, MinionRace.NONE, new ArrayList<>(),
                buff, buffHandler));
        minions2.add(new FirestoneMinion("uniqueId2", "Minion", 2, 2, 1, 1, MinionRace.NONE, new ArrayList<>(), buff2,
                buffHandler));
        minionCard1 = new FirestoneCard("uniqueId3", "Minion", "0", "0", "0", "MINION", buff, "NONE");
        minionCard2 = new FirestoneCard("uniqueId4", "Minion", "0", "0", "0", "MINION", buff2, "NONE");
        spellCard = new FirestoneCard("", "", "0", "0", "0", "SPELL", buff2, "NONE");

        when(p1.getId()).thenReturn("1");
        when(p2.getId()).thenReturn("2");

        buffHandler = new BuffHandler(buffMethods);

        discardPile1 = new ArrayList<Card>();
        discardPile1.add(spellCard);
        discardPile1.add(minionCard1);
        discardPile2 = new ArrayList<>();
        discardPile2.add(minionCard2);
    }

    @Test
    public void testPerformBuffOnPlayedMinionCard() {
        discardPile1.add(minionCard2);
        when(p2.getDiscardPileThisTurn()).thenReturn(discardPile1);
        when(p2.getActiveMinions()).thenReturn(minions2);

        Action action = new Action(players, players.get(1).getId(), minionCard2.getId(), minions2.get(0).getId(), -1,
                null, null, Action.Type.PLAYED_CARD);
        buffHandler.performBuffOnPlayedCard(action);
        verify(buffMethods).dealOneDamage(action, null, true);
    }

    @Test
    public void isPerformBuffValid() {
        when(p1.getHand()).thenReturn(discardPile1);
        when(p2.getHand()).thenReturn(discardPile2);
        when(p1.getActiveMinions()).thenReturn(minions1);
        when(p2.getActiveMinions()).thenReturn(minions2);
        List<Card> cards = new ArrayList<>();
        cards.add(minionCard1);
        cards.add(minionCard2);
        when(p2.getHand()).thenReturn(cards);

        // Test Battlecry deal one damage buff, with target
        Action action = new Action(players, players.get(1).getId(), minionCard2.getId(), null, -1, "1", null,
                Action.Type.PLAYED_CARD);
        buffHandler.isPerformBuffValid(action);
        verify(buffMethods).dealOneDamage(action, null, false);

        // Test Battlecry deal one damage buff, without target
        action = new Action(players, players.get(1).getId(), minionCard2.getId(), null, -1, null, null,
                Action.Type.PLAYED_CARD);
        assertFalse(buffHandler.isPerformBuffValid(action));

        // Test other buff, with target
        action = new Action(players, players.get(0).getId(), minionCard1.getId(), null, -1, "1", null,
                Action.Type.PLAYED_CARD);
        assertFalse(buffHandler.isPerformBuffValid(action));

        // Test other buff, without target
        action = new Action(players, players.get(0).getId(), minionCard1.getId(), null, -1, null, null,
                Action.Type.PLAYED_CARD);
        assertTrue(buffHandler.isPerformBuffValid(action));

    }

    @Test
    public void testPerformBuffOnPlayedSpellCard() {
        when(p1.getDiscardPileThisTurn()).thenReturn(discardPile1);
        when(p1.getActiveMinions()).thenReturn(minions1);
        when(p2.getActiveMinions()).thenReturn(minions2);

        Action action = new Action(players, players.get(0).getId(), spellCard.getId(), null, -1, null, null,
                Action.Type.PLAYED_CARD);

        buffHandler.performBuffOnPlayedCard(action);
        verify(buffMethods).dealOneDamage(action, null, true);
    }

    @Test
    public void testPerformBuff() {
        when(p1.getActiveMinions()).thenReturn(minions1);
        when(p2.getActiveMinions()).thenReturn(minions2);

        Action action = new Action(players, players.get(0).getId(), minionCard1.getId(), null, -1, null, null,
                Action.Type.PLAYED_CARD);

        buffHandler.performBuff(action, minions1.get(0));

        verify(buffMethods).afterSpellDealOneDamageToAllMinions(action, minions1.get(0), true);

        buffHandler.performBuff(action, minions2.get(0));
        verify(buffMethods, times(0)).dealOneDamage(action, minions2.get(0), true);

    }

    @Test
    public void testMap() {
        Map<String, Method> methods = buffHandler.createMethodMap();
        for (String methodString : methods.keySet()) {
            assert (buffHandler.invokeBuff(new Action(players, players.get(0).getId(), minionCard1.getId(), null, -1,
                    null, null, Action.Type.PLAYED_CARD), minions1.get(0), methodString, false));

        }
    }

}
