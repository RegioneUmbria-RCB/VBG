package it.gruppoinit.pal.gp.core.features.attivita;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.Dyn2CampiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;
import it.gruppoinit.pal.gp.core.dao.helper.QueryIAttivitaHelper;
import it.gruppoinit.pal.gp.core.dao.helper.TipoQueryHelperEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IAttivitaDaChiudereHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IAttivitaListHelper;
import it.gruppoinit.pal.gp.core.domain.web.IAttivitaFilter;
import it.gruppoinit.pal.gp.core.features.attivita.datilocalizzativi.LocalizzazioniAttivitaDTO;
import it.gruppoinit.pal.gp.core.features.attivita.datilocalizzativi.QueryAttivitaLocalizzazioniDaFilterHelper;

/**
 * 
 * @author
 */
@Repository
public class IAttivitaDAOImpl extends BaseDAOImpl<IAttivita, PkId> implements IAttivitaDAO {

    private static final Logger log = LoggerFactory.getLogger(IAttivitaDAOImpl.class);
    private Dyn2CampiDAO dyn2CampiDAO;

    @Autowired
    public void setDyn2CampiDAO(Dyn2CampiDAO dyn2CampiDAO) {

	this.dyn2CampiDAO = dyn2CampiDAO;
    }

    @Override
    public Class<IAttivita> getEntityClass() {

	return IAttivita.class;
    }

