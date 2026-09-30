package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.paevolution.ws.pagamenti_types.ElencoDocumentiEsitoType;
import com.paevolution.ws.pagamenti_types.InfoConnettoreType;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.BollettazioneBean;
import it.gruppoinit.pal.gp.core.domain.Borsellino;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.IstanzeoneriPosdebBatchId.TipoDocumentoDaGenerare;
import it.gruppoinit.pal.gp.core.domain.PageResult;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.RuoloAutorizzazioneEnum;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti.AttivaSessionePagamentoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti.AttivaSessionePagamentoResponseBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti.StatoPagamentoNodoHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti.StatoPagamentoPosDebHelper;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.RipartizioneContiHelper;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.PosizioneDebitoriaBorsellinoRest;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.vigili.PosizioneDebitoriaModelEsteso;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DatiPagamento;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.FiltroPagamentoEnum;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.ConnettoreType;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.ConnettoriListType;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.PosizioneDebitoriaResponseType;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato.VerificaStatoPosizioniDebitorie;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoPosizDebRestHelperV2;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoPosizDebRestHelperV2Paged;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoRestHelper;

public interface NodoPagamentiService {

    /**
     * Il metodo viene invocato per inserire una posizione debitoria per gli spuntisti e per i concessionari
     * 
     * 
     * @param idMercatiPresenzaId
     * @return
     */
    public Integer registraPosizioneDebitoriaDaPresenzaSuMercato(Integer idMercatiPresenzaId) throws FunzioneBusinessRemotaException;

    /**
     * La funzionalità richiama il nodo dei pagamenti per l'inserimento della posizione debitoria passata Inserisce
     * nella tabella DETT_POSIZIONE_DEBITORIA e ritorna l'identificativo della riga inserita
     * 
     * @param datiPosizione
     * @param caricamentoMassivo
     *            se true invoca l'API DI CARICAMENTO MASSIVO
     * @return
     * @throws FunzioneBusinessRemotaException
     */
    public List<Integer> registraNuovaPosizioneDebitoria(PosizioneDebitoriaBean datiPosizione, boolean caricamentoMassivo,
	    String identificativoOperazione) throws FunzioneBusinessRemotaException;

    public StatoPagamentoNodoHelper getStatoPagamentoSpuntistaHelper(Integer idPresenza);

    public StatoPagamentoPosDebHelper getStatoPagamentoPosDebHelper(Integer dettPosizioneDebitoriaId, boolean errore);

    public void annullaPosizioneDebitoriaSpuntista(Integer idPresenza) throws FunzioneBusinessRemotaException;

    public List<PagamentiMercatoRestHelper> getPagamentoByAutorizzazioneComune(MercatiService mercatiService, String numeroAutorizzazione,
	    String codiceComune);

    public AttivaSessionePagamentoResponseBean attivaSessionPagamento(AttivaSessionePagamentoBean bean) throws FunzioneBusinessRemotaException;

    public byte[] getQrCodePagamento(Integer dettPosizioneDebitoriaId);

    /**
     * 
     * @param mercatiService
     * @param r
     * @param verificaStatoPosizione
     *            se true allora esegue una verifica stato sul nodo pagamenti
     * @param consideraAncheIlProprietarioTraLeAnagrafiche
     *            ritorna i pagamenti anche se titolare
     * @return
     */
    public List<PagamentiMercatoRestHelper> getPagamentiAttiviByUtente(MercatiService mercatiService, Anagrafe r, boolean verificaStatoPosizione,
	    boolean consideraAncheIlProprietarioTraLeAnagrafiche, RuoloAutorizzazioneEnum ruolo, FiltroPagamentoEnum statoPagamento, Date dallaData,
	    boolean soloAutorizzazioniAttive);

    public boolean isAttivoNodoPagamenti(Integer mercatipresenzeTID);

    /**
     * Contrassegna la posizione debitoria come pagata indicando la metodologia di pagamento (es: casi di pagamenti
     * offline voucher, o altra forma di pagamento)
     * 
     * @param idPosizioneDebitoria
     * @throws FunzioneBusinessRemotaException
     */
    public void updatePosizioneDebitoriaSegnaPagataOfflineSenzaRiferimentiPagamento(Integer dettPosizioneDebitoriaId) throws FunzioneBusinessRemotaException;

    public void updatePosizioneDebitoriaSegnaPagataOfflineConRiferimentiPagamento(DatiPagamento datiPagamento) throws FunzioneBusinessRemotaException;

    /**
     * Esegue l'annullamento della posizione debitoria senza sganciarla dalle eventuali dipendenze (es spuntisti)
     * 
     * @param idPosizioneDebitoria
     * @throws FunzioneBusinessRemotaException
     */
    public void annullaPosizioneDebitoria(Integer dettPosizioneDebitoriaId) throws FunzioneBusinessRemotaException;

