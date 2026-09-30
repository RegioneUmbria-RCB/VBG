package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CdsconvocazioniDAO;
import it.gruppoinit.pal.gp.core.domain.Cds;
import it.gruppoinit.pal.gp.core.domain.Cdsconvocazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface CdsconvocazioniService extends BaseService<Cdsconvocazioni, PkId> {

    /**
     * @see CdsconvocazioniDAO#findAll(Integer, Integer)
     */
    public List<Cdsconvocazioni> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle convocazioni di una determinata CDS ordinate per data ASC, ora ASC
     * 
     * @param cds
     * @return
     */
    public List<Cdsconvocazioni> findByCds(Cds cds);
}
