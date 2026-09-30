package it.gruppoinit.pal.gp.core.features.attivita.snapshots.model;

public class SequenzaSnapshot {

    private Snapshot attuale;
    private Snapshot snapshotPrecedente;
    private Snapshot snapshotSuccessivo;

    public Snapshot getAttuale() {

	return attuale;
    }

    public void setAttuale(Snapshot attuale) {

	this.attuale = attuale;
    }

    public Snapshot getSnapshotPrecedente() {

	return snapshotPrecedente;
    }

    public void setSnapshotPrecedente(Snapshot snapshotPrecedente) {

	this.snapshotPrecedente = snapshotPrecedente;
    }

    public Snapshot getSnapshotSuccessivo() {

	return snapshotSuccessivo;
    }

    public void setSnapshotSuccessivo(Snapshot snapshotSuccessivo) {

	this.snapshotSuccessivo = snapshotSuccessivo;
    }

    public Integer getCodiceOsservatorioSnapshotPrecedente() {

	if (this.snapshotPrecedente != null) {
	    return snapshotPrecedente.getCodiceOsservatorio();
	}
	return null;
    }
}
