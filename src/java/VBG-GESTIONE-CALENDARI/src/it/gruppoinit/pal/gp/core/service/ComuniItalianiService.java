package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ComuniDAO;
import it.gruppoinit.pal.gp.core.domain.ComuniItaliani;

import java.util.List;

public interface ComuniItalianiService extends BaseService<ComuniItaliani, String> {

    /**
     * @see ComuniDAO#findByDescrizione(String comune)
     */
    public List<ComuniItaliani> findByDescrizione(String comune);

    public ComuniItaliani findByCodiceComune(ComuniItaliani entity);

    public ComuniItaliani findByComune(ComuniItaliani comuni);

    public List<ComuniItaliani> findAll(Integer firstResult, Integer maxResult);
}
