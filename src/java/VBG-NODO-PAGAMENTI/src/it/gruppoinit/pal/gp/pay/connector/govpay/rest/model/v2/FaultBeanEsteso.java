package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FaultBeanEsteso", propOrder = { "categoria", "codice", "descrizione", "dettaglio", "id", "location", })
public class FaultBeanEsteso {

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
    @XmlElement(name = "id")
    private String id = null;
    @XmlElement(name = "location")
    private String location = null;

    /**
     * Categoria dell'errore riscontrato: * `AUTORIZZAZIONE` - Operazione non autorizzata * `RICHIESTA` - Richiesta non
     * valida * `OPERAZIONE` - Operazione non eseguibile * `PAGOPA` - Errore da PagoPA * `INTERNO` - Errore interno
     **/
    public FaultBeanEsteso categoria(CategoriaEnum categoria) {

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
    public FaultBeanEsteso codice(String codice) {

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
    public FaultBeanEsteso descrizione(String descrizione) {

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
    public FaultBeanEsteso dettaglio(String dettaglio) {

	this.dettaglio = dettaglio;
	return this;
    }

    public String getDettaglio() {

	return this.dettaglio;
    }

    public void setDettaglio(String dettaglio) {

	this.dettaglio = dettaglio;
    }

    /**
     * identificativo del pagamento creato
     **/
    public FaultBeanEsteso id(String id) {

	this.id = id;
	return this;
    }

    public String getId() {

	return this.id;
    }

    public void setId(String id) {

	this.id = id;
    }

    /**
     * Url del dettaglio del pagamento
     **/
    public FaultBeanEsteso location(String location) {

	this.location = location;
	return this;
    }

    public String getLocation() {

	return this.location;
    }

    public void setLocation(String location) {

	this.location = location;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	FaultBeanEsteso faultBeanEsteso = (FaultBeanEsteso) o;
	return Objects.equals(categoria, faultBeanEsteso.categoria) && Objects.equals(codice, faultBeanEsteso.codice)
		&& Objects.equals(descrizione, faultBeanEsteso.descrizione) && Objects.equals(dettaglio, faultBeanEsteso.dettaglio)
		&& Objects.equals(id, faultBeanEsteso.id) && Objects.equals(location, faultBeanEsteso.location);
    }

    @Override
    public int hashCode() {

	return Objects.hash(categoria, codice, descrizione, dettaglio, id, location);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class FaultBeanEsteso {\n");
	sb.append("    ").append(toIndentedString(super.toString())).append("\n");
	sb.append("    categoria: ").append(toIndentedString(categoria)).append("\n");
	sb.append("    codice: ").append(toIndentedString(codice)).append("\n");
	sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
	sb.append("    dettaglio: ").append(toIndentedString(dettaglio)).append("\n");
	sb.append("    id: ").append(toIndentedString(id)).append("\n");
	sb.append("    location: ").append(toIndentedString(location)).append("\n");
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
