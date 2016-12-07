package kth.firestone;

import java.util.List;

import kth.firestone.player.Player;

public class Action {

    public enum Type {
        PLAYED_CARD, DAMAGE, HERO_POWER
    }

    private List<Player> players;
    private String currentPlayerId;
    private String playedCardId;
    private String minionCreatedId;
    private int position;
    private String targetId;
    private String damagedCharacterId;
    private Type actionType;

    public Action(List<Player> players, String currentPlayerId, String playedCardId, String minionCreatedId,
            int position, String targetId, String damagedCharacterId, Type actionType) {

        this.players = players;
        this.currentPlayerId = currentPlayerId;
        this.playedCardId = playedCardId;
        this.minionCreatedId = minionCreatedId;
        this.position = position;
        this.targetId = targetId;
        this.damagedCharacterId = damagedCharacterId;
        this.actionType = actionType;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(String playerId) {
        this.currentPlayerId = playerId;
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

    public String getDamagedCharacterId() {
        return damagedCharacterId;
    }

    public Type getActionType() {
        return actionType;
    }

}
