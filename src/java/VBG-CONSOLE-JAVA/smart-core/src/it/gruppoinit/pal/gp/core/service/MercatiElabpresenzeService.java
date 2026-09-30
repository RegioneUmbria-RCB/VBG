package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.MercatiElabpresenzeDAO;
import it.gruppoinit.pal.gp.core.domain.MercatiElabpresenze;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface MercatiElabpresenzeService extends BaseService<MercatiElabpresenze, PkId> {

    /**
     * @see MercatiElabpresenzeDAO#findAll(Integer, Integer)
     */
    public List<MercatiElabpresenze> findAll(Integer firstResult, Integer maxResult);

    public void updateConsolidaAnnoMercato(Integer codiceMercato, Integer anno);

    public List<MercatiElabpresenze> findByMercati(Integer codiceMercato);

    public List<MercatiElabpresenze> findByMercatiAndAnno(Integer codiceMercato, Integer anno);
}
