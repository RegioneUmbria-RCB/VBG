package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.gruppoinit.pal.gp.core.dao.ComuniassociatiDAO;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.ComuniassociatiId;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.exception.OperatoreNonHaComuniConfiguratiException;
import it.gruppoinit.pal.gp.core.features.comuniassociati.ComuneAssociato;

public interface ComuniassociatiService extends BaseService<Comuniassociati, ComuniassociatiId> {

    /**
     * @see ComuniassociatiDAO#findByIdcomune(String)
     */
    public List<Comuniassociati> findByIdcomune(String idcomune);

    public List<ComuneAssociato> findAll();

    /**
     * Funzione da richiamare nei metodi del controller che utilizzano la combo comuni.<br />
     * Il metodo controlla se l'operatore correntemente loggato abbia configurati dei comuni (records nella tabella
     * responsabilicomuni).<br />
     * Il metodo viene richiamato solamente nelle installazioni multicomune. questo controllo dovrebbe venir fatto nei
     * metodi di list
     * 
     * @see AreeController#list(HttpServletRequest, HttpServletResponse)
     * 
     * @return torna una lista di comuniabilitati per il responsabile correntemente loggato da usare come filtro nelle
     *         liste. <br/ > Se l'installazione non è di tipo Associazione di comuni allora torna una lista vuota non
     *         nulla
     * @throws OperatoreNonHaComuniConfiguratiException
     *             se l'installazione è di tipo comuni associati e l'operatore correntemente loggato non ha configurato
     *             nessun comune
     * @throws SecurityException
     *             se viene acceduto senza autenticazione
     */
    public List<Responsabilicomuni> checkComuniAbilitatiPerResponsabile();

    /**
     * Verifica che per l'idcomune passato come argomento l'installazione sia di tipo COMUNIASSOCIATI.
     * 
     * @param idcomune
     *            il filtro usato per controllare se l'installazione è di tipo COMUNIASSOCIATI
     * @return <code>true</code> se esistono più di un record nella tabella comuniassociati per quell'idcomune,
     *         <code>false</code> in caso contrario
     */
    public Boolean isComuniassociati(String idcomune);
}
