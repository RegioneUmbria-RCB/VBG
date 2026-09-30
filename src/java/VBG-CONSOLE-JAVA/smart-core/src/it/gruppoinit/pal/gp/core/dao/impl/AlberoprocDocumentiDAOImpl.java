package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDocumentiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class AlberoprocDocumentiDAOImpl extends BaseDAOImpl<AlberoprocDocumenti, PkId> implements AlberoprocDocumentiDAO {

    @Override
    public Class<AlberoprocDocumenti> getEntityClass() {

	return AlberoprocDocumenti.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AlberoprocDocumenti> findByAlberoProc(Integer codice) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("alberoproc.id.codice", codice));
	det.addOrder(Order.asc("ordine"));
	det.addOrder(OrderBySqlFormula.asc("descrizione", FunctionsEnum.SUBSTRING_FUNCTION, "0", "500"));
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public int findMaxOrder() {

	DetachedCriteria det = getIdcomuneCriteria();
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.max("ordine"));
	det.setProjection(projectionList);
	List<Integer> list = getHibernateTemplate().findByCriteria(det);
	if (!list.isEmpty()) {
	    if (list.get(0) != null) {
		return list.get(0);
	    }
	}
	return 0;
    }
}
