package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipiareeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiaree;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 * 
 */
@Repository
public class TipiareeDAOimpl extends BaseDAOImpl<Tipiaree, PkId> implements TipiareeDAO {

    @Override
    public Class<Tipiaree> getEntityClass() {

	return Tipiaree.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tipiaree> findByFilter(Tipiaree entity) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.add(Restrictions.ilike("tipoarea", entity.getTipoarea(), MatchMode.ANYWHERE));
	return (List<Tipiaree>) getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public List<Tipiaree> findAll(Integer firstResult, Integer maxResult) {

	return findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "tipoarea", DAOOrderTypeEnum.ASC);
    }
}
