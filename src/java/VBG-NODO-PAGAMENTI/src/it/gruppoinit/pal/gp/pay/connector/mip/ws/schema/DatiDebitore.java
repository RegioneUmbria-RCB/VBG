package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiDebitore", propOrder = { "tipoIdentificativo", "identificativoUtente", "nome", "cognome", "ragioneSociale", "email",
	"indirizzo" })
public class DatiDebitore {

    @XmlElement(name = "TipoIdentificativo")
    private String tipoIdentificativo;
    @XmlElement(name = "IdentificativoUtente")
    private String identificativoUtente;
    @XmlElement(name = "Nome")
    private String nome;
    @XmlElement(name = "Cognome")
    private String cognome;
    @XmlElement(name = "RagioneSociale")
    private String ragioneSociale;
    @XmlElement(name = "Email")
    private String email;
    @XmlElement(name = "Indirizzo")
    private String indirizzo;

    public String getTipoIdentificativo() {

	return tipoIdentificativo;
    }

    public void setTipoIdentificativo(String tipoIdentificativo) {

	this.tipoIdentificativo = tipoIdentificativo;
    }

    public String getIdentificativoUtente() {

	return identificativoUtente;
    }

    public void setIdentificativoUtente(String identificativoUtente) {

	this.identificativoUtente = identificativoUtente;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getCognome() {

	return cognome;
    }

    public void setCognome(String cognome) {

	this.cognome = cognome;
    }

    public String getRagioneSociale() {

	return ragioneSociale;
    }

    public void setRagioneSociale(String ragioneSociale) {

	this.ragioneSociale = ragioneSociale;
    }

    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    public String getIndirizzo() {

	return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {

	this.indirizzo = indirizzo;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
