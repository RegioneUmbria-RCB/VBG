package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.DocumentiContabilita;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface DocumentiContabilitaDAO extends BaseDAO<DocumentiContabilita, PkId> {

    public List<DocumentiContabilita> findAll(Integer firstResult, Integer maxResult);

    public List<DocumentiContabilita> findByDescrizione(String textToSearch, Integer firstResult, Integer maxResult);
}
