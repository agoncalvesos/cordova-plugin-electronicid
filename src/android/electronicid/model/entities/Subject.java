package pluginCordovaElectronicId.model.entities;

public class Subject {
    private String photo;
    private String primaryName;
    private String secondaryName;
    private String birthDate;
    private String sex;
    private String nationality;
    private String personalNumber;
    private String address;

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String value) {
        this.photo = value;
    }

    public String getPrimaryName() {
        return this.primaryName;
    }

    public void setPrimaryName(String value) {
        this.primaryName = value;
    }

    public String getSecondaryName() {
        return this.secondaryName;
    }

    public void setSecondaryName(String value) {
        this.secondaryName = value;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(String value) {
        this.birthDate = value;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String value) {
        this.sex = value;
    }

    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String value) {
        this.nationality = value;
    }

    public String getPersonalNumber() {
        return this.personalNumber;
    }

    public void setPersonalNumber(String value) {
        this.personalNumber = value;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String value) {
        this.address = value;
    }
}