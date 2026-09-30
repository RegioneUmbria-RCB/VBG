package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.Comparator;

public class RigaDettaglioCalcoloMercatiIdRiferimentoDataContoComparator implements Comparator<RigaDettaglioCalcoloMercati> {

    @Override
    public int compare(RigaDettaglioCalcoloMercati o1, RigaDettaglioCalcoloMercati o2) {

	int c;
	c = o1.getIdRiferimento().compareTo(o2.getIdRiferimento());
	if (c == 0) {
	    c = o1.getDataGiornata().compareTo(o2.getDataGiornata());
	}
	if (c == 0) {
	    c = Boolean.valueOf(o2.isSubentro()).compareTo(Boolean.valueOf(o1.isSubentro())); // prima subentro=1 poi subentro=0
	}
	if (c == 0) {
	    c = o1.getIdAutorizzazioneConcessione().compareTo(o2.getIdAutorizzazioneConcessione()); // aut_conc_con id<
	}
	return c;
    }
}
