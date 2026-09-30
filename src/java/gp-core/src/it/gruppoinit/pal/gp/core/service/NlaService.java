package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.init.sigepro.rte.AggiungiDocumentiNLARequest;
import it.init.sigepro.rte.AggiungiDocumentiNLAResponse;
import it.init.sigepro.rte.AllegatoBinarioNLARequest;
import it.init.sigepro.rte.AllegatoBinarioNLAResponse;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.InserimentoAttivitaNLAResponse;
import it.init.sigepro.rte.RichiestaPraticaNLARequest;
import it.init.sigepro.rte.RichiestaPraticaNLAResponse;
import it.init.sigepro.rte.RichiestaPraticheListaNLARequest;
import it.init.sigepro.rte.RichiestaPraticheListaNLAResponse;
import it.init.sigepro.rte.types.SportelloType;

public interface NlaService {

    public static final int CODICE_ERRORE_PROTOCOLLO = 58003;
    public static final int CODICE_ERRORE_INSERIMENTO_SIGEPRO = 58002;
    public static final int CODICE_ERRORE_RICERCA_PRATICHE_SIGEPRO = 58010;
    public static final int CODICE_ERRORE_RICERCA_PRATICA_SIGEPRO = 58011;

    /**
     * 
     * @param request
     * @param token
     * @param isPecOpRoto
     * @return
     */
    public InserimentoAttivitaNLAResponse inserimentoAttivita(InserimentoAttivitaNLARequest request, String token, boolean isPecOpRoto);

    /**
     * 
     * @param request
     * @return
     */
    public AllegatoBinarioNLAResponse richiestaAllegato(AllegatoBinarioNLARequest request);

    /**
     * 
     * 
     * @param request
     * @return
     */
    public RichiestaPraticaNLAResponse richiestaPratica(RichiestaPraticaNLARequest request);

    /**
     * 
     * @param request
     * @return
     */
    public RichiestaPraticheListaNLAResponse richiestaPraticheLista(RichiestaPraticheListaNLARequest request);

    /***
     * La funzione verifica se devono essere passati i riferimenti del protocollo dal mittente
     * 
     * @param mitt
     * @param dest
     * @return
     */
    public boolean passaProt(SportelloType mitt, SportelloType dest);

    public AggiungiDocumentiNLAResponse aggiungiDocumenti(AggiungiDocumentiNLARequest request);

    public boolean isScaricaAllegatiFisiciPerNodo(SportelloType sportello);

    public SportelloType getSportelloMittente();

    @DeletableCacheElements
    public void resetObjectCached();
}
