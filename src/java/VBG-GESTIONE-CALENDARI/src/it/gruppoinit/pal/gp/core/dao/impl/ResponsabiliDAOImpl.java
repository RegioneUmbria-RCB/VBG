package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ResponsabiliDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ResponsabiliDAOImpl extends BaseDAOImpl<Responsabili, PkId> implements ResponsabiliDAO {

    private static final Logger log = LoggerFactory.getLogger(ResponsabiliDAOImpl.class);

    @Override
    public Class<Responsabili> getEntityClass() {

	return Responsabili.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Responsabili findByUserid(String userid) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("userid", userid));
	List<Responsabili> result = getHibernateTemplate().findByCriteria(det);
	if (result != null) {
	    if (result.size() > 1) {
		log.error("findByUserId: la query ha restituito più di un valore per lo userid={}", userid);
		throw new RuntimeException("Attenzione! trovati più responsabili con lo stesso userid: " + userid);
	    }
	    return result.get(0);
	}
	return null;
    }
}
