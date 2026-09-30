package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import java.util.Comparator;

public class DettaglioInformativaModelComparator implements Comparator<DettaglioInformativaModel> {

    @Override
    public int compare(DettaglioInformativaModel chiave1, DettaglioInformativaModel chiave2) {

	int c = 0;
	c = chiave1.getId().compareTo(chiave2.getId());
	if (c == 0) {
	    c = chiave1.getDataScadenza().compareTo(chiave2.getDataScadenza());
	}
	if (c == 0) {
	    c = chiave1.getInformativa().compareToIgnoreCase(chiave2.getInformativa());
	}
	return c;
    }
}
