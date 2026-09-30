package it.gruppoinit.pal.gp.core.features.sorteggi.testata;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Sorteggitestata;
import it.gruppoinit.pal.gp.core.features.sorteggi.FiltriSorteggioBean;
import it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio.SorteggioDettaglioDTO;

/**
 * 
 * @author
 */
public interface SorteggitestataDAO extends BaseDAO<Sorteggitestata, PkId> {

    /**
     * Restituisce la lista dei Sorteggitestata filtrata per Idcomune e software e ordinata per stDatasorteggio
     * 
     */
    public List<Sorteggitestata> findAll(Integer firstResult, Integer maxResult);

    /**
     * 
     * Restituisce la lista dei Sorteggitestata filtrata per Idcomune e software e con il campo categoria null
     */
    public List<Sorteggitestata> findAllSenzaCategoria();

    public List<Sorteggitestata> findAllSenzaCategoria(String software);

    public List<Integer> getCodiciIstanza(FiltriSorteggioBean filtro);

    public List<SorteggioDettaglioDTO> findDettaglioDTO(Integer idTestata);
}
