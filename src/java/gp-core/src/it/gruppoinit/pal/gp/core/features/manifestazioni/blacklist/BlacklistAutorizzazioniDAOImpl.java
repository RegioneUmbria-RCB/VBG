package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BlacklistAutorizzazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

@Repository
public class BlacklistAutorizzazioniDAOImpl extends BaseDAOImpl<BlacklistAutorizzazioni, PkId> implements BlacklistAutorizzazioniDAO {

    private static final Logger log = LoggerFactory.getLogger(BlacklistAutorizzazioniDAOImpl.class);

    @Override
    public Class<BlacklistAutorizzazioni> getEntityClass() {

	return BlacklistAutorizzazioni.class;
    }

    @Override
    public List<BlackListAttivaBean> findAutorizzazioniInBlackListAttive() {

	DetachedCriteria crit = getIdcomuneCriteria();
	crit.createAlias("blacklistMotivi", "_blm", Criteria.INNER_JOIN);
	crit.add(Restrictions.isNull("_blm.dataFineBl"));
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.groupProperty("autorizzazioniId"), "autorizzazioniId");
	plist.add(Projections.groupProperty("mercatiUsoId"), "mercatiUsoId");
	crit.setProjection(plist);
	crit.setResultTransformer(Transformers.aliasToBean(BlackListAttivaBean.class));
	List<BlackListAttivaBean> result = getHibernateTemplate().findByCriteria(crit);
	return result;
    }

    @Override
    public List<BlackListAttivaBean> findAutorizzazioniInBlackListAttivePerMercatiUso(Integer idMercatiUso, BlackListContestoEnum[] contesti) {

	log.debug("findAutorizzazioniInBlackListAttivePerGiornata: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryBlackListMercatiUsoHelper queryHelper = new QueryBlackListMercatiUsoHelper(sessimpl, idMercatiUso,  contesti);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(BlackListAttivaBean.class));
	return (List<BlackListAttivaBean>) q.list();
    }
}
