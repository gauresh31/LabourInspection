package in.mol.models;

public class SpinnerObject {
    public SpinnerObject(String actId, String actName) {
        this.ruleId = actId;
        this.ruleName = actName;
    }

    public String ruleId;
    public String ruleName;
    public boolean selected;

    public SpinnerObject() {

    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String actId) {
        this.ruleId = actId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String actName) {
        this.ruleName = actName;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return ruleName;
    }
}
