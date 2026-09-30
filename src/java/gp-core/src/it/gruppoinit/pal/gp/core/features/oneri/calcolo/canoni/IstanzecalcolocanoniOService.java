package it.gruppoinit.pal.gp.core.features.oneri.calcolo.canoni;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.IstanzecalcolocanoniODAO;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniO;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniOId;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniT;
import it.gruppoinit.pal.gp.core.service.BaseService;

/**
 * 
 * @author
 */
public interface IstanzecalcolocanoniOService extends BaseService<IstanzecalcolocanoniO, IstanzecalcolocanoniOId> {

    /**
     * @see IstanzecalcolocanoniODAO#findAll(Integer, Integer)
     */
    public List<IstanzecalcolocanoniO> findAll(Integer firstResult, Integer maxResult);

    public void deleteByIdOnere(int istanzeOneriId);

    public List<IstanzecalcolocanoniO> findByIstanzeOneri(Integer codiceIstanzeOneri);

    public IstanzecalcolocanoniT findTestataByIstanzeOneri(Integer codiceIstanzeOneri);
}
