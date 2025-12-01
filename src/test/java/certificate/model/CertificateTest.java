package certificate.model;

import commonValueObjects.Id;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CertificateTest {

    @Test
    void shouldThrowExceptionWhenRewardIsNull() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Certificate(null, new Id(1), new Id (1)));
        assertEquals("Reward cannot be null", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPlayerIdInvalid() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Certificate(CertificateRewardType.BRONZE_BADGE, new Id (0), new Id (1)));
    }

    @Test
    void shouldThrowExceptionWhenRoomIdInvalid() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Certificate(CertificateRewardType.BRONZE_BADGE, new Id (1), new Id (-5)));
    }

    @Test
    void shouldUseDefaultTextFromReward() {
        Certificate cert = new Certificate(CertificateRewardType.SILVER_BADGE, new Id (10), new Id (20));

        assertEquals(
                CertificateRewardType.SILVER_BADGE.getDefaultText(),
                cert.getTextBody()
        );
    }

    @Test
    void secondConstructorShouldOverrideTextBody() {
        Certificate cert = new Certificate(
                new Id (99),
                CertificateRewardType.GOLD_TROPHY,
                new CertificateText ("Custom text"),
                new Id (11),
                new Id (22)
        );

        assertEquals(99, cert.getId().getValue());
        assertEquals("Custom text", cert.getTextBody());
        assertEquals(11, cert.getPlayerId().getValue());
        assertEquals(22, cert.getRoomId().getValue());
    }

    @Test
    void toStringShouldNotBeNull() {
        Certificate cert = new Certificate(CertificateRewardType.BRONZE_BADGE, new Id (1),new Id (1));
        assertNotNull(cert.toString());
    }
}