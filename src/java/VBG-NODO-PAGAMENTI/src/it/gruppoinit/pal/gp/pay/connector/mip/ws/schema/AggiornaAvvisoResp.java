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
 * AggiornaAvvisoResp
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AggiornaAvvisoResp", propOrder = { "idEnte", "codiceAvviso", "esitoOperazione" })
@XmlRootElement(name = "AggiornaAvvisoResp")
public class AggiornaAvvisoResp {

    @XmlElement(name = "IDEnte")
    private String idEnte = null;
    @XmlElement(name = "CodiceAvviso")
    private String codiceAvviso = null;
    @XmlElement(name = "EsitoOperazione")
    private EsitoOperazione esitoOperazione = null;

    public AggiornaAvvisoResp idEnte(String idEnte) {

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

    public AggiornaAvvisoResp codiceAvviso(String codiceAvviso) {

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

    public AggiornaAvvisoResp esitoOperazione(EsitoOperazione esitoOperazione) {

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
	AggiornaAvvisoResp aggiornaAvvisoResp = (AggiornaAvvisoResp) o;
	return Objects.equals(this.idEnte, aggiornaAvvisoResp.idEnte) && Objects.equals(this.codiceAvviso, aggiornaAvvisoResp.codiceAvviso)
		&& Objects.equals(this.esitoOperazione, aggiornaAvvisoResp.esitoOperazione);
    }

    @Override
    public int hashCode() {

	return Objects.hash(idEnte, codiceAvviso, esitoOperazione);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class AggiornaAvvisoResp {\n");
	sb.append("    idEnte: ").append(toIndentedString(idEnte)).append("\n");
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
