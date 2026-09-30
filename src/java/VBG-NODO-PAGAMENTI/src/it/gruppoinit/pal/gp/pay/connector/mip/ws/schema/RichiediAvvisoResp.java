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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * RichiediAvvisoResp
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RichiediAvvisoResp", propOrder = { "idEnte", "esitoOperazione", "datiDebitore", "datiVersamento", "esitoAvvisaturaDigitale",
	"pdFAvvisatura" })
@XmlRootElement(name = "RichiediAvvisoResp")
public class RichiediAvvisoResp {

    @XmlElement(name = "IDEnte")
    private String idEnte = null;
    @XmlElement(name = "EsitoOperazione")
    private EsitoOperazione esitoOperazione = null;
    @XmlElement(name = "DatiDebitore")
    private DatiDebitore datiDebitore = null;
    @XmlElement(name = "DatiVersamento")
    private DatiVersamentoResp datiVersamento = null;
    @XmlElement(name = "EsitoAvvisaturaDigitale")
    private EsitoAvvisaturaDigitale esitoAvvisaturaDigitale = null;
    @XmlElement(name = "PDFAvvisatura")
    private String pdFAvvisatura = null;

    public RichiediAvvisoResp idEnte(String idEnte) {

	this.idEnte = idEnte;
	return this;
    }

    /**
     * Get idEnte
     * 
     * @return idEnte
     **/
    public String getIdEnte() {

	return idEnte;
    }

    public void setIdEnte(String idEnte) {

	this.idEnte = idEnte;
    }

    public RichiediAvvisoResp esitoOperazione(EsitoOperazione esitoOperazione) {

	this.esitoOperazione = esitoOperazione;
	return this;
    }

    /**
     * Get esitoOperazione
     * 
     * @return esitoOperazione
     **/
    public EsitoOperazione getEsitoOperazione() {

	return esitoOperazione;
    }

    public void setEsitoOperazione(EsitoOperazione esitoOperazione) {

	this.esitoOperazione = esitoOperazione;
    }

    public RichiediAvvisoResp datiDebitore(DatiDebitore datiDebitore) {

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

    public RichiediAvvisoResp datiVersamento(DatiVersamentoResp datiVersamento) {

	this.datiVersamento = datiVersamento;
	return this;
    }

    /**
     * Get datiVersamento
     * 
     * @return datiVersamento
     **/
    public DatiVersamentoResp getDatiVersamento() {

	return datiVersamento;
    }

    public void setDatiVersamento(DatiVersamentoResp datiVersamento) {

	this.datiVersamento = datiVersamento;
    }

    public RichiediAvvisoResp esitoAvvisaturaDigitale(EsitoAvvisaturaDigitale esitoAvvisaturaDigitale) {

	this.esitoAvvisaturaDigitale = esitoAvvisaturaDigitale;
	return this;
    }

    /**
     * Get esitoAvvisaturaDigitale
     * 
     * @return esitoAvvisaturaDigitale
     **/
    public EsitoAvvisaturaDigitale getEsitoAvvisaturaDigitale() {

	return esitoAvvisaturaDigitale;
    }

    public void setEsitoAvvisaturaDigitale(EsitoAvvisaturaDigitale esitoAvvisaturaDigitale) {

	this.esitoAvvisaturaDigitale = esitoAvvisaturaDigitale;
    }

    public RichiediAvvisoResp pdFAvvisatura(String pdFAvvisatura) {

	this.pdFAvvisatura = pdFAvvisatura;
	return this;
    }

    /**
     * Get pdFAvvisatura
     * 
     * @return pdFAvvisatura
     **/
    public String getPdFAvvisatura() {

	return pdFAvvisatura;
    }

    public void setPdFAvvisatura(String pdFAvvisatura) {

	this.pdFAvvisatura = pdFAvvisatura;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	RichiediAvvisoResp richiediAvvisoResp = (RichiediAvvisoResp) o;
	return Objects.equals(this.idEnte, richiediAvvisoResp.idEnte) && Objects.equals(this.esitoOperazione, richiediAvvisoResp.esitoOperazione)
		&& Objects.equals(this.datiDebitore, richiediAvvisoResp.datiDebitore)
		&& Objects.equals(this.datiVersamento, richiediAvvisoResp.datiVersamento)
		&& Objects.equals(this.esitoAvvisaturaDigitale, richiediAvvisoResp.esitoAvvisaturaDigitale)
		&& Objects.equals(this.pdFAvvisatura, richiediAvvisoResp.pdFAvvisatura);
    }

    @Override
    public int hashCode() {

	return Objects.hash(idEnte, esitoOperazione, datiDebitore, datiVersamento, esitoAvvisaturaDigitale, pdFAvvisatura);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class RichiediAvvisoResp {\n");
	sb.append("    idEnte: ").append(toIndentedString(idEnte)).append("\n");
	sb.append("    esitoOperazione: ").append(toIndentedString(esitoOperazione)).append("\n");
	sb.append("    datiDebitore: ").append(toIndentedString(datiDebitore)).append("\n");
	sb.append("    datiVersamento: ").append(toIndentedString(datiVersamento)).append("\n");
	sb.append("    esitoAvvisaturaDigitale: ").append(toIndentedString(esitoAvvisaturaDigitale)).append("\n");
	sb.append("    pdFAvvisatura: ").append(toIndentedString(pdFAvvisatura)).append("\n");
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