    @Override
    public List<IAttivita> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }

    @Override
    public List<Istanze> findIstanzeOrdinate(IAttivita iattivita, boolean visstorico) {

	// §§§BEGIN§§§
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	Dialect dialetto = sessimpl.getDialect();
	String hibernateDialect = dialetto.toString();
	String propertyOrderDat = "";
	if (hibernateDialect.indexOf("Oracle") > 0) {
	    propertyOrderDat = "to_char(_istanza.datavalidita,'YYYYMMDD'),?";
	} else if (hibernateDialect.indexOf("MySQL") > 0) {
	    propertyOrderDat = "DATE_FORMAT(_istanza.datavalidita,'%Y%m%d'),?";
	}
	String hql = "select _istanza from IAttivita this_ inner join this_.istanza _istanza where  this_.id.idcomune =? and this_.id.codice=?";
	hql += " order by coalesce( " + propertyOrderDat + " ) desc, coalesce(_istanza.attivitaOrdine,?) asc, _istanza.id.codice desc";
	if (visstorico) {
	    hql = "select _istanza from IAttivita this_ inner join this_.istanzes _istanza where  this_.id.idcomune =? and this_.id.codice=?";
	    hql += " order by coalesce(" + propertyOrderDat + ") desc, coalesce(_istanza.attivitaOrdine,?) asc, _istanza.id.codice desc";
	}
	Calendar c = Calendar.getInstance();
	c.set(Calendar.YEAR, 1000);
	c.set(Calendar.MONTH, 1);
	c.set(Calendar.DATE, 1);
	String dateYYYTMMDD = "19000101";
	Object[] values = new Object[] { ORMHelper.getIdcomune(), iattivita.getId().getCodice(), dateYYYTMMDD, Integer.valueOf(0) };
	@SuppressWarnings("unchecked")
	List<Istanze> result = getHibernateTemplate().find(hql, values);
	return result;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public int countIstanzeWithDateValiditaNull(IAttivita iattivita, boolean visstorico) {

	// §§§BEGIN§§§
	String hql = "select _istanza from IAttivita this_ inner join this_.istanza _istanza where  this_.id.idcomune =? and this_.id.codice=? and _istanza.datavalidita is NULL";
	if (visstorico) {
	    hql = "select _istanza from IAttivita this_ inner join this_.istanzes _istanza where  this_.id.idcomune =? and this_.id.codice=? and _istanza.datavalidita is NULL";
	}
	Object[] values = new Object[] { ORMHelper.getIdcomune(), iattivita.getId().getCodice() };
	@SuppressWarnings("unchecked")
	List<Istanze> result = getHibernateTemplate().find(hql, values);
	if (result != null && !result.isEmpty()) {
	    return result.size();
	}
	return 0;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return 0;@@@ENDALTERNATIVEEXIT@@@
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BigDecimal> findCatenaIstanzeDaCollegare(final Integer codiceIstanzaDaEscludere) {

	// §§§BEGIN§§§
	if (codiceIstanzaDaEscludere == null) {
	    throw new IllegalArgumentException("Il parametro codiceIstanzaDaEscludere non può essere nullo");
	}
	return (List<BigDecimal>) this.getHibernateTemplate().execute(new HibernateCallback() {

	    @Override
	    public List<BigDecimal> doInHibernate(Session session) throws HibernateException, SQLException {

		BigDecimal codiceIstanza = new BigDecimal(codiceIstanzaDaEscludere);
		SQLQuery queryCount = session.createSQLQuery(queryStrCount);
		queryCount.setString(0, ORMHelper.getIdcomune());
		queryCount.setBigDecimal(1, codiceIstanza);
		queryCount.setString(2, ORMHelper.getIdcomune());
		queryCount.setBigDecimal(3, codiceIstanza);
		queryCount.addScalar("conteggio", Hibernate.BIG_DECIMAL);
		List<BigDecimal> counts = queryCount.list();
		int n = 0;
		if (counts != null && counts.size() > 0) {
		    n = ((BigDecimal) counts.get(0)).intValue();
		}
		if (n == 0) {
		    SQLQuery query = session.createSQLQuery(queryStr);
		    query.setString(0, ORMHelper.getIdcomune());
		    query.setBigDecimal(1, codiceIstanza);
		    query.setString(2, ORMHelper.getIdcomune());
		    query.setBigDecimal(3, codiceIstanza);
		    query.addScalar("codiceistanza", Hibernate.BIG_DECIMAL);
		    List<BigDecimal> iReturn = (List<BigDecimal>) query.list();
		    return iReturn;
		} else {
		    return new ArrayList<BigDecimal>(0);
		}
	    }
	});
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IAttivitaListHelper> findIAttivitaListHelperByFilter(IAttivitaFilter filter, Integer firstResult, Integer maxResults) {

	log.debug("findHelperByFilter: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryIAttivitaHelper qih = new QueryIAttivitaHelper(filter, sessimpl, dyn2CampiDAO, false, null, TipoQueryHelperEnum.SELECT);
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
	q.setResultTransformer(Transformers.aliasToBean(IAttivitaListHelper.class));
	List<IAttivitaListHelper> result = (List<IAttivitaListHelper>) q.list();
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public int countIAttivitaListHelperByFilter(IAttivitaFilter filter) {

	log.debug("countHelperByFilter: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryIAttivitaHelper qih = new QueryIAttivitaHelper(filter, sessimpl, dyn2CampiDAO, true, null, TipoQueryHelperEnum.COUNT);
	String sql = qih.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	qih.setFilterValues(q);
	q.addScalar("conteggio_attivita", Hibernate.BIG_DECIMAL);
	List<BigDecimal> rs = q.list();
	int ris = ((BigDecimal) rs.get(0)).intValue();
	return ris;
    }

    // §§§BEGIN§§§
    private final String queryStr = "select istanze.codiceistanza as codiceistanza, istanze.datavalidita from istanze, " +
	    " vw_full_istanzecollegate, vw_full_istanzecollegate progressivi where " +
	    " istanze.idcomune = vw_full_istanzecollegate.idcomune and istanze.codiceistanza = vw_full_istanzecollegate.codiceistanza and " +
	    " vw_full_istanzecollegate.idcomune = progressivi.idcomune and " +
	    " vw_full_istanzecollegate.progressivo = progressivi.progressivo and progressivi.idcomune = ? and " +
	    " progressivi.codiceistanza = ? and istanze.idcomune = ? and istanze.codiceistanza <> ? group by" +
	    " istanze.codiceistanza, istanze.datavalidita, istanze.numeroistanza, istanze.fk_idi_attivita order by istanze.datavalidita asc";
    private final String queryStrCount = "select count(*) as conteggio from istanze, vw_full_istanzecollegate, vw_full_istanzecollegate progressivi where " +
	    " istanze.idcomune = vw_full_istanzecollegate.idcomune and istanze.codiceistanza = vw_full_istanzecollegate.codiceistanza and " +
	    " vw_full_istanzecollegate.idcomune = progressivi.idcomune and " +
	    " vw_full_istanzecollegate.progressivo = progressivi.progressivo and progressivi.idcomune = ? and " +
	    " progressivi.codiceistanza = ? and istanze.idcomune = ? and istanze.codiceistanza <> ? and istanze.fk_idi_attivita is not null";

    // §§§END§§§
    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findCodiciIstanza(Integer idiattivita) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaname = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	String sql = "select codiceistanza from " + schemaname + ".istanze where idcomune=? and fk_idi_attivita=?";
	SQLQuery query = getSession().createSQLQuery(sql);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idiattivita);
	query.addScalar("codiceistanza", Hibernate.INTEGER);
	List<Integer> iReturn = (List<Integer>) query.list();
	return iReturn;
    }

    /**
     * <pre>
     * Il medoto sfrutta la creazione di  che crea una query di ricerca sulle attività per i filtri passati. 
     * La query di select viene incapsulata in una query di insert.
     * ES.
     * 
     * Insert into NOME_TABELLA (nome_campo1,..nome_campoN) (select nome_campo1,..nome_campoN from NOME_TABELLA where condizioni
     * </pre>
     */
    @Override
    public String exportModalitaPentaho(IAttivitaFilter attivitaFilter, Esportazioni esportazioni, Date data, String emailResponsabile,
	    String contesto, boolean isInviaMail) {

	log.debug("exportModalitaPentaho: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryIAttivitaHelper qih = null;
	String cont = StringUtils.defaultIfEmpty(contesto, "ATT");
	if (cont.equals("ATS")) {
	    if (data == null) {
		data = new Date();
	    }
	    qih = new QueryIAttivitaHelper(attivitaFilter, sessimpl, dyn2CampiDAO, false, data, TipoQueryHelperEnum.PENTAHO_EXP_IN_DATA);
	} else {
	    qih = new QueryIAttivitaHelper(attivitaFilter, sessimpl, dyn2CampiDAO, false, null, TipoQueryHelperEnum.PENTAHO_EXP);
	}
	String sql = qih.buildQuery();
	sql = "insert into tmp_esportazioni(IDCOMUNE, SESSIONID, CODICE, CODICECOMUNE, DATA) ( " + sql + ")";
	log.debug("exportModalitaPentaho# Query : {}", sql);
	SQLQuery q = getSession().createSQLQuery(sql);
	qih.setFilterValues(q);
	//qih.setScalarProperties(q);
	q.executeUpdate();
	return q.getQueryString();
    }

    @Override
    public Integer generaCodiceOsservatorio() {

	PkIdGenerator gen = new PkIdGenerator();
	Properties params = new Properties();
	params.put(PkIdGenerator.TABLE, "I_ATTIVITA");
	params.put(PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, "true");
	params.put(PkIdGenerator.TABLE_PARAM, "SEQUENCETABLE");
	params.put(PkIdGenerator.VALUE_COLUMN_PARAM, "CURRVAL");
	params.put(PkIdGenerator.SEGMENT_COLUMN_PARAM, "SEQUENCENAME");
	params.put(PkIdGenerator.SEGMENT_VALUE_PARAM, "I_ATTIVITA.CODICE_OSSERVATORIO");
	SessionImplementor source = (SessionImplementor) getHibernateTemplate().getSessionFactory().getCurrentSession();
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	Dialect dialetto = sessimpl.getDialect();
	gen.configure(new IntegerType(), params, dialetto);
	PkId generatedId = (PkId) gen.generate(source, new IAttivita());
	if (generatedId != null) {
	    return generatedId.getCodice();
	}
	return 1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IAttivitaDaChiudereHelper> findAttivitaScadute(Date data, Integer firstResult, Integer maxResult) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("istanza", "_istanza");
	criteria.createAlias("_istanza.software", "_software");
	Criterion rest1 = Restrictions.eq("operante", true);
	Criterion rest2 = Restrictions.eq("attiva", true);
	criteria.add(Restrictions.or(rest1, rest2));
	//	criteria.add(Restrictions.eq("operante", true));
	//	criteria.add(Restrictions.eq("attiva", true));
	criteria.add(Restrictions.isNotNull("dataFine"));
	criteria.add(Restrictions.le("dataFine", data));
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "CODICEATTIVITA");
	plist.add(Projections.property("id.idcomune"), "IDCOMUNE");
	plist.add(Projections.property("denominazione"), "ATTIVITA");
	plist.add(Projections.property("_software.codice"), "SOFTWARE");
	plist.add(Projections.property("dataFine"), "DATAFINE");
	criteria.setProjection(plist);
	//
	criteria.addOrder(Order.asc("id.idcomune"));
	criteria.addOrder(Order.asc("_software.codice"));
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(IAttivitaDaChiudereHelper.class));
	List<IAttivitaDaChiudereHelper> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    public int updateNonOperanteENonAttiva(IAttivitaDaChiudereHelper iAttivitaDaChiudereHelper) {

	if (iAttivitaDaChiudereHelper == null) {
	    throw new RuntimeException("updateNonOperanteENonAttiva: il parametro iAttivitaDaChiudereHelper passato è nullo");
	}
	String hql = "update IAttivita set operante = ?, attiva = ? where id.idcomune = ? and id.codice=?";
	int i = getHibernateTemplate().bulkUpdate(hql,
		new Object[] { false, false, iAttivitaDaChiudereHelper.getIdcomune(), iAttivitaDaChiudereHelper.getCodiceattivita() });
	if (i != 1) {
	    throw new RuntimeException("La query di aggiornamento in non operante e non attiva dell'attivita :[" +
		    iAttivitaDaChiudereHelper.getCodiceattivita() +
		    "] ha influito su " +
		    i +
		    " record");
	}
	return i;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findIdAttivitaWithoutSnapshot(Integer firstResult, Integer maxResult) {

	Session session = this.getSession(false);
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schema = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	String sql = "SELECT I_ATTIVITA.ID " +
		" FROM  " +
		schema +
		".I_ATTIVITA " +
		" INNER JOIN " +
		schema +
		".ISTANZE ON I_ATTIVITA.IDCOMUNE = ISTANZE.IDCOMUNE AND I_ATTIVITA.CODICEISTANZAULTIMA = ISTANZE.CODICEISTANZA AND ISTANZE.DATAVALIDITA IS NOT NULL " +
		" LEFT JOIN " +
		schema +
		".I_ATTIVITA_SNAPSHOT ON I_ATTIVITA.IDCOMUNE = I_ATTIVITA_SNAPSHOT.IDCOMUNE AND I_ATTIVITA.ID = I_ATTIVITA_SNAPSHOT.FK_IA_ID " +
		" WHERE  I_ATTIVITA.IDCOMUNE = ? AND I_ATTIVITA_SNAPSHOT.IDCOMUNE IS NULL ORDER BY I_ATTIVITA.ID DESC";
	//		+ " AND EXISTS (SELECT FK_IDI_ATTIVITA FROM ISTANZE WHERE I_ATTIVITA.IDCOMUNE = ISTANZE.IDCOMUNE AND I_ATTIVITA.ID = ISTANZE.FK_IDI_ATTIVITA AND ISTANZE.DATAVALIDITA IS NOT NULL ) ";
	log.debug(sql);
	SQLQuery sqlQuery = session.createSQLQuery(sql);
	if (firstResult != null) {
	    sqlQuery.setFirstResult(firstResult);
	}
	if (maxResult != null) {
	    sqlQuery.setMaxResults(maxResult);
	}
	sqlQuery.setString(0, ORMHelper.getIdcomune());
	List<BigDecimal> list = (List<BigDecimal>) sqlQuery.list();
	List<Integer> ris = new ArrayList<Integer>();
	for (BigDecimal bigDecimal : list) {
	    ris.add(new Integer(bigDecimal.intValue()));
	}
	return ris;
    }

    @Override
    public void updateAttiva(Integer codiceAttivita, Boolean attiva) {

	if (codiceAttivita == null) {
	    throw new RuntimeException("updateAttiva: il parametro iAttivitaDaChiudereHelper passato è nullo");
	}
	String hql = "update IAttivita set attiva = ? where id.idcomune = ? and id.codice=?";
	int i = getHibernateTemplate().bulkUpdate(hql, new Object[] { attiva, ORMHelper.getIdcomune(), codiceAttivita });
	if (i != 1) {
	    throw new RuntimeException(
		    "La query di aggiornamento attiva e non attiva dell'attivita :[" + codiceAttivita + "] ha influito su " + i + " record");
	}
    }

    @Override
    public void updateDenominazione(Integer codiceAttivita, String denominazione) {

	if (codiceAttivita == null) {
	    throw new RuntimeException("updateAttiva: il parametro iAttivitaDaChiudereHelper passato è nullo");
	}
	String hql = "update IAttivita set denominazione = ? where id.idcomune = ? and id.codice=?";
	int i = getHibernateTemplate().bulkUpdate(hql, new Object[] { denominazione, ORMHelper.getIdcomune(), codiceAttivita });
	if (i != 1) {
	    throw new RuntimeException(
		    "La query di aggiornamento denominazione dell'attivita :[" + codiceAttivita + "] ha influito su " + i + " record");
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LocalizzazioniAttivitaDTO> findLocalizzazioniByFilter(IAttivitaFilter filter) {

	log.debug("findLocalizzazioniByFilter: inizio");
	QueryAttivitaLocalizzazioniDaFilterHelper helper = new QueryAttivitaLocalizzazioniDaFilterHelper(this.dyn2CampiDAO, filter);
	String sql = helper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	helper.setFilterValues(q);
	helper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(LocalizzazioniAttivitaDTO.class));
	List<LocalizzazioniAttivitaDTO> retVal = q.list();
	log.debug("findLocalizzazioniByFilter: fine");
	return retVal;
	/*
	log.debug("findHelperByFilter: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryIAttivitaHelper qih = new QueryIAttivitaHelper(filter, sessimpl, alberoprocDAO, statiistanzaDAO, dyn2CampiDAO, false, null,
		TipoQueryHelperEnum.SELECT);
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
	q.setResultTransformer(Transformers.aliasToBean(IAttivitaListHelper.class));
	List<IAttivitaListHelper> result = (List<IAttivitaListHelper>) q.list();
	return result;
	*/
    }
}
