package pluginCordovaElectronicId.model.entities;

public class Document {
    private String classification;
    private String type;
    private String issuer;
    private String expiryDate;
    private String subject;
    private String documentNumber;
    private String front;
    private String back;

    public String getClassification() {
        return this.classification;
    }

    public void setClassification(String value) {
        this.classification = value;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public String getIssuer() {
        return this.issuer;
    }

    public void setIssuer(String value) {
        this.issuer = value;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(String value) {
        this.expiryDate = value;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String value) {
        this.subject = value;
    }

    public String getDocumentNumber() {
        return this.documentNumber;
    }

    public void setDocumentNumber(String value) {
        this.documentNumber = value;
    }

    public String getFront() {
        return this.front;
    }

    public void setFront(String value) {
        this.front = value;
    }

    public String getBack() {
        return this.back;
    }

    public void setBack(String value) {
        this.back = value;
    }
}
