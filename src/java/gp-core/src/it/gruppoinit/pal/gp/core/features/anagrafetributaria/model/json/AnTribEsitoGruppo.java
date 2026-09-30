package it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.json;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class AnTribEsitoGruppo {

    @XmlElement(name = "id_esito_gruppo")
    private int idEsitoGruppo;
    @XmlElement(name = "istanze_trovate", nillable = true)
    private List<RiferimentiIstanza> istanzeTrovate;
    @XmlElement(name = "istanza", nillable = true)
    private RiferimentiIstanza istanza;
    @XmlElement(name = "errori")
    private List<AnTribErrori> errori;

    public int getIdEsitoGruppo() {

	return idEsitoGruppo;
    }

    public void setIdEsitoGruppo(int idEsitoGruppo) {

	this.idEsitoGruppo = idEsitoGruppo;
    }

    public RiferimentiIstanza getIstanza() {

	return istanza;
    }

    public void setIstanza(RiferimentiIstanza istanza) {

	this.istanza = istanza;
    }

    public List<AnTribErrori> getErrori() {

	if (null == errori) {
	    errori = new ArrayList<AnTribErrori>();
	}
	return errori;
    }

    public void setErrori(List<AnTribErrori> errori) {

	this.errori = errori;
    }

    public List<RiferimentiIstanza> getIstanzeTrovate() {

	return istanzeTrovate;
    }

    public void setIstanzeTrovate(List<RiferimentiIstanza> istanzeTrovate) {

	this.istanzeTrovate = istanzeTrovate;
    }
}
