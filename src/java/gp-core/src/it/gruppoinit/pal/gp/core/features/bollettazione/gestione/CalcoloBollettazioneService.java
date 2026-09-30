package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.features.bollettazione.nodopagamenti.VerificaPosizioneDebitoriaBean;

public interface CalcoloBollettazioneService {

    // List<EsitoCalcoloBollettazione> calcola(RichiestaCalcoloBollettazioneMercato richiesta);
    /**
     * Torna la lista delle bollettazioni visibili in base agli eventuali ruoli del responsabile passato come argomento
     * 
     * @param codiceResponsabile
     * @param firstResult
     * @param maxResult
     * @return
     */
    List<ElementoListaBollettazione> findByCodiceResponsabile(Integer codiceResponsabile, Integer firstResult, Integer maxResult);
    /**
     * Torna il numero delle bollettazioni visibili in base agli eventuali ruoli del responsabile passato come argomento
     * 
     * @param codiceResponsabile
     * @return
     */
    //    int countByCodiceResponsabile(Integer codiceResponsabile);

    /**
     * Torna l'elemento DettaglioBollettazione a partire dall'ID della bollettazione.
     * 
     * @param idBollettazione
     * @return
     */
    DettaglioBollettazione findById(Integer idBollettazione);

    /**
     * Verifica l'accesso di un operatore ad una determinata bollettazione
     * 
     * 
     * @param codiceResponsabile
     * @param codiceBollettazione
     * @return
     */
    boolean checkAccesso(Integer codiceResponsabile, Integer codiceBollettazione);

    /**
     * Torna la lista delle righe di bollettazione per la bollettazione e l'anagrafica passati come argomenti. Se non ci
     * sono righe trovate torna una lista vuota
     *
     * @param idBollettazione
     * @param idAnagrafica
     * @return
     */
    List<RigaBollettazione> findDettaglioByBollettazioneAndAnagrafe(Integer idBollettazione, Integer idAnagrafica);

    /**
     * Torna il dettaglio partendo dall'id di una riga di bollettazione di una anagrafica
     * 
     * @param idBollettazione
     * @param idAnagrafica
     * @param idRiga
     * @return
     */
    DettaglioRigaBollettazione findDettaglioRigaByBollettazioneAndAnagrafe(Integer idBollettazione, Integer idAnagrafica, Integer idRiga);

    /**
     * Il metodo verifica se per la tipologia di bollettazione esistono records
     * 
     * @param bollCfgTipoId
     *            il codice della tipologia di Bollettazione
     * @return
     */
    // int countByTipoBollettazione(Integer codice);
    /**
     * Il metodo richiama la cancellazione logica di una riga di dettaglio bollettazione. Inoltre segna una nota di log,
     * semppre nella stessa riga indicando lo username dell'utente e la data/ora di sistema in cui avviene l'operazione
     * 
     * @param idDettaglio
     */
    void deleteDettaglio(Integer idDettaglio, String userName);

    /**
     * Il metodo serve per rettificare una riga di una bollettazione. Segna una nota di log con lo username dell'utente
     * che ha effettuato l'operazione e l'ora in cui l'operazione è stata effettuata.
     * 
     * @param idBollettazione
     * @param idAnagrafica
     * @param importoSenzaIVA
     * @param iva
     * @param importo
     * @param idRiga
     * @param userName
     */
    void rettificaRiga(Integer idBollettazione, Integer idAnagrafica, BigDecimal importoSenzaIVA, Integer iva, BigDecimal importo, Integer idRiga,
	    String userName);

    /**
     * Il metodo serve per aggiungere una riga di una bollettazione. Segna una nota di log nelle note di sistema con
     * l'autore e la data di creazione.
     * 
     * @param idBollettazione
     * @param idAnagrafica
     * @param idConto
     * @param userName
     * @param descrizione
     * @param importoSenzaIva
     * @param iva
     * @param importo
     * @param noteUtente
     */
    void aggiungiRiga(Integer idBollettazione, Integer idAnagrafica, Integer idConto, String userName, String descrizione, BigDecimal importoSenzaIva,
	    Integer iva, BigDecimal importo, String noteUtente);

    /**
     * Torna la lista delle tipologie di bollettazione utilizzabili dall'utente in base ai ruoli configurati in comune
     * tra il responsabile e le tipologie di bollettazioni
     * 
     * @param codiceResponsabile
     * @return
     */
    List<CreazioneBollCfgTipo> findBollCfgTipoByCodiceResponsabile(Integer codiceResponsabile);

