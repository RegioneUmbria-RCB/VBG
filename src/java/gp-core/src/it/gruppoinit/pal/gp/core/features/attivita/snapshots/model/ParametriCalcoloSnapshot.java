package it.gruppoinit.pal.gp.core.features.attivita.snapshots.model;

import java.util.Date;

public class ParametriCalcoloSnapshot {

    private Integer idAttivita;
    private Date dataPrecedente;
    private Date dataSuccessiva;

    public ParametriCalcoloSnapshot(Integer idAttivita, Date dataPrecedente, Date dataSuccessiva) {

	super();
	if (idAttivita == null || dataPrecedente == null || dataSuccessiva == null) {
	    throw new IllegalArgumentException("Dati non validi");
	}
	this.idAttivita = idAttivita;
	this.dataPrecedente = dataPrecedente;
	this.dataSuccessiva = dataSuccessiva;
    }

    public Integer getIdAttivita() {

	return idAttivita;
    }

    public Date getDataPrecedente() {

	return dataPrecedente;
    }

    public Date getDataSuccessiva() {

	return dataSuccessiva;
    }
}
