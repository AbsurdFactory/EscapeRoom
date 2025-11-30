package certificate.service;

import certificate.dao.CertificateDaoImpl;
import certificate.model.Certificate;
import commonValueObjects.Id;
import exceptions.DataAccessException;

import java.util.List;
import java.util.Optional;

public class CertificateService {

    private final CertificateDaoImpl certificateDaoImpl;

    public CertificateService(CertificateDaoImpl certificateDaoImpl) {
        this.certificateDaoImpl = certificateDaoImpl;
    }

    public void createCertificate(Certificate certificate) {
        try {
            certificateDaoImpl.save(certificate);
        } catch (Exception e) {
            throw new DataAccessException("Error saving certificate", e);
        }
    }

    public Optional<Certificate> getCertificateById(Id id) {
        try {
            return certificateDaoImpl.findById(id);
        } catch (Exception e) {
            throw new DataAccessException("Error retrieving certificate", e);
        }
    }

    public List<Certificate> getAllCertificates() {
        try {
            return certificateDaoImpl.findAll();
        } catch (Exception e) {
            throw new DataAccessException("Error retrieving certificates", e);
        }
    }

    public boolean updateCertificate(Certificate certificate) {
        try {
            return certificateDaoImpl.update(certificate);
        } catch (Exception e) {
            throw new DataAccessException("Error updating certificate", e);
        }
    }

    public boolean deleteCertificate(Id id) {
        try {
            return certificateDaoImpl.delete(id);
        } catch (Exception e) {
            throw new DataAccessException("Error deleting certificate", e);
        }
    }
}
