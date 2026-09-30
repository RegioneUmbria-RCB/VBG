package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.IstanzeeventiDAO;
import it.gruppoinit.pal.gp.core.dao.ResponsabiliDAO;
import it.gruppoinit.pal.gp.core.dao.SoftwareDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.QueryIstanzeeventiHelper;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeeventiListHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.BatchScadenzarioFilter;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeeventiFilter;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class IstanzeeventiDAOImpl extends BaseDAOImpl<Istanzeeventi, PkId> implements IstanzeeventiDAO {

    private static final Logger log = LoggerFactory.getLogger(IstanzeeventiDAOImpl.class);
    @Autowired
    private AlberoprocDAO alberoprocDAO;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private SoftwareDAO softwareDAO;
    @Autowired
    private ResponsabiliDAO responsabiliDAO;

    @Override
    public Class<Istanzeeventi> getEntityClass() {

	return Istanzeeventi.class;
    }

    @Override
    public List<Istanzeeventi> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "data", DAOOrderTypeEnum.DESC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Istanzeeventi> findByFilter(IstanzeeventiFilter filter, Integer firstResult, Integer maxResult) {

	DetachedCriteria det = getIdcomuneCriteria();
	// Se è un software specifico uso il criterio per idcomune e software
	det.createAlias("istanze", "_istanza", Criteria.LEFT_JOIN);
	det.createAlias("movimenti", "_movimenti", Criteria.LEFT_JOIN);
	det.createAlias("_movimenti.istanza", "_movimentiistanza", Criteria.LEFT_JOIN);
	if (!ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_TT)) {
	    det.add(Restrictions.or(Restrictions.eq("_istanza.software.codice", ORMHelper.getSoftware()),
		    Restrictions.eq("_movimentiistanza.software.codice", ORMHelper.getSoftware())));
	} else// setto l'id comune e software li prendo dal filtro (saranno o quelli attivi per resp o quelli attivi per comune)
	{
	    String[] codiciSoftware = new String[filter.getSoftwares().size()];
	    int i = 0;
	    for (Software software : filter.getSoftwares()) {
		codiciSoftware[i] = software.getCodice();
		i++;
	    }
	    det.add(Restrictions.or(Restrictions.in("_istanza.software.codice", codiciSoftware),
		    Restrictions.in("_movimentiistanza.software.codice", codiciSoftware)));
	}
	if (EntityUtils.getNestedProperty(filter.getCategorieeventibase(), "id") != null) {
	    det.add(Restrictions.eq("categorieeventibase", filter.getCategorieeventibase()));
	}
	if (filter.getFlagLetto() != null) {
	    det.add(Restrictions.eq("flagLetto", filter.getFlagLetto()));
	}
	if (StringUtils.isNotBlank(filter.getDescrizione())) {
	    det.add(Restrictions.eq("descrizione", filter.getDescrizione()));
	}
	det.addOrder(Order.desc("data"));
	det.addOrder(Order.asc("flagLetto"));
	List<Istanzeeventi> list = new ArrayList<Istanzeeventi>();
	if (null != firstResult && null != maxResult) {
	    list = getHibernateTemplate().findByCriteria(det, firstResult.intValue(), maxResult.intValue());
	} else {
	    list = getHibernateTemplate().findByCriteria(det);
	}
	return list;
    }

    @Override
    public List<IstanzeeventiListHelper> findByScadenzarioFilterHelper(BatchScadenzarioFilter filter, Integer firstResult, Integer maxResult) {

	log.debug("findByHelperFilter: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryIstanzeeventiHelper qih = new QueryIstanzeeventiHelper(filter, sessimpl, alberoprocDAO, softwareDAO, responsabiliDAO,
		comuniassociatiService, false, false);
	String sql = qih.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setFirstResult(firstResult);
	q.setMaxResults(maxResult);
	qih.setFilterValues(q);
	qih.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(IstanzeeventiListHelper.class));
	List<IstanzeeventiListHelper> result = (List<IstanzeeventiListHelper>) q.list();
	return result;
    }

    @Override
    public int countByScadenzarioFilterHelper(BatchScadenzarioFilter filter) {

	log.debug("countByHelperFilter: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryIstanzeeventiHelper qih = new QueryIstanzeeventiHelper(filter, sessimpl, alberoprocDAO, softwareDAO, responsabiliDAO,
		comuniassociatiService, true, false);
	String sql = qih.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	qih.setFilterValues(q);
	q.addScalar("conteggio_eventi", Hibernate.BIG_DECIMAL);
	List<BigDecimal> rs = q.list();
	int ris = ((BigDecimal) rs.get(0)).intValue();
	return ris;
    }
}
