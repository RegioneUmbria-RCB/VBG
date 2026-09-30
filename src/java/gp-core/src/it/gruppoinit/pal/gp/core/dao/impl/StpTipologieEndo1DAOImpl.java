package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.StpTipologieEndo1DAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpTipologieEndo1;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class StpTipologieEndo1DAOImpl extends BaseDAOImpl<StpTipologieEndo1, PkId> implements StpTipologieEndo1DAO {

    @Override
    public Class<StpTipologieEndo1> getEntityClass() {

	return StpTipologieEndo1.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public StpTipologieEndo1 findbyStpCodice(Integer stpCodice) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("codiceStp", stpCodice));
	List<StpTipologieEndo1> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public StpTipologieEndo1 findbyTipifamiglieendo(Tipifamiglieendo tipifamiglieendo) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("tipifamiglieendo", tipifamiglieendo));
	List<StpTipologieEndo1> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    public List<StpTipologieEndo1> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "codiceStp", DAOOrderTypeEnum.ASC);
    }
}
