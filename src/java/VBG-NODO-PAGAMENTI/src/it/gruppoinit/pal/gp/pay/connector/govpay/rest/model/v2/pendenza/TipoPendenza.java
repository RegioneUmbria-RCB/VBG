package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoPendenza", propOrder = { "idTipoPendenza", "descrizione", })
public class TipoPendenza {

    @XmlElement(name = "idTipoPendenza")
    private String idTipoPendenza = null;
    @XmlElement(name = "descrizione")
    private String descrizione = null;

    /**
     **/
    public TipoPendenza idTipoPendenza(String idTipoPendenza) {

	this.idTipoPendenza = idTipoPendenza;
	return this;
    }

    public String getIdTipoPendenza() {

	return idTipoPendenza;
    }

    public void setIdTipoPendenza(String idTipoPendenza) {

	this.idTipoPendenza = idTipoPendenza;
    }

    /**
     **/
    public TipoPendenza descrizione(String descrizione) {

	this.descrizione = descrizione;
	return this;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	TipoPendenza tipoPendenza = (TipoPendenza) o;
	return Objects.equals(idTipoPendenza, tipoPendenza.idTipoPendenza) && Objects.equals(descrizione, tipoPendenza.descrizione);
    }

    @Override
    public int hashCode() {

	return Objects.hash(idTipoPendenza, descrizione);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class TipoPendenza {\n");
	sb.append("    ").append(toIndentedString(super.toString())).append("\n");
	sb.append("    idTipoPendenza: ").append(toIndentedString(idTipoPendenza)).append("\n");
	sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
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
