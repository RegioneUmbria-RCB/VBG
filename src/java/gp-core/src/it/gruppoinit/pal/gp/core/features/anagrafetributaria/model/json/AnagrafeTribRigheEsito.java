package it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.json;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class AnagrafeTribRigheEsito {

    @XmlElement(name = "total")
    private int total;
    @XmlElement(name = "offset")
    private int offset;
    @XmlElement(name = "limit")
    private int limit;
    @XmlElement(name = "risultati")
    private List<AnTribEsitoGruppo> risultati;

    public int getTotal() {

	return total;
    }

    public void setTotal(int total) {

	this.total = total;
    }

    public int getOffset() {

	return offset;
    }

    public void setOffset(int offset) {

	this.offset = offset;
    }

    public int getLimit() {

	return limit;
    }

    public void setLimit(int limit) {

	this.limit = limit;
    }

    public List<AnTribEsitoGruppo> getRisultati() {

	if (null == risultati) {
	    this.risultati = new ArrayList<AnTribEsitoGruppo>();
	}
	return risultati;
    }
}
