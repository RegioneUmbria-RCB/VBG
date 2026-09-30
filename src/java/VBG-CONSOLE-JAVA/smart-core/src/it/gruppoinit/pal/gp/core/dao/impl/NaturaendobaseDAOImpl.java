package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.NaturaendobaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Naturaendobase;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class NaturaendobaseDAOImpl extends BaseDAOImpl<Naturaendobase, Integer> implements NaturaendobaseDAO {

    @Override
    public Class<Naturaendobase> getEntityClass() {

	return Naturaendobase.class;
    }

    @Override
    public List<Naturaendobase> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "natura", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Naturaendobase> findBydescrizione(String descrizione) {

	DetachedCriteria criteria = getEmptyCriteriaForClass();
	if (StringUtils.isNotBlank(descrizione)) {
	    try {
		criteria.add(Restrictions.eq("id", Integer.parseInt(descrizione.replaceAll("%", ""))));
	    } catch (Exception e) {
		criteria.add(Restrictions.ilike("natura", descrizione, MatchMode.ANYWHERE));
	    }
	}
	criteria.addOrder(Order.asc("natura"));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public Integer findMaxCodicenatura() {

	DetachedCriteria det = getEmptyCriteriaForClass();
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.max("id"));
	det.setProjection(projectionList);
	List<Integer> list = getHibernateTemplate().findByCriteria(det);
	if (!list.isEmpty()) {
	    if (list.get(0) != null) {
		return list.get(0);
	    }
	}
	return 0;
    }
}
