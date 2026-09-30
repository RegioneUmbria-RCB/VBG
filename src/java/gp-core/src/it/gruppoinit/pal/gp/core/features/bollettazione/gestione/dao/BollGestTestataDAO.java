package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BollGestTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * 
 * @author
 */
public interface BollGestTestataDAO extends BaseDAO<BollGestTestata, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<BollGestTestata> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle testate in base ai ruoi dell'operatore passati come filtro
     * 
     * @param ruoliId
     * @param firstResult
     * @param maxResult
     * @return
     */
    List<BollGestTestata> findByResponsabiliRuoli(List<Integer> ruoliId, Integer firstResult, Integer maxResult);

    /**
     * Metodo count della lista delle testate trovate in base ai ruoi dell'operatore passati come filtro
     * 
     * @param ruoliId
     * @return
     */
    int countByResponsabiliRuoli(List<Integer> ruoliId);

    /**
     * Effettua il conteggio di quante volte è stata utilizzata la configurazione nei calcoli
     * 
     * @param codice
     * @return
     */
    int countByTipoBollettazione(Integer codice);

    /**
     * Effettua il cambio dello stato della bollettazione di cui viene passato l'Id
     * 
     * @param idBollettazione
     * @param stato
     */
    void updateStato(Integer idBollettazione, String stato);

    /**
     * Restituisce una lista di BollGestTestata a partire dall'id di BollCfgTipo
     * 
     * @param bollcfgTipoId
     * @return
     */
    List<BollGestTestata> findByBollCfgTipo(Integer bollcfgTipoId);

    /**
     * Ritorna la bollettazione precedente alla data passata. Utilizzato solitamente in fase di conguaglio per risalire
     * alla bollettazione precedente
     * 
     * @param codiceTipologiaBollettazione
     * @param dataPartenzaBollettazioneAttuale
     * @return
     */
    BollGestTestata findBollettazionePrecedenteByDataAndTipologia(Integer codiceTipologiaBollettazione, Date dataPartenzaBollettazioneAttuale);

    public String findImplementazioneByPosDeb(Integer codicePosDeb);

    public Integer findCodIstanzaByDettPosDebitoria(Integer codicePosDeb);
}
