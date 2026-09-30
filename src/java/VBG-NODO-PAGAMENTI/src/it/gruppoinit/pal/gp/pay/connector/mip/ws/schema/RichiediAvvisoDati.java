/*
 * MIP API - WS Conversion Libreria API del Modulo Incassi e Pagamenti del Comune di Genova per operazioni su Avvisi di
 * Pagamento
 *
 * OpenAPI spec version: 0.0.94 Contact: helpservizionline@comune.genova.it
 *
 */
package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * RichiediAvvisoDati
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RichiediAvvisoDati", propOrder = { "idEnte", "urLNotifica", "urLNotificaRevoca", "urLAttualizzazione", "flagPDFRT",
	"flagPDFAvvisatura", "flagEmailAvviso", "datiEnte", "datiDebitore", "datiVersamento" })
public class RichiediAvvisoDati {

    @XmlElement(name = "IDEnte")
    private String idEnte = null;
    @XmlElement(name = "URLNotifica")
    private String urLNotifica = null;
    @XmlElement(name = "URLNotificaRevoca")
    private String urLNotificaRevoca = null;
    @XmlElement(name = "URLAttualizzazione")
    private String urLAttualizzazione = null;
    @XmlElement(name = "FlagPDFRT")
    private String flagPDFRT = null;
    @XmlElement(name = "FlagPDFAvvisatura")
    private String flagPDFAvvisatura = null;
    @XmlElement(name = "FlagEmailAvviso")
    private String flagEmailAvviso = null;
    @XmlElement(name = "DatiEnte")
    private Object datiEnte = null;
    @XmlElement(name = "DatiDebitore")
    private DatiDebitore datiDebitore = null;
    @XmlElement(name = "DatiVersamento")
    private DatiVersamento datiVersamento = null;

    public RichiediAvvisoDati idEnte(String idEnte) {

	this.idEnte = idEnte;
	return this;
    }

    /**
     * Codice identificativo dell&#x27;Ente - per Comune di Genova inserire 10025
     * 
     * @return idEnte
     **/
    public String getIdEnte() {

	return idEnte;
    }

    public void setIdEnte(String idEnte) {

	this.idEnte = idEnte;
    }

    public RichiediAvvisoDati urLNotifica(String urLNotifica) {

	this.urLNotifica = urLNotifica;
	return this;
    }

    /**
     * Facoltativo - URL a cui inviare notifica del pagamento
     * 
     * @return urLNotifica
     **/
    public String getUrLNotifica() {

	return urLNotifica;
    }

    public void setUrLNotifica(String urLNotifica) {

	this.urLNotifica = urLNotifica;
    }

    public RichiediAvvisoDati urLNotificaRevoca(String urLNotificaRevoca) {

	this.urLNotificaRevoca = urLNotificaRevoca;
	return this;
    }

    /**
     * Facoltativo - URL a cui inviare la richiesta di revoca
     * 
     * @return urLNotificaRevoca
     **/
    public String getUrLNotificaRevoca() {

	return urLNotificaRevoca;
    }

    public void setUrLNotificaRevoca(String urLNotificaRevoca) {

	this.urLNotificaRevoca = urLNotificaRevoca;
    }

    public RichiediAvvisoDati urLAttualizzazione(String urLAttualizzazione) {

	this.urLAttualizzazione = urLAttualizzazione;
	return this;
    }

    /**
     * Facoltativo - URL a cui richiedere l&#x27;attualizzazione dell&#x27;importo
     * 
     * @return urLAttualizzazione
     **/
    public String getUrLAttualizzazione() {

	return urLAttualizzazione;
    }

    public void setUrLAttualizzazione(String urLAttualizzazione) {

	this.urLAttualizzazione = urLAttualizzazione;
    }

    public RichiediAvvisoDati flagPDFRT(String flagPDFRT) {

	this.flagPDFRT = flagPDFRT;
	return this;
    }

    /**
     * Get flagPDFRT
     * 
     * @return flagPDFRT
     **/
    public String getFlagPDFRT() {

	return flagPDFRT;
    }

    public void setFlagPDFRT(String flagPDFRT) {

	this.flagPDFRT = flagPDFRT;
    }

    public RichiediAvvisoDati flagPDFAvvisatura(String flagPDFAvvisatura) {

	this.flagPDFAvvisatura = flagPDFAvvisatura;
	return this;
    }

    /**
     * Get flagPDFAvvisatura
     * 
     * @return flagPDFAvvisatura
     **/
    public String getFlagPDFAvvisatura() {

	return flagPDFAvvisatura;
    }

