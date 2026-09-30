package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDConti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface MercatiDContiService extends BaseService<MercatiDConti, PkId> {

    /**
     * Torna la lista delle configurazioni dei conti di un determinato posteggio
     * 
     * @param entity
     * @return
     */
    public List<MercatiDConti> findByPosteggio(MercatiD posteggio);

    public List<MercatiDConti> findByMercatiAndAnno(Mercati mercati, Integer anno);
}
