package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CittadinanzaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Cittadinanza;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class CittadinanzaDAOImpl extends BaseDAOImpl<Cittadinanza, Integer> implements CittadinanzaDAO {

    @Override
    public Class<Cittadinanza> getEntityClass() {

	return Cittadinanza.class;
    }

    @Override
    public List<Cittadinanza> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "cittadinanza", DAOOrderTypeEnum.ASC);
    }

    @Override
    public List<Cittadinanza> findByDescrizione(String term) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	if (StringUtils.isNotBlank(term)) {
	    det.add(Restrictions.ilike("cittadinanza", term, MatchMode.START));
	}
	det.addOrder(Order.asc("cittadinanza"));
	return (List<Cittadinanza>) getHibernateTemplate().findByCriteria(det);
    }
}
