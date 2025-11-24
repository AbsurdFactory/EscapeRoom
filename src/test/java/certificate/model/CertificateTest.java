package certificate.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CertificateTest {

    @Test
    void shouldThrowExceptionWhenRewardIsNull() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Certificate(null, 1, 1)
        );
        assertEquals("Reward cannot be null", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPlayerIdInvalid() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Certificate(CertificateRewardType.BRONZE_BADGE, 0, 1)
        );
    }

    @Test
    void shouldThrowExceptionWhenRoomIdInvalid() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Certificate(CertificateRewardType.BRONZE_BADGE, 1, -5)
        );
    }

    @Test
    void shouldUseDefaultTextFromReward() {
        Certificate cert = new Certificate(CertificateRewardType.SILVER_BADGE, 10, 20);

        assertEquals(
                CertificateRewardType.SILVER_BADGE.getDefaultText(),
                cert.getTextBody()
        );
    }

    @Test
    void secondConstructorShouldOverrideTextBody() {
        Certificate cert = new Certificate(
                99,
                CertificateRewardType.GOLD_TROPHY,
                "Custom text",
                11,
                22
        );

        assertEquals(99, cert.getId());
        assertEquals("Custom text", cert.getTextBody());
        assertEquals(11, cert.getPlayerId());
        assertEquals(22, cert.getRoomId());
    }

    @Test
    void toStringShouldNotBeNull() {
        Certificate cert = new Certificate(CertificateRewardType.BRONZE_BADGE, 1, 1);
        assertNotNull(cert.toString());
    }
}