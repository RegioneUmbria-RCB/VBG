package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modTSnapshot;

import java.util.ArrayList;
import java.util.List;

public class IAttivitadyn2modTSnapshotHelper {

    private IAttivitadyn2modTSnapshot attivitadyn2modTSnapshot = new IAttivitadyn2modTSnapshot();
    private List<IAttivitadyn2datiSnapshot> attivitadyn2datiSnapshots = new ArrayList<IAttivitadyn2datiSnapshot>(0);

    public IAttivitadyn2modTSnapshot getAttivitadyn2modTSnapshot() {

	return attivitadyn2modTSnapshot;
    }

    public void setAttivitadyn2modTSnapshot(IAttivitadyn2modTSnapshot attivitadyn2modTSnapshot) {

	this.attivitadyn2modTSnapshot = attivitadyn2modTSnapshot;
    }

    public List<IAttivitadyn2datiSnapshot> getAttivitadyn2datiSnapshots() {

	return attivitadyn2datiSnapshots;
    }

    public void setAttivitadyn2datiSnapshots(List<IAttivitadyn2datiSnapshot> attivitadyn2datiSnapshots) {

	this.attivitadyn2datiSnapshots = attivitadyn2datiSnapshots;
    }
}
