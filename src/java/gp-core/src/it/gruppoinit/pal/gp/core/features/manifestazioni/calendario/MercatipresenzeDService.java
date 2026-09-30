package it.gruppoinit.pal.gp.core.features.manifestazioni.calendario;

import java.util.Date;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.MercatipresenzeDDAO;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiPresenzeDTO;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDDTO;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniRestHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.GiornataMercatoSpuntistaRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PosteggioInfoRestBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.model.PosteggioPerAutBean;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.ValoriLivelloServizio;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.model.DettaglioPresenzaComunicazioneModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.utils.PresenzeNonPagateBean;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.FiltroPagamentoEnum;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatiHelper;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoPosizDebRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.PosteggioLiberoHelper;
import it.gruppoinit.pal.gp.core.service.helper.PresenzeSpuntistiHelper;

public interface MercatipresenzeDService extends BaseService<MercatipresenzeD, PkId> {

    /**
     * @see MercatipresenzeDDAO#findByMercatiPresenzeTAndPosteggio(MercatipresenzeT, MercatiD)
     * @param giornoMercato
     * @param posteggio
     * @return
     */
    public MercatipresenzeD findByMercatiPresenzeTAndPosteggio(Integer idGiornataMercato, Integer idPosteggio);

    /**
     * @see MercatipresenzeDDAO#findByMercatiPresenzeTAndAutorizzazione(MercatipresenzeT giorno, Integer
     *      idAutorizzazione)
     * @param giornoMercato
     * @param idAutorizzazione
     * @return
     */
    //public MercatipresenzeD findByMercatiPresenzeTAndAutorizzazione(MercatipresenzeT giorno, Integer idAutorizzazione);
    public MercatipresenzeD findByMercatiPresenzeTAndAutorizzazione(Integer giornoMercato, Integer idAutorizzazione);

    /**
     * @see MercatipresenzeDDAO#findListaPosteggi(MercatipresenzeT)
     * @param giorno
     * @return
     */
    //public List<MercatipresenzeD> findListaPosteggi(MercatipresenzeT giorno);
    public List<MercatipresenzeDDTO> findListaPosteggi(MercatipresenzeT giorno);

    /**
     * 
     * @param codiceMercatopresenzaT
     * @return
     */
    public List<MercatipresenzeDDTO> findByMercatipresenzaT(Integer codiceMercatopresenzaT);

    /**
     * metodo per determinare i concessionari o occupanti del posteggio
     * 
     * @param posteggio
     * @return
     */
    public MercatipresenzeD findByMercatiPosteggio(MercatiD posteggio);

    /**
     * Torna la lista delle MercatipresenzeD di un occupante
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<MercatipresenzeD> findByAnagrafeOccupante(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle MercatipresenzeD di un concessionario
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<MercatipresenzeD> findByAnagrafeConcessionario(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);

    /**
     * verifica se è la prima volta che accedo al dettaglio della giornata di mercato. se è la prima volta la lista di
     * mercatipresenzed collegata è vuota
     * 
     * @param mercatipresenzeT
     * @return
     */
    //public boolean isFirstAccess(MercatipresenzeT mercatipresenzeT);
    public List<PresenzeSpuntistiHelper> findListaPresentiSenzaPosteggio(MercatipresenzeT giorno);

    /**
     * Metodo per verificare se un giorno di mercato può essere chiuso. Il giorno può essere chiuso se c'è almeno un
     * posteggio assegnato
     * 
     * @return boolean
     */
    public boolean isCloseMarketDayAllowed(MercatipresenzeT mercatipresenzeT);

    public MercatipresenzeD findSpuntistaNoPosteggio(MercatipresenzeT giorno, Integer codiceAutorizzazione);

    public List<MercatipresenzeD> findSpuntisti(MercatipresenzeT giorno);

