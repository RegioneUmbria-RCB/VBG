package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.InventarioprocFoTopDAO;
import it.gruppoinit.pal.gp.core.domain.InventarioprocFoTop;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;

import java.util.List;

/**
 * 
 * @author
 */
public interface InventarioprocFoTopService extends BaseService<InventarioprocFoTop, PkId> {

    /**
     * @see InventarioprocFoTopDAO#findAll(Integer, Integer)
     */
    public List<InventarioprocFoTop> findAll(Integer firstResult, Integer maxResult);

    public List<IdentificativoDescrizioneBean> findProcedimenti(Integer firstResult, Integer maxResult);

    public InventarioprocFoTop findByIntevento(Inventarioprocedimenti inventarioprocedimenti);

    public List<InventarioprocFoTop> findBySoftware(String softw);
}
