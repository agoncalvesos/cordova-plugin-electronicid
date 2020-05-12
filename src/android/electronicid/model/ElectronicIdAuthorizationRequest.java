package pluginCordovaElectronicId.model;

public class ElectronicIdAuthorizationRequest {
    private String tenantId;
    private String process;
    private String rauthorityId;

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String value) {
        this.tenantId = value;
    }

    public String getProcess() {
        return this.process;
    }

    public void setProcess(String value) {
        this.process = value;
    }

    public String getRauthorityId() {
        return this.rauthorityId;
    }

    public void setRauthorityId(String value) {
        this.rauthorityId = value;
    }
}
