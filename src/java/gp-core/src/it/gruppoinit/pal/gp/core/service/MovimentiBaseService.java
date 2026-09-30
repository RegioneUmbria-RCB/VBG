package it.gruppoinit.pal.gp.core.service;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.MovimentiDAO;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiHelper;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.movimenti.rest.AggiornaRiferimentiProtocolloMovimentoRequest;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.AggiornamentoProtocolloException;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.MovimentiRabbitTestoBean;
import it.gruppoinit.pal.gp.core.features.scadenzario.BatchScadenzarioFilter;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.exception.OperazioniAutomaticheException;

public interface MovimentiBaseService extends BaseService<Movimenti, PkId> {

    /**
     * 
     * @author riccardob
     *         <ul>
     *         <li>ESEGUITI: i movimenti effettuati</li>
     *         <li>NON_ESEGUITI: i movimenti da effettuare o scadenze</li>
     *         <li>TUTTI: tutti i record della tabella movimenti</li>
     *         </ul>
     */
    public enum SceltaMovimentiEnum {
	ESEGUITI,
	NON_ESEGUITI,
	TUTTI
    }

    public enum MovimentoDaNotificare {
	SI,
	NO
    };

    public static int STC_INVIATO = 1;
    public static int STC_NON_INVIATO = 0;
    public static int STC_DISATTIVATO_DA_OPERATORE = 2;

    /**
     * Metodo per l'integrazione SIGePro/SIMO Deve recuperare tutti i movimenti filtrandoli per Intervallo di
     * data,Alberoprc e TipoMovimento
     * 
     * @return
     */
    public List<Movimenti> findMovimentiSimo(Calendar fromDate, Calendar toDate, Alberoproc alberoproc);

    /**
     * @see MovimentiDAO#findMovimentiByTipoMovimento(Integer, Tipimovimento)
     */
    public Movimenti findMovimentiByTipoMovimento(Integer codiceistanza, String tipimovimento);

    /**
     * Trova l'oggetto per la protocollazione
     * 
     * @param movimento
     * @return
     */
    public Mailtipo findProtocolloOggetto(Movimenti movimento);

    /**
     * Trova l'oggetto per la fascicolazione
     * 
     * @param movimento
     * @return
     */
    public String findFascicoloOggetto(Movimenti movimento);

    /**
     * @see MovimentiDAO#findByFilterTable(FilterTable)
     */
    public List<Movimenti> findByFilterTable(FilterTable filterTable);

    /**
     * Trova tutti i movimenti da effettuare legati ad un movimento eseguito
     * 
     * @param movimento
     * @return
     */
    public List<Movimenti> findContromovimentidaEffettuare(Movimenti movimento);

    /**
     * Trova tutti i contromovimenti effettuati legati ad un movimento eseguito
     * 
     * @param movimento
     * @return
     */
    public List<Movimenti> findContromovimentiEffettuati(Movimenti movimento);

    /**
     * QUESTA FUNZIONE CERCA IL MOVIMENTO CHE HA CREATO TRAMITE STC LA PRATICA INDICATA NEL PARAMETRO
     * 
     * @param istanza
     * @return
     */
    public Movimenti findMovimentoSTCCheHaCreatoIstanza(Istanze istanza);

    /**
     * QUESTA FUNZIONE CERCA IL MOVIMENTO DI AVVIO DELL'ISTANZA
     * 
     * @param istanza
     * @return
     */
    public Movimenti findMovimentoAvvioIstanza(Istanze istanza);

    /**
     * QUESTA FUNZIONE CERCA IL MOVIMENTO DI AVVIO DELL'ISTANZA
     * 
     * @param istanza
     * @return
     */
    public Movimenti findMovimentoChiusuraIstanza(Integer codiceIstanza);

    /**
     * Torna la lista dei movimenti eseguiti (data non nulla) per l' istanza ordinati per data asc, ordineInserimento
     * asc, codicemovimento asc
     * 
     * @param istanza
     * @return
     */
    public List<Movimenti> findEseguitiByIstanza(Istanze istanza);

