package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.TitolaritaPagamentiEnum;
import it.gruppoinit.pal.gp.core.domain.BollGestDettRate;
import it.gruppoinit.pal.gp.core.domain.BollGestDettaglio;
import it.gruppoinit.pal.gp.core.domain.BollGestTestata;
import it.gruppoinit.pal.gp.core.domain.ConcessioneHelper;
import it.gruppoinit.pal.gp.core.domain.LivelloServizio;
import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CreazioneBollTestata;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.DettaglioRateizzazione;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.IntervalloDate;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.RigaDettaglioCalcolo;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.RigaDettaglioCalcoloMercati;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.BollGestDettaglioDTO;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.BollettazioneIstanzeFiltriRicerca;
import it.gruppoinit.pal.gp.core.features.bollettazione.nodopagamenti.VerificaPosizioneDebitoriaBean;
import it.gruppoinit.pal.gp.core.features.contabilita.ImportoIvato;

public interface BollettazioneDAO {

    /**
     * La funzione ricerca tutte le righe degli oneri delle istanze da mettere in bollettazione in base ai filtri
     * passati
     * 
     * @param filtriCodiceComune
     * @param filtriScCodice
     * @param filtriCodiceEndo
     * @param filtriCausaleOnere
     * @param intervalloDate
     * @param conguaglio
     * @param isAzienda
     * @return
     */
    List<RigaDettaglioCalcolo> findByFiltriBollettazioneIstanza(List<String> filtriCodiceComune, List<String> filtriScCodice,
	    List<Integer> filtriCodiceEndo, List<Integer> filtriCausaleOnere, IntervalloDate intervalloDate, Boolean conguaglio, boolean isAzienda);

    /**
     * La funzione ricerca tutte le concessioni da mettere in bollettazione in base ai filtri
     * 
     * @param intervalloDate
     * @param filtriMercati
     * @return
     */
    List<RigaDettaglioCalcoloMercati> findByFiltriBollettazioneMercato(String guid, TitolaritaPagamentiEnum titolarita, List<Integer> filtriMercati,
	    IntervalloDate intervalloDate, Boolean conguaglio);

    public <T> T getByIdForBollettazione(Class<T> cls, Integer id);

    public <T> void save(T entity);

    public <T> void save(List<T> entity);

    public List<BollGestTestata> findTestateByCodiciRuoli(List<Integer> codiciRuoli, Integer firstResult, Integer maxResult);

    public List<BollGestDettaglioDTO> findRigheByIdBollettazioneAndAnagrafe(Integer idBollettazione, Integer idAnagrafica);

    public Boolean esistonoRigheInviateASistemaPagamenti(Integer idBollettazione);

    public void updateRigheSetRiferimentiPosizioneDebitoria(Integer idDettPosizioneDebitoria, List<Integer> idRighe);

    Set<VerificaPosizioneDebitoriaBean> findDettaglioPosizioniDebitorieByBollettazioneEAnagrafe(Integer idBollettazione, Integer idAnagrafica);

    public Integer countTestateByCodiciRuoli(List<Integer> codiciRuoli);

    public Set<VerificaPosizioneDebitoriaBean> findDettaglioPosizioniDebitorieByBollettazione(Integer idBollettazione);

    public void updateStatoTestata(Integer idBollettazione, String descrizioneStato);

    void copiaRiferimentiIstanzeOneriSuBollGestDettaglio(BollGestDettaglio oldEntity, BollGestDettaglio newEntity);

    List<LivelloServizio> findLivelliServizio();

    Boolean esisteBollettazioneStessoPeriodo(CreazioneBollTestata bollTestata);

    ConcessioneHelper getInfoConcessione(Integer idAutorizzazioniConcessione, Boolean passaggioStorico);

    ImportoIvato getImportoRigaValidaByTestataAnagrafeEContoEAutorizzazione(Integer idBollettazione, Integer idAnagrafe, Integer idConto,
	    Integer idAutorizzazione);

    BollGestTestata findBollettazionePrecedenteByDataAndTipologia(Integer codiceTipologiaBollettazione, Date dataPartenzaBollettazioneAttuale);

    /**
     * Restituisce la lista di MercatiFormuleCalcolo attive nell'intervallo passato.
     * 
     * @param dataInizio
     * @param dataFine
     * @return
     */
    List<MercatiFormuleCalcolo> findFormuleByIntervallo(Date dataInizio, Date dataFine);

