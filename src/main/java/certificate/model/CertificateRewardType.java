package certificate.model;

public enum CertificateRewardType {
    BRONZE_BADGE("Great job completing the room!"),
    SILVER_BADGE("Amazing! You solved many cues!"),
    GOLD_TROPHY("Champion! You mastered everything perfectly!");

    private final String defaultText;

    CertificateRewardType(String defaultText) {
        this.defaultText = defaultText;
    }

    public String getDefaultText() {
        return defaultText;
    }
}
