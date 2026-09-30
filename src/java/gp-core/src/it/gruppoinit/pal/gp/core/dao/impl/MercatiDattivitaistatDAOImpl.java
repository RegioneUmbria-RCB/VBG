package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiDattivitaistatDAO;
import it.gruppoinit.pal.gp.core.domain.MercatiDattivitaistat;
import it.gruppoinit.pal.gp.core.domain.MercatiDattivitaistatId;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MercatiDattivitaistatDAOImpl extends BaseDAOImpl<MercatiDattivitaistat, MercatiDattivitaistatId> implements MercatiDattivitaistatDAO {

    @Override
    public Class<MercatiDattivitaistat> getEntityClass() {

	return MercatiDattivitaistat.class;
    }

    @Override
    public Set<MercatiDattivitaistat> findAttivitaPosteggio(Integer codicemercato, Integer idposteggio) {

	// TODO Auto-generated method stub
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatiDattivitaistat> findAttivitaPosteggi(Integer codicemercato, List<Integer> list) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("mercato.id.codice", codicemercato));
	if (list != null && !list.isEmpty()) {
	    criteria.add(Restrictions.in("posteggio.id.codice", list));
	}
	List<MercatiDattivitaistat> result = new ArrayList<MercatiDattivitaistat>();
	List<MercatiDattivitaistat> temp = getHibernateTemplate().findByCriteria(criteria);
	if (!temp.isEmpty()) {
	    result.add(temp.get(0));
	}
	for (int i = 1; i < temp.size(); i++) {
	    if (!isPresent(result, temp.get(i))) {
		result.add(temp.get(i));
	    }
	}
	return result;
    }

    // controlla che nella lista non vengano duplicate le attività
    private boolean isPresent(List<MercatiDattivitaistat> list, MercatiDattivitaistat mercatiDattivitaistat) {

	boolean present = false;
	for (MercatiDattivitaistat temp : list) {
	    if (temp.getId().getFkcodiceattivitaistat().endsWith(mercatiDattivitaistat.getId().getFkcodiceattivitaistat())) {
		present = true;
		break;
	    }
	}
	return present;
    }
}
