package it.gruppoinit.service;

import it.gruppoinit.domain.helper.InserisciDeterminaHelper;
import it.gruppoinit.domain.helper.LeggiAttoHelper;
import it.gruppoinit.domain.helper.LeggiAttoPlusHelper;
import it.gruppoinit.ws.wsatti.AttoOut;

public interface AttiService {

    /**
     * entrambi devono tornare la response del WS atti
     * 
     * @param inserisciDeterminaHelper
     */
    public it.gruppoinit.ws.wsatti.inserisciattistring.AttoInseritoOut inserisciDetermina(InserisciDeterminaHelper inserisciDeterminaHelper,
	    String token);

    /**
     * entrambi devono tornare la response del WS atti
     * 
     * @param inserisciDeterminaHelper
     */
    public AttoOut leggiAtto(LeggiAttoHelper leggiAttoHelper);

    public it.gruppoinit.ws.wsatti.leggiattoplus.attoout.AttoOut leggiAttoPlus(LeggiAttoPlusHelper leggiAttoPlusHelper);
}
