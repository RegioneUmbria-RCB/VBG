package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DatiContribuente {

    @XmlElement
    private String cap;

    @XmlElement
    private String civico;

    @XmlElement
    private String codiceIdentificativoUnivoco;

    @XmlElement
    private String cognome;

    @XmlElement
    private String email;

    @XmlElement
    private String indirizzo;

    @XmlElement
    private String localita;

    @XmlElement
    private String nazione;

    @XmlElement
    private String nome;

    @XmlElement
    private String provincia;

    @XmlElement
    private String ragioneSociale;

    @XmlElement
    private String tipoIdentificativoUnivoco;

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getCodiceIdentificativoUnivoco() {
		return codiceIdentificativoUnivoco;
	}

	public void setCodiceIdentificativoUnivoco(String codiceIdentificativoUnivoco) {
		this.codiceIdentificativoUnivoco = codiceIdentificativoUnivoco;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
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

	public String getLocalita() {
		return localita;
	}

	public void setLocalita(String localita) {
		this.localita = localita;
	}

	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getTipoIdentificativoUnivoco() {
		return tipoIdentificativoUnivoco;
	}

	public void setTipoIdentificativoUnivoco(String tipoIdentificativoUnivoco) {
		this.tipoIdentificativoUnivoco = tipoIdentificativoUnivoco;
	}

    
}
