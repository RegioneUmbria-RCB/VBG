package it.gruppoinit.pal.gp.core.features.manifestazioni;

import java.util.Date;

public class PresenzaDaRegistrareBean {

    private Integer codiceMercato;
    private Integer codiceUso;
    private Date giornata;
    private Integer codicePosteggio;
    private Integer codiceAnagrafeSpuntista;
    private Integer codiceAutorizzazione;
    private String categoriaMerceologica;

    public Integer getCodiceMercato() {

	return codiceMercato;
    }

    public void setCodiceMercato(Integer codiceMercato) {

	this.codiceMercato = codiceMercato;
    }

    public Integer getCodiceUso() {

	return codiceUso;
    }

    public void setCodiceUso(Integer codiceUso) {

	this.codiceUso = codiceUso;
    }

    public Date getGiornata() {

	return giornata;
    }

    public void setGiornata(Date giornata) {

	this.giornata = giornata;
    }

    public Integer getCodicePosteggio() {

	return codicePosteggio;
    }

    public void setCodicePosteggio(Integer codicePosteggio) {

	this.codicePosteggio = codicePosteggio;
    }

    public Integer getCodiceAnagrafeSpuntista() {

	return codiceAnagrafeSpuntista;
    }

    public void setCodiceAnagrafeSpuntista(Integer codiceAnagrafeSpuntista) {

	this.codiceAnagrafeSpuntista = codiceAnagrafeSpuntista;
    }

    public Integer getCodiceAutorizzazione() {

	return codiceAutorizzazione;
    }

    public void setCodiceAutorizzazione(Integer codiceAutorizzazione) {

	this.codiceAutorizzazione = codiceAutorizzazione;
    }

    public String getCategoriaMerceologica() {

	return categoriaMerceologica;
    }

    public void setCategoriaMerceologica(String categoriaMerceologica) {

	this.categoriaMerceologica = categoriaMerceologica;
    }
}
