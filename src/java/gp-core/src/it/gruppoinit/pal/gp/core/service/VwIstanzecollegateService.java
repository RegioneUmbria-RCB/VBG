package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.VwIstanzecollegateDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzecollegate;
import it.gruppoinit.pal.gp.core.domain.VwIstanzecollegate;
import it.gruppoinit.pal.gp.core.domain.VwIstanzecollegateId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzecollegateHelper;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface VwIstanzecollegateService extends BaseService<VwIstanzecollegate, VwIstanzecollegateId> {

    /**
     * @see VwIstanzecollegateDAO#findAll(Integer, Integer)
     */
    public List<VwIstanzecollegate> findAll(Integer firstResult, Integer maxResult);
    
    public List<VwIstanzecollegate> findByFilterTable(FilterTable filterTable);
    
    public List<IstanzecollegateHelper> findIstanzecollegateByIstanza(Istanze istanze);
}
