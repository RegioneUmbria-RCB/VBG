/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PecInbox;
import it.gruppoinit.pal.gp.core.domain.PecInboxAllegati;
import it.gruppoinit.pal.gp.core.domain.PecInboxId;
import it.gruppoinit.pal.gp.core.domain.helper.PECAttachmentHelper;
import it.gruppoinit.pal.gp.core.domain.web.PECInboxFilter;
import it.gruppoinit.pal.gp.core.filters.FilterOrder;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Servizi per l'accesso in lettura e scrittura alla tabella PEC_INBOX
 * 
 * @author francol
 * 
 */
public interface PecInboxService extends BaseService<PecInbox, PecInboxId> {

    /**
     * Recupera l'oggetto da passare al protocollo per la protocollaziione della PEC passata come argomanto
     * 
     * @param pec
     * @return
     */
    public String findProtocolloOggetto(PecInbox pec);

    /**
     * Passando al secondo argomento il contenuto binario dell'intero corpo del messaggio PEC, lo memorizza in OGGETTI e
     * ne imposta il riferimento in PEC_INBOX.CODICEOGGETTOPROTOCOLLO
     * 
     * @param pec
     *            : record di PEC_INBOX da aggiornare
     * @param bytesPec
     *            : dati binari dell'intero messaggio PEC
     * @param nomeFile
     *            : nome da assegnare al file su OGGETTI
     */
    public void salvaOggettoProtocollo(PecInbox pec, byte[] bytesPec, String nomeFile);

    /**
     * Restituisce l'URL da invocare per i WS di NLA-Gestiione Mail
     * 
     * @return
     */
    public String getNlaGestioneMailWSURL();

    /**
     * Riceve come argomenti un'istanza di {@link PecInbox} che rappresenta il messaggio PEC e una lista di
     * {@link PECAttachmentHelper} che contengono i dati binari e i nomi dei files scaricati dal WS
     * NLAGestioneMail.ScaricaAllegatiMessaggioWS. Ciascun allegato passato nella lista verrà inserito nella tabella
     * OGGETTI e collegato alla pec inserendo anche un record in PEC_INBOX_ALLEGATI.
     * 
     * @param pec
     * @param allegatiWS
     */
    public void elaboraAllegatiPec(PecInbox pec, PECAttachmentHelper corpoPec, List<PECAttachmentHelper> allegatiWS);

    /**
     * Restituisce una lista di {@link PecInboxAllegati} che rappresentano gli allegati della PEC passata come
     * argomento. Il metodo restituisce solo gli allegati PEC che sono stati precedentemente salvati nella tabella
     * PEC_INBOX_ALLEGATI e non scarica nuovamente gli allegati dal server di posta elettronica
     * 
     * @param pec
     * @return
     */
    public List<PecInboxAllegati> findAllegatiPec(PecInbox pec);

    /**
     * Invocato dopo la creazione di una nuova istanza a partire dalla PEC. Memorizza nel record di PEC_INBOX il
     * riferimento all'istanza passata come secondo argomento. Il responsabile della pec viene impostato a null
     * rendendola così disponibile per nuove operazioni agli altri operatori.
     * 
     * @param pec
     * @param codiceIstanza
     */
    public void associaPecAIstanza(PecInbox pec, Istanze istanza);

    /**
     * Invocato dopo la creazione di un movimento a partire dalla PEC. Memorizza nel record di PEC_INBOX il riferimento
     * al movimento il cui codice è passato come secondo argomento. Il responsabile della pec viene impostato a null
     * rendendola così disponibile per nuove operazioni agli altri operatori.
     * 
     * @param pec
     * @param codiceIstanza
     */
    public void associaPecAMovimento(PecInbox pec, Movimenti movimento);

    /**
     * Utilizzato per leggere l'elenco delle PEC interrogando direttamente il DB. Restituisce tutti i messaggi PEC
     * aventi data di ricezione compresa fra quelle specificate come argomenti. I messaggi sono ordinati per data
     * ricezione DESC
     * 
     * @param da
     * @param a
     * @return
     */
    public List<PecInbox> findByDataRicezione(Date da, Date a);