    /**
     * Esegue la chiamata al WS del nodo dei pagamenti per connoscere lo stato dei pagamenti passati nella lista
     * 
     * @param idPosizioniDebitorie
     * @param cfEnteCreditore
     * @return
     * @throws FunzioneBusinessRemotaException
     */
    public List<VerificaStatoPosizioniDebitorie> verificaStatoPosizioniDebitorieByIdPosizioneDebitoria(Set<Integer> fkIdPosizioniDebitorie,
	    String cfEnteCreditore) throws FunzioneBusinessRemotaException;

    /**
     * Richiama il metodo {@link #verificaStatoPosizioniDebitorieByIdPosizioneDebitoria(Set)} a partire da
     * DETT_POSIZIONE_DEBITORIA.ID
     * 
     * @param idDettaglioPosizioneDebitoria
     * @return
     * @throws FunzioneBusinessRemotaException
     */
    public VerificaStatoPosizioniDebitorie verificaStatoPosizioneDebitoriaByIdDettaglio(Integer idDettaglioPosizioneDebitoria)
	    throws FunzioneBusinessRemotaException;

    /**
     * Aggiorna lo stato pagamento invocando il WS per la posizione debitoria passata
     * 
     * @param idDettaglioPosizioneDebitoria
     * @throws FunzioneBusinessRemotaException
     **/
    VerificaStatoPosizioniDebitorie aggiornaStatoPagamentoByIdDettPosizioneDebitoria(Integer idDettaglioPosizioneDebitoria)
	    throws FunzioneBusinessRemotaException;

    /**
     * Aggiorna lo stato pagamento invocando il WS per la posizione debitoria passata
     * 
     * @param idDettaglioPosizioniDebitorie
     * @param cfEnteCreditore
     * @throws FunzioneBusinessRemotaException
     **/
    Set<VerificaStatoPosizioniDebitorie> aggiornaStatoPagamentoByIdDettPosizioneDebitoria(Set<Integer> idDettaglioPosizioniDebitorie,
	    String cfEnteCreditore) throws FunzioneBusinessRemotaException;

    /**
     * Aggiorna lo stato pagamento invocando il WS per le posizioni debitorie passate
     * 
     * @param fkIdPosizioniDebitorie
     * @param cfEnteCreditore
     * @throws FunzioneBusinessRemotaException
     **/
    Set<VerificaStatoPosizioniDebitorie> aggiornaStatoPagamentoByIdRiferimentoPosizioneDebitoria(Set<Integer> fkIdPosizioniDebitorie,
	    String cfEnteCreditore) throws FunzioneBusinessRemotaException;

    /**
     * Il metodo verifica, anche chiamando il WS se necessario, se una posizione debitoria è stata pagata o meno
     * 
     * @param idDettaglioPosizioneDebitoria
     * @return
     */
    boolean isPagamentoEffettuato(Integer idDettaglioPosizioneDebitoria);

    /**
     * La lista degli oneri passati <b>DEVE</b> appartenere alla stessa istanza.</br />
     * La posizione debitoria sarà associata al soggetto principale della pratica (richiedente o azienda a seconda di
     * quanto specificato in {@link IVerticalizzazioneNodoPagamentiService#soggettoPendenza(String) };
     * 
     * @param codiciIstanzeoneri
     * @param isRateizzato
     * @return
     * @throws FunzioneBusinessRemotaException
     */
    public List<PosizioniDebitorieIstanzeoneriBean> inserisciPosizionidebitorieDaIstanzeOneri(Set<Integer> codiciIstanzeoneri, boolean isRateizzato)
	    throws FunzioneBusinessRemotaException;

    /**
     * La lista degli oneri passati <b>DEVE</b> appartenere alla stessa istanza
     * 
     * @see {@link #inserisciPosizionidebitorieDaIstanzeOneri(Set, boolean)}
     * @param codiciIstanzeoneri
     * @param codiciAnagrafeSoggettiAggiuntivi
     *            la lista dei soggetti anagrafici della pratica per la quale si vogliono creare altre posizioni
     *            debitorie oltre a quella del richiedente (ovvero SoggettoPendenza principale)
     * @param isRateizzato
     * @return
     * @throws FunzioneBusinessRemotaException
     * 
     */
    public List<PosizioniDebitorieIstanzeoneriBean> inserisciPosizionidebitorieDaIstanzeOneri(Set<Integer> codiciIstanzeoneri,
	    Set<Integer> codiciAnagrafeSoggettiAggiuntivi, boolean isRateizzato) throws FunzioneBusinessRemotaException;

    /**
     * 
     * @param codiciIstanzeoneri
     * @param isRateizzato
     * @return
     */
    public List<String> validaInserimentoPosizioniDebitorieDaIstanzeOneri(Set<Integer> codiciIstanzeoneri);

