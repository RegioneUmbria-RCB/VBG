package it.gruppoinit.dss.ws.legacy;

public class WSQualificationsVerification {

    private String qcWithSSCD;
    private String qcNoSSCD;
    private String qcSSCDStatusAsInCert;
    private String qcForLegalPerson;

    public WSQualificationsVerification() {}

    public String getQcWithSSCD() { return qcWithSSCD; }
    public void setQcWithSSCD(String qcWithSSCD) { this.qcWithSSCD = qcWithSSCD; }

    public String getQcNoSSCD() { return qcNoSSCD; }
    public void setQcNoSSCD(String qcNoSSCD) { this.qcNoSSCD = qcNoSSCD; }

    public String getQcSSCDStatusAsInCert() { return qcSSCDStatusAsInCert; }
    public void setQcSSCDStatusAsInCert(String qcSSCDStatusAsInCert) { this.qcSSCDStatusAsInCert = qcSSCDStatusAsInCert; }

    public String getQcForLegalPerson() { return qcForLegalPerson; }
    public void setQcForLegalPerson(String qcForLegalPerson) { this.qcForLegalPerson = qcForLegalPerson; }
}
