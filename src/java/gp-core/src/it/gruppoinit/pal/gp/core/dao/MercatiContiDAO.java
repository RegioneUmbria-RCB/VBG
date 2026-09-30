package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface MercatiContiDAO extends BaseDAO<MercatiConti, PkId> {

    public List<MercatiConti> findByMercati(MercatiConti entity);

    /**
     * Lista di conti per il mercato mercato e anno selezionato
     */
    public List<MercatiConti> findByMercatiAndAnno(Mercati mercati, Integer anno);
}
