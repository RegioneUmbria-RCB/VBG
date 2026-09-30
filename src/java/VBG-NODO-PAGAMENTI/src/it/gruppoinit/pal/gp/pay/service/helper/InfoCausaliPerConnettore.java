package it.gruppoinit.pal.gp.pay.service.helper;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "connettore")
public class InfoCausaliPerConnettore {

    @XmlElement(name = "descrizione_connettore")
    private String descrizione;
    @XmlElement(name = "java_class")
    private String javaClass;
    @XmlElement(name = "cf_codice_profilo")
    private String cfCodiceProfilo;
    @XmlElement(name = "causali")
    private List<InfoCausaleRestBean> causali;

    private InfoCausaliPerConnettore() {

	super();
    }

    public InfoCausaliPerConnettore(CausaliConnettoreBean c) {

	this();
	this.setDescrizione(c.getDescrizioneconn());
	this.setCfCodiceProfilo(c.getCfcodiceprofilo());
	this.setJavaClass(c.getJavaclass());
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getJavaClass() {

	return javaClass;
    }

    public void setJavaClass(String javaClass) {

	this.javaClass = javaClass;
    }

    public String getCfCodiceProfilo() {

	return cfCodiceProfilo;
    }

    public void setCfCodiceProfilo(String cfCodiceProfilo) {

	this.cfCodiceProfilo = cfCodiceProfilo;
    }

    public List<InfoCausaleRestBean> getCausali() {

	if (this.causali == null) {
	    this.causali = new ArrayList<>();
	}
	return causali;
    }

    public void setCausali(List<InfoCausaleRestBean> causali) {

	this.causali = causali;
    }
}
