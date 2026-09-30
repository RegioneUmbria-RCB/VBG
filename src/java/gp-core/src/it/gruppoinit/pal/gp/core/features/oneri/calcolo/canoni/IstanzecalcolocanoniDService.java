package it.gruppoinit.pal.gp.core.features.oneri.calcolo.canoni;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.IstanzecalcolocanoniDDAO;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;

/**
 * 
 * @author
 */
public interface IstanzecalcolocanoniDService extends BaseService<IstanzecalcolocanoniD, PkId> {

    /**
     * @see IstanzecalcolocanoniDDAO#findAll(Integer, Integer)
     */
    public List<IstanzecalcolocanoniD> findAll(Integer firstResult, Integer maxResult);
}
