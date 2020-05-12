package pluginCordovaElectronicId.model.entities;

public class InitialsMatch {
    private String name;
    private boolean passed;

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public boolean isPassed() {
        return this.passed;
    }

    public void setPassed(boolean value) {
        this.passed = value;
    }
}