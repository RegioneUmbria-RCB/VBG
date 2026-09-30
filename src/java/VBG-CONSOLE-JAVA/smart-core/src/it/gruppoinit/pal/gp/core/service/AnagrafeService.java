package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AnagrafeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.AnagrafeEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafestorico;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafeAvvisiHelper;
import it.gruppoinit.pal.gp.core.domain.web.AnagrafeFilter;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.Date;
import java.util.List;

public interface AnagrafeService extends BaseService<Anagrafe, PkId> {

    /**
     * Ricerca tutti i record della tabella anagrafe che risultano attivi (flag_disabilitato=0 o null)
     * 
     * @param anagrafe
     * @return
     */
    public List<Anagrafe> findActiveByFilter(Anagrafe anagrafe);

    /**
     * Ricerca tutti i record della tabella anagrafe
     * 
     * @param anagrafe
     * @return
     */
    public List<Anagrafe> findAllByFilter(Anagrafe anagrafe);

    /**
     * Ricerca tutti i record della tabella anagrafe che risultano disabilitati (flag_disabilitato=1)
     * 
     * @param anagrafe
     * @return
     */
    public List<Anagrafe> findDisabledByFilter(Anagrafe anagrafe);

    /**
     * Ricerca tutti i record della tabella anagrafe identificati dal filtro
     * 
     * @param anagrafe
     *            il filtro
     * @return
     */
    public List<Anagrafe> findByFilter(AnagrafeFilter filtro);

    /**
     * @see AnagrafeDAO#findAll(Integer firstResult, Integer maxResult)
     */
    public List<Anagrafe> findAll(Integer firstResult, Integer maxResult);

    /**
     * Cerca un ANAGRAFESTORICO che abbia dataInizioValidita minore/uguale alla data passata e dataFineValidita
     * maggiore/uguale alla data passata se non trovata ripete la ricerca con dataFineValidita=null se non trovata
     * eccezione perchè non è presente
     * 
     * @param entity
     * @param dataValidita
     * @return
     */
    public Anagrafestorico findStoricoId(Anagrafe entity, Date dataValidita);

    /**
     * Cerca un ANAGRAFESTORICO che abbia dataInizioValidita minore/uguale alla data passata e dataFineValidita
     * maggiore/uguale alla data odierna se non trovata ripete la ricerca con dataFineValidita=null se non trovata
     * eccezione perchè non è presente
     * 
     * @param entity
     * @return
     */
    public Anagrafestorico findStoricoId(Anagrafe entity);

    /**
     * Calcola il codice fiscale a partire dei dati passati (tutti obbligatori)
     * 
     * @param nominativo
     * @param nome
     * @param datanascita
     * @param sesso
     * @param codicecomune
     * @return
     */
    public String calcolaCodicefiscale(String nominativo, String nome, Date datanascita, String sesso, String codicecomune);

    /**
     * Restituisce campi dell'anagrafe aggiornati richiedendoli tramite WS a un ente terzo passandogli il codice
     * fiscale. Se non viene passato il codice fiscale il metodo ritorna un oggetto null
     * 
     * @param request
     * @param anagrafe
     * @return
     */
    public Anagrafe findAnagrafeAggiornataByCF(Anagrafe anagrafeSigepro);

    /**
     * Restituisce campi dell'anagrafe aggiornati richiedendoli tramite WS a un ente terzo passandogli la partita iva.
     * Se non viene passato il codice fiscale il metodo ritorna un oggetto null
     * 
     * @param request
     * @param anagrafe
     * @return
     */
    public Anagrafe findAnagrafeAggiornataByPI(Anagrafe anagrafeSigepro);

    public List<Anagrafe> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult);

    public int countRecord(FilterTable filterTable);

    public Anagrafe insertNewStoricoFromAnagrafe(Anagrafe entity, Anagrafe oldAnagrafe, Date dataInizioValidità);

    /**
     * Ritorna un oggetto filter Table a partire dalla entity
     */
    public FilterTable createFilterTableByEntity(Anagrafe anagrafe);

    public List<Anagrafe> findByDescrizione(String descrizione, String tipoAnagrafe, AnagrafeEnum statoAnagrafe, Integer firstResult,
	    Integer maxResult);

    /**
     * Converte l'anagrafe passata da fisica a giuridica
     * 
     * @param anagrafe
     */
    public void convertPersonaFisicaToGiuridica(Anagrafe anagrafe);

    /**
     * Converte l'anagrafe passata da fisica a giuridica
     * 
     * @param anagrafe
     */
    public void convertPersonaGiuridicaToFisica(Anagrafe anagrafe);

    public Anagrafe findDatiAnagrafeDaWs(String cfPivaRicercaWs, Anagrafe anagrafe);

    public List<Anagrafestorico> findStorico(Anagrafe anagrafe);

    /**
     * Recupera il record dell'amagrafe storico per l'id passato
     * 
     * @param id
     * @return
     */
    public Anagrafestorico findAnagrafeStoricoById(PkId id);

    /**
     * La funzione torna la lista delle anagrafiche associate ad una istanza presenti in ISTANZE.CODICERICHIEDENTE,
     * ISTANZE.CODICETITOLARELEGALE, ISTANZE.CODICEPROFESSIONISTA, ISTANZERICHIEDENTI.CODICERICHIEDENTE,
     * ISTANZERICHIEDENTI.CODICEANAGRAFECOLL, ISTANZERICHIEDENTI.CODICEPROCURATORE
     * 
     * @param textToSearch
     * @param codiceIstanza
     * @param tipoAnagrafe
     * @return
     */
    public List<Anagrafe> findRichiedentiByIstanza(String textToSearch, Integer codiceIstanza, String tipoAnagrafe);

    /**
     * Restituisce campi dell'anagrafe aggiornati da una richiesta proveniente da Frontend (FO_RICHIESTE)
     * 
     * @return
     */
    public Anagrafe findAnagrafeAggiornataByFE(Integer codiceFoRichiesta);

    public Anagrafe popolateAnagrafeByFoRichiesta(Integer codiceFoRichiesta);

    public void leggiXmlFoRichiesta(byte[] file, it.gruppoinit.wsanagrafe2.schema.Anagrafe anagrafeFrontend);

    /**
     * Elimina l'anagrafe storico passato (solo se non utilizzato nelle istanze)
     */
    public void deleteAnagrafeStorico(Integer codiceAnagrafeStorico);

    public List<Integer> findCodiciAnagrafe();

    public AnagrafeAvvisiHelper findAvvisiAnagrafe(Integer codiceAnagrafe);

    public List<Anagrafe> findByFormegiuridiche(Integer codice, int firstResult, int maxResults);

    public List<Anagrafe> findByTitoli(Integer codice, int firstResult, int maxResults);

    public void clear();

    public Anagrafe findByCF(String deleganteCf);
}
