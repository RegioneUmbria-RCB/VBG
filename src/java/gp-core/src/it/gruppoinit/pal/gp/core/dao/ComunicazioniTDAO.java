package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.ComunicazioniT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniTStatoEnum;

import java.util.List;

/**
 * 
 * @author
 */
public interface ComunicazioniTDAO extends BaseDAO<ComunicazioniT, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<ComunicazioniT> findAll(Integer firstResult, Integer maxResult);

    public void updateStatoComunicazioneT(Integer codiceComunicazioneT, ComunicazioniTStatoEnum comunicazioniTStatoEnum);
}
