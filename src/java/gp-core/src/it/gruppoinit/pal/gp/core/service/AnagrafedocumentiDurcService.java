package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AnagrafedocumentiDurcDAO;
import it.gruppoinit.pal.gp.core.domain.AnagrafedocumentiDurc;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface AnagrafedocumentiDurcService extends BaseService<AnagrafedocumentiDurc, PkId> {

    /**
     * @see AnagrafedocumentiDurcDAO#findAll(Integer, Integer)
     */
    public List<AnagrafedocumentiDurc> findAll(Integer firstResult, Integer maxResult);

    public List<AnagrafedocumentiDurc> findByAnagrafedocumenti(Integer codiceAnagrafeDocumenti);
}
