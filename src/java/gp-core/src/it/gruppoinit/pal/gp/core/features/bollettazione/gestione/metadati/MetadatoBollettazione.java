package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.features.metadati.MetadatoComune;

@XmlRootElement(name = "metadato")
@XmlAccessorType(XmlAccessType.FIELD)
public class MetadatoBollettazione {

    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "codicecomune")
    private String codiceComune;
    @XmlElement(name = "comune")
    private String comune;
    @XmlElement(name = "chiave")
    private String chiave;
    @XmlElement(name = "valore")
    private String valore;

    public MetadatoBollettazione() {

    }

    public MetadatoBollettazione(Integer id, String codiceComune, String comune, String chiave, String valore) {

	this.setId(id);
	this.setComune(comune);
	this.setCodiceComune(codiceComune);
	this.setChiave(chiave);
	this.setValore(valore);
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
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

    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    public static MetadatoBollettazione fromMetadatoComune(MetadatoComune specifico) {

	if (specifico == null) {
	    return null;
	}
	return new MetadatoBollettazione(null, specifico.getCodiceComune(), specifico.getComune(), specifico.getChiave(), specifico.getValore());
    }

    @Override
    public boolean equals(Object obj) {

	if (obj == null) {
	    return false;
	}
	MetadatoBollettazione c = (MetadatoBollettazione) obj;
	if (this.getChiave() == null && c.getChiave() != null) {
	    return false;
	}
	if (this.getChiave() != null && !this.getChiave().equalsIgnoreCase(c.getChiave())) {
	    return false;
	}
	if (this.getCodiceComune() == null && c.getCodiceComune() != null) {
	    return false;
	}
	if (this.getCodiceComune() != null && !this.getCodiceComune().equalsIgnoreCase(c.getCodiceComune())) {
	    return false;
	}
	return true;
    }
}
