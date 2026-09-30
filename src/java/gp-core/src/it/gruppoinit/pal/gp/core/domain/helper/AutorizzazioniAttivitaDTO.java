package it.gruppoinit.pal.gp.core.domain.helper;

public class AutorizzazioniAttivitaDTO {

    private Integer idautorizzazione;
    private String codiceattivita;
    private String descrizioneattivita;

    public Integer getIdautorizzazione() {

	return idautorizzazione;
    }

    public void setIdautorizzazione(Integer idautorizzazione) {

	this.idautorizzazione = idautorizzazione;
    }

    public String getCodiceattivita() {

	return codiceattivita;
    }

    public void setCodiceattivita(String codiceattivita) {

	this.codiceattivita = codiceattivita;
    }

    public String getDescrizioneattivita() {

	return descrizioneattivita;
    }

    public void setDescrizioneattivita(String descrizioneattivita) {

	this.descrizioneattivita = descrizioneattivita;
    }
}
