package it.gruppoinit.faldonetelematico.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "compilazione_modulo", propOrder = { "dataCompilazione", "dataInvio", "oggetto", "utente", "versioneModulo", "codiceModulo",
	"urnModulo", "pagoPA", "valoriCampo" })
public class CompilazioneModulo {

    @XmlElement(name = "data_compilazione")
    Date dataCompilazione;
    @XmlElement(name = "data_invio")
    Date dataInvio;
    @XmlElement(name = "oggetto")
    String oggetto;
    @XmlElement(name = "utente")
    String utente;
    @XmlElement(name = "versionemodulo")
    String versioneModulo;
    @XmlElement(name = "codice_modulo")
    CodiceModulo codiceModulo;
    @XmlElement(name = "urn_modulo")
    UrnModulo urnModulo;
    @XmlElement(name = "pagoPA")
    PagoPA pagoPA;
    @XmlElement(name = "valori_campo")
    ValoriCampo valoriCampo;

    public Date getDataCompilazione() {

	return dataCompilazione;
    }

    public void setDataCompilazione(Date dataCompilazione) {

	this.dataCompilazione = dataCompilazione;
    }

    public Date getDataInvio() {

	return dataInvio;
    }

    public void setDataInvio(Date dataInvio) {

	this.dataInvio = dataInvio;
    }

    public String getOggetto() {

	return oggetto;
    }

    public void setOggetto(String oggetto) {

	this.oggetto = oggetto;
    }

    public String getUtente() {

	return utente;
    }

    public void setUtente(String utente) {

	this.utente = utente;
    }

    public String getVersioneModulo() {

	return versioneModulo;
    }

    public void setVersioneModulo(String versioneModulo) {

	this.versioneModulo = versioneModulo;
    }

    public CodiceModulo getCodiceModulo() {

	return codiceModulo;
    }

    public void setCodiceModulo(CodiceModulo codiceModulo) {

	this.codiceModulo = codiceModulo;
    }

    public UrnModulo getUrnModulo() {

	return urnModulo;
    }

    public void setUrnModulo(UrnModulo urnModulo) {

	this.urnModulo = urnModulo;
    }

    public PagoPA getPagoPA() {

	return pagoPA;
    }

    public void setPagoPA(PagoPA pagoPA) {

	this.pagoPA = pagoPA;
    }

    public ValoriCampo getValoriCampo() {

	return valoriCampo;
    }

    public void setValoriCampo(ValoriCampo valoriCampo) {

	this.valoriCampo = valoriCampo;
    }
}