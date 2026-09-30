package it.gruppoinit.pal.gp.core.features.alberoproc.tempi;

import java.util.Comparator;

public class AlberoProcTempiDettaglioModelComparator implements Comparator<AlberoProcTempiDettaglioModel> {

    @Override
    public int compare(AlberoProcTempiDettaglioModel tempo1, AlberoProcTempiDettaglioModel tempo2) {

	return tempo1.getOrdine().compareTo(tempo2.getOrdine());
    }
}
