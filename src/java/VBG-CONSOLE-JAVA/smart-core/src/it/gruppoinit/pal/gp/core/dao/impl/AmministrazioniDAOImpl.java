/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AmministrazioniDAO;
import it.gruppoinit.pal.gp.core.dao.ConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AmministrazioniHelper;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * 
 */
@Repository
public class AmministrazioniDAOImpl extends BaseDAOImpl<Amministrazioni, PkId> implements AmministrazioniDAO {

    private ConfigurazioneDAO configurazioneDAO;

    @Autowired
    public void setConfigurazioneDAO(ConfigurazioneDAO configurazioneDAO) {

	this.configurazioneDAO = configurazioneDAO;
    }

    @Override
    public Class<Amministrazioni> getEntityClass() {

	return Amministrazioni.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Amministrazioni> findByAmministrazione(String amministrazione, boolean tutteLeAmministrazioni, boolean includiDisabilitate,
	    Integer[] codiciAmministrazioniEscluse) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (StringUtils.isNotBlank(amministrazione)) {
	    try {
		det.add(Restrictions.eq("id.codice", Integer.parseInt(amministrazione)));
	    } catch (Exception e) {
		det.add(Restrictions.ilike("amministrazione", amministrazione, MatchMode.ANYWHERE));
	    }
	}
	if (tutteLeAmministrazioni == false) {
	    Integer[] codiciAmministrazioneSistema = configurazioneDAO.getCodiciTutteEStessaAmministrazioniSistema();
	    if (null != codiciAmministrazioneSistema) {
		det.add(Restrictions.not(Restrictions.in("id.codice", codiciAmministrazioneSistema)));
	    }
	}
	if (includiDisabilitate == false) {
	    det.add(Restrictions.or(Restrictions.eq("flagDisabilitato", Boolean.FALSE), Restrictions.isNull("flagDisabilitato")));
	}
	if (codiciAmministrazioniEscluse != null) {
	    if (codiciAmministrazioniEscluse.length > 0) {
		det.add(Restrictions.not(Restrictions.in("id.codice", codiciAmministrazioniEscluse)));
	    }
	}
	det.addOrder(Order.asc("amministrazione"));
	return (List<Amministrazioni>) getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Amministrazioni> findAmministrazioniByDescrizione(String amministrazione) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (amministrazione != null && !amministrazione.equals("")) {
	    det.add(Restrictions.ilike("amministrazione", amministrazione, MatchMode.ANYWHERE));
	}
	return (List<Amministrazioni>) getHibernateTemplate().findByCriteria(det);
    }

