/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwAlberoproc;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface VwAlberoprocService {

    /**
     * @deprecated
     * 
     *             throw new {@link NotImplementedException}
     */
    public void insert(VwAlberoproc entity);

    /**
     * @deprecated
     * 
     *             throw new {@link NotImplementedException}
     */
    public void update(VwAlberoproc entity);

    /**
     * @deprecated
     * 
     *             throw new {@link NotImplementedException}
     */
    public void delete(VwAlberoproc entity);

    public List<VwAlberoproc> findAll(Integer firstResult, Integer maxResult);

    public List<VwAlberoproc> findByFilterTable(FilterTable filterTable);

    public VwAlberoproc findById(PkId id);
}
