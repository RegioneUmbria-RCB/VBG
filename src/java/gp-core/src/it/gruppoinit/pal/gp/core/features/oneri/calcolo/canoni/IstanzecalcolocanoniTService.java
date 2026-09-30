package it.gruppoinit.pal.gp.core.features.oneri.calcolo.canoni;

import it.gruppoinit.pal.gp.core.dao.IstanzecalcolocanoniTDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzecalcolocanoniTService extends BaseService<IstanzecalcolocanoniT, PkId> {

    /**
     * @see IstanzecalcolocanoniTDAO#findAll(Integer, Integer)
     */
    public List<IstanzecalcolocanoniT> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna la lista dei calcoli del canone per l'istanza passata
     * 
     * @param istanza
     * @return
     */
    public List<IstanzecalcolocanoniT> findByIstanza(Istanze istanza);
}