    /**
     * Il metodo verifica se presente il tipo documento richiesto per la posizione debitoria passata e tipoDocumento
     * 
     * @param idposizionedebitoria
     * @param tipoDocumento
     * @param codiceComune
     * @return
     */
    public boolean checkDocumentiForDettPosizioneDebitoria(Integer idDettPosizioneDebitoria, TipoDocumentoDaGenerare tipoDocumento,
	    String codiceComune) throws FunzioneBusinessRemotaException;

    public ElencoDocumentiEsitoType generaFattura(Integer idDettPosizioneDebitoria) throws FunzioneBusinessRemotaException;

    public ElencoDocumentiEsitoType inviaAvviso(Integer idDettPosizioneDebitoria) throws FunzioneBusinessRemotaException;

    public PosizioneDebitoriaResponseType dettaglioPosizioneDebitoria(Integer idDettPosizioneDebitoria) throws FunzioneBusinessRemotaException;

    public boolean isFunzionalitaAvvisoAbilitatoPerConnettore(DettPosizioneDebitoria dettPosizioneDebitoria) throws FunzioneBusinessRemotaException;

    public InfoConnettoreType getInfoConnettoreByIdPosizioneDebitoria(DettPosizioneDebitoria dettPosizioneDebitoria)
	    throws FunzioneBusinessRemotaException;

    public InfoConnettoreType getInfoConnettore(String codiceComune) throws FunzioneBusinessRemotaException;

    public ElencoDocumentiEsitoType scaricaRicevutaTelematica(Integer idDettPosizioneDebitoria) throws FunzioneBusinessRemotaException;

    /**
     * Il metodo invoca il servizio di aggiornamento della data scadenza della posizione debitoria. se l'operazione ha
     * succecsso viene lanciato un Evento
     * 
     * @param idDettPosizioneDebitoria
     * @param datascadenza
     * @throws FunzioneBusinessRemotaException
     */
    public void modificaDataScadenzaPosizioneDebitoria(Integer idDettPosizioneDebitoria, Date datascadenza) throws FunzioneBusinessRemotaException;

    /**
     * Il metodo invoca il ws per recuperare tutte le liste dei profili del nodo
     * 
     * 
     * @return
     * @throws Exception
     */
    public List<ConnettoriListType> getMappaturaConnettore();

    @DeletableCacheElements
    public void resetObjectCached();

    public List<Istanzeoneri> findOneriInviabiliANodoPagamenti(Integer codiceIstanza, Integer codiceTipiCausali) throws Exception;

    ConnettoreType getMappaturaConnettore(String arCfEnteCreditore, String urlWs);

    String getCodiceVersamentoOrDefaultFromMappatura(ConnettoreType mappaturaConnettore, String codiceMappaturaClient, String defaultVersamento);

    /**
     * Crea la posizione debitoria di ricarica per un borsellino
     * 
     * @param codiceComune
     * @param importo
     * @param borsellino
     * @param ripartizione
     * @return
     */
    public DettPosizioneDebitoria creaPosizionePerBorsellino(String codiceComune, BigDecimal importo, Borsellino borsellino,
	    RipartizioneContiHelper ripartizione) throws FunzioneBusinessRemotaException;

    public PosizioneDebitoriaBorsellinoRest populatePosizioneDebitoriaBorsellino(Integer dettPosizioneDebitoriaId);

    public PosizioneDebitoriaModelEsteso getPosizioneDebitoriaModelEsteso(Integer idDettPosizioneDebitoria) throws FunzioneBusinessRemotaException;

    public List<PagamentiMercatoPosizDebRestHelperV2> getPagamentiAttiviByUtenteV2(MercatiService mercatiService, Anagrafe r,
	    boolean verificaStatoPosizioni, boolean consideraAncheIlProprietarioTraLeAnagrafiche, RuoloAutorizzazioneEnum ruolo,
	    FiltroPagamentoEnum statoPagamento, Date consideraIPagamentiDallaData, boolean soloAttive);

    public PagamentiMercatoPosizDebRestHelperV2Paged getPagamentiAttiviByUtenteV2Paged(MercatiService mercatiService, Anagrafe r,
	    boolean verificaStatoPosizioni, boolean consideraAncheIlProprietarioTraLeAnagrafiche, RuoloAutorizzazioneEnum ruolo,
	    FiltroPagamentoEnum statoPagamento, Date consideraIPagamentiDallaData, boolean soloAttive, int page, int pageSize);


    public String codificaIdPagamento(Integer idPosizioneDebitoria, String codiceComune);

    public PageResult<BollettazioneBean> getPosizioneDebitorieBollettazione(Integer[] autorizzazioniIds, boolean validata,
	    FiltroPagamentoEnum filtroPagamentoEnum, int pagina, int sizePagina);

    public void modificaDataFineValiditaPosizioneDebitoria(Integer idDettPosizioneDebitoria, Integer idBlackList, Date datafinevalidita) throws FunzioneBusinessRemotaException;
}
