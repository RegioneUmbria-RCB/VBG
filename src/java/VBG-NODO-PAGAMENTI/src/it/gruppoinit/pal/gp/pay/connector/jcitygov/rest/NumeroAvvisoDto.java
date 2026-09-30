package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NumeroAvvisoDto {

    @XmlElement
    private Boolean flagAttivaDebito;

    @XmlElement
    private String numeroAvviso;

    @XmlElement
    private Integer versioneNumeroAvviso;

	public Boolean getFlagAttivaDebito() {
		return flagAttivaDebito;
	}

	public void setFlagAttivaDebito(Boolean flagAttivaDebito) {
		this.flagAttivaDebito = flagAttivaDebito;
	}

	public String getNumeroAvviso() {
		return numeroAvviso;
	}

	public void setNumeroAvviso(String numeroAvviso) {
		this.numeroAvviso = numeroAvviso;
	}

	public Integer getVersioneNumeroAvviso() {
		return versioneNumeroAvviso;
	}

	public void setVersioneNumeroAvviso(Integer versioneNumeroAvviso) {
		this.versioneNumeroAvviso = versioneNumeroAvviso;
	}

    
}