    /**
     * Torna la lista dei movimenti non eseguiti (data nulla) per l' istanza ordinati per data scadenza asc
     * 
     * @param istanza
     * @return
     */
    public List<Movimenti> findDaEseguireByIstanza(Istanze istanza);

    /**
     * Verifica se l'operatore ha i diritti ad accedere alla modifica del movimento
     * 
     * <pre>
     * 
     * a.	logica di accesso al movimento (checkPermessiMovimento) (torna TRUE per inserire/modificare il movimento, false per visualizzare il movimento. Per lo scadenzario false significa non mostrare la scadenza)
     * 		i.	istanzeService.checkAccessoIstanza (OK)
     * 		ii.	estrae l'amministrazione dal movimento
     * 		iii.	se l'amministrazione non esiste o non è interna o TIPOMOVIMENTO.flagNoamminterna=1
     * 			return checkAccessoIstanza	checkPermessiMovimento
     * 				CONSENTITO	true
     * 				NON_CONSENTITO	false
     * 				SOLA_LETTURA	false
     * 				SOLA_LETTURA_TUTTI_MOVIMENTI	true
     * 				SOLA_LETTURA_MOVIMENTI_AMM_INTERNA	false
     * 		iv.	se l'amministrazione è interna
     * 			if NON_CONSENTITO allora false
     * 			altrimenti se il ruolo dell'operatore è nei ruoli dell'amministrazione allora true altrimenti false
     * 
     * </pre>
     * 
     * @param entity
     * @param responsabile
     * @param entity
     * @param responsabile
     * @param effettuaControllaPerModificaDati
     *            se true allora significa che si andranno a fare delle operazioni di salvataggio/cancellazione dati su
     *            DB e viene effettuato l'ulteriore controllo che nel caso l'operatore può accedere in sola lettura
     *            all'istanza ma non ha i permessi per eseguire i movimenti (insert/update/delete) allora non può
     *            salvare le informazioni, ma solo visualizzarle
     * @return
     */
    public boolean checkPermessiMovimento(Movimenti entity, Responsabili responsabile, boolean effettuaControllaPerModificaDati);

    /**
     * Il metodo restituisce le caratteristiche di un determinato movimento effettuato.
     * 
     * @param entity
     *            il movimento per il quale recuperare le informazioni
     * @throws IllegalArgumentException
     *             nel caso che il movimento non esista, sia nullo
     * @return
     */
    public MovimentiHelper findCaratteristicheMovimento(Movimenti entity);

    @Override
    /**
     * 
     */
    public void insert(Movimenti movimenti);

    /**
     * @see use {@link MovimentiManager#update(Movimenti)}
     */
    public void update(Movimenti entity);

    @Override
    /**
     * 
     */
    public void delete(Movimenti entity);

    /**
     * Inserisce una scadenza: una scadenza è un movimento non eseguito ma di cui viene settato il campo dataScadenza.
     * La funzione non effettua il controllo sull'obbligatorietà della data, e non esegue operazioni di elaborazione, ma
     * si limita ad inserire un movimento con data nulla.
     * 
     * @param entity
     * @return
     */
    public void insertScadenza(Movimenti entity);

    /**
     * Aggiorna una scadenza. La funzione richiama l'aggiornamento del movimento. Se il movimento non è eseguito allora
     * esegue l'aggiornamento del movimento senza richiamare le funzioni di elaborazione o childDataIntegration.
     * 
     * @param entity
     */
    public void updateScadenza(Movimenti entity);

    /**
     * Trova il movimento di trasmissione di un endoprocedimento (se effettuato)
     */
    public Movimenti findMovimentoTrasmissioneByIstanzeprocedimenti(Istanzeprocedimenti istanzeprocedimenti);

    /**
     * Trova il movimento di ritorno di un endoprocedimento (se effettuato)
     */
    public Movimenti findMovimentoRitornoByIstanzeprocedimenti(Istanzeprocedimenti istanzeprocedimenti);

