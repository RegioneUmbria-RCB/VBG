package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipiorarioDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiorario;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author lucap
 */
@Repository
public class TipiorarioDAOImpl extends BaseDAOImpl<Tipiorario, PkId> implements TipiorarioDAO {

    @Override
    public Class<Tipiorario> getEntityClass() {

	return Tipiorario.class;
    }

    @Override
    public List<Tipiorario> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "toDescrizione", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tipiorario> findByFilter(Tipiorario entity) {

	DetachedCriteria detachedCriteria = getIdcomuneAndSoftwareCriteria();
	if (StringUtils.isNotBlank(entity.getToDescrizione())) {
	    detachedCriteria.add(Restrictions.ilike("toDescrizione", entity.getToDescrizione(), MatchMode.ANYWHERE));
	}
	detachedCriteria.addOrder(Order.asc("toDescrizione"));
	return (List<Tipiorario>) getHibernateTemplate().findByCriteria(detachedCriteria);
    }
}
