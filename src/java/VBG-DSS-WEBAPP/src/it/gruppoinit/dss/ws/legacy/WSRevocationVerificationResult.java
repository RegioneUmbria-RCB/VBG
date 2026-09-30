package it.gruppoinit.dss.ws.legacy;

import java.util.Date;

public class WSRevocationVerificationResult {

    private String status;
    private Date revocationDate;
    private String issuer;
    private Date issuingTime;

    public WSRevocationVerificationResult() {}

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getRevocationDate() { return revocationDate; }
    public void setRevocationDate(Date revocationDate) { this.revocationDate = revocationDate; }

    public String getIssuer() { return issuer; }
    public void setIssuer(String issuer) { this.issuer = issuer; }

    public Date getIssuingTime() { return issuingTime; }
    public void setIssuingTime(Date issuingTime) { this.issuingTime = issuingTime; }
}
