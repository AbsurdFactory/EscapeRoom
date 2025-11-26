package certificate.model;

public class Certificate {

    private int id;
    private String textBody;
    private final CertificateRewardType reward;
    private final int playerId;
    private final int roomId;

    public Certificate(CertificateRewardType reward, int playerId, int roomId) {

        if (reward == null) {
            throw new IllegalArgumentException("Reward cannot be null");
        }
        if (playerId <= 0) {
            throw new IllegalArgumentException("Invalid player ID");
        }
        if (roomId <= 0) {
            throw new IllegalArgumentException("Invalid room ID");
        }

        this.reward = reward;
        this.textBody = reward.getDefaultText();
        this.playerId = playerId;
        this.roomId = roomId;
    }

    public Certificate(int id, CertificateRewardType reward, String textBody, int playerId, int roomId) {
        this(reward, playerId, roomId);
        this.id = id;
        this.textBody = textBody;
    }

    public int getId() {
        return id;
    }

    public String getTextBody() {
        return textBody;
    }

    public CertificateRewardType getReward() {
        return reward;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getRoomId() {
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
                """.formatted(id, reward, textBody, playerId, roomId);
    }
}
