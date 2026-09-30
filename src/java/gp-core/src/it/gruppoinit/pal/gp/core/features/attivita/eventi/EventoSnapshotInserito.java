package it.gruppoinit.pal.gp.core.features.attivita.eventi;

import java.util.Date;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoSnapshotInserito implements IEvent {

    private Integer idAttivita;
    private Date dataRiferimento;

    public EventoSnapshotInserito(Integer idAttivita, Date dataRiferimento) {

	super();
	this.idAttivita = idAttivita;
	this.dataRiferimento = dataRiferimento;
    }

    public Integer getIdAttivita() {

	return idAttivita;
    }

    public Date getDataRiferimento() {

	return dataRiferimento;
    }
}