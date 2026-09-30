/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.StarConfigOneriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StarConfigOneri;
import it.gruppoinit.pal.gp.core.service.StarConfigOneriService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francol
 *
 */
@Service
public class StarConfigOneriServiceImpl extends BaseServiceImpl<StarConfigOneri, PkId> implements StarConfigOneriService {

    @Autowired
    private StarConfigOneriDAO starConfigOneriDAO;

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#insert(java.lang.Object)
     */
    @Override
    public void insert(StarConfigOneri entity) {

	this.starConfigOneriDAO.insert(entity);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#update(java.lang.Object)
     */
    @Override
    public void update(StarConfigOneri entity) {

	this.starConfigOneriDAO.update(entity);
	
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#delete(java.lang.Object)
     */
    @Override
    public void delete(StarConfigOneri entity) {

	this.starConfigOneriDAO.delete(entity);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#findAll(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<StarConfigOneri> findAll(Integer firstResult, Integer maxResult) {

	return this.starConfigOneriDAO.findAll(firstResult, maxResult);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#findById(java.io.Serializable)
     */
    @Override
    public StarConfigOneri findById(PkId id) {

	return this.starConfigOneriDAO.findById(id);
    }

    @Override
    public List<StarConfigOneri> findConfigOneriLocali() {

	List<StarConfigOneri> retOneri = new ArrayList<StarConfigOneri>();
	if (ORMHelper.isConsoleLocale()) {
	    retOneri = this.findAll(null, null);
	}
	return retOneri;
    }

    @Override
    public StarConfigOneri findConfigOneriBase() {

	return this.starConfigOneriDAO.findConfigOneriBase();
    }

    @Override
    public StarConfigOneri findConfigOneriByCodiceComune(String codCOmune) {

	return this.starConfigOneriDAO.findConfigOneriByCodiceComune(codCOmune);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl#getEntityClass()
     */
    @Override
    protected Class<StarConfigOneri> getEntityClass() {

	return StarConfigOneri.class;
    }
}
