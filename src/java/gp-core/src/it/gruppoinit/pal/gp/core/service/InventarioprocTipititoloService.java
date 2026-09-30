package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.InventarioprocTipititoloDAO;
import it.gruppoinit.pal.gp.core.domain.InventarioprocTipititolo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface InventarioprocTipititoloService extends BaseService<InventarioprocTipititolo, PkId> {

    /**
     * @see InventarioprocTipititoloDAO#findAll(Integer, Integer)
     */
    public List<InventarioprocTipititolo> findAll(Integer firstResult, Integer maxResult);

    /**
     * Trova tutti i tipititolo di un Inventarioprocedimento
     * 
     * @param codiceInventarioproc
     * @return
     */
    public List<InventarioprocTipititolo> findByInventarioproc(Integer codiceInventarioproc);
}
