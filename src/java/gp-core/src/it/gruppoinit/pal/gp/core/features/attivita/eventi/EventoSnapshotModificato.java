package it.gruppoinit.pal.gp.core.features.attivita.eventi;

import java.util.Date;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoSnapshotModificato implements IEvent {

    private Integer idAttivita;
    private Date dataRiferimento;
    private boolean snapshotCambiaCardinalita;

    public EventoSnapshotModificato(Integer idAttivita, Date dataRiferimento, boolean snapshotCambiaCardinalita) {

	super();
	this.idAttivita = idAttivita;
	this.dataRiferimento = dataRiferimento;
	this.snapshotCambiaCardinalita = snapshotCambiaCardinalita;
    }

    public Integer getIdAttivita() {

	return idAttivita;
    }

    public Date getDataRiferimento() {

	return dataRiferimento;
    }

    public boolean isSnapshotCambiaCardinalita() {

	return snapshotCambiaCardinalita;
    }
}
