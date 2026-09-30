package it.gruppoinit.pal.gp.core.features.scadenzario;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.ResponsabiliDAO;
import it.gruppoinit.pal.gp.core.dao.SoftwareDAO;
import it.gruppoinit.pal.gp.core.dao.helper.QueryScadenzarioHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BatchScadenzario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.ScadenzarioListHelper;
import it.gruppoinit.pal.gp.core.features.scadenzario.dao.BatchScadenzarioDAO;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BatchScadenzarioDAOImpl extends BaseDAOImpl<BatchScadenzario, PkId> implements BatchScadenzarioDAO {

    private static final Logger log = LoggerFactory.getLogger(BatchScadenzarioDAOImpl.class);
    @Autowired
    private AlberoprocDAO alberoprocDAO;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private SoftwareDAO softwareDAO;
    @Autowired
    private ResponsabiliDAO responsabiliDAO;

    @Override
    public Class<BatchScadenzario> getEntityClass() {

	return BatchScadenzario.class;
    }

    @Override
    public List<ScadenzarioListHelper> findByHelperFilter(BatchScadenzarioFilter filter, Integer firstResult, Integer maxResults) {

	log.debug("findByHelperFilter: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryScadenzarioHelper qih = new QueryScadenzarioHelper(filter, sessimpl, alberoprocDAO, softwareDAO, responsabiliDAO,
		comuniassociatiService, false);
	String sql = qih.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	if (firstResult != null) {
	    q.setFirstResult(firstResult);
	}
	if (maxResults != null) {
	    q.setMaxResults(maxResults);
	}
	qih.setFilterValues(q);
	qih.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(ScadenzarioListHelper.class));
	List<ScadenzarioListHelper> result = (List<ScadenzarioListHelper>) q.list();
	return result;
    }

    @Override
    public int countByHelperFilter(BatchScadenzarioFilter filter) {

	log.debug("countByHelperFilter: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryScadenzarioHelper qih = new QueryScadenzarioHelper(filter, sessimpl, alberoprocDAO, softwareDAO, responsabiliDAO,
		comuniassociatiService, true);
	String sql = qih.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	qih.setFilterValues(q);
	q.addScalar("conteggio_scadenze", Hibernate.BIG_DECIMAL);
	List<BigDecimal> rs = q.list();
	int ris = ((BigDecimal) rs.get(0)).intValue();
	return ris;
    }
}
