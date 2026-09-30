package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OIcalcolototDAO;
import it.gruppoinit.pal.gp.core.domain.OIcalcolotot;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface OIcalcolototService extends BaseService<OIcalcolotot, PkId> {

    /**
     * @see OIcalcolototDAO#findAll(Integer, Integer)
     */
    public List<OIcalcolotot> findAll(Integer firstResult, Integer maxResult);

    public List<OIcalcolotot> findByIstanza(Integer codiceistanza);
}
