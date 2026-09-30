package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper.DIALETTO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.dao.helper.QueryAlberoprocPerInterventiHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.AlberoprocCommand;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.InterventoSimpleBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

@Repository
public class AlberoprocDAOImpl extends BaseDAOImpl<Alberoproc, PkId> implements AlberoprocDAO {

    @Override
    public Class<Alberoproc> getEntityClass() {

	return Alberoproc.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Alberoproc> findByCriteria(DetachedCriteria criteria) {

	return (List<Alberoproc>) getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Alberoproc findByScCodice(String idcomune, String sccodice) {

	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria(idcomune);
	;
	criteria.add(Restrictions.eq("scCodice", sccodice));
	List<Alberoproc> listTemp = (List<Alberoproc>) getHibernateTemplate().findByCriteria(criteria);
	if (!listTemp.isEmpty()) {
	    return listTemp.get(0);
	}
	return null;
    }

    @Override
    public List<Alberoproc> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "scCodice", DAOOrderTypeEnum.ASC);
    }

    @Override
    public List<Alberoproc> findAlberoprocFigli(String idcomune, String scCodiceIniziale, boolean soloPrimoLivello, DAOOrderTypeEnum tipoOrdinamento,
	    Boolean isPerCalcoloprogressivo) {

	String hql = "Select a from Alberoproc a where a.id.idcomune=? and a.software.codice=? ";
	hql += " and a.scCodice like ? ";
	Object[] values = null;
	if (soloPrimoLivello) {
	    values = new Object[] { idcomune, ORMHelper.getSoftware(), scCodiceIniziale + "%", Long.valueOf(scCodiceIniziale.length() + 2) };
	    hql += " and length(a.scCodice)=? ";
	} else {
	    values = new Object[] { idcomune, ORMHelper.getSoftware(), scCodiceIniziale + "%" };
	}
	String orderBy = "";
	if (tipoOrdinamento == null) {
	    orderBy += " asc";
	} else {
	    orderBy += tipoOrdinamento.name();
	}
	// non modificare questo ordinamento perchè utilizzato nel calcolo del prossimo codice
	if (isPerCalcoloprogressivo) {
	    hql += " order by a.scCodice " + orderBy;
	} else {
	    hql += " order by a.scOrdine " + orderBy + ", a.scDescrizione " + orderBy + ",a.scCodice " + orderBy;
	}
	return getHibernateTemplate().find(hql, values);
    }

    @Override
    public int countAlberoprocFigli(String idcomune, String scCodicePadre, boolean soloPrimoLivello) {

	String hql = "Select count(*) from Alberoproc a where a.id.idcomune=? and a.software.codice=? ";
	hql += " and a.scCodice like ? ";
	Object[] values = null;
	if (soloPrimoLivello) {
	    values = new Object[] { idcomune, ORMHelper.getSoftware(), scCodicePadre + "%", Long.valueOf(scCodicePadre.length() + 2) };
	    hql += " and length(a.scCodice)=? ";
	} else {
	    values = new Object[] { idcomune, ORMHelper.getSoftware(), scCodicePadre + "%" };
	}
	Long ris = ((Long) getHibernateTemplate().find(hql, values).get(0)).longValue();
	return ris.intValue();
    }

