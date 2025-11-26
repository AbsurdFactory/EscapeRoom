package room.model;

import java.util.ArrayList;
import java.util.List;

import player.model.Player;
import player.model.Subscriber;

public class RoomEventPublisher {

    private final List<Subscriber> subscribers = new ArrayList<>();

    public void subscribe(Subscriber subscriber) {
        if (subscriber == null) {
            throw new IllegalArgumentException("Subscriber cannot be null");
        }
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }
    public void registerPlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }

        if (player.isSubscribed()) {
            subscribe(player);
        }
    }
    public void notifySubscribers(String message) {
        for (Subscriber s : subscribers) {
            s.notification(message);
        }
    }

    public void notifyRoomCreated(Room room) {
        notifySubscribers("A new room has been created → " + room.getName());
    }
}

