package it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.json;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.domain.AtEsitiErroreTracciato;

@XmlRootElement()
public class AnTribDettaglioRigheTracciato {

    @XmlElement(name = "posizione")
    private int posizione;
    @XmlElement(name = "riga")
    private String riga;

    public int getPosizione() {

	return posizione;
    }

    public void setPosizione(int posizione) {

	this.posizione = posizione;
    }

    public String getRiga() {

	return riga;
    }

    public void setRiga(String riga) {

	this.riga = riga;
    }

    public static AnTribDettaglioRigheTracciato fromtAtEsitiErroreTracciato(AtEsitiErroreTracciato e) {

	AnTribDettaglioRigheTracciato ret = new AnTribDettaglioRigheTracciato();
	ret.setPosizione(e.getPosNelTracciato());
	ret.setRiga(e.getTracciatoRecord());
	return ret;
    }
}
