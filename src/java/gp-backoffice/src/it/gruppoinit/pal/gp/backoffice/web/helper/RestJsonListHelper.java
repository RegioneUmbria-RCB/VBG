package it.gruppoinit.pal.gp.backoffice.web.helper;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RestJsonListHelper<T> {

    @XmlElement
    private List<T> dati;

    public List<T> getDati() {

	return dati;
    }

    public void setDati(List<T> dati) {

	this.dati = dati;
    }
}