    public void setFlagPDFAvvisatura(String flagPDFAvvisatura) {

	this.flagPDFAvvisatura = flagPDFAvvisatura;
    }

    public RichiediAvvisoDati flagEmailAvviso(String flagEmailAvviso) {

	this.flagEmailAvviso = flagEmailAvviso;
	return this;
    }

    /**
     * Get flagEmailAvviso
     * 
     * @return flagEmailAvviso
     **/
    public String getFlagEmailAvviso() {

	return flagEmailAvviso;
    }

    public void setFlagEmailAvviso(String flagEmailAvviso) {

	this.flagEmailAvviso = flagEmailAvviso;
    }

    public RichiediAvvisoDati datiEnte(Object datiEnte) {

	this.datiEnte = datiEnte;
	return this;
    }

    /**
     * Get datiEnte
     * 
     * @return datiEnte
     **/
    public Object getDatiEnte() {

	return datiEnte;
    }

    public void setDatiEnte(Object datiEnte) {

	this.datiEnte = datiEnte;
    }

    public RichiediAvvisoDati datiDebitore(DatiDebitore datiDebitore) {

	this.datiDebitore = datiDebitore;
	return this;
    }

    /**
     * Get datiDebitore
     * 
     * @return datiDebitore
     **/
    public DatiDebitore getDatiDebitore() {

	return datiDebitore;
    }

    public void setDatiDebitore(DatiDebitore datiDebitore) {

	this.datiDebitore = datiDebitore;
    }

    public RichiediAvvisoDati datiVersamento(DatiVersamento datiVersamento) {

	this.datiVersamento = datiVersamento;
	return this;
    }

    /**
     * Get datiVersamento
     * 
     * @return datiVersamento
     **/
    public DatiVersamento getDatiVersamento() {

	return datiVersamento;
    }

    public void setDatiVersamento(DatiVersamento datiVersamento) {

	this.datiVersamento = datiVersamento;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	RichiediAvvisoDati richiediAvvisoDati = (RichiediAvvisoDati) o;
	return Objects.equals(this.idEnte, richiediAvvisoDati.idEnte) && Objects.equals(this.urLNotifica, richiediAvvisoDati.urLNotifica)
		&& Objects.equals(this.urLNotificaRevoca, richiediAvvisoDati.urLNotificaRevoca)
		&& Objects.equals(this.urLAttualizzazione, richiediAvvisoDati.urLAttualizzazione)
		&& Objects.equals(this.flagPDFRT, richiediAvvisoDati.flagPDFRT)
		&& Objects.equals(this.flagPDFAvvisatura, richiediAvvisoDati.flagPDFAvvisatura)
		&& Objects.equals(this.flagEmailAvviso, richiediAvvisoDati.flagEmailAvviso)
		&& Objects.equals(this.datiEnte, richiediAvvisoDati.datiEnte) && Objects.equals(this.datiDebitore, richiediAvvisoDati.datiDebitore)
		&& Objects.equals(this.datiVersamento, richiediAvvisoDati.datiVersamento);
    }

    @Override
    public int hashCode() {

	return Objects.hash(idEnte, urLNotifica, urLNotificaRevoca, urLAttualizzazione, flagPDFRT, flagPDFAvvisatura, flagEmailAvviso, datiEnte,
		datiDebitore, datiVersamento);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class RichiediAvvisoDati {\n");
	sb.append("    idEnte: ").append(toIndentedString(idEnte)).append("\n");
	sb.append("    urLNotifica: ").append(toIndentedString(urLNotifica)).append("\n");
	sb.append("    urLNotificaRevoca: ").append(toIndentedString(urLNotificaRevoca)).append("\n");
	sb.append("    urLAttualizzazione: ").append(toIndentedString(urLAttualizzazione)).append("\n");
	sb.append("    flagPDFRT: ").append(toIndentedString(flagPDFRT)).append("\n");
	sb.append("    flagPDFAvvisatura: ").append(toIndentedString(flagPDFAvvisatura)).append("\n");
	sb.append("    flagEmailAvviso: ").append(toIndentedString(flagEmailAvviso)).append("\n");
	sb.append("    datiEnte: ").append(toIndentedString(datiEnte)).append("\n");
	sb.append("    datiDebitore: ").append(toIndentedString(datiDebitore)).append("\n");
	sb.append("    datiVersamento: ").append(toIndentedString(datiVersamento)).append("\n");
	sb.append("}");
	return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {

	if (o == null) {
	    return "null";
	}
	return o.toString().replace("\n", "\n    ");
    }
}
