package it.gruppoinit.pal.gp.core.features.attivita.snapshots.model;

import java.util.Comparator;

import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * Esegue un compare tra le date di due snapshot non tenendo conto delle ore/minuti/secondi
 * 
 * @param earlier
 *            la prima data. può essere nulla
 * @param later
 *            la seconda data. può essere nulla
 * @return
 *         <ul>
 *         <li>0: se le date sono identiche</li>
 *         <li>&lt;0: se earlier è precedente a later</li>
 *         <li>&gt;0: se earlier è successiva a later</li>
 *         </ul>
 *         se entrambe le date sono nulle allora torna 0<br />
 *         se earlier è nulla e later è non nulla allora torna -1<br />
 *         se earlier è non nulla e later è nulla allora torna 1<br />
 */
public class SnapshotDataComparator implements Comparator<Snapshot> {

    private boolean ordineCrescente = true;

    public SnapshotDataComparator(boolean ordineCrescente) {

	this.ordineCrescente = ordineCrescente;
    }

    @Override
    public int compare(Snapshot earlier, Snapshot later) {

	if (ordineCrescente) {
	    return ordina(earlier, later);
	}
	return ordina(later, earlier);
    }

    private int ordina(Snapshot vecchio, Snapshot nuovo) {

	if (vecchio == null && nuovo == null) {
	    return 0;
	}
	if (vecchio == null) {
	    return -1;
	}
	if (nuovo == null) {
	    return 1;
	}
	return Utilities.compareDates(vecchio.getData(), nuovo.getData());
    }

    public static void main(String[] args) {

    }
}
