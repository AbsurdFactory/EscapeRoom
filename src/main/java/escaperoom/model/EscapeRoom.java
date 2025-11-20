package escaperoom.model;

public class EscapeRoom {
    private int id;
    private String name;

    public EscapeRoom(){};

    public EscapeRoom(int id, String name){
        if(id <= 0) {
            throw new IllegalArgumentException("The id must be greater than zero.");
        }
        if(name.isBlank() || name.isEmpty()){
            throw new IllegalArgumentException("The name cannot be empty or blank.");
        }
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "EscapeRoom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
