package it.gruppoinit.dss.ws.legacy;

public class WSSignatureLevelEPES {

    private String levelReached;
    private String policyValue;

    public WSSignatureLevelEPES() {}

    public String getLevelReached() { return levelReached; }
    public void setLevelReached(String levelReached) { this.levelReached = levelReached; }

    public String getPolicyValue() { return policyValue; }
    public void setPolicyValue(String policyValue) { this.policyValue = policyValue; }
}
