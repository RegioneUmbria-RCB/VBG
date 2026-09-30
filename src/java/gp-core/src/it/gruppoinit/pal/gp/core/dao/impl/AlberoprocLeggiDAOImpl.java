package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocLeggiDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocLeggi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class AlberoprocLeggiDAOImpl extends BaseDAOImpl<AlberoprocLeggi, PkId> implements AlberoprocLeggiDAO {

    @Override
    public Class<AlberoprocLeggi> getEntityClass() {

	return AlberoprocLeggi.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AlberoprocLeggi> findByAlberoProc(Integer codice) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("alberoproc.id.codice", codice));
	det.createAlias("legge", "_legge");
	det.addOrder(Order.asc("_legge.leDescrizione"));
	return getHibernateTemplate().findByCriteria(det);
    }
}
