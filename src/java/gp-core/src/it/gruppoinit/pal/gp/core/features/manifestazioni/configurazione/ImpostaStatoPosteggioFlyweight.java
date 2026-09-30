package it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione;

public class ImpostaStatoPosteggioFlyweight {

    private Integer idGiornata;
    private Integer idPosteggio;
    private Boolean abilitato;
    private String annotazione;

    public Integer getIdGiornata() {

	return idGiornata;
    }

    public void setIdGiornata(Integer idGiornata) {

	this.idGiornata = idGiornata;
    }

    public Integer getIdPosteggio() {

	return idPosteggio;
    }

    public void setIdPosteggio(Integer idPosteggio) {

	this.idPosteggio = idPosteggio;
    }

    public Boolean getAbilitato() {

	return abilitato;
    }

    public void isAbilitato(Boolean abilitato) {

	this.abilitato = abilitato;
    }

    public String getAnnotazione() {

	return annotazione;
    }

    public void setAnnotazione(String annotazione) {

	this.annotazione = annotazione;
    }
}
