package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.EmailDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Email;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EmailDAOImpl extends BaseDAOImpl<Email, PkId> implements EmailDAO {

    @Override
    public Class<Email> getEntityClass() {

	return Email.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Email> findAllOrderByData(Amministrazioni amministrazioni, DAOOrderTypeEnum orderType) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("amministrazioni", amministrazioni));
	switch (orderType) {
	case ASC:
	    criteria.addOrder(Order.asc("data"));
	    break;
	case DESC:
	    criteria.addOrder(Order.desc("data"));
	    break;
	}
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
