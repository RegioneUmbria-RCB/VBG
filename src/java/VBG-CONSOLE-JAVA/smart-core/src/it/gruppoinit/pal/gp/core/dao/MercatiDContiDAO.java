package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDConti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface MercatiDContiDAO extends BaseDAO<MercatiDConti, PkId> {

    /**
     * Torna una lista di MercatiDConti dato un posteggio come parametro
     * 
     * @param posteggio
     *            il posteggio di cui si cercano i record
     * @return
     */
    public List<MercatiDConti> findByPosteggio(MercatiD posteggio);
}
