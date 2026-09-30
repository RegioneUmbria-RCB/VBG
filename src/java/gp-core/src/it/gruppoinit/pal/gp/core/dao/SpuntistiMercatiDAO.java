package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.SpuntistiMercati;
import it.gruppoinit.pal.gp.core.domain.helper.SpuntistiMercatiDTO;

import java.util.List;

/**
 * 
 * @author
 */
public interface SpuntistiMercatiDAO extends BaseDAO<SpuntistiMercati, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<SpuntistiMercati> findAll(Integer firstResult, Integer maxResult);

    public boolean existsByMercatoAndUso(Integer codiceMercato, Integer codiceUso);

    public List<SpuntistiMercatiDTO> findSpuntistaAssenteDa(Integer codiceMercato, Integer codiceUso, Integer giorniDiAssenza);
}
