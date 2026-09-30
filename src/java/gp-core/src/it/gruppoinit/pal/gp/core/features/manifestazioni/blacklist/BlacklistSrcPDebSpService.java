package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.BlacklistSrcPDebSp;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface BlacklistSrcPDebSpService extends BaseService<BlacklistSrcPDebSp, PkId> {

    /**
     * Data una posizione debitoria ritorna tutte le posizioni attive ovvero valide alla data odierna.
     * 
     * @param ppId
     * @return
     */
    public List<BlacklistSrcPDebSp> findByDettPosDebIdAttive(Integer dettPosizioneDebitoriaId);

    /**
     * Data una posizione debitoria verifica se esistono record validi alla data odierna.
     * 
     * @param ppId
     * @return
     */
    public boolean existsByDettPosDebIdAttive(Integer dettPosizioneDebitoriaId);

    /**
     * Il metodo conteggia le blacklist per le posizioni debitorie aperte (ovvero con BLACK_LIST_MOTIVO.DATA_FINE_BL
     * NULL)
     * 
     * @see #findBlackListAperte(Integer, Integer)
     * @return
     */
    public int countBlackListAperte();

    /**
     * Il metodo ricerca le blacklist per le posizioni debitorie aperte (ovvero con BLACK_LIST_MOTIVO.DATA_FINE_BL NULL)
     * 
     * @return
     */
    public List<BlacklistSrcPDebSp> findBlackListAperte(Integer firstResult, Integer maxResult);

    public List<BlacklistSrcPDebSp> findByBlackListMotivo(Integer codice);
}
