package player.model;

import commonValueObjects.Id;

public class Player implements Subscriber {
    private final Id<Player> id;
    private final NickName nickName;
    private final Email email;
    private final boolean subscribed;

    private Player(Id<Player> id, NickName nickName, Email email, boolean subscribed) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.subscribed = subscribed;
    }

    public static Player create(String nickName, String email, boolean subscribed) {
        return new Player(null, new NickName(nickName), new Email(email), subscribed);
    }

    public static Player rehydrate(int id, String nickName, String email, boolean subscribed) {
        return new Player(new Id<>(id), new NickName(nickName), new Email(email), subscribed);
    }

    public Id<Player> getId() {
        return id;
    }

    public NickName getNickName() {
        return nickName;
    }

    public Email getEmail() {
        return email;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    @Override
    public void notification(String message){
        System.out.println("NOTIFICATION to " + nickName + ":" + message);
    }
  
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        return id == player.id;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + (id != null ? id : "N/A") +
                ", nickName=" + nickName +
                ", email=" + email +
                '}';
    }
}
