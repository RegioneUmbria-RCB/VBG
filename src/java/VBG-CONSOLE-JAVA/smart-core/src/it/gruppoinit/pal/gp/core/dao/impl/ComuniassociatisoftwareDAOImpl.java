package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ComuniassociatisoftwareDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociatisoftware;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class ComuniassociatisoftwareDAOImpl extends BaseDAOImpl<Comuniassociatisoftware, PkId> implements ComuniassociatisoftwareDAO {

    @Override
    public Class<Comuniassociatisoftware> getEntityClass() {

	return Comuniassociatisoftware.class;
    }

    @Override
    public List<Comuniassociatisoftware> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }

    @Override
    public Comuniassociatisoftware findByComune(Comuni comune) {

	DetachedCriteria detachedCriteria = getIdcomuneCriteria();
	detachedCriteria.createAlias("configurazione", "_configurazione");
	detachedCriteria.add(Restrictions.eq("_configurazione.id.software", ORMHelper.getSoftware()));
	if (comune != null) {
	    detachedCriteria.add(Restrictions.eq("comuni.codicecomune", comune.getCodicecomune()));
	} else {
	    detachedCriteria.add(Restrictions.isNull("comuni.codicecomune"));
	}
	List<Comuniassociatisoftware> list = getHibernateTemplate().findByCriteria(detachedCriteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }
}
