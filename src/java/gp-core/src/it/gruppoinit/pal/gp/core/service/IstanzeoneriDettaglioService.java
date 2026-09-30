package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IstanzeoneriDettaglioDAO;
import it.gruppoinit.pal.gp.core.domain.IstanzeoneriDettaglio;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzeoneriDettaglioService extends BaseService<IstanzeoneriDettaglio, PkId> {

    /**
     * @see IstanzeoneriDettaglioDAO#findAll(Integer, Integer)
     */
    public List<IstanzeoneriDettaglio> findAll(Integer firstResult, Integer maxResult);

    /**
     * <pre>
     * Ritorna il numero di record presenti nella tabella filtrata per inventarioprocedimento  (parametro codiceProcedimento)
     * 
     * @param codiceIstanza
     * @return
     * </pre>
     */
    public int countByInventarioprocedimento(Integer codiceProcedimento);
}
