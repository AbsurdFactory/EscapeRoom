package certificate.service;

import certificate.dao.CertificateDao;
import certificate.model.Certificate;
import certificate.model.CertificateRewardType;
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

// Extend with MockitoExtension to use @Mock and @InjectMocks
@ExtendWith(MockitoExtension.class)
class CertificateServiceTest {

    // Mocked DAO, simulating the database
    @Mock
    private CertificateDao dao;

    // Service under test; DAO will be injected automatically
    @InjectMocks
    private CertificateService service;

    private Certificate sampleCertificate;

    @BeforeEach
    void setup() {
        // Create a sample certificate to use in tests
        sampleCertificate = new Certificate(CertificateRewardType.BRONZE_BADGE, 1, 1);
    }

    // ===== Test 1: Creating a certificate =====
    @Test
    void createCertificateShouldCallDaoSave() {
        // Call the service method
        service.createCertificate(sampleCertificate);

        // Verify that DAO.save() was called exactly once with our certificate
        verify(dao).save(sampleCertificate);
    }

    @Test
    void createCertificateShouldWrapDaoException() {
        // Configure DAO mock to throw an exception when save() is called
        doThrow(new RuntimeException("DB error")).when(dao).save(sampleCertificate);

        // Assert that the service wraps the exception into a DataAccessException
        assertThrows(DataAccessException.class, () -> service.createCertificate(sampleCertificate));
    }

    // ===== Test 2: Retrieving all certificates =====
    @Test
    void getAllCertificatesShouldReturnList() {
        // Configure mock to return a list of certificates
        when(dao.findAll()).thenReturn(List.of(sampleCertificate));

        List<Certificate> result = service.getAllCertificates();

        // Assert that the returned list contains our certificate
        assertEquals(1, result.size());
        assertEquals(sampleCertificate, result.get(0));
    }

    @Test
    void getAllCertificatesShouldWrapDaoException() {
        // Configure DAO mock to throw an exception
        when(dao.findAll()).thenThrow(new RuntimeException("DB error"));

        // Assert that the service wraps the exception
        assertThrows(DataAccessException.class, () -> service.getAllCertificates());
    }

    // ===== Optional: retrieving by ID =====
    @Test
    void getCertificateByIdShouldReturnCertificate() {
        when(dao.findById(1)).thenReturn(Optional.of(sampleCertificate));

        Optional<Certificate> result = service.getCertificateById(1);

        assertTrue(result.isPresent());
        assertEquals(sampleCertificate, result.get());
    }
}
