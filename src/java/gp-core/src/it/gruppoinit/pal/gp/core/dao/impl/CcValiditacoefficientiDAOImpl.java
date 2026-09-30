package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CcValiditacoefficientiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.CcValiditacoefficienti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.Date;
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
public class CcValiditacoefficientiDAOImpl extends BaseDAOImpl<CcValiditacoefficienti, PkId> implements CcValiditacoefficientiDAO {

    @Override
    public Class<CcValiditacoefficienti> getEntityClass() {

	return CcValiditacoefficienti.class;
    }

    @Override
    public List<CcValiditacoefficienti> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @Override
    public List<CcValiditacoefficienti> listByDataValidita() {

	List<CcValiditacoefficienti> retVal = null;
	DetachedCriteria detachedCriteria;
	detachedCriteria = getIdcomuneAndSoftwareCriteria();
	detachedCriteria.addOrder(Order.desc("datainiziovalidita"));
	retVal = (List<CcValiditacoefficienti>) getHibernateTemplate().findByCriteria(detachedCriteria);
	return retVal;
    }

    @Override
    public CcValiditacoefficienti findValidoAllaData(Date validoAllaData) {

	CcValiditacoefficienti retCoeff = null;
	DetachedCriteria detachedCriteria;
	detachedCriteria = getIdcomuneAndSoftwareCriteria();
	detachedCriteria.add(Restrictions.le("datainiziovalidita", validoAllaData));
	detachedCriteria.addOrder(Order.desc("datainiziovalidita"));
	List<CcValiditacoefficienti> results = getHibernateTemplate().findByCriteria(detachedCriteria);
	if(results != null && results.size() > 0){
	    retCoeff = results.get(0);
	}
	return retCoeff;
    }
}
