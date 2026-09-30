package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ResponsabiliAssenzeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliAssenze;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class ResponsabiliAssenzeDAOImpl extends BaseDAOImpl<ResponsabiliAssenze, PkId> implements ResponsabiliAssenzeDAO {

    @Override
    public Class<ResponsabiliAssenze> getEntityClass() {

	return ResponsabiliAssenze.class;
    }

    @Override
    public List<ResponsabiliAssenze> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, DAOOrderTypeEnum.ASC);
    }

    @Override
    public List<ResponsabiliAssenze> findByResponsabile(Integer codice, Integer firstResult, Integer maxResult) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("responsabili.id.codice", codice));
	//criteria.addOrder(Order.asc("datainiziovalidita"));
	//	criteria.addOrder(OrderBySqlFormula.desc("al", FunctionsEnum.NVL_FUNCTION, "'31/12/9999'", OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	criteria.addOrder(OrderBySqlFormula.desc("dal", FunctionsEnum.NVL_FUNCTION, "'01/01/0001'", OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	criteria.addOrder(Order.desc("id.codice"));
	List<ResponsabiliAssenze> list = new ArrayList<ResponsabiliAssenze>();
	if (null != firstResult && null != maxResult) {
	    list = getHibernateTemplate().findByCriteria(criteria, firstResult, maxResult);
	} else {
	    list = getHibernateTemplate().findByCriteria(criteria);
	}
	return list;
    }
}
