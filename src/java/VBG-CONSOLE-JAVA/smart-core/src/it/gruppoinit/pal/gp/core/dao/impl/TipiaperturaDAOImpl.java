package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipiaperturaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiapertura;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author lucap
 */
@Repository
public class TipiaperturaDAOImpl extends BaseDAOImpl<Tipiapertura, PkId> implements TipiaperturaDAO {

    @Override
    public Class<Tipiapertura> getEntityClass() {

	return Tipiapertura.class;
    }

    @Override
    public List<Tipiapertura> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "taDescrizione", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tipiapertura> findByDescrizione(Tipiapertura filter) {

	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	if (filter != null && !filter.getTaDescrizione().equals("")) {
	    criteria.add(Restrictions.ilike("taDescrizione", filter.getTaDescrizione()));
	}
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