    public List<Amministrazioni> findAmministrazioniWithEmailByDescrizione(String amministrazione) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (amministrazione != null && !amministrazione.equals("")) {
	    det.add(Restrictions.ilike("amministrazione", amministrazione, MatchMode.ANYWHERE));
	}
	det.add(Restrictions.or(Restrictions.isNotNull("pec"), Restrictions.isNotNull("email")));
	return (List<Amministrazioni>) getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Amministrazioni> findAmministrazioniByDescrizioneForProtocolloRegistri(String amministrazione, boolean includiDisabilitate,
	    String codiceComune, String software) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (StringUtils.isNotBlank(amministrazione)) {
	    det.add(Restrictions.ilike("amministrazione", amministrazione, MatchMode.ANYWHERE));
	}
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	Dialect dialetto = sessimpl.getDialect();
	String hibernateDialect = dialetto.toString();
	det.createAlias("amminstrProtocollos", "_amminstrProtocollos", Criteria.INNER_JOIN);
	if (hibernateDialect.indexOf("MySQL") > 0) {
	    Criterion proua = Restrictions.not(Restrictions.or(Restrictions.isNull("_amminstrProtocollos.protUo"),
		    Restrictions.eq("_amminstrProtocollos.protUo", "")));
	    Criterion protruolo = Restrictions.not(Restrictions.or(Restrictions.isNull("_amminstrProtocollos.protRuolo"),
		    Restrictions.eq("_amminstrProtocollos.protRuolo", "")));
	    LogicalExpression orExp = Restrictions.or(proua, protruolo);
	    det.add(orExp);
	} else {
	    Criterion proua = Restrictions.isNotNull("_amminstrProtocollos.protUo");
	    Criterion protruolo = Restrictions.isNotNull("_amminstrProtocollos.protRuolo");
	    LogicalExpression orExp = Restrictions.or(proua, protruolo);
	    det.add(orExp);
	}
	det.add(Restrictions.in("_amminstrProtocollos.software.codice", new String[] { WebConstants.SOFTWARE_TT, ORMHelper.getSoftware() }));
	//comune
	if (StringUtils.isBlank(codiceComune)) {
	    Criterion comunenull = Restrictions.isNull("_amminstrProtocollos.comuni");
	    det.add(comunenull);
	} else {
	    Criterion comunenull = Restrictions.isNull("_amminstrProtocollos.comuni");
	    Criterion comunePassato = Restrictions.eq("_amminstrProtocollos.comuni.codicecomune", codiceComune);
	    LogicalExpression orExpComune = Restrictions.or(comunenull, comunePassato);
	    det.add(orExpComune);
	}
	//comune
	if (includiDisabilitate == false) {
	    det.add(Restrictions.or(Restrictions.eq("flagDisabilitato", Boolean.FALSE), Restrictions.isNull("flagDisabilitato")));
	}
	det.addOrder(Order.asc("amministrazione"));
	det.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	List<Amministrazioni> list = getHibernateTemplate().findByCriteria(det);
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Amministrazioni> findByAmministrazioniInterne() {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("flagAmministrazioneinterna", true));
	det.add(Restrictions.ge("id.codice", 0));
	det.addOrder(Order.asc("amministrazione"));
	return (List<Amministrazioni>) getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isAmministrazioneInternaEsiste() {

	Boolean risultato = false;
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("flagAmministrazioneinterna", true));
	List<Amministrazioni> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    risultato = true;
	}
	return risultato;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AmministrazioniHelper> findAllDTO(Integer firstResult, Integer maxResult, DAOEnum whereClauseMandatoryFields, String orderProperty,
	    DAOOrderTypeEnum orderType) {

	DetachedCriteria detachedCriteria;
	switch (whereClauseMandatoryFields) {
	case FIND_BY_IDCOMUNE:
	    detachedCriteria = getIdcomuneCriteria();
	    break;
	case FIND_BY_IDCOMUNE_AND_SOFTWARE:
	    detachedCriteria = getIdcomuneAndSoftwareCriteria();
	    break;
	case FIND_ALL:
	    detachedCriteria = DetachedCriteria.forClass(getEntityClass());
	    break;
	default:
	    throw new RuntimeException("wrong switch value!");
	}
	if (orderType != null) {
	    switch (orderType) {
	    case ASC:
		detachedCriteria.addOrder(Order.asc(orderProperty));
		break;
	    case DESC:
		detachedCriteria.addOrder(Order.desc(orderProperty));
		break;
	    default:
		break;
	    }
	}
	if (null != firstResult && null != maxResult) {
	    detachedCriteria.setResultTransformer(Transformers.aliasToBean(AmministrazioniHelper.class));
	    return (List<AmministrazioniHelper>) getHibernateTemplate()
		    .findByCriteria(detachedCriteria, firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<AmministrazioniHelper>) getHibernateTemplate().findByCriteria(detachedCriteria);
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public Amministrazioni findAmministrazioniByCodiceancitel(String codiceancitel) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("codiceancitel", codiceancitel));
	det.addOrder(Order.asc("amministrazione"));
	List<Amministrazioni> list = (List<Amministrazioni>) getHibernateTemplate().findByCriteria(det);
	if (list != null) {
	    if (list.size() > 0) {
		return list.get(0);
	    }
	}
	return null;
    }
}
