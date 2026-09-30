package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.StradariozoneDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Stradariozone;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author Luca Proietti
 * @author gianpaolot
 * 
 */
@Repository
public class StradariozoneDAOImpl extends BaseDAOImpl<Stradariozone, PkId> implements StradariozoneDAO {

    @Override
    public Class<Stradariozone> getEntityClass() {

	return Stradariozone.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Stradariozone> findByFilter(Stradariozone entity) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.ilike("zona", entity.getZona(), MatchMode.ANYWHERE));
	det.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	return (List<Stradariozone>) getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public List<Stradariozone> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "zona", DAOOrderTypeEnum.ASC);
    }
}
