package certificate.model;

public record CertificateText(String text) {


    public CertificateText {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Text cannot be empty");
        }

        if (text.length() > 250) {
            throw new IllegalArgumentException("Text cannot be superior to 250 characters");
        }
    }

    @Override
    public String toString() {
        return text;
    }
}


