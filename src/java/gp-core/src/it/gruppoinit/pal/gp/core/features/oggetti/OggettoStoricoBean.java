package it.gruppoinit.pal.gp.core.features.oggetti;

import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.OggettiStorico;

public class OggettoStoricoBean {

    private Integer id;
    private Integer codiceOggetto;
    private String nomeFile;
    private Date dataSostituzione;
    private boolean ripristinabile;

    public OggettoStoricoBean(OggettiStorico oggetto) {

	super();
	if (oggetto == null) {
	    return;
	}
	this.id = oggetto.getId().getCodice();
	this.codiceOggetto = oggetto.getOggettoVecchio().getId().getCodice();
	this.nomeFile = oggetto.getOggettoVecchio().getNomefile();
	this.dataSostituzione = oggetto.getDataSostituzione();
	this.ripristinabile = false;
    }

    public Integer getId() {

	return id;
    }

    public Integer getCodiceOggetto() {

	return codiceOggetto;
    }

    public String getNomeFile() {

	return nomeFile;
    }

    public Date getDataSostituzione() {

	return dataSostituzione;
    }

    public boolean isRipristinabile() {

	return ripristinabile;
    }

    public void setRipristinabile(boolean ripristinabile) {

	this.ripristinabile = ripristinabile;
    }
}
