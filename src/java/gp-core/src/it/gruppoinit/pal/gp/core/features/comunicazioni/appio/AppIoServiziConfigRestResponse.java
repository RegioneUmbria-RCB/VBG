package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dettaglio")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppIoServiziConfigRestResponse {

    @XmlElement(name = "codiceComune")
    private String codiceComune;
    @XmlElement(name = "comune")
    private String comune;
    @XmlElement(name = "identificativoServizio")
    private String identificativoServizio;
    @XmlElement(name = "ambito")
    private String ambito;
    @XmlElement(name = "maxNumMessaggio")
    private String maxNumMessaggio;
    @XmlElement(name = "messaggio")
    private String messaggio;
    @XmlElement(name = "oggettoMesaggio")
    private String oggettoMesaggio;
    @XmlElement(name = "attivo")
    private boolean attivo;
    @XmlElement(name = "software")
    private String software;
    @XmlElement(name = "parametri")
    private List<AppIoParamRestResponse> parametri;

    public String getCodiceComune() {

	return codiceComune;
    }

    public void setCodiceComune(String codiceComune) {

	this.codiceComune = codiceComune;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public String getIdentificativoServizio() {

	return identificativoServizio;
    }

    public void setIdentificativoServizio(String identificativoServizio) {

	this.identificativoServizio = identificativoServizio;
    }

    public String getAmbito() {

	return ambito;
    }

    public void setAmbito(String ambito) {

	this.ambito = ambito;
    }

    public String getMaxNumMessaggio() {

	return maxNumMessaggio;
    }

    public void setMaxNumMessaggio(String maxNumMessaggio) {

	this.maxNumMessaggio = maxNumMessaggio;
    }

    public String getMessaggio() {

	return messaggio;
    }

    public void setMessaggio(String messaggio) {

	this.messaggio = messaggio;
    }

    public String getOggettoMesaggio() {

	return oggettoMesaggio;
    }

    public void setOggettoMesaggio(String oggettoMesaggio) {

	this.oggettoMesaggio = oggettoMesaggio;
    }

    public boolean isAttivo() {

	return attivo;
    }

    public void setAttivo(boolean attivo) {

	this.attivo = attivo;
    }

    public List<AppIoParamRestResponse> getParametri() {

	return parametri;
    }

    public void setParametri(List<AppIoParamRestResponse> parametri) {

	this.parametri = parametri;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }
}