    /**
     * somma delle presenze ricavate da MERCATIPRESENZE_T per quel mercato, uso, autorizzazione
     * 
     * @param autorizzazione
     *            OBBLIGATORIO
     * @param mercato
     *            OBBLIGATORIO
     * @param uso
     *            OBBLIGATORIO
     * @param posteggio
     *            OPZIONALE (se specificato ricava la somma delle presenze fatte sul posteggio)
     * @param catMerc
     *            OPZIONALE (se specificato ricava la somma delle presenze fatte con la categoria merceologica
     *            specificata)
     * @param anno
     *            OPZIONALE (se specificato ricava la somma delle presenze fatte nell'anno)
     * @param giorno
     *            (OPZIONALE per i mercati) (OBBLIGATORIO per le fiere, il calcolo deve essere fatto per l'ultimo
     *            giorno)
     * @param sommaAssenzeGiustificate
     *            false: utilizza l'autorizzazione come filtro nella colonna FK_AUTORIZZAZIONI_ID di
     *            MERCATIPRESENZE_D.<br >
     *            true: utilizza l'autorizzazione come filtro nella colonna AUT_CONCESSIONARIO di MERCATIPRESENZE_D e
     *            FLAG_ASSENZA_GIUST=1
     * @return
     */
    public MercatiPresenzeDTO findSommaDellePresenzeDaiCalendari(Autorizzazioni autorizzazione, Mercati mercato, MercatiUso uso, MercatiD posteggio,
	    String catMerc, Integer anno, MercatipresenzeT giorno, boolean sommaAssenzeGiustificate);

    public List<MercatipresenzeD> findByMercatiPresenzeT(Integer codiceMercatopresenzaT);

    @Override
    public void update(MercatipresenzeD entity);

    @Override
    public void insert(MercatipresenzeD entity);

    @Override
    public void delete(MercatipresenzeD entity);

    /**
     * Per i mercati il cui tipo conteggio sia uguale a 2 (
     * {@link WebConstants#MERCATI_CONTEGGIO_PRESENZE_ASSEGNA_SINGOLA}) elabora tutti i record di mercatipresenzed con
     * lo stesso codiceanagrafe e fk_idautorizzazione e imposta il campo numeropresenze solamente nell'ultima giornata
     * 
     * @param codiceMercato
     * @param anno
     * @return
     */
    public boolean updateConsolidaPresenzePerAnno(Integer codiceMercato, Integer anno);

    public List<String> avvisiSpuntisti(Integer idpresenzad);

    public List<PosteggioLiberoHelper> findListaPosteggiLiberi(Integer codiceGiornata);

    @Deprecated
    public PagamentiMercatiHelper findPagamentiByCf(String cfOccupante);

    public void updateSegnaRinunciaPosteggio(Integer presenzaCodice, Integer idposteggio);

    public void updateRimuoviRinunciaPosteggio(Integer presenzaCodice);

    public MercatipresenzeD isAssegnatoPosteggioASpuntistaAndAutNellaGiornata(Integer codiceMercato, Integer usoMercato, Integer autId,
	    Date giornoMercato);

    public MercatipresenzeD findUltimaPresenza(Integer codiceMercato, Integer codiceUso, Integer codiceAutorizzazione);

    public void updateAzzeraPresenzeByAutorizzazioneAndMercato(Integer codiceAutorizzazione, Integer codiceMercato, Integer codiceuso);

    public List<GiornataMercatoSpuntistaRestBean> findSpuntistiGiornataMercatoRest(Integer idGiornata);

    /**
     * Passando l'id dell'autorizzazione permette di ritonare il singolo spuntista e non la lista contenuta nell'intera
     * giornta
     * 
     * @param idGiornata
     * @param idAut
     * @return
     */
    public GiornataMercatoSpuntistaRestBean findSpuntistiGiornataMercatoRest(Integer idGiornata, Integer idAut);

    /**
     * Il metodo verifica che l'autorizzazione usata non sia già stata registrata in altri mercati (con presenza DA
     * VERIFICARE) e che non sia una concessione di quello stesso mercato/giorno, devo anche verificare che una
     * concessione non possa presentarsi come spuntista in altro mercato. devo anche verificare che non sia presente
     * come concessionario sullo stesso mercato dove non può essere anche spuntista. Se la giornata è impostata con
     * <b>FLAG_POPOLA_CONCESSIONARI=0</b> (false) allora il controllo non lo devo fare
     * 
     * @param idGiornata
     * @param idAutorizzazione
     * @return
     */
    public List<String> verificaAutorizzazionePresenteSuAltriMercati(Integer idGiornata, Integer idAutorizzazione);

