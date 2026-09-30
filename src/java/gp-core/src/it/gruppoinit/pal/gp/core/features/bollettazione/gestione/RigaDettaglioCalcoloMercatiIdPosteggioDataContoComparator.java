package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.Comparator;

public class RigaDettaglioCalcoloMercatiIdPosteggioDataContoComparator implements Comparator<RigaDettaglioCalcoloMercati> {

    @Override
    public int compare(RigaDettaglioCalcoloMercati o1, RigaDettaglioCalcoloMercati o2) {

	int c;
	c = o1.getIdPosteggio().compareTo(o2.getIdPosteggio());
	if (c == 0) {
	    c = o1.getDataGiornata().compareTo(o2.getDataGiornata());
	}
	return c;
    }
}
