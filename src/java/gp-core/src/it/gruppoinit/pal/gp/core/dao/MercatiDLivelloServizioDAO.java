package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.MercatiDLivelloServizio;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiDLivelloServizioDTO;

import java.util.List;

/**
 * 
 * @author
 */
public interface MercatiDLivelloServizioDAO extends BaseDAO<MercatiDLivelloServizio, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<MercatiDLivelloServizio> findAll(Integer firstResult, Integer maxResult);

    public List<MercatiDLivelloServizioDTO> findByPosteggio(Integer codiceposteggio, boolean attivi, boolean scaduti);
}
