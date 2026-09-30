package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.InventarioprocTipititolo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface InventarioprocTipititoloDAO extends BaseDAO<InventarioprocTipititolo, PkId> {

    /**
     * Ricerca tutti gli InventarioprocTipititolo filtrando per idcomune e ordinando per tipotitolo asc
     * 
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
