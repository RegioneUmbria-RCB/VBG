package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.dao.CdsDAO;
import it.gruppoinit.pal.gp.core.domain.Cds;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.CdsFilter;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author
 */
public interface CdsService extends BaseService<Cds, PkId> {

    /**
     * @see CdsDAO#findAll(Integer, Integer)
     */
    public List<Cds> findAll(Integer firstResult, Integer maxResult);

    /**
     * 
     * @param istanza
     * @return
     */
    public List<Cds> findByIstanza(Istanze istanza);

    /**
     * 
     * @param movimento
     * @return
     */
    public List<Cds> findByMovimento(Movimenti movimento);

    /**
     * @see BaseDAO#findByFilterTable(FilterTable)
     */
    public List<Cds> findByFilterTable(FilterTable filterTable);

    /**
     * 
     * @param cdsFilter
     * @return
     */
    public List<Cds> findByFilter(CdsFilter cdsFilter);

    public int countByIstanza(Integer codiceIstanza);

    public int countByMovimento(Integer codiceMovimento);
}
