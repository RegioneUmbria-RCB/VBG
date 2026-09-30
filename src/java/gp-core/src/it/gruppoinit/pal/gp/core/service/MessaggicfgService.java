package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.MessaggicfgDAO;
import it.gruppoinit.pal.gp.core.domain.Messaggicfg;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.List;

public interface MessaggicfgService extends BaseService<Messaggicfg, PkId> {

    /**
     * Metodo che per la ricerca delle configurazioni per software
     * 
     * @param software
     * @return
     */
    public Messaggicfg findBySoftware(Software software);

    /**
     * @see MessaggicfgDAO#findAll(Integer, Integer)
     */
    public List<Messaggicfg> findAll(Integer firstResult, Integer maxResult);
}