    /**
     * <pre>
     * Il metodo fa una copia dei capi di mercatipresenzed da un oggetto origine ad un oggetto destinatario, l'oggetto
     * destinatario deve essere inizializzato.
     * Il metodo NON FA INSERT!!!!. I Campi copiati sono :
     * 
     *  <b>MercatiSpunte 
     *  Attivita 
     *  importo 
     *  flagPagato 
     *  Tipimodalitapagamento  
     *  riferimentiPagamentoOrig
     *  flagRinunciaPresenzaOrig 
     *  mercatiDOrig</b>
     *  
     *  Il metodo è usato in fase di collegamento/scollegamanto spuntista da un posteggio sull'app
     * </pre>
     **/
    public void copiaInformazioniMercatopresenzeDOrigine(MercatipresenzeD origine, MercatipresenzeD destinatario);

    /**
     * 
     * @param idAutorizzazione
     * @param isSpuntista
     *            se fare le ricerche per le autorizzazioni degli spuntisti
     * @param verificaStatoPosizione se true allora esegue una verifica stato sul nodo pagamenti           
     * @return
     */
    public List<PagamentiMercatoRestHelper> getPosizioniDebitoriePerAutorizzazione(Integer[] idAutorizzazione, boolean verificaStatoPosizione,
	    FiltroPagamentoEnum statoPagamento, Date consideraIPagamentiDallaData);

    public PagamentiMercatoPosizDebRestHelper populatePosizioneDebitoriaHelper(MercatipresenzeD pre);

    /**
     * 
     * <ul>
     * <li>Se attivo il nodo pagamenti e la posizione debitoria è nulla torna false</li>
     * <li>Se attivo il nodo pagamenti e la posizione debitoria non è nulla torna il valore della funzione
     * payStatoPagamentiService.statoPagatoPosizioneDebitoria(StringUtils.defaultString(s.getStato()));</li>
     * <li>Se non attivo il nodo pagamento ritorna il valore BooleanUtils.toBoolean(arh.getPagamentoEffettuato());</li>
     * </ul>
     * 
     * 
     * @param arh
     * @param isAttivoNodoPagamenti
     * @return
     */
    public Boolean verificaAndAggiornaStatoPagamenti(AutorizzazioniRestHelper arh, boolean isAttivoNodoPagamenti);

    /**
     * @see #verificatoStatoPagamenti(AutorizzazioniRestHelper, boolean)
     * @param posizioneDebitoriaSpuntistaId
     * @param flagPagato
     * @param isAttivoNodoPagamenti
     * @return
     */
    public Boolean verificaAndAggiornaStatoPagamenti(Integer idDettaglioPosizioneDebitoria, Boolean flagPagato, boolean isAttivoNodoPagamenti);

    public List<MercatipresenzeDDTO> findByMercatiPresenzeTAndPosteggi(Integer idGiornata, List<Long> idPosteggi);

    /**
     * Ritorna le presenze associate a quella posizione debitoria (in teoria sempre una),
     * 
     * 
     * @param ppId
     *            l'identificativo della posizione debitoria
     * @param escludiLePosizioniSenzaPosteggio
     *            se true allora esclude le righe delle posizioni debitorie "Sganciate" dal posteggio. se ne trova
     *            qualcuna sganciata dal posteggio dovrebbe essere una anomalia in quanto possono esistere posizioni
     *            dedbitorie solo per spuntisti che hanno occupato il posteggio
     * @return
     */
    public List<MercatipresenzeD> findByPosizioneDebitoria(Integer dettPosizioneDebitoriaId, boolean escludiLePosizioniSenzaPosteggio);

