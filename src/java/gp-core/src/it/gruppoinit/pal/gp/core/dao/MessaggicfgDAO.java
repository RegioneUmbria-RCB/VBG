package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Messaggicfg;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.List;

public interface MessaggicfgDAO extends BaseDAO<Messaggicfg, PkId> {

    /**
     * Metodo che per la ricerca delle configurazioni per software
     * 
     * @param software
     * @return
     */
    public Messaggicfg findBySoftware(Software software);

    /**
     * Restituisce una lista di Messaggicfg filtrata per idcomune e software e ordinata per messaggicfgbase
     * 
     */
    public List<Messaggicfg> findAll(Integer firstResult, Integer maxResult);
}
