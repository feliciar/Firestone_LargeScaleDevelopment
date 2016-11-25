package kth.firestone;

import java.util.List;

import kth.firestone.player.Player;

public class Action {
	
	public enum Type {
		PLAYED_CARD,
		DAMAGE
	}
	
	private List<Player> players;
	private String currentPlayerId;
	private String playedCardId;
	private String minionCreatedId;
	private int position;
	private String targetId;
	private Type actionType;
	
	public Action(List<Player> players, String currentPlayerId, String playedCardId, 
			String minionCreatedId, int position, String targetId, Type actionType) {
		
		this.players = players;
		this.currentPlayerId = currentPlayerId;
		this.playedCardId = playedCardId;
		this.minionCreatedId = minionCreatedId;
		this.position = position;
		this.targetId = targetId;
		this.actionType = actionType;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public String getCurrentPlayerId() {
		return currentPlayerId;
	}

	public String getPlayedCardId() {
		return playedCardId;
	}

	public String getMinionCreatedId() {
		return minionCreatedId;
	}

	public int getPosition() {
		return position;
	}

	public String getTargetId() {
		return targetId;
	}

	public Type getActionType() {
		return actionType;
	}
	
}