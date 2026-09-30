package it.gruppoinit.pal.gp.core.features.metadati;

import javax.xml.bind.annotation.XmlElement;

public class MetadatoComune extends Metadato {

    @XmlElement(name = "codicecomune")
    private String codiceComune;
    @XmlElement(name = "comune")
    private String comune;

    public MetadatoComune() {

	// TODO Auto-generated constructor stub
    }

    public MetadatoComune(String codiceComune, String comune, String chiave, String valore) {

	this.setComune(comune);
	this.setCodiceComune(codiceComune);
	this.setChiave(chiave);
	this.setValore(valore);
    }

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
}
