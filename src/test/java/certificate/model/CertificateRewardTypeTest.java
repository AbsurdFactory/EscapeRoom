package certificate.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CertificateRewardTypeTest {
    @Test
    void defaultTextShouldNotBeNull() {
        for (CertificateRewardType type : CertificateRewardType.values()) {
            assertNotNull(type.getDefaultText());
            assertFalse(type.getDefaultText().isBlank());
        }
    }

    @Test
    void bronzeBadgeHasCorrectText() {
        assertEquals(
                "Great job completing the room!",
                CertificateRewardType.BRONZE_BADGE.getDefaultText()
        );
    }

    @Test
    void silverBadgeHasCorrectText() {
        assertEquals(
                "Amazing! You solved many cues!",
                CertificateRewardType.SILVER_BADGE.getDefaultText()
        );
    }

    @Test
    void goldTrophyHasCorrectText() {
        assertEquals(
                "Champion! You mastered everything perfectly!",
                CertificateRewardType.GOLD_TROPHY.getDefaultText()
        );
    }

}