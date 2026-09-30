package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BackUrlDto {
	@XmlElement
    private String backUrlCancel;
	@XmlElement
    private String backUrlKO;
	@XmlElement
    private String backUrlNotify;
	@XmlElement
    private String backUrlOK;

    // Getters and Setters
    public String getBackUrlCancel() { return backUrlCancel; }
    public void setBackUrlCancel(String backUrlCancel) { this.backUrlCancel = backUrlCancel; }

    public String getBackUrlKO() { return backUrlKO; }
    public void setBackUrlKO(String backUrlKO) { this.backUrlKO = backUrlKO; }

    public String getBackUrlNotify() { return backUrlNotify; }
    public void setBackUrlNotify(String backUrlNotify) { this.backUrlNotify = backUrlNotify; }

    public String getBackUrlOK() { return backUrlOK; }
    public void setBackUrlOK(String backUrlOK) { this.backUrlOK = backUrlOK; }
}
