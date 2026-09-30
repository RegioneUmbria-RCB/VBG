package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.ComunicazioniD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.ComunicazioniDDTO;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniDStatoEnum;

import java.util.List;

/**
 * 
 * @author
 */
public interface ComunicazioniDDAO extends BaseDAO<ComunicazioniD, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<ComunicazioniD> findAll(Integer firstResult, Integer maxResult);

//    public List<ComunicazioniDDTO> findByComunicazioniT(Integer codiceComunicazione, Integer firstResult, Integer maxResult);

    public void upadateStato(Integer codiceComD, ComunicazioniDStatoEnum stato);
}
