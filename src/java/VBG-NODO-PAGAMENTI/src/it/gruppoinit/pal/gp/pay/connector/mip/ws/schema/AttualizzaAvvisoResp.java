/*
 * MIP TO SG API - WS Conversion Libreria API del ***SG*** per il Modulo Incassi e Pagamenti del Comune di Genova
 *
 * OpenAPI spec version: 0.5 Contact: ....@fornitore.it
 *
 */
package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * AttualizzaAvvisoResp
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttualizzaAvvisoResp", propOrder = { "idEnte", "esitoOperazione", "datiVersamento" })
public class AttualizzaAvvisoResp {

    @XmlElement(name = "IDEnte")
    private String idEnte = null;
    @XmlElement(name = "EsitoOperazione")
    private EsitoOperazione esitoOperazione = null;
    @XmlElement(name = "DatiVersamento")
    private DatiVersamento datiVersamento = null;

    public AttualizzaAvvisoResp idEnte(String idEnte) {

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

    public AttualizzaAvvisoResp esitoOperazione(EsitoOperazione esitoOperazione) {

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

    public AttualizzaAvvisoResp datiVersamento(DatiVersamento datiVersamento) {

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
	AttualizzaAvvisoResp attualizzaAvvisoResp = (AttualizzaAvvisoResp) o;
	return Objects.equals(this.idEnte, attualizzaAvvisoResp.idEnte) && Objects.equals(this.esitoOperazione, attualizzaAvvisoResp.esitoOperazione)
		&& Objects.equals(this.datiVersamento, attualizzaAvvisoResp.datiVersamento);
    }

    @Override
    public int hashCode() {

	return Objects.hash(idEnte, esitoOperazione, datiVersamento);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class AttualizzaAvvisoResp {\n");
	sb.append("    idEnte: ").append(toIndentedString(idEnte)).append("\n");
	sb.append("    esitoOperazione: ").append(toIndentedString(esitoOperazione)).append("\n");
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
