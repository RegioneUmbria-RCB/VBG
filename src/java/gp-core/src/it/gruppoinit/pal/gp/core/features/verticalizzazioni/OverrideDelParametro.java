package it.gruppoinit.pal.gp.core.features.verticalizzazioni;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.domain.AlberoprocMetadati;

@XmlRootElement
public class OverrideDelParametro {

    @XmlElement(name = "contesto")
    private String contesto;
    @XmlElement(name = "software")
    private String software;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "chiave")
    private String chiave;
    @XmlElement(name = "valore")
    private String valore;
    @XmlElement(name = "link")
    private String link;

    public String getContesto() {

	return contesto;
    }

    public void setContesto(String contesto) {

	this.contesto = contesto;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
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

    public String getLink() {

	return link;
    }

    public void setLink(String link) {

	this.link = link;
    }

    public static OverrideDelParametro fromAlberoprocMetadati(AlberoprocMetadati metadati) {

	if (metadati == null) {
	    return new OverrideDelParametro();
	}
	OverrideDelParametro result = new OverrideDelParametro();
	result.contesto = ContestoOverrideEnum.ALBEROPROC.getDescrizione();
	result.software = metadati.getAlberoproc().getSoftware().getDescrizione();
	result.descrizione = metadati.getAlberoproc().getDescrizioneCompleta();
	result.chiave = metadati.getId().getChiave();
	result.valore = metadati.getValore();
	result.link = "../alberoproc/view.htm?software=" +
		metadati.getAlberoproc().getSoftware().getCodice() +
		"&codice=" +
		metadati.getAlberoproc().getId().getCodice() +
		"#albmetadati_anchor";
	return result;
    }
}
