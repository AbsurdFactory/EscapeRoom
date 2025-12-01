package certificate.model;

import commonValueObjects.Id;
import player.model.Player;
import room.model.Room;


public class Certificate {

    private Id<Id> id;
    private CertificateText textBody;
    private final CertificateRewardType reward;
    private final Id <Player> playerId;
    private final Id <Room> roomId;

    public Certificate(CertificateRewardType reward, Id<Player> playerId, Id<Room> roomId) {

        if (reward == null) {
            throw new IllegalArgumentException("Reward cannot be null");
        }

        this.reward = reward;
        this.textBody = new CertificateText(reward.getDefaultText());
        this.playerId = playerId;
        this.roomId = roomId;
    }

    public Certificate(Id id, CertificateRewardType reward, CertificateText textBody, Id<Player> playerId, Id<Room> roomId) {
        this(reward, playerId, roomId);
        this.id = id;
        this.textBody = textBody;
    }

    public Id<Id> getId() {
        return id;
    }

    public String getTextBody() {
        return textBody.toString();
    }

    public CertificateRewardType getReward() {
        return reward;
    }

    public Id<Player> getPlayerId() {
        return playerId;
    }

    public Id<Room> getRoomId() {
        return roomId;
    }

    @Override
    public String toString() {
        return """ 
                Certificate: {
                Id = %s,
                Reward = %s ,
                TextBody = %s ,
                PlayerId = %s,
                RoomId = %s
                }
                """.formatted(id.getValue(), reward, textBody.toString(), playerId.getValue(), roomId.getValue());
    }
}
