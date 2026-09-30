package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import java.math.BigDecimal;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NuovoDocumento", propOrder = { "identificativo", "descrizione", "rata", "soglia", })
public class NuovoDocumento {

    @XmlElement(name = "identificativo")
    private String identificativo = null;
    @XmlElement(name = "descrizione")
    private String descrizione = null;
    @XmlElement(name = "rata")
    private BigDecimal rata = null;
    @XmlElement(name = "soglia")
    private VincoloPagamento soglia = null;

    /**
     * Identificativo del documento
     **/
    public NuovoDocumento identificativo(String identificativo) {

	this.identificativo = identificativo;
	return this;
    }

    public String getIdentificativo() {

	return identificativo;
    }

    public void setIdentificativo(String identificativo) {

	this.identificativo = identificativo;
    }

    /**
     * descrizione del documento
     **/
    public NuovoDocumento descrizione(String descrizione) {

	this.descrizione = descrizione;
	return this;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    /**
     * Rata del documento minimum: 1
     **/
    public NuovoDocumento rata(BigDecimal rata) {

	this.rata = rata;
	return this;
    }

    public BigDecimal getRata() {

	return rata;
    }

    public void setRata(BigDecimal rata) {

	this.rata = rata;
    }

    /**
     **/
    public NuovoDocumento soglia(VincoloPagamento soglia) {

	this.soglia = soglia;
	return this;
    }

    public VincoloPagamento getSoglia() {

	return soglia;
    }

    public void setSoglia(VincoloPagamento soglia) {

	this.soglia = soglia;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	NuovoDocumento nuovoDocumento = (NuovoDocumento) o;
	return Objects.equals(identificativo, nuovoDocumento.identificativo) && Objects.equals(descrizione, nuovoDocumento.descrizione)
		&& Objects.equals(rata, nuovoDocumento.rata) && Objects.equals(soglia, nuovoDocumento.soglia);
    }

    @Override
    public int hashCode() {

	return Objects.hash(identificativo, descrizione, rata, soglia);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class NuovoDocumento {\n");
	sb.append("    identificativo: ").append(toIndentedString(identificativo)).append("\n");
	sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
	sb.append("    rata: ").append(toIndentedString(rata)).append("\n");
	sb.append("    soglia: ").append(toIndentedString(soglia)).append("\n");
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
