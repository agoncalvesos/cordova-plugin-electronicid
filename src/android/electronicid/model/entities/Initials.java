package pluginCordovaElectronicId.model.entities;

public class Initials {
    private String name;
    private boolean found;
    private Bbox bbox;
    private String side;

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public boolean isFound() {
        return this.found;
    }

    public void setFound(boolean value) {
        this.found = value;
    }

    public Bbox getBbox() {
        return this.bbox;
    }

    public void setBbox(Bbox value) {
        this.bbox = value;
    }

    public String getSide() {
        return this.side;
    }

    public void setSide(String value) {
        this.side = value;
    }
}