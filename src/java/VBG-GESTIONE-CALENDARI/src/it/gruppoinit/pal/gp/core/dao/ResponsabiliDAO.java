package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;

public interface ResponsabiliDAO extends BaseDAO<Responsabili, PkId> {

    /**
     * ricerca un responsabile filtrando sulla colonna USERID
     * 
     * @param userid
     * @return
     */
    public Responsabili findByUserid(String userid);
}
