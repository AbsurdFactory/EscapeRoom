package player.service;

public class SubscriptionResult {
    private final boolean success;
    private final String message;

    public SubscriptionResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
