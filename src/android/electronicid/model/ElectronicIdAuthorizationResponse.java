package pluginCordovaElectronicId.model;

public class ElectronicIdAuthorizationResponse {
    private String id;
    private String authorization;

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getAuthorization() {
        return this.authorization;
    }

    public void setAuthorization(String value) {
        this.authorization = value;
    }
}
