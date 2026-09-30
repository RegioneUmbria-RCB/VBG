package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.PeriodiEnum;

public class CalcoloIntervalloFactory {

    public CalcoloIntervalloStrategy get(PeriodiEnum periodo) {

	switch (periodo) {
	case MENSILE:
	    return new CalcoloIntervalloMensile();
	case BIMESTRALE:
	    return new CalcoloIntervalloBimestrale();
	case TRIMESTRALE:
	    return new CalcoloIntervalloTrimestrale();
	case QUADRIMESTRALE:
	    return new CalcoloIntervalloQuadrimestrale();
	case SEMESTRALE:
	    return new CalcoloIntervalloSemestrale();
	case ANNUALE:
	    return new CalcoloIntervalloAnnuale();
	default:
	    throw new NotImplementedException("Il tipo di periodo " + periodo + " non è supportato");
	}
    }
}