    /**
     * Torna la lista dei movimenti associati ad un endoprocedimento e istanza (Istanze procedimenti). Torna, a seconda
     * del parametro sceltaMovimentiEnum, tutti i movimenti, quelli eseguiti, quelli non eseguiti
     * 
     * @param istanzeprocedimenti
     * @param sceltaMovimentiEnum
     *            Se non passato di default è {@link SceltaMovimentiEnum#TUTTI}
     * @return
     */
    public List<Movimenti> findMovimentiByIstanzeprocedimenti(Istanzeprocedimenti istanzeprocedimenti, SceltaMovimentiEnum sceltaMovimentiEnum);

    /**
     * Se il movimento è da eseguire setta il flagDisabilitato = true
     * 
     * @param entity
     */
    public void disabilitaMovimento(Movimenti entity);

    public void flush();

    /**
     * Se il movimento è da eseguire setta il flagDisabilitato = false ed esegue l'elaborazione del movimento che lo ha
     * generato (se presente)
     * 
     * @param entity
     */
    public void abilitaMovimento(Movimenti entity);

    /**
     * Torna la lista dei movimenti da eseguire che sono stati disabilitati
     * 
     * @param istanza
     * @return
     */
    public List<Movimenti> findDisabilitatiByIstanza(Istanze istanza);

    /**
     * @see MovimentiDAO#findMovimentiDaAssociareAllaCommissione(Date filtroData, CommissioniedilizieT
     *      commissioniedilizieT)
     */
    public List<Movimenti> findMovimentiDaAssociareAllaCommissione(Date filtroData, CommissioniedilizieT commissioniedilizieT, Integer firstResult,
	    Integer maxResult);

    public List<Movimenti> findMovimentoPerFkIdProtocollo(String fkIdProtocollo);

    public boolean isDPR160(Movimenti entity);

    /**
     * il metodo verifica se il movimento è stato protocollato e se di tipo DPR160 cioè se uno dei due campi
     * MailtipoByFkTipimovricTelMailtipo o MailtipoByFkTipimovcomTelMailtipo è valorizzato. In caso positivo crea i due
     * allegati al movimento (se non sono presenti) con oggetto.
     * 
     * @param entity
     */
    public void gestioneAllegatiPerComunicazioniTelematiche(Movimenti entity);

    /**
     * il metodo verifica se il movimento contiene almeno un file con estensione .p7m
     * 
     * @param entity
     * @return
     */
    public boolean checkAllegatoFirmatoPerComunicazioniTelematiche(Movimenti entity);

    /**
     * True o false se il movimento è stato eseguito o è una scadenza
     * 
     * @param entity
     * @return
     */
    public boolean isEffettuato(Movimenti entity);

    /**
     * La funzione controlla se è possibile modificare/cancellare il movimento. Un movimento è modificabile/cancellabile
     * se l'istanza non è chiusa e se la data del movimento che si vuole inserire è successiva a quella di chiusura
     * istanza (TIPIPROCEDURE.IDCHIUSURAISTANZA). Se il movimento di chiusura non è stato specificato (nella procedura
     * dell'istanza) o non è stato ancora eseguito posso inserire / modificare il movimento (anche se l'istanza si trova
     * in uno stato di chiusura).
     * 
     * @param entity
     */
    public boolean isMovimentoModificabile(Movimenti entity);

    /**
     * La funzione controlla se il movimento deve essere notificato tramite STC
     * 
     * @param entity
     */
    public MovimentoDaNotificare isMovimentoDaNotificareSTC(Integer codiceMovimento);

    /**
     * Metodo per la gestione in automatico della notifica ad STC
     * 
     * @param entity
     */
    public void notificaStc(Movimenti entity);

    /**
     * La funzione esegue l'aggiornamento del campo CODICEAMMINISTRAZIONE_STC del movimento indicato dal codice
     * 
     * @param entity
     */
    public void updateAmministrazioniStc(Integer codiceMovimento, Integer codiceAmministrazioneStc);

