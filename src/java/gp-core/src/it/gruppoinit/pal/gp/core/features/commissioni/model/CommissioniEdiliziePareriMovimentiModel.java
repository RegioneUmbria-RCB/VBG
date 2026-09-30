package it.gruppoinit.pal.gp.core.features.commissioni.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class CommissioniEdiliziePareriMovimentiModel {

    @XmlElement(name = "codice_tipologia_parere")
    private Integer codiceTipologiaParere;
    @XmlElement(name = "codice_software")
    private String codicesoftware;
    @XmlElement()
    private String software;
    @XmlElement(name = "tipo_movimento")
    private String tipoMovimento;
    @XmlElement(name = "descrizione_movimento")
    private String descrizioneMovimento;

    public Integer getCodiceTipologiaParere() {

	return codiceTipologiaParere;
    }

    public void setCodiceTipologiaParere(Integer codiceTipologiaParere) {

	this.codiceTipologiaParere = codiceTipologiaParere;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getTipoMovimento() {

	return tipoMovimento;
    }

    public void setTipoMovimento(String tipoMovimento) {

	this.tipoMovimento = tipoMovimento;
    }

    public String getDescrizioneMovimento() {

	return descrizioneMovimento;
    }

    public void setDescrizioneMovimento(String descrizioneMovimento) {

	this.descrizioneMovimento = descrizioneMovimento;
    }
}
