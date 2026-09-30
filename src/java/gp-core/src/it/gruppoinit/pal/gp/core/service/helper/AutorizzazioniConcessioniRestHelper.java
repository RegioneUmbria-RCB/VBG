package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

import java.util.Date;

public class AutorizzazioniConcessioniRestHelper {

    private Integer id;
    private CodiceDescrizioneBean comune;
    private boolean isConcessione;
    private String numero;
    private CodiceDescrizioneBean registro;
    private Date dataScadenza;
    private String autorizzataDa;
    private Date dataAutorizzazione;
    private String tipoConcessione;
    private CodiceDescrizioneBean mercato;
    private CodiceDescrizioneBean giorno;
    private CodiceDescrizioneBean posteggio;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public CodiceDescrizioneBean getComune() {

	return comune;
    }

    public void setComune(CodiceDescrizioneBean comune) {

	this.comune = comune;
    }

    public boolean isConcessione() {

	return isConcessione;
    }

    public void setConcessione(boolean isConcessione) {

	this.isConcessione = isConcessione;
    }

    public String getNumero() {

	return numero;
    }

    public void setNumero(String numero) {

	this.numero = numero;
    }

    public CodiceDescrizioneBean getRegistro() {

	return registro;
    }

    public void setRegistro(CodiceDescrizioneBean registro) {

	this.registro = registro;
    }

    public Date getDataScadenza() {

	return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {

	this.dataScadenza = dataScadenza;
    }

    public String getAutorizzataDa() {

	return autorizzataDa;
    }

    public void setAutorizzataDa(String autorizzataDa) {

	this.autorizzataDa = autorizzataDa;
    }

    public Date getDataAutorizzazione() {

	return dataAutorizzazione;
    }

    public void setDataAutorizzazione(Date dataAutorizzazione) {

	this.dataAutorizzazione = dataAutorizzazione;
    }

    public String getTipoConcessione() {

	return tipoConcessione;
    }

    public void setTipoConcessione(String tipoConcessione) {

	this.tipoConcessione = tipoConcessione;
    }

    public CodiceDescrizioneBean getMercato() {

	return mercato;
    }

    public void setMercato(CodiceDescrizioneBean mercato) {

	this.mercato = mercato;
    }

    public CodiceDescrizioneBean getGiorno() {

	return giorno;
    }

    public void setGiorno(CodiceDescrizioneBean giorno) {

	this.giorno = giorno;
    }

    public CodiceDescrizioneBean getPosteggio() {

	return posteggio;
    }

    public void setPosteggio(CodiceDescrizioneBean posteggio) {

	this.posteggio = posteggio;
    }
}
