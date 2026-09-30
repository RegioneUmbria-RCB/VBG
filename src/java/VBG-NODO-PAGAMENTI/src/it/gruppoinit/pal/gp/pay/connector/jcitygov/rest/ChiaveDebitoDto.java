package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ChiaveDebitoDto {

    @XmlElement
    private String codEnteCreditore;

    @XmlElement
    private String codiceTipoDebito;

    @XmlElement
    private String iDeb;

    @XmlElement
    private String iPos;

	public String getCodEnteCreditore() {
		return codEnteCreditore;
	}

	public void setCodEnteCreditore(String codEnteCreditore) {
		this.codEnteCreditore = codEnteCreditore;
	}

	public String getCodiceTipoDebito() {
		return codiceTipoDebito;
	}

	public void setCodiceTipoDebito(String codiceTipoDebito) {
		this.codiceTipoDebito = codiceTipoDebito;
	}

	public String getiDeb() {
		return iDeb;
	}

	public void setiDeb(String iDeb) {
		this.iDeb = iDeb;
	}

	public String getiPos() {
		return iPos;
	}

	public void setiPos(String iPos) {
		this.iPos = iPos;
	}

    
}
