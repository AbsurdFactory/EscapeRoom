package player.model;

import valueobjects.Age;
import valueobjects.Email;
import valueobjects.NickName;
import valueobjects.Id;

public class Player {
    private final Id<Player> id;
    private final NickName nickName;
    private final Email email;

    private Player(Id<Player> id, NickName nickName, Email email) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
    }

    public static Player create(String nickName, String email) {
        return new Player(null, new NickName(nickName), new Email(email));
    }

    public static Player rehydrate(int id, String nickName, String email) {
        return new Player(new Id(id), new NickName(nickName), new Email(email));
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
