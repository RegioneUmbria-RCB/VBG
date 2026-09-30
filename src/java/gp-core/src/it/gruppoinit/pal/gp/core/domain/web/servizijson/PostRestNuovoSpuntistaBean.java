package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.ArrayList;
import java.util.List;

public class PostRestNuovoSpuntistaBean {

    private String anagrafeTrovataInEnum;
    private String codiceFiscaleImpresa;
    private String ragioneSociale;
    private String nome;
    private String cognome;
    private String numeroAutorizzazione;
    private String dataAutorizzazione;
    private String comuneAutorizzazione;
    private String categoriaMerceologica;
    // 
    private String categoriaMerceologicaGiornata;
    private Integer idGiornata;
    private List<Long> allegati = new ArrayList<Long>();

    public String getAnagrafeTrovataInEnum() {

	return anagrafeTrovataInEnum;
    }

    public void setAnagrafeTrovataInEnum(String anagrafeTrovataInEnum) {

	this.anagrafeTrovataInEnum = anagrafeTrovataInEnum;
    }

    public String getCodiceFiscaleImpresa() {

	return codiceFiscaleImpresa;
    }

    public void setCodiceFiscaleImpresa(String codiceFiscaleImpresa) {

	this.codiceFiscaleImpresa = codiceFiscaleImpresa;
    }

    public String getRagioneSociale() {

	return ragioneSociale;
    }

    public void setRagioneSociale(String ragioneSociale) {

	this.ragioneSociale = ragioneSociale;
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

    public String getNumeroAutorizzazione() {

	return numeroAutorizzazione;
    }

    public void setNumeroAutorizzazione(String numeroAutorizzazione) {

	this.numeroAutorizzazione = numeroAutorizzazione;
    }

    public String getDataAutorizzazione() {

	return dataAutorizzazione;
    }

    public void setDataAutorizzazione(String dataAutorizzazione) {

	this.dataAutorizzazione = dataAutorizzazione;
    }

    public String getComuneAutorizzazione() {

	return comuneAutorizzazione;
    }

    public void setComuneAutorizzazione(String comuneAutorizzazione) {

	this.comuneAutorizzazione = comuneAutorizzazione;
    }

    public String getCategoriaMerceologica() {

	return categoriaMerceologica;
    }

    public void setCategoriaMerceologica(String categoriaMerceologica) {

	this.categoriaMerceologica = categoriaMerceologica;
    }

    public String getCategoriaMerceologicaGiornata() {

	return categoriaMerceologicaGiornata;
    }

    public void setCategoriaMerceologicaGiornata(String categoriaMerceologicaGiornata) {

	this.categoriaMerceologicaGiornata = categoriaMerceologicaGiornata;
    }

    public Integer getIdGiornata() {

	return idGiornata;
    }

    public void setIdGiornata(Integer idGiornata) {

	this.idGiornata = idGiornata;
    }

    public List<Long> getAllegati() {

	if (allegati == null) {
	    allegati = new ArrayList<Long>();
	}
	return allegati;
    }

    public void setAllegati(List<Long> allegati) {

	this.allegati = allegati;
    }
}
