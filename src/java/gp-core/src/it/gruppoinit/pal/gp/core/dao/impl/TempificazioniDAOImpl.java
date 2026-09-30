package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TempificazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tempificazioni;

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
public class TempificazioniDAOImpl extends BaseDAOImpl<Tempificazioni, PkId> implements TempificazioniDAO {

    @Override
    public Class<Tempificazioni> getEntityClass() {

	return Tempificazioni.class;
    }

    @Override
    public List<Tempificazioni> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "tempificazione", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tempificazioni> findByDescrizione(String tempificazione) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	if (StringUtils.isNotBlank(tempificazione)) {
	    try {
		criteria.add(Restrictions.eq("id.codice", Integer.parseInt(tempificazione)));
	    } catch (Exception e) {
		criteria.add(Restrictions.ilike("tempificazione", tempificazione, MatchMode.ANYWHERE));
	    }
	}
	criteria.addOrder(Order.asc("tempificazione"));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