    /**
     * 
     * @param entity
     */
    public void operazioniAutomatiche(Movimenti entity) throws OperazioniAutomaticheException;

    /**
     * <pre>
     * Ritorna il numero di record presenti nella tabella filtrata per inventarioprocedimento  (parametro codiceProcedimento)
     * 
     * &#64;param codiceIstanza
     * &#64;return
     * </pre>
     */
    public int countByInventarioprocedimento(Integer codiceProcedimento);

    public List<Movimenti> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult);

    /**
     * <pre>
     * Torna la lista dei movimenti eseguiti (data non nulla) per la lista di istanze passate ordinati 
     *   istanza
     *   data asc
     *   ordineInserimento asc
     *   codicemovimento asc
     * 
     * &#64;param istanze
     * &#64;return
     * 
     * </pre>
     */
    /**
     * ritorna una lista di movimenti effettutati per le istanze passate e ordinati per istanza.
     * 
     * @param istanzes
     * @return
     */
    public Set<Movimenti> findEseguitiByIstanze(List<Istanze> istanzes);

    /**
     * <pre>
     * Ritorna il numero di movimenti con flag_da_leggere = true
     * 
     * </pre>
     * 
     * @param batchScadenzarioFilter
     * 
     */
    public int countMovimentiDaLeggere(BatchScadenzarioFilter batchScadenzarioFilter);

    /**
     * @param batchScadenzarioFilter
     * 
     * @return
     */
    public List<Movimenti> findMovimentiDaLeggere(BatchScadenzarioFilter batchScadenzarioFilter, int startRowPage, int endRowPage);

    /**
     * @param batchScadenzarioFilter
     * 
     * @return
     */
    public List<MovimentiDTO> findMovimentiDTODaLeggere(BatchScadenzarioFilter batchScadenzarioFilter, int startRowPage, int endRowPage);

    /**
     * ritorna il numero di movimenti che attivano la comunicazione con STC TIPIMOVIMENTO.FLAG_STC=1 e che non sono
     * stati ancora notificati MOVIMENTI.INVIATO_CON_STC=0.
     * 
     * @param batchScadenzarioFilter
     * 
     * 
     * @return
     */
    public int countMovimentiSTCNonNotificati(BatchScadenzarioFilter batchScadenzarioFilter);

    /**
     * @param batchScadenzarioFilter
     * 
     * @return
     */
    public List<Movimenti> findMovimentiSTCNonNotificati(BatchScadenzarioFilter batchScadenzarioFilter, int startRowPage, int endRowPage);

    public List<MovimentiDTO> findMovimentiDTOSTCNonNotificati(BatchScadenzarioFilter batchScadenzarioFilter, Integer firstResult,
	    Integer MaxResults);

    /**
     * Il metodo serve per aggiornare una proprietà booleana della tabella movimenti senza passare per la validazione
     * 
     * @param codice
     * @param propertyToUpdate
     * @param value
     */
    public void updateBooleanProperty(Integer codice, String propertyToUpdate, Boolean value);

    /**
     * Il metodo serve per aggiornare una proprietà di tipo Integer della tabella movimenti senza passare per la
     * validazione
     * 
     * @param codice
     * @param propertyToUpdate
     * @param value
     */
    public void updateIntegerProperty(Integer codice, String propertyToUpdate, Integer value);

    /**
     * Ritorna il numero dei movimenti che devo essere ancora mandati in commissioni filtrati per commissio e data
     * maggiore di quella passata
     * 
     * @param date
     * @param commissioniedilizieT
     * @return
     */
    public int countMovimentiDaAssociareAllaCommissione(Date date, CommissioniedilizieT commissioniedilizieT);

    public List<Movimenti> findByFilterTable(FilterTable ft, Integer valueOf, Integer valueOf2);

    /**
     * Ritorna la lista dei movimenti fatti dell'istanza filtrati per codice tipo movimento (ordinati per data desc)
     * 
     * @param codTipomovimento
     * @param codiceIstanza
     * @return
     */
    public List<Movimenti> findMovimentiIstanzaFattiByTipoMovimento(String codTipomovimento, Integer codiceIstanza);

    /**
     * Ritorna la lista dei movimenti da fare (data == null) dell'istanza filtrati per codice tipo movimento
     * 
     * @param codTipomovimento
     * @param codiceIstanza
     * @return
     */
    public List<Movimenti> findMovimentiIstanzaDaFareByTipoMovimento(String codTipomovimento, Integer codiceIstanza);

    public List<Movimenti> findMovimentiByIstanzeprocedimentiAndAmministrazione(Istanzeprocedimenti entity, Integer codiceAmministrazione,
	    SceltaMovimentiEnum eseguiti);

    /**
     * Ritorna la lista dei movimenti associatio all'istanza escludendo quello passato, se non viene passato nessun
     * codice movimento, non escludera niete.
     * 
     * @param codiceIstanza
     * @param codiceMovimento
     * @return
     */
    public List<Movimenti> findByIstanzaAndExcludeMovimento(Integer codiceIstanza, Integer codiceMovimento, SceltaMovimentiEnum sceltaMovimentiEnum);

    public void evict(Movimenti movimento);

    int countByTipimovimento(String tipomovimento);

    public List<ChiaveValoreBean<String, Integer>> countMovimentiSTCConAnomalie();

    /**
     * Rimuove il messaggio di notifica con errore (es 239). Logga l'operazione e scrive in un evento i dati che stanno
     * in idAttDest e statoAttDest. Setta il movimento come non notificato.
     * 
     * @param codice
     */
    public void updateRimuoviNotificaConErrore(Integer codiceMovimento);

    /**
     * Se il movimento
     * 
     * @param tipomovimento
     * @param codiceAmministrazioneStc
     * @return
     */
    boolean verificaSeNotificareSubEndo(String tipomovimento, Integer codiceAmministrazioneStc);

    /**
     * Recupera tutti i documenti all'interno dell'oggetto che appartengono allo zip logico del movimento e li raggruppa
     * in un archivio zip
     * 
     * @param documentiHelper
     * @return
     */
    public ByteArrayOutputStream downloadDocumentiZipLogico(Integer codiceMovimento);

    /**
     * Valido la richiesta di downloadzip confrontando l'uiid istanza sia del movimento
     * 
     * @param codiceMovimento
     * @param uuidIstanza
     */
    public boolean validateDownloadZipLogico(Integer codiceMovimento, String uuidIstanza);

    Movimenti findMovimentiByTipoMovimentoAndDataAndAmministrazione(Integer codiceIstanzaDestinazione, String tipomovimento, Date data,
	    Integer codiceAmministrazione);

    public Movimenti findDataByTipoMovandcodIstanza(String tipomovimento, Integer codiceIstanza);

    public void eseguiFormuleDelleSchedeDinamiche(Integer codicemovimento) throws FunzioneBusinessRemotaException;

    /**
     * È movimento CDS se:
     * <ul>
     * <li>il tipomovimento ha flag_cds=true</li>
     * <li>il tipomovimento è uguale al movimento specificato nel campo TIPIPROCEDURE.IDCOMUNCDS</li>
     * <li>il tipomovimento è uguale al movimento specificato nel campo CONFIGURAZIONE.TT.IDCDSVPR</li>
     * </ul>
     * 
     * @param movimento
     * @return
     */
    boolean isMovimentoCDS(Movimenti movimento);

    Date getDataMovimentoDaElaborare(Movimenti entity);

    void updateStatoistanza(Movimenti movimento);

    void updateRiferimentiProtocolloMovimento(AggiornaRiferimentiProtocolloMovimentoRequest request) throws AggiornamentoProtocolloException;

    void updateRiferimentiProtocolloMovimentoAvvio(AggiornaRiferimentiProtocolloMovimentoRequest request) throws AggiornamentoProtocolloException;

    MovimentiRabbitTestoBean replaceTestoPerMovimentoeTopic(Integer codiceMovimento, String topic);

    Movimenti findMovimentoByUuId(String uuid);
}
