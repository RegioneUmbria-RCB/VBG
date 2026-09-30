package it.gruppoinit.stc.dao.impl;

import it.gruppoinit.stc.dao.SicurezzaDAO;
import it.gruppoinit.stc.domain.Sicurezza;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class SicurezzaDAOImpl extends BaseDAOImpl<Sicurezza, Integer> implements SicurezzaDAO {

    @Override
    public Class<Sicurezza> getEntityClass() {
	return Sicurezza.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Sicurezza findByToken(String token) {

	DetachedCriteria criteria = getDefaultCriteria();
	criteria.add(Restrictions.eq("token", token));

	List<Sicurezza> list = this.getHibernateTemplate().findByCriteria(criteria);
	return list.isEmpty() ? null : list.get(0);
    }

}
