package it.gruppoinit.pal.gp.core.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.Dyn2CampiDAO;
import it.gruppoinit.pal.gp.core.dao.IstanzeDAO;
import it.gruppoinit.pal.gp.core.dao.StatiistanzaDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ArchiviazioniMetadatiOggettiQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.IstanzeConOggettiPerArchiviazioneDocumentaleQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.dao.helper.QueryIstanzeComMassHelper;
import it.gruppoinit.pal.gp.core.dao.helper.QueryIstanzeHelper;
import it.gruppoinit.pal.gp.core.dao.helper.TipoQueryHelperEnum;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StatiistanzaId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeDaChiudereHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeListHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzePerArchiviazioneDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzePerInterventiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzePerProcedimentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzestradarioDTO;
import it.gruppoinit.pal.gp.core.domain.helper.SorteggidettaglioDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.domain.web.TracciatoEquitaliaFilter;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.HelperTypeEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.custom.DettaglioRigaIstanze;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioDAO;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.helper.ArchiviazioneMetadatiIstanza;
import it.gruppoinit.pal.gp.core.service.helper.ArchiviazioneMetadatiOggetto;
import it.gruppoinit.pal.gp.core.service.helper.IstanzePerArchiviazioneFilter;
import it.gruppoinit.pal.gp.core.service.helper.TipoDocumentoPratica;
import it.gruppoinit.pal.gp.core.service.impl.ArchiviazioniManagerImpl;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Repository
public class IstanzeDAOImpl extends BaseComuniAssociatiDAOImpl<Istanze, PkId> implements IstanzeDAO {

    private static final Logger log = LoggerFactory.getLogger(IstanzeDAOImpl.class);
    private AlberoprocDAO alberoprocDAO;
    private ComuniassociatiService comuniassociatiService;
    private VerticalizzazioniService verticalizzazioniService;
    private StatiistanzaDAO statiistanzaDAO;
    private IstanzestradarioDAO istanzestradarioDAO;
    private Dyn2CampiDAO dyn2CampiDAO;

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setDyn2CampiDAO(Dyn2CampiDAO dyn2CampiDAO) {

	this.dyn2CampiDAO = dyn2CampiDAO;
    }

    @Autowired
    public void setIstanzestradarioDAO(IstanzestradarioDAO istanzestradarioDAO) {

	this.istanzestradarioDAO = istanzestradarioDAO;
    }

    @Autowired
    public void setAlberoprocDAO(AlberoprocDAO alberoprocDAO) {

	this.alberoprocDAO = alberoprocDAO;
    }

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setStatiistanzaDAO(StatiistanzaDAO statiistanzaDAO) {

	this.statiistanzaDAO = statiistanzaDAO;
    }

