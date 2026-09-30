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
 * AnnullaAvvisoResp
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnnullaAvvisoResp", propOrder = { "idEnte", "tipologiaEntrata", "codiceAvviso", "esitoOperazione" })
public class AnnullaAvvisoResp {

    @XmlElement(name = "IDEnte")
    private String idEnte = null;
    @XmlElement(name = "TipologiaEntrata")
    private String tipologiaEntrata = null;
    @XmlElement(name = "CodiceAvviso")
    private String codiceAvviso = null;
    @XmlElement(name = "EsitoOperazione")
    private EsitoOperazione esitoOperazione = null;

    public AnnullaAvvisoResp idEnte(String idEnte) {

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

    public AnnullaAvvisoResp tipologiaEntrata(String tipologiaEntrata) {

	this.tipologiaEntrata = tipologiaEntrata;
	return this;
    }

    /**
     * Get tipologiaEntrata
     * 
     * @return tipologiaEntrata
     **/
    public String getTipologiaEntrata() {

	return tipologiaEntrata;
    }

    public void setTipologiaEntrata(String tipologiaEntrata) {

	this.tipologiaEntrata = tipologiaEntrata;
    }

    public AnnullaAvvisoResp codiceAvviso(String codiceAvviso) {

	this.codiceAvviso = codiceAvviso;
	return this;
    }

    /**
     * Get codiceAvviso
     * 
     * @return codiceAvviso
     **/
    public String getCodiceAvviso() {

	return codiceAvviso;
    }

    public void setCodiceAvviso(String codiceAvviso) {

	this.codiceAvviso = codiceAvviso;
    }

    public AnnullaAvvisoResp esitoOperazione(EsitoOperazione esitoOperazione) {

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

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	AnnullaAvvisoResp annullaAvvisoResp = (AnnullaAvvisoResp) o;
	return Objects.equals(this.idEnte, annullaAvvisoResp.idEnte) && Objects.equals(this.tipologiaEntrata, annullaAvvisoResp.tipologiaEntrata)
		&& Objects.equals(this.codiceAvviso, annullaAvvisoResp.codiceAvviso)
		&& Objects.equals(this.esitoOperazione, annullaAvvisoResp.esitoOperazione);
    }

    @Override
    public int hashCode() {

	return Objects.hash(idEnte, tipologiaEntrata, codiceAvviso, esitoOperazione);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class AnnullaAvvisoResp {\n");
	sb.append("    idEnte: ").append(toIndentedString(idEnte)).append("\n");
	sb.append("    tipologiaEntrata: ").append(toIndentedString(tipologiaEntrata)).append("\n");
	sb.append("    codiceAvviso: ").append(toIndentedString(codiceAvviso)).append("\n");
	sb.append("    esitoOperazione: ").append(toIndentedString(esitoOperazione)).append("\n");
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
