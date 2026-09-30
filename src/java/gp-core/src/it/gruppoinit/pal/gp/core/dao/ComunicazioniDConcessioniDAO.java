package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.ComunicazioniDConcessioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.ComunicazioniDConcessioniDTO;

import java.util.List;

/**
 * 
 * @author
 */
public interface ComunicazioniDConcessioniDAO extends BaseDAO<ComunicazioniDConcessioni, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<ComunicazioniDConcessioni> findAll(Integer firstResult, Integer maxResult);

    public List<ComunicazioniDConcessioniDTO> findByComunicazioniT(Integer codiceComunicazione, Integer firstResult, Integer maxResult);
}
