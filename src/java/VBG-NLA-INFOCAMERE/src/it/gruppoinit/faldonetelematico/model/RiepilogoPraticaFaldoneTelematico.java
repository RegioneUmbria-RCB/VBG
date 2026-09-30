package it.gruppoinit.faldonetelematico.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "apiFaldone", "chiave", "comuneCompilazione", "idCompilazione", "versione", "compilazioneModulo",
	"allegatiFaldoneTelematico" })
@XmlRootElement(name = "faldone_telematico")
public class RiepilogoPraticaFaldoneTelematico {

    @XmlElement(name = "api_faldone")
    String apiFaldone;
    @XmlAttribute(name = "chiave")
    String chiave;
    @XmlElement(name = "comune_compilazione")
    String comuneCompilazione;
    @XmlElement(name = "id_compilazione")
    String idCompilazione;
    @XmlAttribute(name = "versione")
    String versione;
    @XmlElement(name = "compilazione_modulo")
    List<CompilazioneModulo> compilazioneModulo;
    @XmlElement(name = "allegati")
    AllegatiFaldoneTelematico allegatiFaldoneTelematico;

    public String getApiFaldone() {

	return apiFaldone;
    }

    public void setApiFaldone(String apiFaldone) {

	this.apiFaldone = apiFaldone;
    }

    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    public String getComuneCompilazione() {

	return comuneCompilazione;
    }

    public void setComuneCompilazione(String comuneCompilazione) {

	this.comuneCompilazione = comuneCompilazione;
    }

    public String getIdCompilazione() {

	return idCompilazione;
    }

    public void setIdCompilazione(String idCompilazione) {

	this.idCompilazione = idCompilazione;
    }

    public String getVersione() {

	return versione;
    }

    public void setVersione(String versione) {

	this.versione = versione;
    }

    public List<CompilazioneModulo> getCompilazioneModulo() {

	return compilazioneModulo;
    }

    public void setCompilazioneModulo(List<CompilazioneModulo> compilazioneModulo) {

	this.compilazioneModulo = compilazioneModulo;
    }

    public AllegatiFaldoneTelematico getAllegatiFaldoneTelematico() {

	return allegatiFaldoneTelematico;
    }

    public void setAllegatiFaldoneTelematico(AllegatiFaldoneTelematico allegatiFaldoneTelematico) {

	this.allegatiFaldoneTelematico = allegatiFaldoneTelematico;
    }
}