    public void delete(Integer idBollettazione);

    /**
     * Il metodo recupera il codice della lettera tipo associata alla tipologia di bollettazione risalendo dal
     * riferimento delle posizioni debitorie create alla tipologia bollettazione.codiceletteratipoaccompagmanento
     * 
     * @param cf_ente_creditore
     * @param riIdPosizioneDebitoria
     * @return
     */
    public Integer recuperaCodiceLetteraAccompagnamento(String cf_ente_creditore, Integer riIdPosizioneDebitoria);

    /**
     * La funzionalità ritorna una lista di righe escludendo tutte quelle non validabili, cioè attualmente rettificate o
     * eliminate logicamente
     * 
     * @param idBollettazione
     * @return
     */
    List<BollGestDettaglio> findRigheValidabili(Integer idBollettazione);

    void copiaRiferimentiConcessioniSuBollGestDettaglio(BollGestDettaglio oldEntity, BollGestDettaglio newEntity);

    void aggiornaDataScadenza(String idcomune, Integer idBollettazione, Date dataScadenza);

    void inserisciRata(BollGestDettRate rata);

    List<DettaglioRateizzazione> findDettaglioRateizzazione(Integer idRiga);

    List<DettaglioRateizzazione> findDettaglioRateizzazionePerAnagrafica(Integer idBollettazione, Integer idAnagrafica);

    String findDescrizionePosteggio(Integer idPosteggio);

    /**
     * Il metodo riserva n° totaleRecordDaInserire sulla sequenza BOLL_GEST_ISTANZEONERI.ID e ritorna il currVal prima
     * dell'aggiornamento
     * 
     * @param totaleRecordDaInserire
     * @return
     */
    int aggiornaSequenzaBollGestIstanzeOneri(int totaleRecordDaInserire);

    /**
     * Il metodo riserva n° totaleRecordDaInserire sulla sequenza BOLL_GEST_DETTAGLIO.ID e ritorna il currVal prima
     * dell'aggiornamento
     * 
     * @param totaleRecordDaInserire
     * @return
     */
    int aggiornaSequenzaBollGestDettaglio(int totaleRecordDaInserire);

    /**
     * Il metodo riserva n° totaleRecordDaInserire sulla sequenza BOLL_GEST_DETT_AUTORIZZ.ID e ritorna il currVal prima
     * dell'aggiornamento
     * 
     * @param totaleRecordDaInserire
     * @return
     */
    int aggiornaSequenzaBollGestDettAutorizz(int totaleRecordDaInserire);

    /**
     * Il metodo riserva n° totaleRecordDaInserire sulla sequenza BOLL_GEST_MERCATI_DETT.ID e ritorna il currVal prima
     * dell'aggiornamento
     * 
     * @param totaleRecordDaInserire
     * @return
     */
    int aggiornaSequenzaBollGestMercatiDett(int totaleRecordDaInserire);

    void inserisciRigheIstanzeOneri(BollettazioneIstanzeFiltriRicerca filtriRicerca, int numeroInizialeBollGestDettaglio,
	    int numeroInizialeBollGestIstanzeOneri, boolean conguaglio, boolean azienda, String guid, Integer bollTestataId, Date dataScadenza);

    int contaRigheIstanzeOneri(BollettazioneIstanzeFiltriRicerca filtriRicerca, boolean conguaglio, boolean azienda);

    /**
     * @see BollGestDettaglioDAO#findBollGestDettaglioDTOByTestata(Integer)
     * @param idBollettazione
     * @return
     */
    List<BollGestDettaglioDTO> findBollGestDettaglioDTOByTestata(Integer idBollettazione);

    /**
     * @see BaseDAO#commit()
     */
    void commit();

    /**
     * @see BaseDAO#flush()
     */
    void flush();

    /**
     * @see BaseDAO#clear()
     */
    void clear();

    List<Object[]> getSummaryForBollettazione(int idbollettazionetestata, int idanagrafe, boolean soloValidati);
    
    AbstractQueryBollettazioneMercatiHelper getQueryBuilder(String guid, TitolaritaPagamentiEnum titolarita, List<Integer> filtriMercati,
	    IntervalloDate intervalloDate, Boolean conguaglio);
}
