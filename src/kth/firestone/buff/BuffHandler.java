package kth.firestone.buff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.spi.CalendarDataProvider;

import javax.swing.table.TableColumnModel;

import kth.firestone.card.Card;
import kth.firestone.minion.FirestoneMinion;
import kth.firestone.minion.Minion;
import kth.firestone.player.Player;

public class BuffHandler {
	
	private Player playerInTurn; 
	private Card playedCard;
	private int positionOfPlayedCard;
	private String attackerId;
	private String targetId;
	private List<Player> players;
	Map<String, Runnable> buffMethods;
	Minion currentMinion;
	
public void performAllBuffs(List<Player> players, Player playerInTurn, Card playedCard, int position, String attackerId, String targetId){
		this.players = players;
		this.playerInTurn = playerInTurn;
		this.playedCard = playedCard;
		this.positionOfPlayedCard = position;
		this.attackerId = attackerId;
		this.targetId = targetId;
		
		performBuffForPlayedCard();
		performBuffsForAllMinions();
	}
	
	private void performBuffForPlayedCard(){
		if(playedCard!=null && buffMethods.containsKey(playedCard.getDescription())){
			buffMethods.get(playedCard.getDescription()).run();
		}
	}
	
	private void performBuffsForAllMinions(){
		for(Player p : players){
			for(Minion m : p.getActiveMinions()){
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
	

	
	
	
	public void createBuffMethods(){
		buffMethods = new HashMap<>();
		
		buffMethods.put("Battlecry: Deal 1 damage.", () -> {
			if(targetId != null && !targetId.equals("")){
				for(Player p : players){
					for(Minion m : p.getActiveMinions()){
						if(m.getId().equals(targetId)){
							((FirestoneMinion) m).reduceHealth(1);
						}
					}
				}
			}
		});
		
		buffMethods.put("After you cast a spell, deal 1 damage to ALL minions.", () ->{
			if(playedCard!= null && playedCard.getType().equals(Card.Type.SPELL)){
				for(Player p : players){
					for(Minion m : p.getActiveMinions()){
						if(!m.equals(currentMinion)){
							((FirestoneMinion) m).reduceHealth(1);
						}
					}
				}
			}
		});
		
	}
	
	

}
