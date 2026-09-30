package it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.paevolution.ws.pagamenti_types.EsitoDocumentoPosizioneDebitoriaType;
import com.paevolution.ws.pagamenti_types.VerificaStatoPosizioniResponseType;

import it.gruppoinit.pal.gp.core.domain.BollettazioneBean;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.PageResult;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti.AttivaSessionePagamentoResponseBean;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatoPagamentiBreveJson;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatoPagamentiDettaglioJson;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.DettPosizioneDebitoriaProvenienzaEnum;
import it.gruppoinit.pal.gp.core.service.BaseService;

/**
 * 
 * @author
 */
public interface DettPosizioneDebitoriaService extends BaseService<DettPosizioneDebitoria, PkId> {

    /**
     * @see DettPosizioneDebitoriaDAO#findAll(Integer, Integer)
     */
    public List<DettPosizioneDebitoria> findAll(Integer firstResult, Integer maxResult);

    /*
     * Il metodo la classe DettPosizioneDebitoria partendo dal riferimento della posizione debitoria del nodo dei pagamenti
     */
    public DettPosizioneDebitoria findByFkIdPosizioneDebitoriaAndCfEnteCreditore(Integer fkIdPosizioneDebitoria, String cfEnteCreditore);

    /*
     * Il metodo ritorna un Set di ID_POSIZIONE_DEBITORIA a partire da un Set di ID della tabella 
     */
    Set<Integer> findFkIdPosizioneDebitoriaByIdList(Set<Integer> id);

    /**
     * ritorna una struttura di tipo {@link StatoPagamentiBreveJson} dato l'identificativo della riga di
     * DETT_POSIZIONE_DEBITORIA
     * 
     * @param id
     * @return
     */
    public StatoPagamentiBreveJson getStatoBreveById(Integer id);

    /**
     * ritorna una struttura di tipo {@link StatoPagamentiDettaglioJson} dato l'identificativo della riga di
     * DETT_POSIZIONE_DEBITORIA
     * 
     * @param id
     * @return
     */
    public StatoPagamentiDettaglioJson getStatoDettagliatoById(Integer id);

    /**
     * Ritorna la lista dei dettagli posizioni debitorie non pagate per le giornate di presenze di mercati
     * 
     * @return
     */
    public List<Integer> findCodiciDettaglioPosizioniNonPagate();
    //public List<Integer> findCodiciDettaglioPosizioniSpuntistiNonPagatePerBlackList();

    @Override
    void insert(DettPosizioneDebitoria entity);

    @Override
    void update(DettPosizioneDebitoria entity);

    /**
     * Il metodo serve per l'upgr da versioni precedenti alla 2.77 per spostare gli identificativi delle posizioni
     * debitorie da PAY_POSIZIONI_DEBITORIE a DETT_POSIZIONE_DEBITORIA
     *
     */
    public void upgradeDettPosizioniDebitorieDaBlackList();

    /**
     * Il metodo, prima di effettuare l'update, risale ai record delle posizioni debitorie in base al riferimento della
     * posizione debitoria del nodo dei pagamenti e ne aggiorna solamente i dati passati
     * 
     * @param dettPosizioneDebitoria
     */
    public void updateDaRiferimentoNodoPagamenti(String cf_ente_creditore, VerificaStatoPosizioniResponseType statoPosizione);

    /**
     * Il metodo verifica se la posizione debitoria è pagata/annullata; in alternativa richiama il nodo dei pagamenti
     * per chiedere l'aggiornamento dello stato
     * 
     * @param idDettPosizioneDebitoria
     * @param baseUrl
     * @return
     */
    public DettPosizioneDebitoriaResponseType verificaStato(String baseUrl, int idDettPosizioneDebitoria);

    /**
     * Il metodo ritorna il dettaglio della posizione debitoria e le url necessarie ad eventuali chiamate successive
     * 
     * @param idDettPosizioneDebitoria
     * @param baseUrl
     * @return
     */
    public DettPosizioneDebitoriaResponseType dettaglioPosizione(String baseUrl, int idDettPosizioneDebitoria);

    /**
     * Il metodo richiama la generazione dell'avviso di pagamento
     * 
     * @param idDettPosizioneDebitoria
     */
    public EsitoDocumentoPosizioneDebitoriaType generaAvviso(Integer idDettPosizioneDebitoria);

    /**
     * Il metodo richiama la generazione della ricevuta telematica
     * 
     * @param idDettPosizioneDebitoria
     */
    public EsitoDocumentoPosizioneDebitoriaType generaRicevutaTelematica(Integer idDettPosizioneDebitoria);

    /**
     * Il metodo richiama la generazione della fattura
     * 
     * @param idDettPosizioneDebitoria
     */
    public EsitoDocumentoPosizioneDebitoriaType generaFattura(Integer idDettPosizioneDebitoria);

    public void pagaOffline(Integer idDettPosizioneDebitoria);

    public AttivaSessionePagamentoResponseBean attivaSessionePagamento(Integer idDettPosizioniDebitoria);

    public void registraPagamentoOffline(DatiPagamento datiPagamento);

    /**
     * Annulla una posizione debitoria precedentemente aperta
     * 
     * @param idDettPosizioniDebitoria
     */
    public void annullaPosizioneDebitoria(Integer idDettPosizioniDebitoria, String noteAnnullamento) throws RuntimeException;

    /*
     * Il metodo ritorna l'entity a partire da un uuid e un cf_ente_creditore
     */
    public DettPosizioneDebitoria findByCfEnteEUuid(String cfEnte, String uuid);
    // String getCodiceComuneFromDettPosizioneDebitoria(Integer idDettPosizioneDebitoria);
    // ISoftwareComuneData getSoftwareAndcomuneFromDettPosizioneDebitoria(Integer idDettPosizioneDebitoria);

    public DettPosizioneDebitoriaProvenienzaEnum provenienza(Integer idPosizioneDebitoria);

    public PageResult<BollettazioneBean> getPosizioneDebitorieBollettazione(Integer[] autorizzazioniIds, boolean validata,
	    FiltroPagamentoEnum filtroPagamentoEnum, int pagina, int sizePagina);
}
