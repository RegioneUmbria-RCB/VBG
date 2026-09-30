package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CdsattiDAO;
import it.gruppoinit.pal.gp.core.domain.Cdsatti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CdsattiDTO;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface CdsattiService extends BaseService<Cdsatti, PkId> {

    public List<CdsattiDTO> findDTOByCds(Integer idCds, Boolean cercaOggetti);

    /**
     * @see CdsattiDAO#findAll(Integer, Integer)
     */
    public List<Cdsatti> findAll(Integer firstResult, Integer maxResult);

    public List<CdsattiDTO> findDTOByIstanza(Integer codiceIstanza, Boolean cercaOggetti);
}
