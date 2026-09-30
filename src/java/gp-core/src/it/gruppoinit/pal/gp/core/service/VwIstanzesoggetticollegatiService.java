package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.VwIstanzesoggetticollegatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.VwIstanzesoggetticollegatiHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.VwIstanzesoggetticollegati;
import it.gruppoinit.pal.gp.core.domain.VwIstanzesoggetticollegatiId;

import java.util.List;

/**
 * 
 * @author
 */
public interface VwIstanzesoggetticollegatiService extends BaseService<VwIstanzesoggetticollegati, VwIstanzesoggetticollegatiId> {

    /**
     * @see VwIstanzesoggetticollegatiDAO#findAll(Integer, Integer)
     */
    public List<VwIstanzesoggetticollegati> findAll(Integer firstResult, Integer maxResult);

    /**
     * NotImplementedException
     */
    @Override
    public void update(VwIstanzesoggetticollegati entity);

    /**
     * NotImplementedException
     */
    @Override
    public void delete(VwIstanzesoggetticollegati entity);

    /**
     * Ritorna un lista di oggetti VwIstanzesoggetticollegatiHelper filtrato per richiedente (anagrafe) e ordinati per
     * software.
     */
    public List<VwIstanzesoggetticollegatiHelper> findByRichiedenteAndGroupBySoftware(Anagrafe richiedente);

    public List<VwIstanzesoggetticollegati> findByIstanza(Integer codiceistanza);
}
