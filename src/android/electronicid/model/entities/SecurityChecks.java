package pluginCordovaElectronicId.model.entities;

public class SecurityChecks {
    private InitialsMatch initialsMatch;
    private NonExpired nonExpired;
    private NotUnderage notUnderage;
    private DataIntegrity dataIntegrity;
    private Liveness liveness;
    private NotBWCopy notBWCopy;
    private SidesMatch sidesMatch;

    public InitialsMatch getInitialsMatch() {
        return this.initialsMatch;
    }

    public void setInitialsMatch(InitialsMatch value) {
        this.initialsMatch = value;
    }

    public NonExpired getNonExpired() {
        return this.nonExpired;
    }

    public void setNonExpired(NonExpired value) {
        this.nonExpired = value;
    }

    public NotUnderage getNotUnderage() {
        return this.notUnderage;
    }

    public void setNotUnderage(NotUnderage value) {
        this.notUnderage = value;
    }

    public DataIntegrity getDataIntegrity() {
        return this.dataIntegrity;
    }

    public void setDataIntegrity(DataIntegrity value) {
        this.dataIntegrity = value;
    }

    public Liveness getLiveness() {
        return this.liveness;
    }

    public void setLiveness(Liveness value) {
        this.liveness = value;
    }

    public NotBWCopy getNotBWCopy() {
        return this.notBWCopy;
    }

    public void setNotBWCopy(NotBWCopy value) {
        this.notBWCopy = value;
    }

    public SidesMatch getSidesMatch() {
        return this.sidesMatch;
    }

    public void setSidesMatch(SidesMatch value) {
        this.sidesMatch = value;
    }
}