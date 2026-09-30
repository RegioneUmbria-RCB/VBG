package it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria;

import java.util.Date;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.UpgradeDettPosizioniDebitorieBean;
import it.gruppoinit.pal.gp.core.domain.BollGestDettaglio;
import it.gruppoinit.pal.gp.core.domain.BollettazioneBean;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.PageResult;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.DettPosizioneDebitoriaProvenienzaEnum;

/**
 * 
 * @author
 */
public interface DettPosizioneDebitoriaDAO extends BaseDAO<DettPosizioneDebitoria, PkId> {

    public List<DettPosizioneDebitoria> findAll(Integer firstResult, Integer maxResult);

    /**
     * Restituisce una set di BollGestDettaglio partendo dall'id della Bollettazione
     * 
     * @param Integer
     *            idBollGestTestata
     * @return BollGestDettaglio
     */
    public Set<BollGestDettaglio> findBollGestDettByIdBollGestTestata(Integer idBollGestTestata);

    /*
     * Il metodo ritorna un Set di ID_POSIZIONE_DEBITORIA a partire da un Set di ID della tabella 
     *
     * @param idPosizioniDebitorie
     * @return
     */
    Set<Integer> findFkIdPosizioneDebitoriaByIdList(Set<Integer> id);

    /**
     * Torna la lista degli identificativi di DETT_POSIZIONE_DEBITORIA che hanno quello stato
     * 
     * @param statiDaVerificare
     * @return
     */
    Set<Integer> findIdDettaglioByListaStati(String[] statiDaVerificare);

    /**
     * La funzione torna il campo DETT_POSIZIONE_DEBITORIA.STATO dell'id passato
     * 
     * @param id
     * @return
     */
    String findStato(Integer id);

    public List<Integer> findCodiciDettaglioPosizioniSpuntistiNonPagatePerBlackList();

    /**
     * Ritorna il dettaglio delle posizioni debitorie da creare per l'UPGR
     * 
     * @return
     */
    public List<UpgradeDettPosizioniDebitorieBean> findDettaglioDaBlackList();

    /**
     * Il metodo ritorna una lista di id delle posizioni debitorie che non rientrano degli stati "PAGATI"
     * 
     * @return
     */
    public List<Integer> findCodiciDettaglioPosizioniNonPagate();

    public ImportoResponseType recuperaImporti(int idDettPosizioniDebitorie);

    public DettPosizioneDebitoria findByFkIdPosizioneDebitoriaAndCfEnteCreditore(Integer fkIdPosizioneDebitoria, String cfEnteCreditore);

    public DettPosizioneDebitoria findByCfEnteEUuid(String cfEnte, String uuid);

    public DettPosizioneDebitoriaProvenienzaEnum calcolaProvenienza(Integer idPosizioneDebitoria);

    public PageResult<BollettazioneBean> getPosizioneDebitorieBollettazione(Integer[] autorizzazioniIds, boolean validata,
	    FiltroPagamentoEnum filtroPagamentoEnum, int pagina, int sizePagina);
}