    /**
     * Utilizzato per leggere l'elenco delle PEC interrogando direttamente il DB. Restituisce tutti i messaggi PEC che
     * corrispondono ai criteri di filtro specificati nel primo argomento. E' anche possibile specificare i criteri di
     * ordinamento dei risultati, se non se ne specifica nessuno i messaggi restituiti saranno ordinati per data.
     * ricezione DESC.
     * 
     * @param pecFilter
     *            TODO
     * 
     * @return
     */
    public List<PecInbox> findByDataRicezioneFlagLettaMittenteOggettoProtocolloAndSort(PECInboxFilter pecFilter, List<FilterOrder> sortBy,
	    Integer firstRow, Integer maxRows);

    /**
     * Restiutisce il conteggio delle PEC che corrispondono ai criteri di filtro specificati.
     * 
     * @param pecFilter
     *            oggetto che contiene tutte le informazioni sui criteri di filtro delle PEC
     * 
     * @see findByDataRicezioneFlagLettaMittenteOggettoAndSort
     * @return
     */
    public int countByDataRicezioneFlagLettaMittenteOggettoProtocollo(PECInboxFilter pecFilter);

    /**
     * Contrassegna la PEC come cancellata dal server. Se il secondo argomento è true viene anche impostato a null il
     * responsabile.
     * 
     * @param pec
     */
    public void contrassegnaPecCancellata(PecInbox pec, boolean anullaResponsabile);

    /**
     * , Integer firstResult, Integer maxResult
     * 
     * @param codiceMovimento
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<PecInbox> findByMovimento(Integer codiceMovimento, Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle PecInbox di un'Istanza
     * 
     * @param codiceIstanza
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<PecInbox> findByIstanza(Integer codiceIstanza, Integer firstResult, Integer maxResult);

    /**
     * Restituisce la data fino alla quale l'account è già stato sincronizzato.
     * 
     * @param mailAccount
     * @return
     */
    public Date getLastSynchronizationDate(String mailAccount);

    /**
     * Restituisce una lista di {@link Oggetti} che puntano agli allegati della PEC che devono essere utillizzati come
     * allegati dell'istanza o del movimento durante la creazione di istanze o movimenti da PEC. Se il flag boolean è
     * false vengono restituiti i riferimenti agli allegati della PEC così come sono, se invece vale true allora viene
     * verificata la presenza di archivi compressi fra gli allegati della PEC, ogni allegato compresso viene
     * scompattato, e tutti i suoi contenuti vengono salvati in OGGETTI e restituiti nella lista al posto dell'allegato
     * compresso. Si prevede di supportare inizialmente i formati .zip e .rar.
     * 
     * @param allegatiPec
     * @param scompatta
     * @return
     */
    public List<Oggetti> getAllegatiPECPerIstanzaoMovimento(Collection<PecInboxAllegati> allegatiPec, boolean scompatta);

    /**
     * Questo metodo recupera la riga di PECINBOX corrispondente all'id passato come argomento, lockando la riga
     * selezionata. Viene effettuata una verifica che non ci siano già protocolli associati a questa PEC o associati ad
     * eventuali {@link Istanze} o {@link Movimenti} collegati alla PEC. Se non c'è ancora nessun protocollo l'utente
     * corrente viene assegnato come responsabile della PEC. Se c'è già un protocollo associato alla PEC (direttamente o
     * tramite l'istanza o il movimento) il metodo restituisce comunque il riferimento all'oggetto {@link PecInbox} che
     * rappresenta il record cercato. Il lock è rilasciato alla chiusura della transazione ossia all'uscita da questo
     * metodo Se il record che si sta leggendo risulta già lockato da altra transazione il metodo restituisce
     * un'eccezione.
     * 
     * @return
     */
    public PecInbox lockPecInboxAssegnaResponsabileProtocollo(String idPec);

    /**
     * Questo metodo recupera la riga di PECINBOX corrispondente all'id passato come argomento, lockando la riga
     * selezionata. Viene effettuata una verifica che non ci siano già istanze associate a questa PEC. Se non c'è ancora
     * nessun'istanza l'utente corrente viene assegnato come responsabile della PEC. Se c'è già un istanza associata
     * alla PEC il metodo restituisce comunque il riferimento all'oggetto {@link PecInbox} che rappresenta il record
     * cercato. Il lock è rilasciato alla chiusura della transazione ossia all'uscita da questo metodo Se il record che
     * si sta leggendo risulta già lockato da altra transazione il metodo restituisce un'eccezione.
     * 
     * @return
     */
    public PecInbox lockPecInboxAssegnaResponsabileIstanzaOMovimento(String idPec);

    public List<PecInbox> findByAccountId(Integer idAccount);

    public int countByAccountId(Integer idAccount);
}
