package it.sgp.middleware.security.service;

import it.sgp.middleware.security.dao.ComunisecurityAppDAO;
import it.sgp.middleware.security.domain.ComunisecurityApp;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 
 * @author
 */
public interface ComunisecurityAppService extends BaseService<ComunisecurityApp, String> {

    /**
     * @see ComunisecurityAppDAO#findAll(Integer, Integer)
     */
    public List<ComunisecurityApp> findAll(Integer firstResult, Integer maxResult);

    public Page<ComunisecurityApp> findAllByExamplePaginated(PageRequest pageable, Example<ComunisecurityApp> exampleFromRequest);
}
