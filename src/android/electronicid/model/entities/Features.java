package pluginCordovaElectronicId.model.entities;

public class Features {
    private Chip chip;
    private Flag flag;
    private Signature signature;
    private Initials initials;
    private Hologram hologram;
    private Photo photo;
    private Badge1 badge1;
    private EuroBadge euroBadge;

    public Chip getChip() {
        return this.chip;
    }

    public void setChip(Chip value) {
        this.chip = value;
    }

    public Flag getFlag() {
        return this.flag;
    }

    public void setFlag(Flag value) {
        this.flag = value;
    }

    public Signature getSignature() {
        return this.signature;
    }

    public void setSignature(Signature value) {
        this.signature = value;
    }

    public Initials getInitials() {
        return this.initials;
    }

    public void setInitials(Initials value) {
        this.initials = value;
    }

    public Hologram getHologram() {
        return this.hologram;
    }

    public void setHologram(Hologram value) {
        this.hologram = value;
    }

    public Photo getPhoto() {
        return this.photo;
    }

    public void setPhoto(Photo value) {
        this.photo = value;
    }

    public Badge1 getBadge1() {
        return this.badge1;
    }

    public void setBadge1(Badge1 value) {
        this.badge1 = value;
    }

    public EuroBadge getEuroBadge() {
        return this.euroBadge;
    }

    public void setEuroBadge(EuroBadge value) {
        this.euroBadge = value;
    }
}