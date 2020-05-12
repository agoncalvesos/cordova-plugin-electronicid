package pluginCordovaElectronicId.model.entities;

public class Face {
    private String image;
    private String similarityLevel;

    public String getImage() {
        return this.image;
    }

    public void setImage(String value) {
        this.image = value;
    }

    public String getSimilarityLevel() {
        return this.similarityLevel;
    }

    public void setSimilarityLevel(String value) {
        this.similarityLevel = value;
    }
}