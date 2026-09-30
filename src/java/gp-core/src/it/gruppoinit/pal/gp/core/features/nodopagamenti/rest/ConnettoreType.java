package it.gruppoinit.pal.gp.core.features.nodopagamenti.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "connettore")
@XmlSeeAlso({ CausaliType.class })
public class ConnettoreType {

    @XmlElement(name = "descrizione_connettore")
    private String descrizioneConnettore;
    @XmlElement(name = "java_class")
    private String javaClass;
    @XmlElement(name = "cf_codice_profilo")
    private String cfCodiceProfilo;
    @XmlElement(name = "causali")
    private List<CausaliType> causali;

    public ConnettoreType() {

    }

    public String getDescrizioneConnettore() {

	return descrizioneConnettore;
    }

    public void setDescrizioneConnettore(String descrizioneConnettore) {

	this.descrizioneConnettore = descrizioneConnettore;
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

    public List<CausaliType> getCausali() {

	if (causali == null) {
	    this.causali = new ArrayList<CausaliType>();
	}
	return causali;
    }

    public void setCausali(List<CausaliType> causali) {

	this.causali = causali;
    }
}
