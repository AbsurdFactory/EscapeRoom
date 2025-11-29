package certificate.service;

import certificate.dao.CertificateDao;
import certificate.model.Certificate;
import certificate.model.CertificateRewardType;
import commonValueObjects.Id;
import exceptions.DataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class CertificateServiceTest {

    @Mock
    private CertificateDao dao;

    @InjectMocks
    private CertificateService service;

    private Certificate sampleCertificate;

    @BeforeEach
    void setup() {
        sampleCertificate = new Certificate(CertificateRewardType.BRONZE_BADGE, 1, 1);
    }

    @Test
    void createCertificateShouldCallDaoSave() {
        service.createCertificate(sampleCertificate);

        verify(dao).save(sampleCertificate);
    }

    @Test
    void createCertificateShouldWrapDaoException() {
        doThrow(new RuntimeException("DB error")).when(dao).save(sampleCertificate);

        assertThrows(DataAccessException.class, () -> service.createCertificate(sampleCertificate));
    }

    @Test
    void getAllCertificatesShouldReturnList() {
        when(dao.findAll()).thenReturn(List.of(sampleCertificate));

        List<Certificate> result = service.getAllCertificates();

        assertEquals(1, result.size());
        assertEquals(sampleCertificate, result.get(0));
    }

    @Test
    void getAllCertificatesShouldWrapDaoException() {
        when(dao.findAll()).thenThrow(new RuntimeException("DB error"));

        assertThrows(DataAccessException.class, () -> service.getAllCertificates());
    }

    @Test
    void getCertificateByIdShouldReturnCertificate() {
        when(dao.findById(new Id<>(1))).thenReturn(Optional.of(sampleCertificate));

        Optional<Certificate> result = service.getCertificateById(new Id<>(1));

        assertTrue(result.isPresent());
        assertEquals(sampleCertificate, result.get());
    }
}
