package player.model;

public class Player {
    private int id;
    private String nickName;
    private String email;
    private int age;

    public Player() {}

    public Player(int id, String nickName, String email, int age) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.age = age;
    }

    public int getId() { return id; }

    public String getNickName() { return nickName; }

    public String getEmail() { return email; }

    public int getAge() { return age; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        return id == player.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
