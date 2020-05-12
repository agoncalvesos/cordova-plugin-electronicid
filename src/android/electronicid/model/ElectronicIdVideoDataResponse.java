package pluginCordovaElectronicId.model;

import pluginCordovaElectronicId.model.entities.Biometrics;
import pluginCordovaElectronicId.model.entities.Completion;
import pluginCordovaElectronicId.model.entities.Document;
import pluginCordovaElectronicId.model.entities.Features;
import pluginCordovaElectronicId.model.entities.Request;
import pluginCordovaElectronicId.model.entities.SecurityChecks;

public class ElectronicIdVideoDataResponse {
    private String id;
    private String process;
    private String status;
    private String tenantId;
    private Request request;
    private Completion completion;
    private Document document;
    private Biometrics biometrics;
    private Features features;
    private SecurityChecks securityChecks;

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getProcess() {
        return this.process;
    }

    public void setProcess(String value) {
        this.process = value;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String value) {
        this.tenantId = value;
    }

    public Request getRequest() {
        return this.request;
    }

    public void setRequest(Request value) {
        this.request = value;
    }

    public Completion getCompletion() {
        return this.completion;
    }

    public void setCompletion(Completion value) {
        this.completion = value;
    }

    public Document getDocument() {
        return this.document;
    }

    public void setDocument(Document value) {
        this.document = value;
    }

    public Biometrics getBiometrics() {
        return this.biometrics;
    }

    public void setBiometrics(Biometrics value) {
        this.biometrics = value;
    }

    public Features getFeatures() {
        return this.features;
    }

    public void setFeatures(Features value) {
        this.features = value;
    }

    public SecurityChecks getSecurityChecks() {
        return this.securityChecks;
    }

    public void setSecurityChecks(SecurityChecks value) {
        this.securityChecks = value;
    }
}
