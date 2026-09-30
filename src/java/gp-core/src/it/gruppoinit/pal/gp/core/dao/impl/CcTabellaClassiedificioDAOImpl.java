package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CcTabellaClassiedificioDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.CcTabellaClassiedificio;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CcTabellaClassiedificioDAOImpl extends BaseDAOImpl<CcTabellaClassiedificio, PkId> implements CcTabellaClassiedificioDAO {

    @Override
    public Class<CcTabellaClassiedificio> getEntityClass() {

	return CcTabellaClassiedificio.class;
    }

    @Override
    public List<CcTabellaClassiedificio> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @Override
    public List<CcTabellaClassiedificio> listByIntervallo() {

	List<CcTabellaClassiedificio> retList = new ArrayList<CcTabellaClassiedificio>();
	DetachedCriteria detachedCriteria;
	detachedCriteria = getIdcomuneAndSoftwareCriteria();
	detachedCriteria.addOrder(Order.asc("da"));
	detachedCriteria.addOrder(Order.asc("a"));
	retList = (List<CcTabellaClassiedificio>) getHibernateTemplate().findByCriteria(detachedCriteria);
	return retList;
    }
}
