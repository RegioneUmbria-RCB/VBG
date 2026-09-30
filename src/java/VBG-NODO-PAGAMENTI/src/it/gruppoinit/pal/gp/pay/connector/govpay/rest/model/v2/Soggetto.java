package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.TipoSoggetto;

/**
 * dati anagrafici di un versante o pagatore.
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Soggetto", propOrder = { "tipo", "identificativo", "anagrafica", "indirizzo", "civico", "cap", "localita", "provincia", "nazione",
	"email", "cellulare", })
public class Soggetto {

    @XmlElement(name = "tipo")
    private TipoSoggetto tipo = null;
    @XmlElement(name = "identificativo")
    private String identificativo = null;
    @XmlElement(name = "anagrafica")
    private String anagrafica = null;
    @XmlElement(name = "indirizzo")
    private String indirizzo = null;
    @XmlElement(name = "civico")
    private String civico = null;
    @XmlElement(name = "cap")
    private String cap = null;
    @XmlElement(name = "localita")
    private String localita = null;
    @XmlElement(name = "provincia")
    private String provincia = null;
    @XmlElement(name = "nazione")
    private String nazione = null;
    @XmlElement(name = "email")
    private String email = null;
    @XmlElement(name = "cellulare")
    private String cellulare = null;

    /**
     **/
    public Soggetto tipo(TipoSoggetto tipo) {

	this.tipo = tipo;
	return this;
    }

    public TipoSoggetto getTipo() {

	return tipo;
    }

    public void setTipo(TipoSoggetto tipo) {

	this.tipo = tipo;
    }

    /**
     * codice fiscale o partita iva del soggetto
     **/
    public Soggetto identificativo(String identificativo) {

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
     * nome e cognome o altra ragione sociale del soggetto
     **/
    public Soggetto anagrafica(String anagrafica) {

	this.anagrafica = anagrafica;
	return this;
    }

    public String getAnagrafica() {

	return anagrafica;
    }

    public void setAnagrafica(String anagrafica) {

	this.anagrafica = anagrafica;
    }

    /**
     **/
    public Soggetto indirizzo(String indirizzo) {

	this.indirizzo = indirizzo;
	return this;
    }

    public String getIndirizzo() {

	return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {

	this.indirizzo = indirizzo;
    }

    /**
     **/
    public Soggetto civico(String civico) {

	this.civico = civico;
	return this;
    }

    public String getCivico() {

	return civico;
    }

    public void setCivico(String civico) {

	this.civico = civico;
    }

    /**
     **/
    public Soggetto cap(String cap) {

	this.cap = cap;
	return this;
    }

    public String getCap() {

	return cap;
    }

    public void setCap(String cap) {

	this.cap = cap;
    }

    /**
     **/
    public Soggetto localita(String localita) {

	this.localita = localita;
	return this;
    }

    public String getLocalita() {

	return localita;
    }

    public void setLocalita(String localita) {

	this.localita = localita;
    }

    /**
     **/
    public Soggetto provincia(String provincia) {

	this.provincia = provincia;
	return this;
    }

    public String getProvincia() {

	return provincia;
    }

    public void setProvincia(String provincia) {

	this.provincia = provincia;
    }

    /**
     **/
    public Soggetto nazione(String nazione) {

	this.nazione = nazione;
	return this;
    }

    public String getNazione() {

	return nazione;
    }

    public void setNazione(String nazione) {

	this.nazione = nazione;
    }

    /**
     **/
    public Soggetto email(String email) {

	this.email = email;
	return this;
    }

    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    /**
     **/
    public Soggetto cellulare(String cellulare) {

	this.cellulare = cellulare;
	return this;
    }

    public String getCellulare() {

	return cellulare;
    }

    public void setCellulare(String cellulare) {

	this.cellulare = cellulare;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	Soggetto soggetto = (Soggetto) o;
	return Objects.equals(tipo, soggetto.tipo) && Objects.equals(identificativo, soggetto.identificativo)
		&& Objects.equals(anagrafica, soggetto.anagrafica) && Objects.equals(indirizzo, soggetto.indirizzo)
		&& Objects.equals(civico, soggetto.civico) && Objects.equals(cap, soggetto.cap) && Objects.equals(localita, soggetto.localita)
		&& Objects.equals(provincia, soggetto.provincia) && Objects.equals(nazione, soggetto.nazione) && Objects.equals(email, soggetto.email)
		&& Objects.equals(cellulare, soggetto.cellulare);
    }

    @Override
    public int hashCode() {

	return Objects.hash(tipo, identificativo, anagrafica, indirizzo, civico, cap, localita, provincia, nazione, email, cellulare);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class Soggetto {\n");
	sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
	sb.append("    identificativo: ").append(toIndentedString(identificativo)).append("\n");
	sb.append("    anagrafica: ").append(toIndentedString(anagrafica)).append("\n");
	sb.append("    indirizzo: ").append(toIndentedString(indirizzo)).append("\n");
	sb.append("    civico: ").append(toIndentedString(civico)).append("\n");
	sb.append("    cap: ").append(toIndentedString(cap)).append("\n");
	sb.append("    localita: ").append(toIndentedString(localita)).append("\n");
	sb.append("    provincia: ").append(toIndentedString(provincia)).append("\n");
	sb.append("    nazione: ").append(toIndentedString(nazione)).append("\n");
	sb.append("    email: ").append(toIndentedString(email)).append("\n");
	sb.append("    cellulare: ").append(toIndentedString(cellulare)).append("\n");
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
