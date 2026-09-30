package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AppIoServiziConfigParamRestResponse {

    @XmlElement(name = "codiceComune")
    private String codiceComune;
    @XmlElement(name = "identificativoServizio")
    private String identificativoServizio;
    @XmlElement(name = "precIdentificativoServizio")
    private String precIdentificativoServizio;
    @XmlElement(name = "parametro")
    private String parametro;
    @XmlElement(name = "valore")
    private String valore;

    public String getCodiceComune() {

	return codiceComune;
    }

    public void setCodiceComune(String codiceComune) {

	this.codiceComune = codiceComune;
    }

    public String getIdentificativoServizio() {

	return identificativoServizio;
    }

    public void setIdentificativoServizio(String identificativoServizio) {

	this.identificativoServizio = identificativoServizio;
    }

    public String getPrecIdentificativoServizio() {

	return precIdentificativoServizio;
    }

    public void setPrecIdentificativoServizio(String precIdentificativoServizio) {

	this.precIdentificativoServizio = precIdentificativoServizio;
    }

    public String getParametro() {

	return parametro;
    }

    public void setParametro(String parametro) {

	this.parametro = parametro;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }
}
