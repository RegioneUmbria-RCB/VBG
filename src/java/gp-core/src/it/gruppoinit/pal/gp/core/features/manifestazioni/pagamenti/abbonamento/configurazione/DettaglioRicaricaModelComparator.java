package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import java.util.Comparator;

public class DettaglioRicaricaModelComparator implements Comparator<DettaglioRicaricaModel> {

    @Override
    public int compare(DettaglioRicaricaModel chiave1, DettaglioRicaricaModel chiave2) {

	int c = chiave1.getImporto().compareTo(chiave2.getImporto());
	if (c == 0) {
	    c = chiave2.getTipologia().getDescrizione().compareToIgnoreCase(chiave1.getTipologia().getDescrizione());
	}
	return c;
    }
}