    @Override
    public Class<Istanze> getEntityClass() {

	return Istanze.class;
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public List<Istanze> findByNumeroistanzaOrRichiedente(String filterString) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.createAlias("richiedente", "_richiedente");
	det.add(Restrictions.or(Restrictions.ilike("numeroistanza", filterString, MatchMode.ANYWHERE),
		Restrictions.or(Restrictions.ilike("_richiedente.nome", filterString, MatchMode.ANYWHERE),
			Restrictions.ilike("_richiedente.nominativo", filterString, MatchMode.ANYWHERE))));
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.distinct(Projections.property("id.codice")));
	det.setProjection(projectionList);
	// det.addOrder(Order.asc("numeroistanza"));
	List<Integer> temp = getHibernateTemplate().findByCriteria(det);
	List<Istanze> istanzeList = new ArrayList<Istanze>();
	for (Integer integer : temp) {
	    PkId id = new PkId(integer);
	    Istanze istanze = this.findById(id);
	    istanzeList.add(istanze);
	}
	return istanzeList;
    }

    @Override
    protected void setCodiceComune(Istanze entity) {

	if (!checkIfCodiceComuneIsSet(entity.getComune())) {
	    entity.setComune(getDefaultComune());
	}
    }

    @Override
    public void updateStatoIstanza(Istanze istanza, String nuovoStato) {

	if (istanza == null) {
	    throw new RuntimeException("updateStatoIstanza: il parametro istanza passato è nullo");
	}
	if (EntityUtils.getNestedProperty(istanza, "id.codice") == null) {
	    throw new RuntimeException("updateStatoIstanza: il parametro istanza passato è nullo [" + istanza.getId() + "]");
	}
	if (StringUtils.isNotBlank(nuovoStato)) {
	    String hql = "update Istanze set chiusuraId = ? where id.idcomune = ? and id.codice=?";
	    int i = getHibernateTemplate().bulkUpdate(hql, new Object[] { nuovoStato, istanza.getId().getIdcomune(), istanza.getId().getCodice() });
	    istanza.setChiusura(statiistanzaDAO.findById(new StatiistanzaId(nuovoStato)));
	    if (i != 1) {
		throw new RuntimeException("La query di aggiornamento dello stato istanza :[" +
			istanza.getId() +
			"] con stato [" +
			nuovoStato +
			"] ha influito su " +
			i +
			" record");
	    }
	}
    }

    @Override
    public void updateOrdineAttivita(Integer codIstanza, Integer nuovoOrdine) {

	if (codIstanza == null) {
	    throw new RuntimeException("updateOrdineAttivita: il parametro cod istanza passato è nullo");
	}
	if (nuovoOrdine != null) {
	    String hql = "update Istanze set attivitaOrdine = ? where id.idcomune = ? and id.codice=?";
	    int i = getHibernateTemplate().bulkUpdate(hql, new Object[] { nuovoOrdine, ORMHelper.getIdcomune(), codIstanza });
	    if (i != 1) {
		throw new RuntimeException("La query di aggiornamento dello stato istanza :[" +
			codIstanza +
			"] con ordine [" +
			nuovoOrdine +
			"] ha influito su " +
			i +
			" record");
	    }
	}
    }

    @Override
    public void updateDatavalidita(String idcomune, Integer codiceIstanza, Date dataValidita) {

	if (codiceIstanza == null) {
	    throw new RuntimeException("updateDatavalidita: il parametro istanza passato è nullo");
	}
	if (StringUtils.isBlank(idcomune)) {
	    throw new RuntimeException("updateDatavalidita: il parametro idcomune passato è nullo");
	}
	String hql = "";
	int i = 0;
	if (dataValidita == null) {
	    hql = "update Istanze i set i.datavalidita = null where i.id.idcomune = ? and i.id.codice=? ";
	    i = getHibernateTemplate().bulkUpdate(hql, new Object[] { idcomune, codiceIstanza });
	} else {
	    //	    Calendar c = Calendar.getInstance();
	    //	    c.setTime(dataValidita);
	    //	    c.set(Calendar.HOUR_OF_DAY, 0);
	    //	    c.set(Calendar.MINUTE, 0);
	    //	    c.set(Calendar.SECOND, 0);
	    //	    Date _datavaliditaWithoutTime = new Date(c.getTimeInMillis());
	    //	    hql = "update Istanze i set i.datavalidita = ? where i.id.idcomune = ? and i.id.codice=? ";
	    //	    i = getHibernateTemplate().bulkUpdate(hql, new Object[] { _datavaliditaWithoutTime, idcomune, codiceIstanza });
	    hql = "update Istanze i set i.datavalidita = ? where i.id.idcomune = ? and i.id.codice=? ";
	    i = getHibernateTemplate().bulkUpdate(hql, new Object[] { dataValidita, idcomune, codiceIstanza });
	}
	if (i != 1) {
	    throw new RuntimeException("La query di aggiornamento della data validità istanza :[" +
		    codiceIstanza +
		    "-" +
		    idcomune +
		    "] con data [" +
		    dataValidita +
		    "] ha influito su " +
		    i +
		    " record");
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Istanze> findIstanzePerInserimentoMassivo(IstanzeFilter filter) {

	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	if (filter.getAlberoproc() != null) {
	    if (filter.getAlberoproc().getId() != null) {
		if (filter.getAlberoproc().getId().getCodice() != null) {
		    criteria.add(Restrictions.eq("alberoprocId", filter.getAlberoproc().getId().getCodice()));
		}
	    }
	}
	if (EntityUtils.getNestedProperty(filter, "dallaData") != null) {
	    criteria.add(Restrictions.ge("data", filter.getDallaData()));
	}
	if (EntityUtils.getNestedProperty(filter, "allaData") != null) {
	    criteria.add(Restrictions.le("data", filter.getAllaData()));
	}
	String hierarchyMovimento = "istanzemovimentis";
	FilterField<String> existsTipoMovimento = new FilterField<String>("tipomovimento.id.tipomovimento", hierarchyMovimento,
		FieldOperationsEnum.EXISTS, new String[] { filter.getTipoMovimento().getId().getTipomovimento() }, Movimenti.class);
	existsTipoMovimento.setExistsChildEntityId("istanza.id");
	existsTipoMovimento.setExistsParentEntityId("id");
	Criterion notExists = gestExistsCondition(existsTipoMovimento, false, true, FilterUtils.isNotNull("data"));
	criteria.add(notExists);
	if (filter.getTipoMovimentoFattoPerInserimentoMassivo() != null) {
	    if (filter.getTipoMovimentoFattoPerInserimentoMassivo().getId() != null) {
		if (StringUtils.isNotEmpty(filter.getTipoMovimentoFattoPerInserimentoMassivo().getId().getTipomovimento())) {
		    FilterField<String> existsTipoMovimentoFatto = new FilterField<String>("tipomovimento.id.tipomovimento", hierarchyMovimento,
			    FieldOperationsEnum.EXISTS,
			    new String[] { filter.getTipoMovimentoFattoPerInserimentoMassivo().getId().getTipomovimento() }, Movimenti.class);
		    existsTipoMovimentoFatto.setExistsChildEntityId("istanza.id");
		    existsTipoMovimentoFatto.setExistsParentEntityId("id");
		    Criterion exists = gestExistsCondition(existsTipoMovimentoFatto, true, true, FilterUtils.isNotNull("data"));
		    criteria.add(exists);
		}
	    }
	}
	// Criterion or = Restrictions.or(notExists, exists);	
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findTuttiCodiciIstanzaPerSoftwareAndIntervento(String pSoftware, List<Integer> codiceIntervento) {

	String hql = "Select i.id.codice from Istanze i where i.id.idcomune=? and i.software.codice=? ";
	Object[] values = null;
	if (codiceIntervento != null) {
	    if (codiceIntervento.size() > 0) {
		String qm = "";
		values = new Object[(2 + codiceIntervento.size())];
		values[0] = ORMHelper.getIdcomune();
		values[1] = pSoftware;
		int pos = 2;
		for (Integer ci : codiceIntervento) {
		    qm += "?,";
		    values[pos] = ci;
		    pos++;
		}
		qm = qm.substring(0, qm.length() - 1);
		hql += " and i.alberoprocId in (" + qm + ")";
	    } else {
		values = new Object[] { ORMHelper.getIdcomune(), pSoftware };
	    }
	} else {
	    values = new Object[] { ORMHelper.getIdcomune(), pSoftware };
	}
	return getHibernateTemplate().find(hql, values);
    }

    @Override
    public void updateMqIstanza(Integer codiceIstanza, BigDecimal metriquadrati) {

	if (codiceIstanza == null) {
	    throw new RuntimeException("updateMqIstanza: il parametro istanza passato è nullo");
	}
	if (metriquadrati == null) {
	    metriquadrati = BigDecimal.ZERO;
	}
	String hql = "update Istanze set metriquadrati = ? where id.idcomune = ? and id.codice=?";
	int i = getHibernateTemplate().bulkUpdate(hql, new Object[] { metriquadrati, ORMHelper.getIdcomune(), codiceIstanza });
	if (i != 1) {
	    throw new RuntimeException("La query di aggiornamento dei metriquadrati istanza :[" + codiceIstanza + "] ha modificato " + i + " record");
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IstanzeListHelper> findIstanzeListHelperByFilter(IstanzeFilter filter, Integer firstResult, Integer maxResults) {

	log.debug("findHelperByFilter: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryIstanzeHelper qih = new QueryIstanzeHelper(filter, sessimpl, alberoprocDAO, statiistanzaDAO, dyn2CampiDAO, comuniassociatiService,
		verticalizzazioniService, TipoQueryHelperEnum.SELECT, firstResult, maxResults);
	String sql = qih.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	Dialect d = sessimpl.getDialect();
	String hibernateDialect = d.toString();
	DialettoEnum dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	//..
	if (!DialettoEnum.ORACLE.equals(dialetto)) {
	    if (firstResult != null) {
		q.setFirstResult(firstResult);
	    }
	    if (maxResults != null) {
		q.setMaxResults(maxResults);
	    }
	}
	qih.setFilterValues(q);
	qih.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(IstanzeListHelper.class));
	List<IstanzeListHelper> result = (List<IstanzeListHelper>) q.list();
	return result;
    }
    
    @Override
    public List<DettaglioRigaIstanze> findIstanzeListHelperByFilterMass(IstanzeFilter filter, HelperTypeEnum type, Integer firstResult, Integer maxResults) {

	log.debug("findHelperByFilter: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryIstanzeComMassHelper qih = new QueryIstanzeComMassHelper(filter, sessimpl, alberoprocDAO, statiistanzaDAO, dyn2CampiDAO, comuniassociatiService,
		verticalizzazioniService, type, firstResult, maxResults);
	String sql = qih.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
//	Dialect dialetto = sessimpl.getDialect();
//	String hibernateDialect = dialetto.toString();
//	DIALETTO _dialetto = qih.fromString(hibernateDialect);
//	//..
//	if (!DIALETTO.ORACLE.equals(_dialetto)) {
//	    if (firstResult != null) {
//		q.setFirstResult(firstResult);
//	    }
//	    if (maxResults != null) {
//		q.setMaxResults(maxResults);
//	    }
//	}
	qih.setFilterValues(q);
	qih.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(DettaglioRigaIstanze.class));
	List<DettaglioRigaIstanze> result = (List<DettaglioRigaIstanze>) q.list();
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public int countIstanzeListHelperByFilter(IstanzeFilter filter) {

	log.debug("countHelperByFilter: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryIstanzeHelper qih = new QueryIstanzeHelper(filter, sessimpl, alberoprocDAO, statiistanzaDAO, dyn2CampiDAO, comuniassociatiService,
		verticalizzazioniService, TipoQueryHelperEnum.COUNT, null, null);
	String sql = qih.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	qih.setFilterValues(q);
	q.addScalar("conteggio_istanze", Hibernate.BIG_DECIMAL);
	List<BigDecimal> rs = q.list();
	int ris = ((BigDecimal) rs.get(0)).intValue();
	return ris;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Istanze> findByAttivitaAndBeforeDataValiditaIstanza(Integer codiceAttivita, Date dataValidita, Integer ordine) {

	if (ordine == null) {
	    ordine = Integer.valueOf(0);
	}
	//DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	DetachedCriteria criteria = getIdcomuneCriteria();
	// criteria.createAlias("software", "_software");
	// FIXME deve arrivare da  ORMHelper che ora passa sempre TT
	//criteria.add(Restrictions.eq("_software.codice", "CO"));
	criteria.add(Restrictions.eq("attivitaId", codiceAttivita));
	criteria.add(Restrictions.le("datavalidita", dataValidita));
	criteria.addOrder(
		OrderBySqlFormula.desc("datavalidita", FunctionsEnum.NVL_FUNCTION, "'01/01/2999'", OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	criteria.addOrder(Order.asc("attivitaOrdine"));
	List<Istanze> list = getHibernateTemplate().findByCriteria(criteria);
	// Devo escludere tutte quelle istanze che hanno la stessa data di validi di quella passata ed ordine < di quello passato.
	List<Istanze> risultato = new ArrayList<Istanze>();
	log.debug("Escludo dalla lista le istanze trovate quelle con istanza.datavalidita == {} e istanza.Attivitaordine",
		new Object[] { dataValidita, ordine });
	for (Istanze istanze : list) {
	    // Posso mettere diverso da zero in quanto le date al massimo saranno uguali , in quanto la query precedente
	    // elimina tutte quelle con data maggiore
	    if (Utilities.compareDates(dataValidita, istanze.getDatavalidita()) != 0) {
		risultato.add(istanze);
	    } else {
		if (istanze.getAttivitaOrdine() == null || istanze.getAttivitaOrdine().compareTo(ordine) >= 0) {
		    risultato.add(istanze);
		}
	    }
	}
	return risultato;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Istanze> findByAttivitaAndBeforeDataValiditaIstanza(Integer codiceAttivita, Date dataValidita, boolean includiDataValiditaNull) {

	//DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	DetachedCriteria criteria = getIdcomuneCriteria();
	//criteria.createAlias("software", "_software");
	// FIXME deve arrivare da  ORMHelper che ora passa sempre TT
	//criteria.add(Restrictions.eq("_software.codice", "CO"));
	criteria.add(Restrictions.eq("attivitaId", codiceAttivita));
	if (includiDataValiditaNull) {
	    criteria.add(Restrictions.or(Restrictions.le("datavalidita", dataValidita), Restrictions.isNull("datavalidita")));
	} else {
	    criteria.add(Restrictions.le("datavalidita", dataValidita));
	}
	criteria.addOrder(
		OrderBySqlFormula.asc("datavalidita", FunctionsEnum.NVL_FUNCTION, "'01/01/1900'", OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	criteria.addOrder(Order.asc("attivitaOrdine"));
	List<Istanze> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Istanze findIstanzaUltimaAttivitaAllaData(Integer codiceAttivita, Date dataValidita) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("attivitaId", codiceAttivita));
	criteria.add(Restrictions.eq("datavalidita", dataValidita));
	criteria.addOrder(OrderBySqlFormula.asc("attivitaOrdine", FunctionsEnum.NVL_FUNCTION, "0"));
	List<Istanze> list = getHibernateTemplate().findByCriteria(criteria, 0, 1);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public int findOrdineAttivitaMaxByData(IAttivita attivita, Date datavalidita) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("attivita", "_attivita");
	criteria.add(Restrictions.eq("_attivita.id.codice", attivita.getId().getCodice()));
	if (datavalidita != null) {
	    criteria.add(Restrictions.eq("datavalidita", datavalidita));
	} else {
	    criteria.add(Restrictions.isNull("datavalidita"));
	}
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.max("attivitaOrdine"));
	criteria.setProjection(projectionList);
	List<Integer> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    if (list.get(0) != null) {
		return list.get(0);
	    }
	}
	return 0;
    }

    @Override
    public void updateLavoriestesa(Integer codiceIstanza, String lavoriestesa) {

	if (codiceIstanza == null) {
	    throw new RuntimeException("updateLavoriestesa: il parametro istanza passato è nullo");
	}
	String hql = "update Istanze set lavoriestesa = ? where id.idcomune = ? and id.codice=?";
	int i = getHibernateTemplate().bulkUpdate(hql, new Object[] { lavoriestesa, ORMHelper.getIdcomune(), codiceIstanza });
	if (i != 1) {
	    throw new RuntimeException("La query di aggiornamento dei lavoriestesa istanza :[" + codiceIstanza + "] ha modificato " + i + " record");
	}
    }

    @Override
    public void updateTipoProtFallita(Integer codiceIstanza, String tipoProtFallita) {

	if (codiceIstanza == null) {
	    throw new RuntimeException("updateTipoProtFallita: il parametro istanza passato è nullo");
	}
	String hql = "update Istanze set tipoProtFallita = ? where id.idcomune = ? and id.codice=?";
	int i = getHibernateTemplate().bulkUpdate(hql, new Object[] { tipoProtFallita, ORMHelper.getIdcomune(), codiceIstanza });
	if (i != 1) {
	    throw new RuntimeException("La query di aggiornamento dei lavoriestesa istanza :[" + codiceIstanza + "] ha modificato " + i + " record");
	}
    }

    @Override
    public void updateComuneIstanza(Integer codiceIstanza, String codiceComune) {

	if (codiceIstanza == null) {
	    throw new RuntimeException("updateComuneIstanza: il parametro istanza passato è nullo");
	}
	String hql = "update Istanze set codicecomune = ? where id.idcomune = ? and id.codice=?";
	int i = getHibernateTemplate().bulkUpdate(hql, new Object[] { codiceComune, ORMHelper.getIdcomune(), codiceIstanza });
	if (i != 1) {
	    throw new RuntimeException("La query di aggiornamento dei codiceComune istanza :[" + codiceIstanza + "] ha modificato " + i + " record");
	}
    }

    @Override
    public void updateNumeroistanza(Integer codiceIstanza, String numeroistanza) {

	if (codiceIstanza == null) {
	    throw new RuntimeException("updateNumeroistanza: il parametro codiceIstanza passato è nullo");
	}
	if (StringUtils.isBlank(StringUtils.defaultString(numeroistanza).trim())) {
	    throw new RuntimeException("updateNumeroistanza: il parametro numeroistanza passato è nullo");
	}
	String hql = "update Istanze set numeroistanza = ? where id.idcomune = ? and id.codice=?";
	int i = getHibernateTemplate().bulkUpdate(hql, new Object[] { numeroistanza, ORMHelper.getIdcomune(), codiceIstanza });
	if (i != 1) {
	    throw new RuntimeException(
		    "La query di aggiornamento updateNumeroistanza istanza :[" + codiceIstanza + "] ha modificato " + i + " record");
	}
    }

    @Override
    public void updateIstruttore(Integer codiceIstanza, Integer codiceIstruttore) {

	if (codiceIstanza == null) {
	    throw new RuntimeException("updateIstruttore: il parametro codiceIstanza passato è nullo");
	}
	if (codiceIstruttore == null) {
	    throw new RuntimeException("updateIstruttore: il parametro codiceIstruttore passato è nullo");
	}
	String hql = "update Istanze set istruttoreId = ? where id.idcomune = ? and id.codice=?";
	int i = getHibernateTemplate().bulkUpdate(hql, new Object[] { codiceIstruttore, ORMHelper.getIdcomune(), codiceIstanza });
	if (i != 1) {
	    throw new RuntimeException("La query di aggiornamento updateIstruttore istanza :[" + codiceIstanza + "] ha modificato " + i + " record");
	}
    }

    @Override
    public List<IstanzePerArchiviazioneDTO> findIstanzePerArchiviazioneDocumentale(IstanzePerArchiviazioneFilter filter) {

	throw new NotImplementedException("MODALITA' DI ARCHIVIAZIONE NON PIU' DISPONIBILE. IMPOSTARE ARCHIVIAZIONE_PER_OGGETTO ");
	//	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	//	if (ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_TT)) {
	//	    criteria = getIdcomuneCriteria();
	//	}
	//	criteria.createAlias("archiviazioniIstanzes", "_arch_istanze", DetachedCriteria.LEFT_JOIN);
	//	criteria.createAlias("istanzeTempistica", "_tempistica");
	//	criteria.createAlias("chiusura", "_chiusura");
	//	criteria.add(Restrictions.isNull("_arch_istanze.istanzeId"));
	//	criteria.add(Restrictions.isNotNull("_tempistica.datafineeffettiva"));
	//	//filtro su date
	//	if (filter.getDallaData() != null) {
	//	    criteria.add(Restrictions.ge("_tempistica.datafineeffettiva", filter.getDallaData()));
	//	}
	//	if (filter.getAllaData() != null) {
	//	    criteria.add(Restrictions.le("_tempistica.datafineeffettiva", filter.getAllaData()));
	//	}
	//	//
	//	criteria.add(Restrictions.ne("_chiusura.staticomportamento.codcomportamento", 0));
	//	//ordinamento
	//	criteria.addOrder(Order.asc("_tempistica.datafineeffettiva"));
	//	criteria.addOrder(Order.asc("id.codice"));
	//	//
	//	ProjectionList plist = Projections.projectionList();
	//	plist.add(Projections.property("id.codice"), "ID_CODICE");
	//	plist.add(Projections.property("_arch_istanze.id.codice"), "CODICEARCHIVIAZIONEISTANZE");
	//	plist.add(Projections.property("numeroistanza"), "NUMEROISTANZA");
	//	criteria.setProjection(plist);
	//	//
	//	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(IstanzePerArchiviazioneDTO.class));
	//	return getHibernateTemplate().findByCriteria(criteria, filter.getFirstResult(), filter.getMaxResult());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<IstanzePerArchiviazioneDTO> findIstanzeConOggettiPerArchiviazioneDocumentale(IstanzePerArchiviazioneFilter filter) {

	Set<IstanzePerArchiviazioneDTO> archiviazioneMetadatiOggettos = new HashSet<IstanzePerArchiviazioneDTO>();
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	// DOCUMENTI ISTANZA
	IstanzeConOggettiPerArchiviazioneDocumentaleQueryHelper queryHelper = new IstanzeConOggettiPerArchiviazioneDocumentaleQueryHelper(sfi, filter,
		filter.getMimeTypeFileAmmessi(), TipoDocumentoPratica.DOC_ISTANZA);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(IstanzePerArchiviazioneDTO.class));
	List<IstanzePerArchiviazioneDTO> archiviazioneMetadatiOggettoDocumentiIstanza = q.list();
	for (IstanzePerArchiviazioneDTO istanzePerArchiviazioneDTO : archiviazioneMetadatiOggettoDocumentiIstanza) {
	    archiviazioneMetadatiOggettos.add(istanzePerArchiviazioneDTO);
	}
	// ISTANZE ALLEGATI 
	IstanzeConOggettiPerArchiviazioneDocumentaleQueryHelper queryHelpereEndo = new IstanzeConOggettiPerArchiviazioneDocumentaleQueryHelper(sfi,
		filter, filter.getMimeTypeFileAmmessi(), TipoDocumentoPratica.DOC_ENDO);
	String sqlEndo = queryHelpereEndo.buildQuery();
	SQLQuery qEndo = getSession().createSQLQuery(sqlEndo);
	queryHelpereEndo.setFilterValues(qEndo);
	queryHelpereEndo.setScalarProperties(qEndo);
	qEndo.setResultTransformer(Transformers.aliasToBean(IstanzePerArchiviazioneDTO.class));
	List<IstanzePerArchiviazioneDTO> archiviazioneMetadatiOggettoDocumentiEndo = qEndo.list();
	for (IstanzePerArchiviazioneDTO istanzePerArchiviazioneDTO : archiviazioneMetadatiOggettoDocumentiEndo) {
	    archiviazioneMetadatiOggettos.add(istanzePerArchiviazioneDTO);
	}
	// ALLEGATI MOVIMENTI
	IstanzeConOggettiPerArchiviazioneDocumentaleQueryHelper queryHelperMovimenti = new IstanzeConOggettiPerArchiviazioneDocumentaleQueryHelper(
		sfi, filter, filter.getMimeTypeFileAmmessi(), TipoDocumentoPratica.DOC_MOVIMENTI);
	String sqlMovimentiAllegati = queryHelperMovimenti.buildQuery();
	SQLQuery qAllegatiMovimenti = getSession().createSQLQuery(sqlMovimentiAllegati);
	queryHelperMovimenti.setFilterValues(qAllegatiMovimenti);
	queryHelperMovimenti.setScalarProperties(qAllegatiMovimenti);
	qAllegatiMovimenti.setResultTransformer(Transformers.aliasToBean(IstanzePerArchiviazioneDTO.class));
	List<IstanzePerArchiviazioneDTO> archiviazioneMetadatiOggettoDocumentiMovimenti = qAllegatiMovimenti.list();
	for (IstanzePerArchiviazioneDTO istanzePerArchiviazioneDTO : archiviazioneMetadatiOggettoDocumentiMovimenti) {
	    archiviazioneMetadatiOggettos.add(istanzePerArchiviazioneDTO);
	}
	// ISTANZE PROCURE
	IstanzeConOggettiPerArchiviazioneDocumentaleQueryHelper queryHelperProcure = new IstanzeConOggettiPerArchiviazioneDocumentaleQueryHelper(sfi,
		filter, filter.getMimeTypeFileAmmessi(), TipoDocumentoPratica.DOC_PROCURE);
	String sqlProcure = queryHelperProcure.buildQuery();
	SQLQuery qProcure = getSession().createSQLQuery(sqlProcure);
	queryHelperProcure.setFilterValues(qProcure);
	queryHelperProcure.setScalarProperties(qProcure);
	qProcure.setResultTransformer(Transformers.aliasToBean(IstanzePerArchiviazioneDTO.class));
	List<IstanzePerArchiviazioneDTO> archiviazioneMetadatiOggettoDocumentiProcure = qProcure.list();
	for (IstanzePerArchiviazioneDTO istanzePerArchiviazioneDTO : archiviazioneMetadatiOggettoDocumentiProcure) {
	    archiviazioneMetadatiOggettos.add(istanzePerArchiviazioneDTO);
	}
	return archiviazioneMetadatiOggettos;
    }

    @Override
    public ArchiviazioneMetadatiIstanza findMetadatiIstanzaPerArchiviazioneDocumentale(Integer codiceIstanza) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("richiedente", "_richiedente");
	criteria.createAlias("professionista", "_professionista", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("software", "_software");
	criteria.createAlias("alberoproc", "_alberoproc");
	criteria.createAlias("_alberoproc.vwAlberoproc", "_vwalberoproc");
	criteria.add(Restrictions.eq("id.codice", codiceIstanza));
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "CODICEISTANZA");
	plist.add(Projections.property("numeroistanza"), "COLLEGAMENTOPRATICHE");
	plist.add(Projections.property("id.idcomune"), "IDCOMUNE");
	plist.add(Projections.property("_software.codice"), "TIPOLOGIAPRATICA");
	plist.add(Projections.property("_vwalberoproc.scDescrizione"), "SOTTOTIPO");
	//
	plist.add(Projections.property("numeroprotocollo"), "NUMEROPROTOCOLLO");
	plist.add(Projections.property("dataprotocollo"), "DATAPROTOCOLLO");
	//
	plist.add(Projections.property("_richiedente.nome"), "RICHIEDENTENOME");
	plist.add(Projections.property("_richiedente.nominativo"), "RICHIEDENTECOGNOME");
	plist.add(Projections.property("_richiedente.codicefiscale"), "RICHIEDENTECF");
	plist.add(Projections.property("_richiedente.partitaiva"), "RICHIEDENTEPIVA");
	//
	plist.add(Projections.property("_professionista.nome"), "PROFESSIONISTANOME");
	plist.add(Projections.property("_professionista.nominativo"), "PROFESSIONISTACOGNOME");
	plist.add(Projections.property("_professionista.codicefiscale"), "PROFESSIONISTACF");
	plist.add(Projections.property("_professionista.partitaiva"), "PROFESSIONISTAPIVA");
	criteria.setProjection(plist);
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(ArchiviazioneMetadatiIstanza.class));
	return (ArchiviazioneMetadatiIstanza) getHibernateTemplate().findByCriteria(criteria).get(0);
    }

    @Override
    public List<ArchiviazioneMetadatiOggetto> findMetadatiOggettiPerArchiviazioneDocumentale(Integer codiceIstanza) {

	return this.findMetadatiOggettiPerArchiviazioneDocumentale(codiceIstanza, null, ArchiviazioniManagerImpl.ARCHIVIAZIONE_MULTI_ISTANZE);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ArchiviazioneMetadatiOggetto> findMetadatiOggettiPerArchiviazioneDocumentale(Integer codiceIstanza,
	    IstanzePerArchiviazioneFilter filter, String algoritomoArchiviazione) {

	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	List<ArchiviazioneMetadatiOggetto> archiviazioneMetadatiOggettos = new ArrayList<ArchiviazioneMetadatiOggetto>();
	// le lascio separate anche se fanno la stessa cosa, nel caso si voglia tornare alla logica precedente
	if (algoritomoArchiviazione.equals(ArchiviazioniManagerImpl.ARCHIVIAZIONE_MULTI_ISTANZE)) {
	    throw new NotImplementedException("MODALITA' DI ARCHIVIAZIONE NON PIU' DISPONIBILE. IMPOSTARE ARCHIVIAZIONE_PER_OGGETTO");
	    //	    log.debug("findMetadatiOggettiPerArchiviazioneDocumentale# ricerca oggetti da archiviare per algoritmo = {}", algoritomoArchiviazione);
	    //	    ArchiviazioniMetadatiOggettiQueryHelper queryHelper = new ArchiviazioniMetadatiOggettiQueryHelper(sfi, codiceIstanza, filter);
	    //	    String sql = queryHelper.buildQuery();
	    //	    SQLQuery q = getSession().createSQLQuery(sql);
	    //	    queryHelper.setFilterValues(q);
	    //	    queryHelper.setScalarProperties(q);
	    //	    q.setResultTransformer(Transformers.aliasToBean(ArchiviazioneMetadatiOggetto.class));
	    //	    archiviazioneMetadatiOggettos = q.list();
	} else {
	    log.debug("findMetadatiOggettiPerArchiviazioneDocumentale# ricerca oggetti da archiviare per algoritmo = {}", algoritomoArchiviazione);
	    ArchiviazioniMetadatiOggettiQueryHelper queryHelper = new ArchiviazioniMetadatiOggettiQueryHelper(sfi, codiceIstanza, filter);
	    String sql = queryHelper.buildQuery();
	    SQLQuery q = getSession().createSQLQuery(sql);
	    queryHelper.setFilterValues(q);
	    queryHelper.setScalarProperties(q);
	    q.setResultTransformer(Transformers.aliasToBean(ArchiviazioneMetadatiOggetto.class));
	    if (filter.getMaxResultOggetti() != null) {
		q.setMaxResults(filter.getMaxResultOggetti());
	    }
	    archiviazioneMetadatiOggettos = q.list();
	}
	return archiviazioneMetadatiOggettos;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IstanzeDTO> findAttivitaOrdineByAttivitaAndDatavalidita(Integer codiceAttivita, Date dataValidita) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("attivitaId", codiceAttivita));
	criteria.add(Restrictions.eq("datavalidita", dataValidita));
	criteria.addOrder(OrderBySqlFormula.asc("attivitaOrdine", FunctionsEnum.NVL_FUNCTION, "0"));
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	plist.add(Projections.property("attivitaOrdine"), "ATTIVITAORDINE");
	criteria.setProjection(plist);
	//
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(IstanzeDTO.class));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public List<CodiceDescrizioneBean> findMetadatiIstanza(Integer codiceIstanza) {

	List<CodiceDescrizioneBean> result = new ArrayList<CodiceDescrizioneBean>();
	if (codiceIstanza == null) {
	    return result;
	}
	IstanzeFilter f = new IstanzeFilter();
	f.setCodiceIstanza(codiceIstanza);
	List<IstanzeListHelper> s = findIstanzeListHelperByFilter(f, 0, 1);
	if (!s.isEmpty()) {
	    IstanzeListHelper i = s.get(0);
	    //	INITMD_NUMERO_ISTANZA
	    CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
	    cdb.setCodice("INITMD_NUMERO_ISTANZA");
	    cdb.setDescrizione(i.getNumeroistanza());
	    result.add(cdb);
	    //		INITMD_RICHIEDENTE_CF
	    if (StringUtils.isNotBlank(i.getRichiedentecodicefiscale())) {
		cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_RICHIEDENTE_CF");
		cdb.setDescrizione(i.getRichiedentecodicefiscale());
		result.add(cdb);
	    }
	    //	INITMD_RICHIEDENTE_DESCRIZIONE
	    if (StringUtils.isNotBlank(i.getRichiedentenominativo())) {
		cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_RICHIEDENTE_DESCRIZIONE");
		String nominativo = i.getRichiedentenominativo();
		if (StringUtils.isNotBlank(i.getRichiedentenome())) {
		    nominativo += " " + i.getRichiedentenome();
		}
		cdb.setDescrizione(nominativo);
		result.add(cdb);
	    }
	    //	INITMD_AZIENDA_RAGIONESOCIALE
	    if (StringUtils.isNotBlank(i.getAziendanominativo())) {
		cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_AZIENDA_RAGIONESOCIALE");
		String nominativo = i.getAziendanominativo();
		if (StringUtils.isNotBlank(i.getAziendanome())) {
		    nominativo += " " + i.getAziendanome();
		}
		cdb.setDescrizione(nominativo);
		result.add(cdb);
	    }
	    //	INITMD_AZIENDA_CF_PIVA
	    if (StringUtils.isNotBlank(i.getAziendacodicefiscale()) || StringUtils.isNotBlank(i.getAziendapartitaiva())) {
		cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_AZIENDA_CF_PIVA");
		String cfPiva = "";
		boolean presenteCF = false;
		if (StringUtils.isNotBlank(i.getAziendacodicefiscale())) {
		    cfPiva = "CF: " + i.getAziendacodicefiscale();
		    presenteCF = true;
		}
		if (presenteCF) {
		    cfPiva += ", ";
		}
		if (StringUtils.isNotBlank(i.getAziendapartitaiva())) {
		    cfPiva += "PIVA: " + i.getAziendapartitaiva();
		}
		cdb.setDescrizione(cfPiva);
		result.add(cdb);
	    }
	    //	INITMD_TIPO_INTERVENTO
	    if (StringUtils.isNotBlank(i.getInterventoproc())) {
		cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_TIPO_INTERVENTO");
		cdb.setDescrizione(i.getInterventoproc());
		result.add(cdb);
	    }
	    // INITMD_DOMICILIO_ELETTRONICO
	    if (StringUtils.isNotBlank(i.getDomicilioelettronico())) {
		cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_DOMICILIO_ELETTRONICO");
		cdb.setDescrizione(i.getDomicilioelettronico());
		result.add(cdb);
	    }
	    // INITMD_CODICEPRATICATELEMATICA
	    if (StringUtils.isNotBlank(i.getCodicepraticatelematica())) {
		cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_CODICEPRATICATELEMATICA");
		cdb.setDescrizione(i.getCodicepraticatelematica());
		result.add(cdb);
	    }
	    // INITMD_DATAPRATICA
	    if (i.getData() != null) {
		String data = Utilities.formatDate(i.getData(), false);
		cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_DATAPRATICA");
		cdb.setDescrizione(data);
		result.add(cdb);
	    }
	    // INITMD_SPORTELLO
	    if (StringUtils.isNotBlank(i.getDescrizionesportello())) {
		cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_SPORTELLO");
		cdb.setDescrizione(i.getDescrizionesportello());
		result.add(cdb);
	    }
	    // INITMD_RESP_PROCEDIMENTO
	    if (StringUtils.isNotBlank(i.getResponsabileprocnome())) {
		cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_RESP_PROCEDIMENTO");
		cdb.setDescrizione(i.getResponsabileprocnome());
		result.add(cdb);
	    }
	    // INITMD_COMUNE_ISTANZA
	    if (StringUtils.isNotBlank(i.getComune())) {
		cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_COMUNE_ISTANZA");
		cdb.setDescrizione(i.getComune());
		result.add(cdb);
	    }
	    // INITMD_DATA_PROT_ISTANZA
	    if (i.getDataprotocollo() != null) {
		String data = Utilities.formatDate(i.getDataprotocollo(), false);
		cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_DATA_PROT_ISTANZA");
		cdb.setDescrizione(data);
		result.add(cdb);
	    }
	    // INITMD_NUM_PROT_ISTANZA
	    if (StringUtils.isNotBlank(i.getNumeroprotocollo())) {
		cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_NUM_PROT_ISTANZA");
		cdb.setDescrizione(i.getNumeroprotocollo());
		result.add(cdb);
	    }
	    // INITMD_AZIENDA_PIVA
	    if (StringUtils.isNotBlank(i.getAziendapartitaiva())) {
		cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_AZIENDA_PIVA");
		cdb.setDescrizione(i.getAziendapartitaiva());
		result.add(cdb);
	    }
	    // INITMD_OGGETTO_ISTANZA
	    if (StringUtils.isNotBlank(i.getOggettoistanza())) {
		cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_OGGETTO_ISTANZA");
		cdb.setDescrizione(i.getOggettoistanza());
		result.add(cdb);
	    }
	    if (StringUtils.isNotBlank(i.getTecnicocognome())) {
		cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_INTERM_DESCRIZIONE");
		String nominativo = i.getTecnicocognome();
		if (StringUtils.isNotBlank(i.getTecniconome())) {
		    nominativo += " " + i.getTecniconome();
		}
		cdb.setDescrizione(nominativo);
		result.add(cdb);
	    }
	    // INITMD_INTERM_CF
	    if (StringUtils.isNotBlank(i.getTecnicocf())) {
		cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_INTERM_CF");
		cdb.setDescrizione(i.getTecnicocf());
		result.add(cdb);
	    }
	    // INITMD_INTERM_PIVA
	    if (StringUtils.isNotBlank(i.getTecnicopiva())) {
		cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_INTERM_PIVA");
		cdb.setDescrizione(i.getTecnicopiva());
		result.add(cdb);
	    }
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IstanzeDaChiudereHelper> findIstanzedaChiudere() {

	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String sql = queryIstanzeDaChiudere.replaceAll(SCHEMA_NAME, schemaName + ".");
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("codiceIstanza", Hibernate.INTEGER);
	q.addScalar("numeroistanza", Hibernate.STRING);
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("statochiusura", Hibernate.STRING);
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, ORMHelper.getSoftware());
	q.setDate(2, Calendar.getInstance().getTime());
	q.setResultTransformer(Transformers.aliasToBean(IstanzeDaChiudereHelper.class));
	return q.list();
    }

    protected final String SCHEMA_NAME = "#SCHEMA_NAME#";
    private String queryIstanzeDaChiudere = "SELECT i.idcomune as idcomune, " + //
	    "  i.codiceistanza as codiceistanza, " + //
	    "  i.numeroistanza as numeroistanza, " + //
	    "  i.software, tp.fk_stato_chius AS statochiusura " + //
	    "FROM " +
	    SCHEMA_NAME +
	    "istanze i " + // 
	    "INNER JOIN " +
	    SCHEMA_NAME +
	    "istanze_tempistica it " + //
	    "ON it.idcomune=i.idcomune " + //
	    "AND it.codiceistanza=i.codiceistanza " + //
	    "INNER JOIN " +
	    SCHEMA_NAME +
	    "statiistanza si " + //
	    "ON si.idcomune=i.idcomune " + //
	    "AND si.software=i.software " + //
	    "AND si.codicestato=i.chiusura " + //
	    "AND si.fkcodcomportamento=0 " + //
	    "INNER JOIN " +
	    SCHEMA_NAME +
	    "tipiprocedure tp " + //
	    "ON tp.idcomune=i.idcomune " + //
	    "AND tp.codiceprocedura=i.codiceprocedura " + //
	    "WHERE i.idcomune=? " + //
	    "AND i.software=? " + //
	    "AND (it.datafine IS NOT NULL " + //
	    "AND it.datafine <= ?) " + //
	    "AND tp.flag_chiusura_aut=1 " + //
	    "AND tp.idchiusuraistanza IS NOT NULL " + //
	    "AND NOT EXISTS " + //
	    "  (SELECT 1 " + //
	    "  FROM " +
	    SCHEMA_NAME +
	    "movimenti m " + //
	    "  WHERE m.idcomune=i.idcomune " + //
	    "  AND m.codiceistanza=i.codiceistanza " + //
	    "  AND m.tipomovimento=tp.idchiusuraistanza " + //
	    "  AND m.data IS NOT NULL )";

    @SuppressWarnings("unchecked")
    @Override
    public List<IstanzePerInterventiHelper> countNumeroIstanzeGrupByInterventi(String codiceSoftware, Date fromDate, Date toDate, Integer startRow,
	    Integer maxRow) {

	String queryIstanzeGroupByInterventi = "select count(*) as numero, CODICEINTERVENTOPROC as codiceIntervento" +
		" from " +
		SCHEMA_NAME +
		"istanze " +
		" where idcomune=? and software=? " +
		" and data> ? and data < ?" +
		" group by CODICEINTERVENTOPROC " +
		" order by  numero desc ";
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String sql = queryIstanzeGroupByInterventi.replaceAll(SCHEMA_NAME, schemaName + ".");
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("numero", Hibernate.INTEGER);
	q.addScalar("codiceIntervento", Hibernate.INTEGER);
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, codiceSoftware);
	Calendar t = Calendar.getInstance();
	t.setTime(fromDate);
	t.set(Calendar.HOUR, 0);
	t.set(Calendar.MINUTE, 0);
	t.set(Calendar.SECOND, 0);
	q.setTimestamp(2, t.getTime());
	Calendar t1 = Calendar.getInstance();
	t.setTime(toDate);
	t.set(Calendar.HOUR, 0);
	t.set(Calendar.MINUTE, 0);
	t.set(Calendar.SECOND, 0);
	q.setTimestamp(3, t1.getTime());
	q.setResultTransformer(Transformers.aliasToBean(IstanzePerInterventiHelper.class));
	List<IstanzePerInterventiHelper> list = new ArrayList<IstanzePerInterventiHelper>();
	if (startRow != null && maxRow != null) {
	    q.setFirstResult(startRow);
	    q.setMaxResults(maxRow);
	    list = q.list();
	} else {
	    list = q.list();
	}
	return list;
    }

    /**
     * 
     * 
     * select count(*) as numero, CODICEINVENTARIO from ISTANZEPROCEDIMENTI left join ISTANZE on
     * ISTANZEPROCEDIMENTI.IDCOMUNE =ISTANZE.IDCOMUNE and ISTANZEPROCEDIMENTI.CODICEISTANZA =ISTANZE.CODICEISTANZA where
     * ISTANZEPROCEDIMENTI.IDCOMUNE='E256' and ISTANZE.SOFTWARE='SS' and data > TO_DATE('01-08-2013','dd-MM-yyyy') and
     * data < TO_DATE('18-02-2016','dd-MM-yyyy') group by CODICEINVENTARIO order by numero desc
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<IstanzePerProcedimentiHelper> countNumeroIstanzeGrupByProcedimenti(String codiceSoftware, Date fromDate, Date toDate,
	    Integer startRow, Integer maxRow) {

	String query = "select count(*) as numero, CODICEINVENTARIO as codiceProcedimento " +
		" from ISTANZEPROCEDIMENTI left join ISTANZE on " +
		" ISTANZEPROCEDIMENTI.IDCOMUNE =ISTANZE.IDCOMUNE and ISTANZEPROCEDIMENTI.CODICEISTANZA =ISTANZE.CODICEISTANZA " +
		" where ISTANZEPROCEDIMENTI.IDCOMUNE=? and ISTANZE.SOFTWARE=? and data >? and " +
		" data < ? " +
		" group by CODICEINVENTARIO order by numero desc";
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String sql = query.replaceAll(SCHEMA_NAME, schemaName + ".");
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("numero", Hibernate.INTEGER);
	q.addScalar("codiceProcedimento", Hibernate.INTEGER);
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, codiceSoftware);
	Calendar t = Calendar.getInstance();
	t.setTime(fromDate);
	t.set(Calendar.HOUR, 0);
	t.set(Calendar.MINUTE, 0);
	t.set(Calendar.SECOND, 0);
	q.setTimestamp(2, t.getTime());
	Calendar t1 = Calendar.getInstance();
	t.setTime(toDate);
	t.set(Calendar.HOUR, 0);
	t.set(Calendar.MINUTE, 0);
	t.set(Calendar.SECOND, 0);
	q.setTimestamp(3, t1.getTime());
	q.setResultTransformer(Transformers.aliasToBean(IstanzePerProcedimentiHelper.class));
	List<IstanzePerProcedimentiHelper> list = new ArrayList<IstanzePerProcedimentiHelper>();
	if (startRow != null && maxRow != null) {
	    q.setFirstResult(startRow);
	    q.setMaxResults(maxRow);
	    list = q.list();
	} else {
	    list = q.list();
	}
	return list;
    }

    /**
     * <pre>
     * Il medoto sfrutta la creazione di QueryIstanzeHelper che crea una query di ricerca sulle istanze per i filtri passati. 
     * La query di select viene incapsulata in una query di insert.
     * ES.
     * 
     * Insert into NOME_TABELLA (nome_campo1,..nome_campoN) (select nome_campo1,..nome_campoN from NOME_TABELLA where condizioni
     * </pre>
     */
    @Override
    public String exportModalitaPentaho(IstanzeFilter filter, Esportazioni esportazioni, String emailResponsabile, boolean isInviaMail) {

	log.debug("findHelperByFilter: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	// Creao la query di ricerca sql su istanze per filtri passati
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryIstanzeHelper qih = new QueryIstanzeHelper(filter, sessimpl, alberoprocDAO, statiistanzaDAO, dyn2CampiDAO, comuniassociatiService,
		verticalizzazioniService, TipoQueryHelperEnum.PENTAHO_EXP, null, null);
	String sql = qih.buildQuery();
	// Incpsulo la query di ricerca in una query di insert
	sql = "insert into tmp_esportazioni(IDCOMUNE, SESSIONID, CODICE, CODICECOMUNE, DATA) ( " + sql + ")";
	log.debug("exportModalitaPentaho# Query : {}", sql);
	SQLQuery q = getSession().createSQLQuery(sql);
	// Imposto i filtri nella query di ricerca
	qih.setFilterValues(q);
	//
	q.executeUpdate();
	return q.getQueryString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public SorteggidettaglioDTO findByIstanza(Integer codiceIstanza) {

	//////////////////////////////////////////// CONDIZIONI DI FROM ///////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("id.codice", codiceIstanza));
	det.createAlias("alberoproc", "_alberoproc", Criteria.LEFT_JOIN);
	det.createAlias("_alberoproc.vwAlberoproc", "_vwAlberoproc", Criteria.LEFT_JOIN);
	det.createAlias("tipiarchivioistanza", "_tipiarchivioistanza", Criteria.LEFT_JOIN);
	det.createAlias("responsabile", "_responsabile", Criteria.LEFT_JOIN);
	det.createAlias("istruttore", "_istruttore", Criteria.LEFT_JOIN);
	det.createAlias("responsabileProcedimento", "_responsabileProcedimento", Criteria.LEFT_JOIN);
	det.createAlias("richiedente", "_richiedente", Criteria.LEFT_JOIN);
	det.createAlias("titolarelegale", "_titolarelegale", Criteria.LEFT_JOIN);
	det.createAlias("tipisoggetto", "_tipisoggetto", Criteria.LEFT_JOIN);
	det.createAlias("comune", "_comune", Criteria.LEFT_JOIN);
	///////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////// CONDIZIONI DI SELECT ///////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "codiceistanza");
	//istanza.numeroistanza
	plist.add(Projections.property("numeroistanza"), "numeroistanza");
	//istanza.data
	plist.add(Projections.property("data"), "dataistanza");
	//istanza.numeroprotocollo
	plist.add(Projections.property("numeroprotocollo"), "numeroprotocollo");
	//istanza.dataprotocollo
	plist.add(Projections.property("dataprotocollo"), "dataprotocollo");
	//istanza.alberoproc.vwAlberoproc.scDescrizione
	plist.add(Projections.property("_vwAlberoproc.scDescrizione"), "scDescrizione");
	//istanza.tipiarchivioistanza.archivio
	plist.add(Projections.property("_tipiarchivioistanza.archivio"), "archivio");
	//istanza.lavori
	plist.add(Projections.property("lavori"), "lavori");
	//istanza.responsabile.responsabile
	plist.add(Projections.property("_responsabile.responsabile"), "responsabile");
	//istanza.istruttore.responsabile
	plist.add(Projections.property("_istruttore.responsabile"), "responsabileIstruttore");
	//istanza.istruttore.responsabile
	plist.add(Projections.property("_responsabileProcedimento.responsabile"), "responsabileprocedimento");
	//istanza.richiedente.nominativo
	plist.add(Projections.property("_richiedente.nominativo"), "richiedenteNominativo");
	//istanza.richiedente.nome
	plist.add(Projections.property("_richiedente.nome"), "richiedenteNome");
	//istanza.richiedente.nome
	plist.add(Projections.property("_titolarelegale.nominativo"), "titolarelegaleNominativo");
	plist.add(Projections.property("_tipisoggetto.flgSpecificadescrizione"), "flgSpecificadescrizione");
	plist.add(Projections.property("_tipisoggetto.tiposoggetto"), "tiposoggetto");
	plist.add(Projections.property("descrsoggetto"), "descrsoggetto");
	plist.add(Projections.property("_comune.comune"), "comune");
	det.setProjection(plist);
	det.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(SorteggidettaglioDTO.class));
	det.addOrder(Order.desc("data"));
	String[] padNumeroistanza = new String[] { "20", "' '" };
	det.addOrder(OrderBySqlFormula.desc("numeroistanza", FunctionsEnum.LPAD_FUNCTION, padNumeroistanza));
	//List<SorteggidettaglioDTO> result=new ArrayList<SorteggidettaglioDTO>();
	List<SorteggidettaglioDTO> list = (List<SorteggidettaglioDTO>) getHibernateTemplate().findByCriteria(det);
	for (SorteggidettaglioDTO sorteggidettaglioDTO : list) {
	    IstanzestradarioDTO istanzestradarioDTO = istanzestradarioDAO.findIstanzeStradarioDTOByIstanza(sorteggidettaglioDTO.getCodiceistanza());
	    if (istanzestradarioDTO != null) {
		sorteggidettaglioDTO.setIstanzestradarioDTO(istanzestradarioDTO);
	    } else {
		sorteggidettaglioDTO.setIstanzestradarioDTO(new IstanzestradarioDTO());
	    }
	}
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeResponsabiliPerIstanzeInCorso(Integer codiceIstanza, Map<Integer, Integer> codResp,
	    String scCodice) {

	String qMarks = "";
	for (int i = 0; i < codResp.size(); i++) {
	    qMarks += ",?";
	}
	if (qMarks.length() > 0) {
	    qMarks = qMarks.substring(1);
	}
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String sql = queryCountResponsabiliIstanza.replaceAll(SCHEMA_NAME, schemaName + ".");
	sql = sql.replaceAll("CODICI_RESPONSABILI", qMarks);
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("chiave", Hibernate.INTEGER);
	q.addScalar("valore", Hibernate.INTEGER);
	q.setInteger(0, 0);
	q.setString(1, ORMHelper.getIdcomune());
	q.setString(2, ORMHelper.getSoftware());
	q.setString(3, StringUtils.defaultString(scCodice) + "%");
	q.setInteger(4, codiceIstanza);
	int pos = 5;
	for (Map.Entry<Integer, Integer> m : codResp.entrySet()) {
	    q.setInteger(pos, m.getKey());
	    pos++;
	}
	q.setResultTransformer(Transformers.aliasToBean(ChiaveValoreBean.class));
	return q.list();
    }

    private String queryCountResponsabiliIstanza = "select istanze.codiceresponsabileproc as chiave, " //
	    + "  count(*) as valore " //
	    + "from " + SCHEMA_NAME + "istanze " //
	    + "inner join " + SCHEMA_NAME + "alberoproc " // 
	    + "on alberoproc.idcomune = istanze.idcomune " //
	    + "and alberoproc.sc_id   = istanze.codiceinterventoproc " //
	    + "inner join " + SCHEMA_NAME + "statiistanza " //
	    + "on statiistanza.idcomune = istanze.idcomune " //
	    + "and statiistanza.SOFTWARE = istanze.software " //
	    + "and statiistanza.codicestato       = istanze.chiusura " //
	    + "and statiistanza.fkcodcomportamento=? " //
	    + "inner join " + SCHEMA_NAME + "responsabili r " //
	    + "on r.idcomune            = istanze.idcomune " //
	    + "and r.codiceresponsabile = istanze.codiceresponsabileproc " //
	    + "where istanze.idcomune   = ? " //
	    + "and istanze.software     = ? " //
	    + "and alberoproc.sc_codice like ? " //
	    + "and (not codiceistanza   = ? ) " //
	    + "and istanze.codiceresponsabileproc in (CODICI_RESPONSABILI) " //
	    + "group by istanze.codiceresponsabileproc " //
	    + "order by count(*) asc";//

    @SuppressWarnings("unchecked")
    @Override
    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeIstruttoriPerIstanzeInCorso(Integer codiceIstanza, Map<Integer, Integer> codResp,
	    String scCodice) {

	String qMarks = "";
	for (int i = 0; i < codResp.size(); i++) {
	    qMarks += ",?";
	}
	if (qMarks.length() > 0) {
	    qMarks = qMarks.substring(1);
	}
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String sql = queryCountIstruttoriIstanza.replaceAll(SCHEMA_NAME, schemaName + ".");
	sql = sql.replaceAll("CODICI_RESPONSABILI", qMarks);
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("chiave", Hibernate.INTEGER);
	q.addScalar("valore", Hibernate.INTEGER);
	q.setInteger(0, 0);
	q.setString(1, ORMHelper.getIdcomune());
	q.setString(2, ORMHelper.getSoftware());
	q.setString(3, scCodice + "%");
	q.setInteger(4, codiceIstanza);
	int pos = 5;
	for (Map.Entry<Integer, Integer> m : codResp.entrySet()) {
	    q.setInteger(pos, m.getKey());
	    pos++;
	}
	q.setResultTransformer(Transformers.aliasToBean(ChiaveValoreBean.class));
	return q.list();
    }

    private String queryCountIstruttoriIstanza = "select istanze.codiceistruttore as chiave, " //
	    + "  count(*) as valore " //
	    + "from " + SCHEMA_NAME + "istanze " //
	    + "inner join " + SCHEMA_NAME + "alberoproc " // 
	    + "on alberoproc.idcomune = istanze.idcomune " //
	    + "and alberoproc.sc_id   = istanze.codiceinterventoproc " //
	    + "inner join " + SCHEMA_NAME + "statiistanza " //
	    + "on statiistanza.idcomune           = istanze.idcomune " //
	    + "and statiistanza.SOFTWARE = istanze.software " //
	    + "and statiistanza.codicestato       = istanze.chiusura " //
	    + "and statiistanza.fkcodcomportamento=? " //
	    + "inner join " + SCHEMA_NAME + "responsabili r " //
	    + "on r.idcomune            = istanze.idcomune " //
	    + "and r.codiceresponsabile = istanze.codiceistruttore " //
	    + "where istanze.idcomune   = ? " //
	    + "and istanze.software     = ? " //
	    + "and alberoproc.sc_codice like ? " //
	    + "and (not codiceistanza   = ? ) " //
	    + "and istanze.codiceistruttore in (CODICI_RESPONSABILI) " //
	    + "group by istanze.codiceistruttore " //
	    + "order by count(*) asc";//

    @Override
    public void updateContatori(boolean processaSoloIlPrimoGiornoDEllanno) {

	Calendar c = Calendar.getInstance();
	boolean esegui = true;
	if (processaSoloIlPrimoGiornoDEllanno && !(Utilities.isFirstMonth(c) && Utilities.isFirstDayofMonth(c))) {
	    esegui = false;
	}
	if (esegui) {
	    int nuovoAnno = c.get(Calendar.YEAR);
	    int annoPassato = nuovoAnno - 1;
	    //..
	    // CONCAT('1/',  REPLACE(SUBSTR(progressivoistanze, INSTR(progressivoistanze,'/')+1),@ANNO_PASSATO, @ANNO)) 
	    String update = "update configurazione set progressivoistanze=concat('1/'" + //
			    ", REPLACE(SUBSTR(progressivoistanze, INSTR(progressivoistanze,'/') +1 ),'" + //
			    annoPassato + "', '" + nuovoAnno + "'))" + //
			    " where idcomune=?  and " + //  
			    " progressivoistanze is not null and instr(progressivoistanze,'/" + annoPassato + "')>0 "; //
	    SQLQuery q = getSession().createSQLQuery(update);
	    log.warn("AZZERAMENTO CONTATORI CONFIGURAZIONE ISTANZE: query={}, idcomune={}", update, ORMHelper.getIdcomune());
	    q.addScalar("idcomune", Hibernate.STRING);
	    q.setString(0, ORMHelper.getIdcomune()); // IDCOMUNE
	    int i = q.executeUpdate();
	    log.warn("AZZERAMENTO CONTATORI CONFIGURAZIONE ISTANZE: {} record aggiornati ", i);
	    update = "update configurazione set progressivo_registri_aut=concat('1/'" +
		     ", REPLACE(SUBSTR(progressivo_registri_aut, INSTR(progressivo_registri_aut,'/') +1 ),'" + //
		     annoPassato + "', '" + nuovoAnno + "'))" + //
		     " where idcomune=?  and " + " progressivo_registri_aut is not null and instr(progressivo_registri_aut,'/" + annoPassato +
		     "')>0 ";
	    q = getSession().createSQLQuery(update);
	    log.warn("AZZERAMENTO CONTATORI CONFIGURAZIONE REGISTRI: query={}, idcomune={}", update, ORMHelper.getIdcomune());
	    q.addScalar("idcomune", Hibernate.STRING);
	    q.setString(0, ORMHelper.getIdcomune()); // IDCOMUNE
	    i = q.executeUpdate();
	    log.warn("AZZERAMENTO CONTATORI CONFIGURAZIONE REGISTRI: {} record aggiornati ", i);
	    update = "update alberoproc set progressivoistanze=concat('1/'" + //
		     ", REPLACE(SUBSTR(progressivoistanze, INSTR(progressivoistanze,'/') +1 ),'" + //
		     annoPassato + "', '" + nuovoAnno + "'))" + //
		     " where idcomune=?  and progressivoistanze is not null and instr(progressivoistanze,'/" + annoPassato + "')>0";
	    q = getSession().createSQLQuery(update);	    
	    log.warn("AZZERAMENTO CONTATORI ALBEROPROC: query={}, idcomune={}", update, ORMHelper.getIdcomune());
	    q.addScalar("idcomune", Hibernate.STRING);
	    q.setString(0, ORMHelper.getIdcomune()); // IDCOMUNE
	    i = q.executeUpdate();
	    log.warn("AZZERAMENTO CONTATORI ALBEROPROC: {} record aggiornati ", i);
	    update = "UPDATE TIPOLOGIAREGISTRI SET tr_progressivo=concat('1/'" + //
		     ", REPLACE(SUBSTR(tr_progressivo, INSTR(tr_progressivo,'/') +1 ),'" + //
		     annoPassato + "', '" + nuovoAnno + "'))" + //
		     " WHERE idcomune=?  and tr_progressivo IS NOT NULL AND instr(tr_progressivo,'/" + annoPassato + "')>0";
	    q = getSession().createSQLQuery(update);
	    log.warn("AZZERAMENTO CONTATORI TIPOLOGIAREGISTRI: query={}, idcomune={}", update, ORMHelper.getIdcomune());
	    q.addScalar("idcomune", Hibernate.STRING);
	    q.setString(0, ORMHelper.getIdcomune()); // IDCOMUNE
	    i = q.executeUpdate();
	    log.warn("AZZERAMENTO CONTATORI TIPOLOGIAREGISTRI: {} record aggiornati ", i);
	}
    }

    @Override
    public void updatePrendiInCarico(Integer codiceIstanza, Integer codiceResponsabile) {

	if (codiceIstanza == null) {
	    throw new RuntimeException("updatePrendiInCarico: il parametro cod istanza passato è nullo");
	}
	int i = 0;
	if (codiceResponsabile == null) {
	    String hql = "update Istanze set operatoreInCaricoId = null where id.idcomune = ? and id.codice=?";
	    i = getHibernateTemplate().bulkUpdate(hql, new Object[] { ORMHelper.getIdcomune(), codiceIstanza });
	} else {
	    String hql = "update Istanze set operatoreInCaricoId = ? where id.idcomune = ? and id.codice=?";
	    i = getHibernateTemplate().bulkUpdate(hql, new Object[] { codiceResponsabile, ORMHelper.getIdcomune(), codiceIstanza });
	}
	if (i != 1) {
	    throw new RuntimeException("La query di aggiornamento della presa in carico dell'istanza :[" +
		    codiceIstanza +
		    "] con responsabile [" +
		    codiceResponsabile +
		    "] ha influito su " +
		    i +
		    " record");
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IstanzeListHelper> findIstanzaLocalizzazioneSimile(Integer codiceStradario, String civico, String esponente, String colore,
	    Integer codiceIstanza, Integer firstResult, Integer maxResult) {

	//projection
	StringBuffer campiSelect = new StringBuffer(
		" I.NUMEROISTANZA as numeroistanza, I.CODICEISTANZA as codiceistanza, I.DATA as data, SW.DESCRIZIONE as software,SW.ORDINE,SW.CODICE as codicesoftware, AP.SC_DESCRIZIONE as interventoproc, ");
	campiSelect = campiSelect
		.append(" A.NOMINATIVO as richiedentenominativo, A.NOME as richiedentenome, A.CODICEFISCALE as richiedentecodicefiscale, ")
		.append("A.PARTITAIVA as richiedentepartitaiva, SI.STATO as statoistanza ");
	StringBuffer campiGroupBy = new StringBuffer(
		" I.NUMEROISTANZA, I.CODICEISTANZA , I.DATA , SW.DESCRIZIONE ,SW.ORDINE,SW.CODICE, AP.SC_DESCRIZIONE,A.NOMINATIVO , A.NOME, A.CODICEFISCALE , ");
	campiGroupBy = campiGroupBy.append("A.PARTITAIVA , SI.STATO");
	// join
	StringBuffer join = new StringBuffer("");
	join = join.append(
		" #SCHEMA_NAME#.ISTANZE I LEFT JOIN #SCHEMA_NAME#.STATIISTANZA SI  ON SI.CODICESTATO= I.CHIUSURA AND SI.IDCOMUNE= I.IDCOMUNE AND  SI.SOFTWARE= I.SOFTWARE ")
		.append(" INNER JOIN #SCHEMA_NAME#.VW_ALBEROPROC AP  ON I.CODICEINTERVENTOPROC=AP.SC_ID AND I.IDCOMUNE =AP.IDCOMUNE ")
		.append(" INNER JOIN #SCHEMA_NAME#.ISTANZESTRADARIO ISTR  ON I.CODICEISTANZA=ISTR.CODICEISTANZA  AND I.IDCOMUNE =ISTR.IDCOMUNE ")
		.append(" INNER JOIN #SCHEMA_NAME#.STRADARIO STRD ON ISTR.CODICESTRADARIO=STRD.CODICESTRADARIO  AND ISTR.IDCOMUNE      =STRD.IDCOMUNE ")
		.append(" LEFT OUTER JOIN #SCHEMA_NAME#.COMUNI COM ON STRD.COMUNE_LOCALIZZAZIONE=COM.CODICECOMUNE ")
		.append(" INNER JOIN #SCHEMA_NAME#.ANAGRAFE A ON I.CODICERICHIEDENTE=A.CODICEANAGRAFE AND I.IDCOMUNE        =A.IDCOMUNE ")
		.append(" LEFT OUTER JOIN #SCHEMA_NAME#.FORMEGIURIDICHE FG ON A.FORMAGIURIDICA=FG.CODICEFORMAGIURIDICA AND A.IDCOMUNE     =FG.IDCOMUNE ")
		.append(" LEFT OUTER JOIN #SCHEMA_NAME#.SOFTWARE SW ON I.SOFTWARE=SW.CODICE ");
	//where
	StringBuffer whereCondition = new StringBuffer(" I.IDCOMUNE = ? AND ISTR.CODICESTRADARIO= ? ");
	if (StringUtils.isNotBlank(civico)) {
	    whereCondition = whereCondition.append(" AND ISTR.CIVICO = ?");
	}
	if (StringUtils.isNotBlank(esponente)) {
	    whereCondition = whereCondition.append(" AND ISTR.ESPONENTE = ?");
	}
	if (StringUtils.isNotBlank(colore)) {
	    whereCondition = whereCondition.append(" AND ISTR.COLORE = ?");
	}
	if (codiceIstanza != null) {
	    whereCondition = whereCondition.append(" AND I.CODICEISTANZA <> ? ");
	}
	StringBuffer orderBy = new StringBuffer(" ORDER BY SW.ORDINE ASC, I.NUMEROISTANZA ASC ");
	StringBuffer query = new StringBuffer("");
	query = query.append("SELECT ").append(campiSelect).append(" FROM ").append(join).append(" WHERE ").append(whereCondition)
		.append(" GROUP BY ").append(campiGroupBy).append(orderBy);
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String qu = query.toString();
	String sql = qu.replaceAll("#SCHEMA_NAME#.", schemaName + ".");
	SQLQuery q = getSession().createSQLQuery(sql);
	int position = 0;
	q.setString(position, ORMHelper.getIdcomune());
	position++;
	q.setInteger(position, codiceStradario);
	position++;
	if (StringUtils.isNotBlank(civico)) {
	    q.setString(position, civico);
	    position++;
	}
	if (StringUtils.isNotBlank(esponente)) {
	    q.setString(position, esponente);
	    position++;
	}
	if (StringUtils.isNotBlank(colore)) {
	    q.setString(position, colore);
	    position++;
	}
	if (codiceIstanza != null) {
	    q.setInteger(position, codiceIstanza);
	}
	q.setFirstResult(firstResult);
	q.setMaxResults(maxResult);
	// setScalar
	q.addScalar("numeroistanza", Hibernate.STRING);
	q.addScalar("codiceistanza", Hibernate.BIG_DECIMAL);
	q.addScalar("data", Hibernate.DATE);
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("codicesoftware", Hibernate.STRING);
	q.addScalar("interventoproc", Hibernate.STRING);
	q.addScalar("statoistanza", Hibernate.STRING);
	q.addScalar("richiedentenominativo", Hibernate.STRING);
	q.addScalar("richiedentenome", Hibernate.STRING);
	q.addScalar("richiedentecodicefiscale", Hibernate.STRING);
	q.addScalar("richiedentepartitaiva", Hibernate.STRING);
	// transformer
	q.setResultTransformer(Transformers.aliasToBean(IstanzeListHelper.class));
	return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean findByAttivitaAndBeforeDataValiditaIstanza(Integer codiceAttivita, Date dataValidita) {

	log.debug("countHelperByFilter: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	String sql = "select count(*) as chiave, istanze.azione as valore from " +
		schemaName +
		".istanze  INNER JOIN  " +
		schemaName +
		".statiistanza ON statiistanza.idcomune = istanze.idcomune AND statiistanza.software = istanze.software " +
		"AND statiistanza.codicestato = istanze.chiusura where istanze.idcomune=? and istanze.fk_idi_attivita=? and istanze.datavalidita <= ? and  istanze.azione<>?  and STATIISTANZA.FKCODCOMPORTAMENTO <> ? group by istanze.azione";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("chiave", Hibernate.BIG_DECIMAL);
	q.addScalar("valore", Hibernate.STRING);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceAttivita);
	Calendar t = Calendar.getInstance();
	t.setTime(dataValidita);
	t.set(Calendar.HOUR, 23);
	t.set(Calendar.MINUTE, 59);
	t.set(Calendar.SECOND, 59);
	q.setTimestamp(2, t.getTime());
	q.setString(3, "=");
	q.setInteger(4, -1);
	ChiaveValoreBean<BigDecimal, String> c = new ChiaveValoreBean<BigDecimal, String>();
	q.setResultTransformer(Transformers.aliasToBean(c.getClass()));
	List<ChiaveValoreBean<BigDecimal, String>> rs = q.list();
	int piu = 0;
	int meno = 0;
	for (ChiaveValoreBean<BigDecimal, String> cvb : rs) {
	    if ("+".equals(cvb.getValore())) {
		piu = cvb.getChiave().intValue();
	    }
	    if ("-".equals(cvb.getValore())) {
		meno = cvb.getChiave().intValue();
	    }
	}
	return piu > meno;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findIstanzaPerTracciatoEquitalia(TracciatoEquitaliaFilter tracciatoEquitaliaFilter, Boolean isInviate, Integer firstResult,
	    Integer maxResults) {

	SQLQuery q = getQueryIstanzaPerTracciatoEquitalia(tracciatoEquitaliaFilter, isInviate, TipoQueryHelperEnum.SELECT);
	List<Integer> list = new ArrayList<Integer>();
	if (firstResult != null && maxResults != null) {
	    q.setFirstResult(firstResult);
	    q.setMaxResults(maxResults);
	    list = q.list();
	} else {
	    list = q.list();
	}
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer countPerTracciatoEquitalia(TracciatoEquitaliaFilter tracciatoEquitaliaFilter, Boolean isInviate) {

	SQLQuery q = getQueryIstanzaPerTracciatoEquitalia(tracciatoEquitaliaFilter, isInviate, TipoQueryHelperEnum.COUNT);
	List<Integer> list = new ArrayList<Integer>();
	list = q.list();
	if (list.isEmpty()) {
	    return 0;
	}
	return list.size();
    }

    private SQLQuery getQueryIstanzaPerTracciatoEquitalia(TracciatoEquitaliaFilter tracciatoEquitaliaFilter, Boolean isInviate,
	    TipoQueryHelperEnum tipoQueryHelperEnum) {

	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	Dialect _dialetto = sfi.getDialect();
	String hibernateDialect = _dialetto.toString();
	log.debug("getQueryIstanzaPerTracciatoEquitalia: Il dialetto della SessionFactoryImplementor è {}", hibernateDialect);
	String selectQuery = "";
	switch (tipoQueryHelperEnum) {
	    case COUNT:
		selectQuery = "select count(*) as value ";
		break;
	    case SELECT:
		selectQuery = "select DISTINCT i.codiceistanza as value ";
		break;
	    default:
		break;
	}
	log.debug("getQueryIstanzaPerTracciatoEquitalia# START SEZIONE FROM.....");
	String query = selectQuery;
	query += " from " + SCHEMA_NAME + "istanze i ";
	if (isInviate) {
	    query += " left join " +
		    SCHEMA_NAME +
		    "EQUITALIATRACCIATO_D equitalia1_" +
		    " ON i.CODICEISTANZA=equitalia1_.FK_ISTANZA AND i.idcomune =  equitalia1_.idcomune ";
	}
	//. CONDIZIONE JOIN PER RECUPERARE SOLO QUELLE IN STATO CHIUSE
	query += " LEFT JOIN " +
		SCHEMA_NAME +
		"statiistanza si ON i.idcomune = si.idcomune " +
		" and i.software = si.software " +
		" and i.chiusura = si.codicestato ";
	// CONDIZIONE JOIN PER RICERCA SU CAMPI DINAMICO
	if (StringUtils.isNotBlank(tracciatoEquitaliaFilter.getValoredyn2CampiFiltroAmbito())
		&& StringUtils.isNotBlank(tracciatoEquitaliaFilter.getDyn2CampiFiltroAmbito())) {
	    log.debug("getQueryIstanzaPerTracciatoEquitalia# Condizione filtro per ambito. Campo presente si istanze dyn 2 dati..");
	    query += " LEFT JOIN " +
		    SCHEMA_NAME +
		    "ISTANZEDYN2DATI id2d " +
		    " ON id2d.idcomune    = i.idcomune " +
		    " AND id2d.codiceistanza    = i.codiceistanza " +
		    " LEFT JOIN " +
		    SCHEMA_NAME +
		    "DYN2_CAMPI d2c " +
		    " ON d2c.idcomune    = id2d.idcomune " +
		    " AND d2c.ID    = id2d.fk_d2c_id ";
	}
	String query_join_movimenti = "";
	String query_where_movimenti = "";
	// CONDIZIONE JOIN PER RICERCA SULLA PRESENZA DI SPECIFICI MOVIMENTI FATTI
	if (!tracciatoEquitaliaFilter.getMovimentisFatti().isEmpty()) {
	    List<String> _codMovFatti = tracciatoEquitaliaFilter.getMovimentisFatti();
	    int i = 0;
	    for (String codMov : _codMovFatti) {
		String aliasMov = "mov_" + codMov + "_" + i;
		query_join_movimenti += " LEFT JOIN " + SCHEMA_NAME + "MOVIMENTI " + aliasMov
		//	
			+ " ON i.CODICEISTANZA= " + aliasMov + ".CODICEISTANZA"
			//
			+ " AND i.IDCOMUNE    =" + aliasMov + ".IDCOMUNE ";
		query_where_movimenti += " AND " + aliasMov + ".TIPOMOVIMENTO=? AND " + aliasMov + ".data is not null";
		i++;
	    }
	}
	//
	if (StringUtils.isNotBlank(query_join_movimenti)) {
	    query += query_join_movimenti;
	}
	log.debug("getQueryIstanzaPerTracciatoEquitalia# START CONDIZIONEDI WHERE...");
	String queryWhere = " where i.idcomune=? and i.software=? and si.FKCODCOMPORTAMENTO = ? ";
	//.
	if (!tracciatoEquitaliaFilter.getFlagFiltraSuDataAut()) {
	    queryWhere += " and i.data> ? and i.data < ? ";
	}
	//.
	queryWhere += " and equitalia1_.id IS NULL ";
	if (StringUtils.isNotBlank(query_where_movimenti)) {
	    queryWhere += query_where_movimenti;
	}
	//
	//
	// CONDIZIONE DI NON PRESENZA DI UN MOVIMENTO SPECIFICO
	String queryNotExistMov = "";
	if (StringUtils.isNotBlank(tracciatoEquitaliaFilter.getMovimentoMessoARuolo())) {
	    queryNotExistMov = " AND NOT EXISTS "
	    //
		    + " (select 1 from "
		    //
		    +
		    SCHEMA_NAME +
		    "movimenti m1 " +
		    " where " +
		    " i.CODICEISTANZA= m1.CODICEISTANZA and " +
		    " i.idcomune= m1.idcomune and " +
		    " m1.TIPOMOVIMENTO = ? and m1.data IS NOT NULL " +
		    ") ";
	}
	query += queryWhere + queryNotExistMov;
	String queryNotExistAut = "";
	// CONDIZIONE DI  PRESENZA DI UN AUTORIZZAZIONE CON REGISTRO SPECIFICO
	if (StringUtils.isNotBlank(tracciatoEquitaliaFilter.getCodiceRegAutOrdinanza())) {
	    queryNotExistAut = " AND EXISTS "
	    //
		    + " (select 1 from "
		    //
		    +
		    SCHEMA_NAME +
		    "AUTORIZZAZIONI aut " +
		    " where " +
		    " i.CODICEISTANZA= aut.FKIDISTANZA and " +
		    " i.idcomune= aut.idcomune and " +
		    " AUT.FKIDREGISTRO = ? ";
	    if (tracciatoEquitaliaFilter.getFlagFiltraSuDataAut()) {
		queryNotExistAut += " AND aut.AUTORIZDATA >= ? AND aut.AUTORIZDATA <= ? ";
	    }
	    queryNotExistAut += ") ";
	    query += queryNotExistAut;
	}
	// VALORI PER RICERCA SU CAMPI DINAMICI (AMBITO)
	String queryWhereAmbito = "";
	if (StringUtils.isNotBlank(tracciatoEquitaliaFilter.getValoredyn2CampiFiltroAmbito())
		&& StringUtils.isNotBlank(tracciatoEquitaliaFilter.getDyn2CampiFiltroAmbito())) {
	    if (hibernateDialect.indexOf("Oracle") > 0) {
		queryWhereAmbito += " AND d2c.nomecampo = ? AND dbms_lob.substr(id2d.valore, 50, 1)= ? ";
	    } else {
		queryWhereAmbito += " AND d2c.nomecampo = ? AND id2d.valore = ? ";
	    }
	    query += queryWhereAmbito;
	}
	log.debug("getQueryIstanzaPerTracciatoEquitalia# START SEZIONE SET VALORI PER QUERY");
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String sql = query.replaceAll(SCHEMA_NAME, schemaName + ".");
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("value", Hibernate.INTEGER);
	int pos = 0;
	q.setString(pos, ORMHelper.getIdcomune());
	pos++;
	q.setString(pos, ORMHelper.getSoftware());
	pos++;
	q.setInteger(pos, 0);
	pos++;
	if (!tracciatoEquitaliaFilter.getFlagFiltraSuDataAut()) {
	    Calendar t = Calendar.getInstance();
	    t.setTime(tracciatoEquitaliaFilter.getIstanzaDataDa());
	    t.set(Calendar.HOUR, 0);
	    t.set(Calendar.MINUTE, 0);
	    t.set(Calendar.SECOND, 0);
	    q.setTimestamp(pos, t.getTime());
	    pos++;
	    Calendar t1 = Calendar.getInstance();
	    t1.setTime(tracciatoEquitaliaFilter.getIstanzaDataA());
	    t1.set(Calendar.HOUR, 23);
	    t1.set(Calendar.MINUTE, 59);
	    t1.set(Calendar.SECOND, 59);
	    q.setTimestamp(pos, t1.getTime());
	    pos++;
	}
	if (!tracciatoEquitaliaFilter.getMovimentisFatti().isEmpty()) {
	    List<String> _codMovFatti = tracciatoEquitaliaFilter.getMovimentisFatti();
	    for (String codMov : _codMovFatti) {
		q.setString(pos, codMov);
		pos++;
	    }
	}
	if (StringUtils.isNotBlank(tracciatoEquitaliaFilter.getMovimentoMessoARuolo())) {
	    q.setString(pos, tracciatoEquitaliaFilter.getMovimentoMessoARuolo());
	    pos++;
	}
	if (StringUtils.isNotBlank(tracciatoEquitaliaFilter.getCodiceRegAutOrdinanza())) {
	    q.setString(pos, tracciatoEquitaliaFilter.getCodiceRegAutOrdinanza());
	    pos++;
	}
	if (tracciatoEquitaliaFilter.getFlagFiltraSuDataAut()) {
	    Calendar t = Calendar.getInstance();
	    t.setTime(tracciatoEquitaliaFilter.getIstanzaDataDa());
	    t.set(Calendar.HOUR, 0);
	    t.set(Calendar.MINUTE, 0);
	    t.set(Calendar.SECOND, 0);
	    q.setTimestamp(pos, t.getTime());
	    pos++;
	    Calendar t1 = Calendar.getInstance();
	    t1.setTime(tracciatoEquitaliaFilter.getIstanzaDataA());
	    t1.set(Calendar.HOUR, 23);
	    t1.set(Calendar.MINUTE, 59);
	    t1.set(Calendar.SECOND, 59);
	    q.setTimestamp(pos, t1.getTime());
	    pos++;
	}
	// Condizione di ricerca su campo dinamico  set valori
	if (StringUtils.isNotBlank(tracciatoEquitaliaFilter.getValoredyn2CampiFiltroAmbito())
		&& StringUtils.isNotBlank(tracciatoEquitaliaFilter.getDyn2CampiFiltroAmbito())) {
	    q.setString(pos, tracciatoEquitaliaFilter.getDyn2CampiFiltroAmbito());
	    pos++;
	    q.setString(pos, tracciatoEquitaliaFilter.getValoredyn2CampiFiltroAmbito());
	}
	//	q.setResultTransformer(Transformers.aliasToBean(IstanzePerInterventiHelper.class));
	return q;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findIstanzaProtocollazioneFallitaByTipoProtocollazione(String[] codicetipoprotocollazione) {

	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	StringBuffer query = new StringBuffer("SELECT ISTANZE.CODICEISTANZA AS CODICEISTANZA ");
	query.append(" FROM ").append(SCHEMA_NAME).append("ISTANZE ")
		// WHERE
		.append(" WHERE ")
		// IDCOMUNE // SOFTWARE 
		.append(" ISTANZE.IDCOMUNE = ? AND ").append(" ISTANZE.SOFTWARE = ? ")
		// TIPO_PROT_FALLITA
		.append(" AND ISTANZE.TIPO_PROT_FALLITA is not null ")
		// NUMEROPROTOCOLLO
		.append(" AND ISTANZE.NUMEROPROTOCOLLO is  null ");
	if (codicetipoprotocollazione != null && codicetipoprotocollazione.length > 1) {
	    List<String> puntiInterrogativi = new ArrayList<String>();
	    for (int i = 0; i < codicetipoprotocollazione.length; i++) {
		puntiInterrogativi.add("?");
	    }
	    String _puntiInterrogativi = StringUtils.join(puntiInterrogativi.toArray(), ",");
	    query = query.append(" AND ISTANZE.TIPO_PROT_FALLITA IN (").append(_puntiInterrogativi).append(") ");
	}
	String _query = query.toString();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String sql = _query.replaceAll(SCHEMA_NAME, schemaName + ".");
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("codiceistanza", Hibernate.INTEGER);
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, ORMHelper.getSoftware());
	int i = 2;
	if (codicetipoprotocollazione != null && codicetipoprotocollazione.length > 1) {
	    for (int j = 0; j < codicetipoprotocollazione.length; j++) {
		q.setString(i, codicetipoprotocollazione[j]);
		i++;
	    }
	}
	return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isChiusa(Integer codiceIstanza) {

	String sql = " select " + // 
		" statiistanza.FKCODCOMPORTAMENTO as comportamento " + // 
		" from " + // 
		" istanze " + // 
		" inner join statiistanza  " + // 
		" on " + // 
		" statiistanza.idcomune = istanze.idcomune " + // 
		" and statiistanza.codicestato = istanze.chiusura " + // 
		" and statiistanza.fkcodcomportamento = ? " + // 
		" and statiistanza.SOFTWARE = istanze.software " + // 
		" where " + // 
		" istanze.IDCOMUNE = ? " + // 
		" and istanze.CODICEISTANZA = ? ";
	SQLQuery query = getSession().createSQLQuery(sql);
	query.setInteger(0, 0);
	query.setString(1, ORMHelper.getIdcomune());
	query.setInteger(2, codiceIstanza);
	query.addScalar("comportamento", Hibernate.INTEGER);
	List<Integer> list = query.list();
	return list.isEmpty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeResponsabiliPerIstanzeInCorsoNew(Integer codiceIstanza,
	    Map<Integer, Integer> codResp, String scCodice, Integer idTestata) {

	String qMarks = "";
	for (int i = 0; i < codResp.size(); i++) {
	    qMarks += ",?";
	}
	if (qMarks.length() > 0) {
	    qMarks = qMarks.substring(1);
	}
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String sql = queryCountResponsabiliIstanzaNew.replaceAll(SCHEMA_NAME, schemaName + ".");
	sql = sql.replaceAll("CODICI_RESPONSABILI", qMarks);
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("chiave", Hibernate.INTEGER);
	q.addScalar("valore", Hibernate.INTEGER);
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, ORMHelper.getSoftware());
	if (idTestata == null) {
	    idTestata = 0;
	}
	q.setInteger(2, idTestata);
	int pos = 3;
	for (Map.Entry<Integer, Integer> m : codResp.entrySet()) {
	    q.setInteger(pos, m.getKey());
	    pos++;
	}
	q.setResultTransformer(Transformers.aliasToBean(ChiaveValoreBean.class));
	return q.list();
    }

    private String queryCountResponsabiliIstanzaNew = "select i.codiceresponsabileproc as chiave, " + // 
	    "count(*) as valore " + // 
	    "from " +
	    SCHEMA_NAME +
	    "istanze i " + // 
	    "inner join " +
	    SCHEMA_NAME +
	    "assegnazione_gruppi_dettaglio agd on " + // 
	    "agd.IDCOMUNE = i.IDCOMUNE " + // 
	    "and agd.CODICEISTANZA = i.CODICEISTANZA " + // 
	    "inner join " +
	    SCHEMA_NAME +
	    " assegnazione_gruppi_testata agt on " + // 
	    "agt.IDCOMUNE = agd.IDCOMUNE " + // 
	    "and agd.IDTESTATA = agt.id " + // 
	    "inner join " +
	    SCHEMA_NAME +
	    " responsabili r on " + // 
	    "r.IDCOMUNE = i.IDCOMUNE " + // 
	    "and r.CODICERESPONSABILE = i.CODICERESPONSABILEPROC " + // 
	    "where i.IDCOMUNE = ? " + // 
	    "and i.software = ? " + //
	    "and agt.ID = ? " + // 
	    "and i.codiceresponsabileproc in (CODICI_RESPONSABILI) " + //						    
	    "group by i.codiceresponsabileproc " + // 
	    "order by count(*) asc ";

    @SuppressWarnings("unchecked")
    @Override
    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeIstruttoriPerIstanzeInCorsoNew(Integer codiceIstanza, Map<Integer, Integer> codResp,
	    String scCodice, Integer idTestata) {

	String qMarks = "";
	for (int i = 0; i < codResp.size(); i++) {
	    qMarks += ",?";
	}
	if (qMarks.length() > 0) {
	    qMarks = qMarks.substring(1);
	}
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String sql = queryCountIstruttoriIstanza2.replaceAll(SCHEMA_NAME, schemaName + ".");
	sql = sql.replaceAll("CODICI_RESPONSABILI", qMarks);
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("chiave", Hibernate.INTEGER);
	q.addScalar("valore", Hibernate.INTEGER);
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, ORMHelper.getSoftware());
	if (idTestata == null) {
	    idTestata = 0;
	}
	q.setInteger(2, idTestata);
	int pos = 3;
	for (Map.Entry<Integer, Integer> m : codResp.entrySet()) {
	    q.setInteger(pos, m.getKey());
	    pos++;
	}
	q.setResultTransformer(Transformers.aliasToBean(ChiaveValoreBean.class));
	return q.list();
    }

    private String queryCountIstruttoriIstanza2 = "select i.codiceistruttore as chiave, " + // 
	    "count(*) as valore " + // 
	    "from " +
	    SCHEMA_NAME +
	    "istanze i " + // 
	    "inner join " +
	    SCHEMA_NAME +
	    "assegnazione_gruppi_dettaglio agd on " + // 
	    "agd.IDCOMUNE = i.IDCOMUNE " + // 
	    "and agd.CODICEISTANZA = i.CODICEISTANZA " + // 
	    "inner join " +
	    SCHEMA_NAME +
	    " assegnazione_gruppi_testata agt on " + // 
	    "agt.IDCOMUNE = agd.IDCOMUNE " + // 
	    "and agd.IDTESTATA = agt.id " + // 
	    "inner join " +
	    SCHEMA_NAME +
	    " responsabili r on " + // 
	    "r.IDCOMUNE = i.IDCOMUNE " + // 
	    "and r.CODICERESPONSABILE = i.CODICEISTRUTTORE " + // 
	    "where i.IDCOMUNE = ? " + // 
	    "and i.software = ? " + //
	    "and agt.ID = ? " + // 
	    "and i.codiceistruttore in (CODICI_RESPONSABILI) " + //						    
	    "group by i.codiceistruttore " + // 
	    "order by count(*) asc ";
}
