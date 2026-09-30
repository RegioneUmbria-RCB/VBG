package it.gruppoinit.pal.gp.core.features.istanze.cambiointervento;

import it.gruppoinit.pal.gp.core.features.istanze.rest.RicercaIstanzeIstanzeResult;

public interface ISpostamentoPraticheService {

    public EsitoSpostamentoPratiche spostaPraticheDaInterventoAIntervento(SpostamentoPraticheParams parametri);

    /**
     * Esegue una serie di verifiche per capire se possibile spostare le pratiche da una voce di intervento ad un altra.
     * L'operazione <b>NON DEVE</b> essere consentita se:
     * <ul>
     * <li>Azione differente sorgente/destinazione</li>
     * <li>sorgente/destinazione sono stessa foglia</li>
     * <li>sorgente/destinazione sono cartelle</li>
     * </ul>
     * 
     * @param codiceInterventoOrigine
     *            La voce di intervento di partenza o sorgente
     * @param codiceInterventoDestinazione
     *            La voce di intervento di destinazione
     * @return una struttura dati che contiene se posso eseguire l'operazione (nel caso una descrizione dell'eventuale
     *         errore) e la lista dei ruoli/schede dinamiche configurate
     */
    public VerificaSpostamentoPratiche checkInterventoSelezionabile(Integer codiceInterventoOrigine, Integer codiceInterventoDestinazione);

    public RicercaIstanzeIstanzeResult cercaPratichePerIntervento(Integer codiceInterventoOrigine, int offset, int limit);
}