    /**
     * Il metodo verifica se il concessionario era presente o no (non importa se assenza giustificata o meno) in una
     * determinata giornata. se non trovo la giornata non devo rilanciare l'errore in quanto per la bollettazione dovrei
     * poter trovare anche le giornate non registrate nel DB. Torno false in questo caso
     * 
     * @param idGiornata
     * @param idPosteggio
     * @return
     */
    public boolean isConcessionarioPresenteByIdGiornataEIdPosteggio(Integer idGiornata, Integer idPosteggio);

    /**
     * Il metodo verifica se al posto di un concessionario è stata fatta la presenza da uno spuntista se non trovo la
     * giornata non devo rilanciare l'errore in quanto per la bollettazione dovrei poter trovare anche le giornate non
     * registrate nel DB. Torno false in questo caso
     * 
     * @param idGiornata
     * @param idPosteggio
     * @return
     */
    public boolean isSpuntistaPresenteByIdGiornataEIdPosteggio(Integer idGiornata, Integer idPosteggio);

    public List<ValoriLivelloServizio> livelliDiServizioConfiguratiPerIdGiornataEIdPosteggio(Integer idGiornata, Integer idPosteggio);

    /**
     * se non trovo la giornata non devo rilanciare l'errore in quanto per la bollettazione dovrei poter trovare anche
     * le giornate non registrate nel DB. Torno false in questo caso
     * 
     * @param idGiornata
     * @param idPosteggio
     * @return
     */
    public boolean isAssenzaGiustificata(Integer idGiornata, Integer idPosteggio);

    /**
     * Il metodo ritorna lo stato di tutte le posizioni debitorie legate alla giornata passata
     * 
     * @param idgiornata
     * @return
     */
    public StatiPosizioniDebitorieGiornataMercatoBean verificaStatoPosizioniDebitoriePerGiornata(Integer idGiornata);

    /**
     * Il metodo ritorna lo stato di tutte le posizioni debitorie legate alla giornata passata
     * 
     * @param idgiornata
     * @return
     */
    public StatiPosizioniDebitorieGiornataMercatoBean verificaStatoPosizioniDebitoriePerAutorizzazioni(Integer idgiornata,
	    List<Integer> autorizzazioni);

    /**
     * Il metodo torna le presenze dei concessionari senza posizioni debitorie e che non siano state associate a
     * movimenti di borsellino
     */
    public List<Integer> findPresenzeConcessionariSenzaPosizioniDebitorie(Date data);

    /**
     * Trova la lista delle presenze sulle quali non è presente la posizione debitoria o un pagameto tramite borsellino
     * a partire da un rabge di date.
     * 
     * @param dalladata
     * @param alladata
     * @param codiceIstat
     * @param tipologia
     * @return
     */
    public List<PresenzeNonPagateBean> findPresenzeNonAssociateAMetodoDiPagamento(Date dalladata, Date alladata, String[] codiceIstat);

    PosteggioInfoRestBean calcolaCostoPosteggio(Integer idMercatipresenzeD);

    List<DettaglioPresenzaComunicazioneModel> findPresenzeScalateDalBorsellino(int idGiornata);

    /**
     * Il metodo torna la lista delle presenze fatte/registrate dalla concessione sul mercato sulla quale la concessione
     * è attiva
     * 
     * E' usato per verificare se posso scambiare il posteggio tra due autorizzazioni
     * 
     * @param autConc
     * @param dataCessazione
     * @param firstresult
     * @param maxResults
     * @return
     */
    public List<MercatipresenzeD> findPresenzeConcessionarioDallaData(AutorizzazioniConcessioni autConc, Date dataCessazione, Integer firstresult,
	    Integer maxResults);

    /**
     * Il metodo torna il conteggio delle presenze fatte/registrate dalla concessione sul mercato sulla quale la
     * concessione è attiva.
     * 
     * E' usato per verificare se posso scambiare il posteggio tra due autorizzazioni
     * 
     * 
     * @param autConc
     * @param dataCessazione
     * @param firstresult
     * @param maxResults
     * @return
     */
    public int countPresenzeConcessionarioDallaData(AutorizzazioniConcessioni autConc, Date dataCessazione);

    public List<PosteggioPerAutBean> findAutorizzazioniSganciateDaSIAP(Set<Integer> autSenzaPosteggi);
}
