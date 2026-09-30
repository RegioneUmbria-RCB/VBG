/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.Dyn2EspressioniDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Espressioni;
import it.gruppoinit.pal.gp.core.domain.Dyn2Regole;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.Dyn2EspressioniService;

/**
 * @author francol
 * 
 */
@Service
public class Dyn2EspressioniServiceImpl extends BaseServiceImpl<Dyn2Espressioni, PkId> implements Dyn2EspressioniService {

    private Dyn2EspressioniDAO dyn2EspressioniDAO;

    /**
     * 
     */
    public Dyn2EspressioniServiceImpl() {

    }

    @Autowired
    public void setDyn2EspressioniDAO(Dyn2EspressioniDAO dyn2EspressioniDAO) {

	this.dyn2EspressioniDAO = dyn2EspressioniDAO;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#insert(java.lang.Object)
     */
    @Override
    public void insert(Dyn2Espressioni entity) {

	if (validateEntity(entity) && isInsertOrUpdateAllowed(entity)) {
	    this.dyn2EspressioniDAO.insert(entity);
	}
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#update(java.lang.Object)
     */
    @Override
    public void update(Dyn2Espressioni entity) {

	if (validateEntity(entity) && isInsertOrUpdateAllowed(entity)) {
	    this.dyn2EspressioniDAO.update(entity);
	}
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#delete(java.lang.Object)
     */
    @Override
    public void delete(Dyn2Espressioni entity) {

	if(isDeleteAllowed(entity)){
	    this.dyn2EspressioniDAO.delete(entity);
	}
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#findAll(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<Dyn2Espressioni> findAll(Integer firstResult, Integer maxResult) {

	return this.dyn2EspressioniDAO.findAll(firstResult, maxResult);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#findById(java.io.Serializable)
     */
    @Override
    public Dyn2Espressioni findById(PkId id) {

	return this.dyn2EspressioniDAO.findById(id);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl#getEntityClass()
     */
    @Override
    protected Class<Dyn2Espressioni> getEntityClass() {

	return Dyn2Espressioni.class;
    }

    private boolean isInsertOrUpdateAllowed(Dyn2Espressioni entity) {

	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	Dyn2Espressioni dyn2Exp = this.finfByRegolaEProgressivo(entity.getDyn2Regole(), entity.getProgressivo());
	if (dyn2Exp != null && !dyn2Exp.getId().getCodice().equals((entity.getId().getCodice()))) {
	    _ivs.add(new InvalidValue("dyn2espressioni.service_error.progressivo_esistente", null, null, null, null));
	    insert = false;
	}
	if (!insert) {
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }

    @Override
    public Dyn2Espressioni finfByRegolaEProgressivo(Dyn2Regole regola, int progressivo) {

	return this.dyn2EspressioniDAO.finfByRegolaEProgressivo(regola, progressivo);
    }
}
