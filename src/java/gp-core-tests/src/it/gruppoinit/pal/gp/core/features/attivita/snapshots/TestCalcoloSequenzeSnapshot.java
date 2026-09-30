package it.gruppoinit.pal.gp.core.features.attivita.snapshots;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.attivita.snapshots.model.Snapshot;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.model.SnapshotDataComparator;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class TestCalcoloSequenzeSnapshot {

    private static final String DD_MM_YYYY = "dd/MM/yyyy";

    @Test
    public void verificaOrdinamentoSnapshot() {

	List<Snapshot> lista = new ArrayList<Snapshot>();
	Snapshot oggi = new Snapshot();
	oggi.setId(1);
	oggi.setData(Utilities.getDate("25/03/2021", DD_MM_YYYY).getTime());
	lista.add(0, oggi);
	Snapshot domani = new Snapshot();
	domani.setId(2);
	domani.setData(Utilities.getDate("26/03/2021", DD_MM_YYYY).getTime());
	lista.add(1, domani);
	Snapshot ieri = new Snapshot();
	ieri.setId(0);
	ieri.setData(Utilities.getDate("24/03/2021", DD_MM_YYYY).getTime());
	lista.add(2, ieri);
	Collections.sort(lista, new SnapshotDataComparator(true));
	Assert.assertTrue("l'id dello snapshot è 0 ", lista.get(0).getId().equals(0));
	Collections.sort(lista, new SnapshotDataComparator(false));
	Assert.assertTrue("l'id dello snapshot è 2 ", lista.get(0).getId().equals(2));
    }
}
