package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.GruppiIstruttoriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.GruppiIstruttori;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class GruppiIstruttoriDAOImpl extends BaseDAOImpl<GruppiIstruttori, PkId> implements GruppiIstruttoriDAO {

    @Override
    public Class<GruppiIstruttori> getEntityClass() {

	return GruppiIstruttori.class;
    }

    @Override
    public List<GruppiIstruttori> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @Override
    public List<GruppiIstruttori> findByDescrizione(String textToSearch) {

	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	if (StringUtils.isNotBlank(textToSearch)) {
	    try {
		criteria.add(Restrictions.eq("id.codice", Integer.parseInt(textToSearch.replaceAll("%", ""))));
	    } catch (Exception e) {
		criteria.add(Restrictions.ilike("descrizione", textToSearch, MatchMode.ANYWHERE));
	    }
	}
	criteria.addOrder(Order.asc("descrizione"));
	//	if (null != firstResult && null != maxResult) {
	//	    return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResult);
	//	} else {
	return getHibernateTemplate().findByCriteria(criteria);
	//	}
    }
}
