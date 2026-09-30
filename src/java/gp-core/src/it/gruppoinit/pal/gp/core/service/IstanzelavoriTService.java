package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IstanzelavoriTDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzelavoriT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzelavoriTService extends BaseService<IstanzelavoriT, PkId> {

    /**
     * @see IstanzelavoriTDAO#findAll(Integer, Integer)
     */
    public List<IstanzelavoriT> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna una lista di istanze lavori T filtrate per l'istanza passata e ordinate per tipi lavori
     * 
     * @param istanze
     * @return
     */
    public List<IstanzelavoriT> findByIstanza(Istanze istanze);

    public List<IstanzelavoriT> findByFilterTable(FilterTable filterTable);
}
