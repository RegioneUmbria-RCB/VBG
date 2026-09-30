package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.DocumentiContabilitaDAO;
import it.gruppoinit.pal.gp.core.domain.DocumentiContabilita;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.DocumenticontabilitaFilter;

import java.util.List;

/**
 * 
 * @author
 */
public interface DocumentiContabilitaService extends BaseService<DocumentiContabilita, PkId> {

    /**
     * @see DocumentiContabilitaDAO#findAll(Integer, Integer)
     */
    public List<DocumentiContabilita> findAll(Integer firstResult, Integer maxResult);

    public List<DocumentiContabilita> findByDescrizione(String textToSearch, Integer firstResult, Integer maxResult);

    /**
     * Ritorna una lista di documenti della contablità filtrati per l'ogetto DocumenticontabilitaFilter e paginati
     * 
     * @param filter
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<DocumentiContabilita> findByFilter(DocumenticontabilitaFilter filter, Integer firstResult, Integer maxResult);
}
