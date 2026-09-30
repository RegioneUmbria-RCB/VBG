/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.Dyn2EspressioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Espressioni;
import it.gruppoinit.pal.gp.core.domain.Dyn2Regole;
import it.gruppoinit.pal.gp.core.domain.PkId;


/**
 * @author francol
 *
 */
@Repository
public class Dyn2EspressioniDAOImpl extends BaseDAOImpl<Dyn2Espressioni, PkId> implements Dyn2EspressioniDAO {

    /**
     * 
     */
    public Dyn2EspressioniDAOImpl() {

    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl#getEntityClass()
     */
    @Override
    public Class<Dyn2Espressioni> getEntityClass() {

	return Dyn2Espressioni.class;
    }

    @Override
    public Dyn2Espressioni finfByRegolaEProgressivo(Dyn2Regole regola, int progressivo) {

	Dyn2Espressioni retVal = null;
	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.eq("progressivo", progressivo));
	det.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	det.add(Restrictions.eq("dyn2Regola", regola));
	List<Dyn2Espressioni> exprs = getHibernateTemplate().findByCriteria(det);
	if(!exprs.isEmpty()){
	    retVal = exprs.get(0);
	}
	return retVal;
    }

    @Override
    public List<Dyn2Espressioni> findByCampoDinamico(Dyn2Campi campo) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.eq("dyn2Campo", campo));
	return getHibernateTemplate().findByCriteria(det);
    }
}
