package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatipresenzeStoricoDAO;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeStorico;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

@Repository
public class MercatipresenzeStoricoDAOImpl extends BaseDAOImpl<MercatipresenzeStorico, PkId> implements MercatipresenzeStoricoDAO {

    @Override
    public Class<MercatipresenzeStorico> getEntityClass() {

	return MercatipresenzeStorico.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findAnniDaStorico() {

	DetachedCriteria criteria = getIdcomuneCriteria();
	ProjectionList projList = Projections.projectionList();
	projList.add(Projections.distinct(Projections.property("anno")));
	criteria.setProjection(projList);
	criteria.addOrder(Order.desc("anno"));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
