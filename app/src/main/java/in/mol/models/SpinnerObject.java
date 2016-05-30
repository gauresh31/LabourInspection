package in.mol.models;

public class SpinnerObject {
    public SpinnerObject(String actId, String actName) {
        this.actId = actId;
        this.actName = actName;
    }

    public String actId;
    public String actName;

    public SpinnerObject() {

    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    @Override
    public String toString() {
        return actName;
    }
}