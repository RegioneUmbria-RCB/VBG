package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AlberoprocArendoDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocArendo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface AlberoprocArendoService extends BaseService<AlberoprocArendo, PkId> {

    /**
     * @see AlberoprocArendoDAO#findAll(Integer, Integer)
     */
    public List<AlberoprocArendo> findAll(Integer firstResult, Integer maxResult);

    /**
     * <pre>
     * Ritorna una lista di degli enedo procedimenti per l'area riservata associati alla voce dell'albero passata. La lista verrà
     * ordinata secondo la logica 
     *   1- asc tipifamigliaendo.ordine 
     *   2- asc tipifamigliaendo.tipo
     *   3- asc tipiendo.ordine
     *   4- asc tipiendo.tipo 
     * @param codice
     * @return
     * 
     * </pre>
     */
    public List<AlberoprocArendo> findByAlberoProc(Integer codice);
}
