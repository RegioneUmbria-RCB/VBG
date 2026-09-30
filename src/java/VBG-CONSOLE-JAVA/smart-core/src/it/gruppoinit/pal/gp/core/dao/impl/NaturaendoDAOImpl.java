package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.NaturaendoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.NaturaendoId;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author francescop
 * @author gianpaolot
 */
@Repository
public class NaturaendoDAOImpl extends BaseDAOImpl<Naturaendo, NaturaendoId> implements NaturaendoDAO {

    @Override
    public Class<Naturaendo> getEntityClass() {

	return Naturaendo.class;
    }

    @Override
    public List<Naturaendo> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "id.codice", DAOOrderTypeEnum.DESC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Naturaendo> findBydescrizione(String descrizione) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	if (StringUtils.isNotBlank(descrizione)) {
	    try {
		criteria.add(Restrictions.eq("id.codice", Integer.parseInt(descrizione.replaceAll("%", ""))));
	    } catch (Exception e) {
		criteria.add(Restrictions.ilike("natura", descrizione, MatchMode.ANYWHERE));
	    }
	}
	criteria.addOrder(Order.asc("natura"));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public Integer findMaxCodicenatura() {

	DetachedCriteria det = getIdcomuneCriteria();
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.max("id.codice"));
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
