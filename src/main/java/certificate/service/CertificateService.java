package certificate.service;

import certificate.dao.CertificateDao;
import certificate.model.Certificate;
import commonValueObjects.Id;
import exceptions.DataAccessException;

import java.util.List;
import java.util.Optional;

public class CertificateService {

    private final CertificateDao certificateDao;

    public CertificateService(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    public void createCertificate(Certificate certificate) {
        try {
            certificateDao.save(certificate);
        } catch (Exception e) {
            throw new DataAccessException("Error saving certificate", e);
        }
    }

    public Optional<Certificate> getCertificateById(Id id) {
        try {
            return certificateDao.findById(id);
        } catch (Exception e) {
            throw new DataAccessException("Error retrieving certificate", e);
        }
    }

    public List<Certificate> getAllCertificates() {
        try {
            return certificateDao.findAll();
        } catch (Exception e) {
            throw new DataAccessException("Error retrieving certificates", e);
        }
    }

    public boolean updateCertificate(Certificate certificate) {
        try {
            return certificateDao.update(certificate);
        } catch (Exception e) {
            throw new DataAccessException("Error updating certificate", e);
        }
    }

    public boolean deleteCertificate(Id id) {
        try {
            return certificateDao.delete(id);
        } catch (Exception e) {
            throw new DataAccessException("Error deleting certificate", e);
        }
    }
}
