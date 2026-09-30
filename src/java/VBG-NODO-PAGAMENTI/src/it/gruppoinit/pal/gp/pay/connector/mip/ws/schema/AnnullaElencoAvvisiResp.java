/*
 * MIP API - WS Conversion Libreria API del Modulo Incassi e Pagamenti del Comune di Genova per operazioni su Avvisi di
 * Pagamento
 *
 * OpenAPI spec version: 0.0.94 Contact: helpservizionline@comune.genova.it
 *
 */
package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * AnnullaElencoAvvisiResp
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnnullaElencoAvvisiResp", propOrder = { "idEnte", "tipologiaEntrata", "esitoOperazione", "elencoCodiciAvviso" })
public class AnnullaElencoAvvisiResp {

    @XmlElement(name = "IDEnte")
    private String idEnte = null;
    @XmlElement(name = "TipologiaEntrata")
    private String tipologiaEntrata = null;
    @XmlElement(name = "EsitoOperazione")
    private Object esitoOperazione = null;
    @XmlElement(name = "ElencoCodiciAvviso")
    private List<Object> elencoCodiciAvviso = null;

    public AnnullaElencoAvvisiResp idEnte(String idEnte) {

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

    public AnnullaElencoAvvisiResp tipologiaEntrata(String tipologiaEntrata) {

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

    public AnnullaElencoAvvisiResp esitoOperazione(Object esitoOperazione) {

	this.esitoOperazione = esitoOperazione;
	return this;
    }

    /**
     * Get esitoOperazione
     * 
     * @return esitoOperazione
     **/
    public Object getEsitoOperazione() {

	return esitoOperazione;
    }

    public void setEsitoOperazione(Object esitoOperazione) {

	this.esitoOperazione = esitoOperazione;
    }

    public AnnullaElencoAvvisiResp elencoCodiciAvviso(List<Object> elencoCodiciAvviso) {

	this.elencoCodiciAvviso = elencoCodiciAvviso;
	return this;
    }

    public AnnullaElencoAvvisiResp addElencoCodiciAvvisoItem(Object elencoCodiciAvvisoItem) {

	if (this.elencoCodiciAvviso == null) {
	    this.elencoCodiciAvviso = new ArrayList<Object>();
	}
	this.elencoCodiciAvviso.add(elencoCodiciAvvisoItem);
	return this;
    }

    /**
     * Get elencoCodiciAvviso
     * 
     * @return elencoCodiciAvviso
     **/
    public List<Object> getElencoCodiciAvviso() {

	return elencoCodiciAvviso;
    }

    public void setElencoCodiciAvviso(List<Object> elencoCodiciAvviso) {

	this.elencoCodiciAvviso = elencoCodiciAvviso;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	AnnullaElencoAvvisiResp annullaElencoAvvisiResp = (AnnullaElencoAvvisiResp) o;
	return Objects.equals(this.idEnte, annullaElencoAvvisiResp.idEnte)
		&& Objects.equals(this.tipologiaEntrata, annullaElencoAvvisiResp.tipologiaEntrata)
		&& Objects.equals(this.esitoOperazione, annullaElencoAvvisiResp.esitoOperazione)
		&& Objects.equals(this.elencoCodiciAvviso, annullaElencoAvvisiResp.elencoCodiciAvviso);
    }

    @Override
    public int hashCode() {

	return Objects.hash(idEnte, tipologiaEntrata, esitoOperazione, elencoCodiciAvviso);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class AnnullaElencoAvvisiResp {\n");
	sb.append("    idEnte: ").append(toIndentedString(idEnte)).append("\n");
	sb.append("    tipologiaEntrata: ").append(toIndentedString(tipologiaEntrata)).append("\n");
	sb.append("    esitoOperazione: ").append(toIndentedString(esitoOperazione)).append("\n");
	sb.append("    elencoCodiciAvviso: ").append(toIndentedString(elencoCodiciAvviso)).append("\n");
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
