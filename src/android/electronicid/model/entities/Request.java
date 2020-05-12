package pluginCordovaElectronicId.model.entities;

public class Request {
    private float date;
    private String minSimilarityLevel;

    public float getDate() {
        return this.date;
    }

    public void setDate(float value) {
        this.date = value;
    }

    public String getMinSimilarityLevel() {
        return this.minSimilarityLevel;
    }

    public void setMinSimilarityLevel(String value) {
        this.minSimilarityLevel = value;
    }
}
