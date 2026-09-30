package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Cdsatti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CdsattiDTO;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface CdsattiDAO extends BaseDAO<Cdsatti, PkId> {

    /**
     * Ricerca tutte le cds atti filtrati per id comune
     * 
     */
    public List<Cdsatti> findAll(Integer firstResult, Integer maxResult);

    public List<CdsattiDTO> findDTOByCds(Integer idCds, Boolean soloConOggetti);

    public List<CdsattiDTO> findDTOByIstanza(Integer codiceIstanza, Boolean cercaOggetti);
}
