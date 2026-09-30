package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.Date;
import java.util.Set;

import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

public interface ICalcoloBollettazioneService<U extends RichiestaCalcoloBollettazione> {

    /**
     * Calcola la lista delle bollettazioni dei mercati in base ai filtri passati nella richiesta
     * 
     * @param richiesta
     * @param conguaglio
     * @return
     */
    EsitoCalcoloBollettazione calcola(U richiesta, Boolean conguaglio);

    /**
     * Calcola la lista delle bollettazioni sul periodo precedente da sottoporre a confronto con l'attuale per eventuali
     * conguagli
     * 
     * @param codiceTipologiaBollettazione
     * @param dataPartenzaBollettazioneAttuale
     * @param codiceResponsabile
     * @return
     */
    EsitoCalcoloBollettazione calcolaConguaglio(Integer codiceTipologiaBollettazione, Date dataPartenzaBollettazioneAttuale,
	    Integer codiceResponsabile);

    /**
     * Inserisce una bollettazione e avvia il processo di calcolo dei dettagli della bollettazione
     * 
     * @param creazioneBollTestata
     * @param codiceResponsabile
     * @return l'id della bollettazione creata
     */
    Integer creaBollettazione(CreazioneBollTestata creazioneBollTestata, Integer codiceResponsabile);

    void validaConfigurazioneBollettazione(Set<String> codiciComune) throws BusinessValidationException;
}
