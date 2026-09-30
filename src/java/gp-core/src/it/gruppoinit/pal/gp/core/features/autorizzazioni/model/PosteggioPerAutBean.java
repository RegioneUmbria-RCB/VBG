package it.gruppoinit.pal.gp.core.features.autorizzazioni.model;

import java.util.Date;

public class PosteggioPerAutBean {

    private Integer idaut;
    private String mercato;
    private String uso;
    private String codiceposteggio;
    private String giorno;
    private String tipomanif;
    private Date ultimapresenza;

    public Integer getIdaut() {

	return idaut;
    }

    public void setIdaut(Integer idaut) {

	this.idaut = idaut;
    }

    public String getMercato() {

	return mercato;
    }

    public void setMercato(String mercato) {

	this.mercato = mercato;
    }

    public String getUso() {

	return uso;
    }

    public void setUso(String uso) {

	this.uso = uso;
    }

    public String getCodiceposteggio() {

	return codiceposteggio;
    }

    public void setCodiceposteggio(String codiceposteggio) {

	this.codiceposteggio = codiceposteggio;
    }

    public String getGiorno() {

	return giorno;
    }

    public void setGiorno(String giorno) {

	this.giorno = giorno;
    }

    public String getTipomanif() {

	return tipomanif;
    }

    public void setTipomanif(String tipomanif) {

	this.tipomanif = tipomanif;
    }

    public Date getUltimapresenza() {

	return ultimapresenza;
    }

    public void setUltimapresenza(Date ultimapresenza) {

	this.ultimapresenza = ultimapresenza;
    }
}