    /**
     * Effettua la cancellazione della bollettazione dalla testata fino all'ultima tabella di dettaglio. La
     * cancellazione è possibile solo se non ci sono righe inviate al sistema di pagamento
     * 
     * @param idBollettazione
     */
    void delete(Integer idBollettazione);

    /**
     * La funzione imposta il flag validato e scrive un messaggio nelle note di sistema della riga della bollettazione
     * 
     * @param idRiga
     * @param valido
     * @param autore
     */
    void validaDettaglioBollettazione(Integer idRiga, Boolean valido, String autore);

    /**
     * La Fuzione invia al Nodo dei pagamenti le varie posizioni debitorie a partire dall'id della bollettazione.
     * Vengono eseguiti una serie di controlli: nodo di pagamenti attivo almeno una riga deve validata
     * 
     * <pre>
     * Operazioni:
    
    Crea le posizioni debitorie a partire dalle righe di dettaglio di bollettazione
    A seconda del tipo di Bollettazione vanno create le posizioni debitorie raggruppate o meno per anagrafe (vd configurazione del tipo di bollettazione).
    Recupera le informazioni di testata della notifica da inviare al sistema di pagamento (tipo cf ente creditore). DA APPROFONDIRE
    
    
    Verifiche:
    
    La configurazione di testata deve essere corretta
    I dati che vanno a creare la posizione debitoria devono essere tutti presenti. Es.
        CF anagrafica
        Importi presenti e positivi
    La verticalizzazione NODO_PAGAMENTI deve essere attiva
    L'invio delle posizioni debitorie deve essere possibile solamente quando tutte le righe di dettaglio non eliminate e non rettificate sono validate.
    Lo stato di una riga di dettaglio deve cambiare in "Notificata"
    Non deve essere più possibile apportare modifiche ad una riga notificata
     * </pre>
     * 
     * @param idBollettazione
     */
    String inviaNodoPagamenti(Integer idBollettazione);

    /**
     * Aggiorna lo stato dei pagamenti dell'anagrafica passata e della bollettazione passata
     * 
     * @param idBollettazione
     * @param idAnagrafica
     */
    void aggiornaStatoPagamento(Integer idBollettazione, Integer idAnagrafica);

    /**
     * Aggiorna lo stato di tutti i pagamenti delle anagrafiche coinvolte nella bollettazione passata
     * 
     * @param idBollettazione
     */
    void aggiornaStatoPagamento(Integer idBollettazione);

    /**
     * Il metodo ritorna la lista dei riferimenti alle posizioni debitorie di una determinata anagrafica presente nella
     * bollettazione
     * 
     * @param idBollettazione
     * @param idAnagrafica
     * @return
     */
    Set<VerificaPosizioneDebitoriaBean> findPosizioniDebitorieByBollettazioneEAnagrafe(Integer idBollettazione, Integer idAnagrafica);

    /**
     * Il metodo ritorna la lista dei riferimenti alle posizioni debitorie presenti nella bollettazione
     * 
     * @param idBollettazione
     * @return
     */
    Set<VerificaPosizioneDebitoriaBean> findDettaglioPosizioniDebitorieByBollettazione(Integer idBollettazione);

    CreazioneBollTestata inizializzaCreazioneBollTestata(Integer codiceResponsabile);

    IntervalloDate getIntervalloDateDaBollCfgTipo(Integer codiceTipo, Date dataOperazione);

    /**
     * La funzionalità imposta tutte le righe valide o non valide al netto di quelle eliminate/rettificate
     * 
     * @param idBollettazione
     * @param validato
     * @param autore
     */
    void validaInteraBollettazione(Integer idBollettazione, Boolean validato, String autore);

    void aggiornaDataScadenza(Integer idBollettazione, Date dataScadenza);

    List<DettaglioRateizzazione> findDettaglioRateizzazione(Integer idRiga);

    List<DettaglioRateizzazione> findDettaglioRateizzazionePerAnagrafica(Integer idBollettazione, Integer idAnagrafica);

    String getHtmlForBollettazione(Integer idBollettazione, Integer codiceanagrafe, boolean isPdf, String alias, String software,
	    boolean soloValidati);

    byte[] getPdfReportForBollettazione(Integer idBollettazione, Integer codiceanagrafe, String alias, String software, boolean soloValidati)
	    throws Exception;

    Date getDataScadenzaBollettazione(Integer idBollettazione);
}
