package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.StpEndoTipo2DAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;

@Repository
public class StpEndoTipo2DAOImpl extends BaseDAOImpl<StpEndoTipo2, PkId> implements StpEndoTipo2DAO {

    private static Logger log = LoggerFactory.getLogger(StpEndoTipo2DAOImpl.class);

    @Override
    public Class<StpEndoTipo2> getEntityClass() {

	return StpEndoTipo2.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public StpEndoTipo2 findbyAlberoproc(Integer codiceAlberoproc) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("alberoprocId", codiceAlberoproc));
	List<StpEndoTipo2> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public StpEndoTipo2 findbyStpCodice(Integer stpCodice, String tipo) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("codiceStp", stpCodice));
	criteria.add(Restrictions.eq("tipo", tipo));
	List<StpEndoTipo2> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public StpEndoTipo2 findbyStpCodice(Integer stpCodice) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("codiceStp", stpCodice));
	List<StpEndoTipo2> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StpEndoTipo2> findBySoftwareAndTipo(String software, String tipo) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	if (StringUtils.isNotBlank(tipo)) {
	    criteria.add(Restrictions.eq("tipo", tipo));
	}
	DetachedCriteria alberoprocCrit = criteria.createCriteria("alberoproc");
	alberoprocCrit.add(Restrictions.eq("software.codice", software));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StpEndoTipo2> verificaSchedeEndo2() {

	DetachedCriteria criteria = getIdcomuneCriteria();
	DetachedCriteria oggettiCrit = criteria.createCriteria("oggetti", Criteria.LEFT_JOIN);
	oggettiCrit.add(Restrictions.isNull("id.codice"));
	criteria.add(Restrictions.eq("tipo", StpEndoTipo2Service.TIPO_ENDO));
	DetachedCriteria alberoprocCrit = criteria.createCriteria("alberoproc");
	alberoprocCrit.add(Restrictions.eq("software.codice", ORMHelper.getSoftware()));
	DetachedCriteria vwAlberoprocCrit = alberoprocCrit.createCriteria("vwAlberoproc");
	vwAlberoprocCrit.addOrder(Order.asc("scDescrizione"));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public Map<String, ChiaveValoreBean<String, List<ChiaveValoreBean<Integer, String>>>> findListaAttivitaCartOrdinate() {

	Map<String, ChiaveValoreBean<String, List<ChiaveValoreBean<Integer, String>>>> result = new HashMap<String, ChiaveValoreBean<String, List<ChiaveValoreBean<Integer, String>>>>();
	DetachedCriteria criteria = getIdcomuneCriteria();
	/*
	 * 
	select ST2.CODICE_ENDO_REGIONALE, ST2.TIPO, ST2.FK_SC_ID, AP.SC_CODICE from stp_endo_tipo2 ST2
	INNER JOIN ALBEROPROC AP ON 
	AP.IDCOMUNE=ST2.IDCOMUNE AND AP.SC_ID=ST2.FK_SC_ID 
	where ST2.idcomune='G713' 
	AND ST2.tipo in ('ATTIVITA','ENDO')
	AND AP.SOFTWARE='SS'
	ORDER BY ST2.CODICE_ENDO_REGIONALE, ST2.TIPO, SC_CODICE;
	 */
	criteria.add(Restrictions.in("tipo", new String[] { StpEndoTipo2Service.TIPO_ATTIVITA, StpEndoTipo2Service.TIPO_ENDO }));
	DetachedCriteria alberoprocCrit = criteria.createCriteria("alberoproc", "ap", Criteria.INNER_JOIN);
	alberoprocCrit.add(Restrictions.eq("software.codice", ORMHelper.getSoftware()));
	criteria.addOrder(Order.asc("codiceEndoRegionale"));
	criteria.addOrder(Order.asc("tipo"));
	criteria.addOrder(Order.asc("ap.scCodice"));
	ProjectionList p = Projections.projectionList();
	p.add(Projections.property("codiceEndoRegionale"));
	p.add(Projections.property("tipo"));
	p.add(Projections.property("ap.id.codice"), "codiceAlberoproc");
	p.add(Projections.property("ap.scCodice"), "scCodice");
	criteria.setProjection(p);
	List<Object[]> l = getHibernateTemplate().findByCriteria(criteria);
	for (Object[] r : l) {
	    String codiceEndoRegionale = (String) r[0];
	    String tipo = (String) r[1];
	    Integer codiceAlberoProc = (Integer) r[2];
	    String scCodice = (String) r[3];
	    if (log.isDebugEnabled()) {
		log.debug("findListaAttivitaCartOrdinate# DATI: {},{},{},{}", new Object[] { codiceEndoRegionale, tipo, codiceAlberoProc, scCodice });
	    }
	    ChiaveValoreBean<String, List<ChiaveValoreBean<Integer, String>>> c = result.get(codiceEndoRegionale);
	    if (c == null) {
		c = new ChiaveValoreBean<String, List<ChiaveValoreBean<Integer, String>>>();
	    }
	    if (tipo.equalsIgnoreCase(StpEndoTipo2Service.TIPO_ATTIVITA)) {
		if (log.isDebugEnabled()) {
		    log.debug("findListaAttivitaCartOrdinate# processo il codiceRegionale {}", codiceEndoRegionale);
		}
		c.setChiave(scCodice);
	    } else {
		if (StringUtils.isBlank(c.getChiave())) {
		    c.setChiave(scCodice);
		}
		List<ChiaveValoreBean<Integer, String>> vals = c.getValore();
		if (vals == null) {
		    vals = new ArrayList<ChiaveValoreBean<Integer, String>>();
		    c.setValore(vals);
		}
		if (log.isDebugEnabled()) {
		    log.debug("findListaAttivitaCartOrdinate# DATI AGGIUNTI: {},{},{},{}",
			    new Object[] { codiceEndoRegionale, tipo, codiceAlberoProc, scCodice });
		}
		ChiaveValoreBean<Integer, String> cvb = new ChiaveValoreBean<Integer, String>();
		cvb.setChiave(codiceAlberoProc);
		cvb.setValore(scCodice);
		vals.add(cvb);
	    }
	    result.put(codiceEndoRegionale, c);
	}
	return result;
    }

    @Override
    public List<StpEndoTipo2> findAllByStpCodice(Integer stpCodice, String tipo) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("codiceStp", stpCodice));
	criteria.add(Restrictions.eq("tipo", tipo));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public List<StpEndoTipo2> findByTipoSortByStpCodice(String tipo, boolean sortAsc) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.isNotNull("codiceStp"));
	if (StringUtils.isNotBlank(tipo)) {
	    criteria.add(Restrictions.eq("tipo", tipo));
	}
	//Order order = sortAsc ? Order.asc("stpCodice") : Order.desc("stpCodice");
	Order order = sortAsc ? Order.asc("codiceStp") : Order.desc("codiceStp");
	criteria.addOrder(order);
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public List<Integer> findCodiciBySoftwareAndTipo(String software, String tipo) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	if (StringUtils.isNotBlank(tipo)) {
	    criteria.add(Restrictions.eq("tipo", tipo));
	}
	DetachedCriteria alberoprocCrit = criteria.createCriteria("alberoproc");
	alberoprocCrit.add(Restrictions.eq("software.codice", software));
	ProjectionList p = Projections.projectionList();
	p.add(Projections.groupProperty("id.codice"));
	criteria.setProjection(p);
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public List<Integer> findListCategorieAndAttivita() {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.in("tipo", new String[] { StpEndoTipo2Service.TIPO_ATTIVITA, StpEndoTipo2Service.TIPO_CATEGORIA }));
	criteria.add(Restrictions.isNotNull("tipo"));
	criteria.add(Restrictions.isNotNull("codiceEndoRegionale"));
	ProjectionList p = Projections.projectionList();
	p.add(Projections.groupProperty("id.codice"));
	criteria.setProjection(p);
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