    @Override
    public String findDescrizionePrimaVoceAlberoproc(String idcomune, String scCodice) {

	String hql = "Select scDescrizione from Alberoproc a  where a.id.idcomune=? and a.software.codice=? and a.scCodice=? ";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	int paramPos = 0;
	q.setString(paramPos, idcomune);
	paramPos++;
	q.setString(paramPos, ORMHelper.getSoftware());
	paramPos++;
	q.setString(paramPos, scCodice.substring(0, 2));
	List<String> list = (List<String>) q.list();
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public String findDescrizioneAlberoproc(String idcomune, Integer codiceAlberoproc) {

	String hql = "Select scDescrizione from Alberoproc a  where a.id.idcomune=? and a.id.codice=? ";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	int paramPos = 0;
	q.setString(paramPos, idcomune);
	paramPos++;
	q.setInteger(paramPos, codiceAlberoproc);
	paramPos++;
	List<String> list = (List<String>) q.list();
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public List<AlberoprocCommand> findAlberoprocCommand(String idcomunebase, Integer rootCodiceAlbero) {

	String scCodice = "";
	if (rootCodiceAlbero != null) {
	    Alberoproc ap = this.findById(new PkId(idcomunebase, rootCodiceAlbero));
	    if (ap == null) {
		throw new RuntimeException("Nessun intervento con codice " + rootCodiceAlbero.intValue());
	    }
	    scCodice = ap.getScCodice();
	}
	// Setto condizioni base di where
	DetachedCriteria criteria = getIdcomunebaseAndSoftwareCriteria();
	if (StringUtils.isNotBlank(scCodice)) {
	    criteria.add(Restrictions.like("scCodice", scCodice, MatchMode.START));
	}
	// Setto condizioni di join
	criteria.createAlias("vwAlberoproc", "_vwAlberoproc");
	//Setto i campi per cui vogliamo fare la projection
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "ID");
	plist.add(Projections.property("scDescrizione"), "NAME");
	plist.add(Projections.property("_vwAlberoproc.scDescrizione"), "DESCRIZIONEESTESA");
	plist.add(Projections.property("scCodice"), "CODICE");
	plist.add(Projections.property("scAttivo"), "SCATTIVO");
	plist.add(Projections.property("scPadre"), "PADRE");
	// Setto condizioni di ordinamento
	criteria.addOrder(OrderBySqlFormula.asc("scCodice", OrderBySqlFormula.FunctionsEnum.LENGTH_FUNCTION));
	criteria.addOrder(Order.asc("scOrdine"));
	criteria.addOrder(Order.asc("scDescrizione"));
	criteria.setProjection(plist);
	//
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(AlberoprocCommand.class));
	List<AlberoprocCommand> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    public void updateScCodice(String idcomune, Integer codiceAlberoproc, String scCodice) {

	if (codiceAlberoproc == null) {
	    throw new RuntimeException("updateScCodice: il parametro codiceAlberoproc passato è nullo");
	}
	if (StringUtils.isBlank(scCodice)) {
	    throw new RuntimeException("updateScCodice: il parametro updateScCodice passato è nullo ");
	}
	String hql = "update Alberoproc set scCodice = ? where id.idcomune = ? and id.codice=?";
	getHibernateTemplate().bulkUpdate(hql, new Object[] { scCodice, idcomune, codiceAlberoproc });
    }

    @Override
    public boolean checkComunica(Alberoproc ap) {

	String hql = "select count(*) from Alberoproc a where a.id.idcomune=? and a.software.codice=? and length(a.scCodice)>=? and a.scCodice like ? and a.scAttivo=0 and (a.scPubblica=null or a.scPubblica in(1,2)) and a.flagComunica=?";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	q.setString(0, ap.getId().getIdcomune());
	q.setString(1, ORMHelper.getSoftware());
	q.setInteger(2, ap.getScCodice().length());
	q.setString(3, ap.getScCodice() + "%");
	q.setInteger(4, 1);
	Long size = (Long) q.uniqueResult();
	if (size == null || size == 0) {
	    return false;
	}
	return true;
    }

    @Override
    public List<InterventoSimpleBean> findInterventiByDescrizione(String idcomune, String testoDaCercare, String tipoRicerca, String campiRicerca,
	    boolean filtraSoloComunica, Integer firstResult, Integer maxResults, boolean soloModulisticaNazionale) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryAlberoprocPerInterventiHelper qih = new QueryAlberoprocPerInterventiHelper(sessimpl, idcomune, testoDaCercare, tipoRicerca,
		campiRicerca, filtraSoloComunica, false, soloModulisticaNazionale);
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
	q.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(InterventoSimpleBean.class));
	List<InterventoSimpleBean> result = (List<InterventoSimpleBean>) q.list();
	return result;
    }

    @Override
    public boolean checkModulisticaNazionale(Alberoproc ap) {

	String hql = "select count(*) from Alberoproc a where a.id.idcomune=? and a.software.codice=? and length(a.scCodice)>=? and a.scCodice like ? "
		+ "and a.scAttivo=0 and (a.scPubblica=null or a.scPubblica in(1,2)) and a.flagModulisiticanazionale=?";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	q.setString(0, ap.getId().getIdcomune());
	q.setString(1, ORMHelper.getSoftware());
	q.setInteger(2, ap.getScCodice().length());
	q.setString(3, ap.getScCodice() + "%");
	q.setInteger(4, 1);
	Long size = (Long) q.uniqueResult();
	if (size == null || size == 0) {
	    return false;
	}
	return true;
    }

    @Override
    public Set<Integer> verificaInterventiConEndoPrincipale(Map<Integer, String> codiciIntervento, String codiceComune) {

	Set<Integer> result = new HashSet<Integer>();
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	Dialect dialetto = sessimpl.getDialect();
	BaseQueryHelper.DIALETTO d = BaseQueryHelper.fromString(dialetto.toString());
	String schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	for (Entry<Integer, String> intervento : codiciIntervento.entrySet()) {
	    String sc_codice = intervento.getValue();
	    Integer sc_id = intervento.getKey();
	    int lengthCodice = sc_codice.length();
	    int lengthTree = lengthCodice / 2;
	    List<String> codiciPadre = new ArrayList<String>();
	    for (int i = 0; i < lengthTree - 1; i++) {
		lengthCodice = lengthCodice - 2;
		codiciPadre.add(sc_codice.substring(0, lengthCodice));
	    }
	    String sql = getQueryAlberoprocEndoForScCodice(schemaName, d, sc_codice, codiciPadre);
	    SQLQuery q = getSession().createSQLQuery(sql);
	    int pos = 0;
	    q.setString(pos++, ORMHelper.getIdcomunebase());
	    if (codiciPadre.size() > 0) {
		q.setString(pos++, ORMHelper.getIdcomunebase());
		q.setString(pos++, ORMHelper.getSoftware());
		for (String cp : codiciPadre) {
		    q.setString(pos++, cp);
		}
	    }
	    q.setString(pos++, ORMHelper.getIdcomunebase());
	    q.setString(pos++, ORMHelper.getSoftware());
	    q.setString(pos++, sc_codice + "%");
	    q.setInteger(pos++, 1); // principale
	    q.setInteger(pos++, 1); // pubblica
	    q.addScalar("conta", Hibernate.BIG_DECIMAL);
	    List<BigDecimal> list = (List<BigDecimal>) q.list();
	    boolean trovato = false;
	    for (BigDecimal c : list) {
		if (c != null && c.intValue() > 0) {
		    trovato = true;
		}
	    }
	    if (trovato) {
		result.add(sc_id);
	    } else {
		sql = getQueryAlberoprocEndoLocForScCodice(schemaName, d, sc_codice, codiciPadre);
		q = getSession().createSQLQuery(sql);
		pos = 0;
		q.setString(pos++, ORMHelper.getIdcomune());
		q.setString(pos++, ORMHelper.getIdcomunebase());
		if (codiciPadre.size() > 0) {
		    q.setString(pos++, ORMHelper.getIdcomunebase());
		    q.setString(pos++, ORMHelper.getSoftware());
		    for (String cp : codiciPadre) {
			q.setString(pos++, cp);
		    }
		}
		q.setString(pos++, ORMHelper.getIdcomunebase());
		q.setString(pos++, ORMHelper.getSoftware());
		q.setString(pos++, sc_codice + "%");
		q.setInteger(pos++, 1); // necessario
		q.setInteger(pos++, 1); // pubblica
		q.setString(pos++, codiceComune);
		q.addScalar("conta", Hibernate.BIG_DECIMAL);
		list = (List<BigDecimal>) q.list();
		for (BigDecimal c : list) {
		    if (c != null && c.intValue() > 0) {
			trovato = true;
			result.add(sc_id);
		    }
		}
	    }
	}
	return result;
    }

    private String getQueryAlberoprocEndoLocForScCodice(String schemaName, DIALETTO d, String sc_codice, List<String> codiciPadre) {

	String sql = "SELECT COUNT(*) as conta FROM SCHEMA_OWNER.ALBEROPROC_ENDO_LOC WHERE IDCOMUNE=? AND FK_AP_IDCOMUNE=?  AND (  ";
	if (codiciPadre.size() > 0) {
	    sql += " ALBEROPROC_ENDO_LOC.FK_AP_SCID IN (SELECT SC_ID FROM SCHEMA_OWNER.ALBEROPROC WHERE IDCOMUNE=? AND SOFTWARE=? AND SC_CODICE IN (";
	    String in = "";
	    for (String scCodicePadre : codiciPadre) {
		in += "?,";
	    }
	    in = in.substring(0, (in.length() - 1));
	    sql += in + ") ) OR ";
	}
	sql += "   FK_AP_SCID IN (SELECT SC_ID FROM SCHEMA_OWNER.ALBEROPROC WHERE IDCOMUNE=? AND SOFTWARE=? AND SC_CODICE LIKE ?)) "
		+ "AND ALBEROPROC_ENDO_LOC.FLAG_NECESSARIO=? AND FLAG_PUBBLICA=? and (ALBEROPROC_ENDO_LOC.codicecomune is null or ALBEROPROC_ENDO_LOC.codicecomune=?)";
	return sql.toString().replaceAll("SCHEMA_OWNER", schemaName);
    }

    private String getQueryAlberoprocEndoForScCodice(String schemaName, DIALETTO d, String sc_codice, List<String> codiciPadre) {

	String sql = "SELECT COUNT(*) as conta FROM SCHEMA_OWNER.ALBEROPROC_ENDO WHERE SCHEMA_OWNER.ALBEROPROC_ENDO.IDCOMUNE=? AND ( ";
	if (codiciPadre.size() > 0) {
	    sql += " FKSCID IN (SELECT SC_ID FROM SCHEMA_OWNER.ALBEROPROC WHERE IDCOMUNE=? AND SOFTWARE=? AND SC_CODICE IN (";
	    String in = "";
	    for (String scCodicePadre : codiciPadre) {
		in += "?,";
	    }
	    in = in.substring(0, (in.length() - 1));
	    sql += in + ")  ) OR ";
	}
	sql += "   FKSCID IN (SELECT SC_ID FROM SCHEMA_OWNER.ALBEROPROC WHERE IDCOMUNE=? AND SOFTWARE=? AND SC_CODICE LIKE ? )) "
		+ "AND FLAG_PRINCIPALE=? AND FLAG_PUBBLICA=?";
	return sql.toString().replaceAll("SCHEMA_OWNER", schemaName);
    }
}
