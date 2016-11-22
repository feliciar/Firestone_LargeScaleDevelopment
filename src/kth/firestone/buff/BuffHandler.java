package kth.firestone.buff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kth.firestone.DamageHandler;
import kth.firestone.card.Card;
import kth.firestone.hero.FirestoneHero;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;
import kth.firestone.player.GamePlayer;
import kth.firestone.player.Player;

/**
 * @author Felicia Rosell
 *
 */
public class BuffHandler {
	
	private Player playerInTurn; 
	private Card playedCard;
	private boolean willPerformBuffOnPlayedCard = false;
	private Minion playedMinion;
	private int positionOfPlayedCard;
	private String attackerId;
	private String targetId;
	private List<Player> players;
	Map<String, Runnable> buffMethods;
	Minion currentMinion;
	DamageHandler damageHandler;
	
	
	public BuffHandler(DamageHandler dh){
		this.damageHandler = dh;
	}
	
	/**
	 * This method performs all buffs that happen because of the played card, for ALL minions on the board. 
	 * @param players
	 * @param playerInTurn
	 * @param playedCard
	 * @param position
	 */
	public void performBuffsAfterCardPlayed(List<Player> players, Player playerInTurn, Card playedCard, int position, String targetId){
		willPerformBuffOnPlayedCard = true;
		performAllBuffs(players, playerInTurn, playedCard, position, null, targetId);
	}
	
	/**
	 * This method performs all buffs that happen because of the attack, for ALL minions on the board. 
	 * @param players
	 * @param playerInTurn
	 * @param attackerId
	 * @param targetId
	 */
	public void performBuffsAfterAttack(List<Player> players, Player playerInTurn, String attackerId, String targetId){
		performAllBuffs(players, playerInTurn, null, -1, attackerId, targetId);
	}
	
	/**
	 * 
	 * @param players, all players in the game 
	 * @param playerInTurn, the player in turn
	 * @param playedCard, if a card was just played, insert it here
	 * @param position, the position of a card played 
	 * @param attackerId, if an attack just occurred, insert the attackerId here
	 * @param targetId, if an attack just occurred, insert the target here
	 */
	private void performAllBuffs(List<Player> players, Player playerInTurn, Card playedCard, int position, String attackerId, String targetId){
		this.players = players;
		this.playerInTurn = playerInTurn;
		this.playedCard = playedCard;
		this.positionOfPlayedCard = position;
		this.attackerId = attackerId;
		this.targetId = targetId;
		
		for(Minion m : playerInTurn.getActiveMinions()){
			if(m.getName().equals(playedCard.getName())){
				playedMinion = m;
			}
		}	
		if(willPerformBuffOnPlayedCard){
			performBuffOnPlayedCard();
			willPerformBuffOnPlayedCard = false;
		}
		performBuffsForAllMinions();
	}
	
	private void performBuffOnPlayedCard(){
		if(playedCard!=null && buffMethods.containsKey(playedCard.getDescription())){
			buffMethods.get(playedCard.getDescription()).run();
		}
	}
	
	private void performBuffsForAllMinions(){
		for(Player p : players){
			for(Minion m : p.getActiveMinions()){
				if( ! m.equals(playedMinion)){
					currentMinion = m;
					for(BuffDescription d : m.getBuffDescriptions()){
						String buff = d.getName().equals("") ? d.getDescription() : d.getName() +": "+ d.getDescription();
						if(buffMethods.containsKey(buff)){
							buffMethods.get(buff).run();
						}else{
							System.err.println("There was no method mapped to this buff: "+buff);
						}
					}
				}
			}
		}
	}
		
	/**
	 * 
	 * @param players, all players in the game 
	 * @param playerInTurn, the player in turn
	 * @param playedCard, if a card was just played, insert it here
	 * @param position, the position of a card played 
	 * @param attackerId, if an attack just occurred, insert the attackerId here
	 * @param targetId, if an attack just occurred, insert the target here
	 */
	public void createBuffMethods(){
		buffMethods = new HashMap<>();
		buffMethods.put("Battlecry: Deal 1 damage.", () -> {
			if((willPerformBuffOnPlayedCard && playedCard != null && targetId != null)){
				for(Player p : players){
					for(Minion m : p.getActiveMinions()){
						if(m.getId().equals(targetId)){
							damageHandler.dealDamageToOneMinion((FirestoneMinion)m, 1);
						}
					}
					damageHandler.removeDeadMinions(p.getActiveMinions());
				}
			}
		});
		
		buffMethods.put("After you cast a spell, deal 1 damage to ALL minions.", () ->{
			if(playedCard!= null && playedCard.getType().equals(Card.Type.SPELL)){
				List<Minion> minionsToDealDamageTo = new ArrayList<Minion>();
				for(Player p : players){
					minionsToDealDamageTo.addAll(p.getActiveMinions());
				}
				minionsToDealDamageTo.remove(currentMinion);
				//damageHandler.dealOneDamageToSeveralMinions(minionsToDealDamageTo);
				//TODO deal damage to all minions and remove dead minions
			}
		});
		
		buffMethods.put("Battlecry: Gain +1 Health for each card in your hand.", () ->{
			if(willPerformBuffOnPlayedCard && playedCard !=null){
				int health = ((FirestoneHero )playerInTurn.getHero()).getHealth();
				health += playerInTurn.getHand().size();
				((FirestoneHero )playerInTurn.getHero()).setHealth(health);
			}
		});
		
		buffMethods.put("Combo: Gain +2/+2 for each card played earlier this turn.", () ->{
			//TODO Find the played minion
			if(willPerformBuffOnPlayedCard && playedCard!= null && playedMinion != null){
				int increase = ((GamePlayer)playerInTurn).getDiscardPile().size();
				FirestoneMinion m = ((FirestoneMinion)playedMinion);
				//Increase health and attack
			}
		});
		
	}

}
