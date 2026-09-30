package it.gruppoinit.dss.ws.legacy;

public class WSQCStatementInformation {

    private String qcPPresent;
    private String qcPPlusPresent;
    private String qcCompliancePresent;
    private String qcSCCDPresent;
    private String qcLimitValue;
    private String qcRetentionPeriod;

    public WSQCStatementInformation() {}

    public String getQcPPresent() { return qcPPresent; }
    public void setQcPPresent(String qcPPresent) { this.qcPPresent = qcPPresent; }

    public String getQcPPlusPresent() { return qcPPlusPresent; }
    public void setQcPPlusPresent(String qcPPlusPresent) { this.qcPPlusPresent = qcPPlusPresent; }

    public String getQcCompliancePresent() { return qcCompliancePresent; }
    public void setQcCompliancePresent(String qcCompliancePresent) { this.qcCompliancePresent = qcCompliancePresent; }

    public String getQcSCCDPresent() { return qcSCCDPresent; }
    public void setQcSCCDPresent(String qcSCCDPresent) { this.qcSCCDPresent = qcSCCDPresent; }

    public String getQcLimitValue() { return qcLimitValue; }
    public void setQcLimitValue(String qcLimitValue) { this.qcLimitValue = qcLimitValue; }

    public String getQcRetentionPeriod() { return qcRetentionPeriod; }
    public void setQcRetentionPeriod(String qcRetentionPeriod) { this.qcRetentionPeriod = qcRetentionPeriod; }
}
