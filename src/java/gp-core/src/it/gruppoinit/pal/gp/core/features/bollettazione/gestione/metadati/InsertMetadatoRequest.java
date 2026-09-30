package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class InsertMetadatoRequest {

    @XmlElement(name = "codicecomune")
    private String codiceComune;
    @XmlElement(name = "idcfgtipo")
    private Integer idCfgTipo;
    @XmlElement(name = "chiave")
    private String chiave;
    @XmlElement(name = "valore")
    private String valore;

    public InsertMetadatoRequest() {

    }

    public InsertMetadatoRequest(String codiceComune, Integer idCfgTipo, String chiave, String valore) {

	this.codiceComune = codiceComune;
	this.idCfgTipo = idCfgTipo;
	this.chiave = chiave;
	this.valore = valore;
    }

    public String getCodiceComune() {

	return codiceComune;
    }

    public Integer getIdCfgTipo() {

	return idCfgTipo;
    }

    public String getChiave() {

	return chiave;
    }

    public String getValore() {

	return valore;
    }
}
