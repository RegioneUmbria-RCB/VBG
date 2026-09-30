package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.InventarioprocedimentipeopleDAO;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentipeople;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface InventarioprocedimentipeopleService extends BaseService<Inventarioprocedimentipeople, PkId> {

    /**
     * @see InventarioprocedimentipeopleDAO#findAll(Integer, Integer)
     */
    public List<Inventarioprocedimentipeople> findAll(Integer firstResult, Integer maxResult);

    /**
     * ricerca in INVENTARIOPROCEDIMENTI in join con INVENTARIOPROCEDIMENTIPEOPLE per IDCOMUNE SOFTWARE in
     * (TT,codiceSoftware) e COD_PROC_PEOPLE
     * 
     * @param codiceSTP
     * @param codiceSoftware
     * @return null o il record trovato
     */
    public Inventarioprocedimentipeople findByCodiceSTP(String codiceSTP, String codiceSoftware);
}
