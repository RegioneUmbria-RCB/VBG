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
 * AttualizzaAvvisoDati
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttualizzaAvvisoDati", propOrder = { "idEnte", "codiceAvviso" })
public class AttualizzaAvvisoDati {

    @XmlElement(name = "IDEnte")
    private String idEnte = null;
    @XmlElement(name = "CodiceAvviso")
    private String codiceAvviso = null;

    public AttualizzaAvvisoDati idEnte(String idEnte) {

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

    public AttualizzaAvvisoDati codiceAvviso(String codiceAvviso) {

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

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	AttualizzaAvvisoDati attualizzaAvvisoDati = (AttualizzaAvvisoDati) o;
	return Objects.equals(this.idEnte, attualizzaAvvisoDati.idEnte) && Objects.equals(this.codiceAvviso, attualizzaAvvisoDati.codiceAvviso);
    }

    @Override
    public int hashCode() {

	return Objects.hash(idEnte, codiceAvviso);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class AttualizzaAvvisoDati {\n");
	sb.append("    idEnte: ").append(toIndentedString(idEnte)).append("\n");
	sb.append("    codiceAvviso: ").append(toIndentedString(codiceAvviso)).append("\n");
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
