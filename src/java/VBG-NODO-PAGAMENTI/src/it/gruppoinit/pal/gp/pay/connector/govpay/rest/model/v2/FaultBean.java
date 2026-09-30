package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "FaultBean", propOrder = { "categoria", "codice", "descrizione", "dettaglio", })
public class FaultBean {

    /**
     * Categoria dell'errore riscontrato: * `AUTORIZZAZIONE` - Operazione non autorizzata * `RICHIESTA` - Richiesta non
     * valida * `OPERAZIONE` - Operazione non eseguibile * `PAGOPA` - Errore da PagoPA * `INTERNO` - Errore interno
     */
    @XmlType(name = "CategoriaEnum")
    @XmlEnum
    public enum CategoriaEnum {

	@XmlEnumValue("AUTORIZZAZIONE")
	AUTORIZZAZIONE("AUTORIZZAZIONE"),
	@XmlEnumValue("RICHIESTA")
	RICHIESTA("RICHIESTA"),
	@XmlEnumValue("OPERAZIONE")
	OPERAZIONE("OPERAZIONE"),
	@XmlEnumValue("PAGOPA")
	PAGOPA("PAGOPA"),
	@XmlEnumValue("EC")
	EC("EC"),
	@XmlEnumValue("INTERNO")
	INTERNO("INTERNO");

	private String value;

	CategoriaEnum(String value) {

	    this.value = value;
	}

	@Override
	public String toString() {

	    return String.valueOf(this.value);
	}

	public static CategoriaEnum fromValue(String text) {

	    for (CategoriaEnum b : CategoriaEnum.values()) {
		if (String.valueOf(b.value).equals(text)) {
		    return b;
		}
	    }
	    return null;
	}
    }

    @XmlElement(name = "categoria")
    private CategoriaEnum categoria = null;
    @XmlElement(name = "codice")
    private String codice = null;
    @XmlElement(name = "descrizione")
    private String descrizione = null;
    @XmlElement(name = "dettaglio")
    private String dettaglio = null;

    /**
     * Categoria dell'errore riscontrato: * `AUTORIZZAZIONE` - Operazione non autorizzata * `RICHIESTA` - Richiesta non
     * valida * `OPERAZIONE` - Operazione non eseguibile * `PAGOPA` - Errore da PagoPA * `EC` - Errore da Ente Creditore
     * * `INTERNO` - Errore interno
     **/
    public FaultBean categoria(CategoriaEnum categoria) {

	this.categoria = categoria;
	return this;
    }

    public CategoriaEnum getCategoria() {

	return this.categoria;
    }

    public void setCategoria(CategoriaEnum categoria) {

	this.categoria = categoria;
    }

    /**
     * Codice di errore
     **/
    public FaultBean codice(String codice) {

	this.codice = codice;
	return this;
    }

    public String getCodice() {

	return this.codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    /**
     * Descrizione dell'errore
     **/
    public FaultBean descrizione(String descrizione) {

	this.descrizione = descrizione;
	return this;
    }

    public String getDescrizione() {

	return this.descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    /**
     * Descrizione aggiuntiva
     **/
    public FaultBean dettaglio(String dettaglio) {

	this.dettaglio = dettaglio;
	return this;
    }

    public String getDettaglio() {

	return this.dettaglio;
    }

    public void setDettaglio(String dettaglio) {

	this.dettaglio = dettaglio;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || this.getClass() != o.getClass()) {
	    return false;
	}
	FaultBean faultBean = (FaultBean) o;
	return Objects.equals(this.categoria, faultBean.categoria) && Objects.equals(this.codice, faultBean.codice)
		&& Objects.equals(this.descrizione, faultBean.descrizione) && Objects.equals(this.dettaglio, faultBean.dettaglio);
    }

    @Override
    public int hashCode() {

	return Objects.hash(this.categoria, this.codice, this.descrizione, this.dettaglio);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class FaultBean {\n");
	sb.append("    categoria: ").append(this.toIndentedString(this.categoria)).append("\n");
	sb.append("    codice: ").append(this.toIndentedString(this.codice)).append("\n");
	sb.append("    descrizione: ").append(this.toIndentedString(this.descrizione)).append("\n");
	sb.append("    dettaglio: ").append(this.toIndentedString(this.dettaglio)).append("\n");
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
