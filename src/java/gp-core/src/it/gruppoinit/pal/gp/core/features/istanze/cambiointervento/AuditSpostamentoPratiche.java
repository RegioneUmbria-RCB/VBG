package it.gruppoinit.pal.gp.core.features.istanze.cambiointervento;

import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class AuditSpostamentoPratiche extends LoggerCancellazioni {

    private static final String CAMBIO_INTERVENTO = "#CAMBIO_INTERVENTO# In data ''{0}'' l''operatore ''{1}'' ha effettuato lo spostamento di {2} pratiche dall'intervento con id {3} all'intervento con id {4}";

    public static void tracciaSpostamento(String responsabile, Integer codiceInterventoProcOrigine, Integer codiceInterventoProcDestinazione,
	    Integer conteggioIstanzeSpostate) {

	String message = Utilities.formatMessage(CAMBIO_INTERVENTO, Utilities.getToday(true), responsabile, conteggioIstanzeSpostate,
		codiceInterventoProcOrigine, codiceInterventoProcDestinazione);
	log(message);
    }
}
