package it.gruppoinit.pal.gp.core.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.IstanzeoneriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Raggruppamentocausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;
import it.gruppoinit.pal.gp.core.features.oneri.CalcolaInteressiDiMoraBean;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeOneriNodoPagamentiHelper;
import it.gruppoinit.pal.gp.core.features.oneri.QueryOneriSistemaPagamentiHelper;
import it.gruppoinit.pal.gp.core.features.rateizzazioni.IstanzeoneriDerateizzatoBean;
import it.gruppoinit.pal.gp.core.features.scadenzario.ScadenzarioOneri;
import it.gruppoinit.pal.gp.core.features.scadenzario.ScadenzarioOneriQueryHelper;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.TipologiaOnere;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.regulus.gestoreincassi.RegulusConstants;

@Repository
public class IstanzeoneriDAOImpl extends BaseDAOImpl<Istanzeoneri, PkId> implements IstanzeoneriDAO {

    private static final Logger log = LoggerFactory.getLogger(IstanzeoneriDAOImpl.class);
    private AmministrazioniService amministrazioniService;

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Override
    public Class<Istanzeoneri> getEntityClass() {

	return Istanzeoneri.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Istanzeoneri> getDebtSituationIstanzeOneri(String codiceFiscale, Date DATAINIZIO, Date DATAFINE, String annoDocumento,
	    String codiceTributo) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("istanza", "_istanza");
	criteria.createAlias("_istanza.richiedente", "_richiedente", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_istanza.titolarelegale", "_titolarelegale", DetachedCriteria.LEFT_JOIN);
	if (codiceFiscale.length() == RegulusConstants.LUNGHEZZA_CODICEFISCALE) {
	    criteria.add(Restrictions.or(Restrictions.ilike("_richiedente.codicefiscale", codiceFiscale),
		    Restrictions.ilike("_titolarelegale.codicefiscale", codiceFiscale)));
	} else if (codiceFiscale.length() == RegulusConstants.LUNGHEZZA_PARTITAIVA) {
	    criteria.add(Restrictions.or(Restrictions.ilike("_richiedente.partitaiva", codiceFiscale),
		    Restrictions.ilike("_titolarelegale.partitaiva", codiceFiscale)));
	}
	if (DATAINIZIO != null && DATAFINE == null) {
	    criteria.add(Restrictions.ge("datascadenza", DATAINIZIO));
	}
	if (DATAFINE != null && DATAINIZIO == null) {
	    criteria.add(Restrictions.le("datascadenza", DATAFINE));
	}
	if (DATAINIZIO != null && DATAFINE != null) {
	    criteria.add(Restrictions.between("datascadenza", DATAINIZIO, DATAFINE));
	}
	criteria.addOrder(Order.asc("nrDocumento"));
	criteria.add(Restrictions.isNotNull("nrDocumento"));
	criteria.add(Restrictions.isNull("datapagamento"));
	if (codiceTributo != null && !codiceTributo.equals("")) {
	    Integer codiceTributoParam = 0;
	    try {
		codiceTributoParam = Integer.parseInt(codiceTributo);
		criteria.add(Restrictions.eq("tipicausalioneri.id.codice", codiceTributoParam));
	    } catch (NumberFormatException e2) {
		if (log.isDebugEnabled()) {
		    log.error("ERRORE WEB SERVICE SISTEMA PAGAMENTI REGULUS. getDebtSituation (695): " + e2.getMessage());
		}
	    }
	}
	if (annoDocumento != null && !annoDocumento.equals("")) {
	    List<Istanzeoneri> listTemp = getHibernateTemplate().findByCriteria(criteria);
	    List<Istanzeoneri> list = new ArrayList<Istanzeoneri>();
	    for (Istanzeoneri istanzeOneri : listTemp) {
		if (istanzeOneri.getAnno().equals(annoDocumento)) {
		    list.add(istanzeOneri);
		}
	    }
	    return list;
	}
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer getNumeroRateInScadenza(Tipicausalioneri tipicausalioneri) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.count("numerorata"));
	// projectionList.add(Projections.groupProperty("tipicausalioneri.id.codice"));
	criteria.setProjection(projectionList);
	criteria.add(Restrictions.eq("tipicausalioneri.id.codice", tipicausalioneri.getId().getCodice()));
	criteria.add(Restrictions.isNull("datapagamento"));
	List<Integer> list = getHibernateTemplate().findByCriteria(criteria);
	Integer nrRateScadenza = 0;
	if (!list.isEmpty()) {
	    nrRateScadenza = list.get(0);
	}
	return nrRateScadenza;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Istanzeoneri> getBillDetailsIstanzeOneri(String nrDocumento, String codicefiscale, String annoDocumento, String codiceTributo) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("istanza", "_istanza");
	criteria.createAlias("_istanza.richiedente", "_richiedente", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_istanza.titolarelegale", "_titolarelegale", DetachedCriteria.LEFT_JOIN);
	if (codicefiscale.length() == RegulusConstants.LUNGHEZZA_CODICEFISCALE) {
	    criteria.add(Restrictions.or(Restrictions.ilike("_richiedente.codicefiscale", codicefiscale),
		    Restrictions.ilike("_titolarelegale.codicefiscale", codicefiscale)));
	} else if (codicefiscale.length() == RegulusConstants.LUNGHEZZA_PARTITAIVA) {
	    criteria.add(Restrictions.or(Restrictions.ilike("_richiedente.partitaiva", codicefiscale),
		    Restrictions.ilike("_titolarelegale.partitaiva", codicefiscale)));
	}
	criteria.add(Restrictions.eq("nrDocumento", nrDocumento));
	criteria.addOrder(Order.asc("numerorata"));
	if (!codiceTributo.equals("")) {
	    criteria.add(Restrictions.eq("tipicausalioneri.id.codice", Integer.parseInt(codiceTributo)));
	}
	if (!annoDocumento.equals("")) {
	    List<Istanzeoneri> listTemp = getHibernateTemplate().findByCriteria(criteria);
	    List<Istanzeoneri> list = new ArrayList<Istanzeoneri>();
	    for (Istanzeoneri istanzeOneri : listTemp) {
		if (istanzeOneri.getAnno().equals(annoDocumento)) {
		    list.add(istanzeOneri);
		}
	    }
	    return list;
	}
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Istanze getIstanzeByNrDocumento(String nrDocumento) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("istanza", "_istanza");
	criteria.add(Restrictions.eq("nrDocumento", nrDocumento));
	List<Istanzeoneri> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list.get(0).getIstanza();
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Istanzeoneri getOneriByNrDocRata(String nrDocumento, Short nrRata) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("nrDocumento", nrDocumento));
	criteria.add(Restrictions.eq("numerorata", nrRata));
	criteria.add(Restrictions.isNull("datapagamento"));
	List<Istanzeoneri> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Istanzeoneri> getOnereBollo(Integer codiceCausaleBollo, Istanze istanza) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("istanza", istanza));
	criteria.add(Restrictions.eq("tipicausalioneri.id.codice", codiceCausaleBollo));
	criteria.add(Restrictions.isNull("datapagamento"));
	List<Istanzeoneri> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Istanzeoneri getOnereBolloByOnere(Integer codiceCausale, Istanze istanza) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("istanza", istanza));
	criteria.createAlias("tipicausalioneri", "_tipicausalioneri");
	criteria.add(Restrictions.eq("_tipicausalioneri.fkcausalebollo", codiceCausale));
	criteria.add(Restrictions.isNull("datapagamento"));
	List<Istanzeoneri> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public BigDecimal sumOneriCausaliByIstanzaAndRaggruppamento(Istanze istanza, Raggruppamentocausalioneri raggruppamentocausalioneri,
	    TipologiaOnere tipologiaOnere, Boolean isEntrata) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("istanza", istanza));
	criteria.createAlias("tipicausalioneri", "_tipicausalioneri");
	if (raggruppamentocausalioneri != null) {
	    criteria.add(Restrictions.eq("_tipicausalioneri.raggruppamentocausalioneri", raggruppamentocausalioneri));
	} else {// gestisce il caso in cui la causale onere non ha un raggruppamento configurato (dato non obbligatorio)
	    criteria.add(Restrictions.isNull("_tipicausalioneri.raggruppamentocausalioneri.id.codice"));
	}
	ProjectionList projectionListPrecedenti = Projections.projectionList();
	if (isEntrata != null && isEntrata.equals(new Boolean(true))) {
	    criteria.add(Restrictions.eq("flentratauscita", true));
	}
	if (isEntrata != null && isEntrata.equals(new Boolean(false))) {
	    criteria.add(Restrictions.eq("flentratauscita", false));
	}
	switch (tipologiaOnere) {
	case CAUSALE_ONERE:
	    projectionListPrecedenti.add(Projections.sum("prezzo"));
	    break;
	case ISTRUTTORIA_ONERE:
	    projectionListPrecedenti.add(Projections.sum("prezzoistruttoria"));
	    break;
	default:
	    throw new RuntimeException("Deve essere scelta una tipologia di onere");
	}
	criteria.setProjection(projectionListPrecedenti);
	List<BigDecimal> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty() && list.get(0) != null) {
	    return list.get(0);
	}
	return new BigDecimal(0);
    }

    @Override
    public BigDecimal sumRibassiOneriByIstanzaAndRaggruppamento(Istanze istanza, Raggruppamentocausalioneri raggruppamentocausalioneri) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("istanza", istanza));
	criteria.createAlias("tipicausalioneri", "_tipicausalioneri");
	if (raggruppamentocausalioneri != null) {
	    criteria.add(Restrictions.eq("_tipicausalioneri.raggruppamentocausalioneri", raggruppamentocausalioneri));
	} else {// gestisce il caso in cui la causale onere non ha un raggruppamento configurato (dato non obbligatorio)
	    criteria.add(Restrictions.isNull("_tipicausalioneri.raggruppamentocausalioneri.id.codice"));
	}
	criteria.add(Restrictions.eq("flentratauscita", false));
	criteria.add(Restrictions.eq("flribasso", true));
	List<Istanzeoneri> list = getHibernateTemplate().findByCriteria(criteria);
	// Calcolo la somma dei ribassi
	BigDecimal totale = new BigDecimal(0);
	BigDecimal subTotale = null;
	for (Istanzeoneri istanzeoneri : list) {
	    subTotale = new BigDecimal(0);
	    subTotale = istanzeoneri.getPrezzo().divide(new BigDecimal(100)).multiply(new BigDecimal(istanzeoneri.getPercribasso()));
	    totale = totale.add(subTotale);
	}
	return totale;
    }

    @Override
    public BigDecimal sumOneriCausaliByIstanza(Istanze istanza, TipologiaOnere tipologiaOnere, Boolean isEntrata) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("istanza", istanza));
	ProjectionList projectionListPrecedenti = Projections.projectionList();
	if (isEntrata != null && isEntrata.equals(new Boolean(true))) {
	    criteria.add(Restrictions.eq("flentratauscita", true));
	}
	if (isEntrata != null && isEntrata.equals(new Boolean(false))) {
	    criteria.add(Restrictions.eq("flentratauscita", false));
	}
	switch (tipologiaOnere) {
	case CAUSALE_ONERE:
	    projectionListPrecedenti.add(Projections.sum("prezzo"));
	    break;
	case ISTRUTTORIA_ONERE:
	    projectionListPrecedenti.add(Projections.sum("prezzoistruttoria"));
	    break;
	default:
	    throw new RuntimeException("Deve essere scelta una tipologia di onere");
	}
	criteria.setProjection(projectionListPrecedenti);
	List<BigDecimal> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty() && list.get(0) != null) {
	    return list.get(0);
	}
	return new BigDecimal(0);
    }

    @Override
    public BigDecimal sumRibassiOneriByIstanza(Istanze istanza) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("istanza", istanza));
	criteria.add(Restrictions.eq("flentratauscita", false));
	criteria.add(Restrictions.eq("flribasso", true));
	List<Istanzeoneri> list = getHibernateTemplate().findByCriteria(criteria);
	// Calcolo la somma dei ribassi
	BigDecimal totale = new BigDecimal(0);
	BigDecimal subTotale = null;
	for (Istanzeoneri istanzeoneri : list) {
	    subTotale = new BigDecimal(0);
	    subTotale = istanzeoneri.getPrezzo().divide(new BigDecimal(100)).multiply(new BigDecimal(istanzeoneri.getPercribasso()));
	    totale = totale.add(subTotale);
	}
	return totale;
    }

    @Override
    public BigDecimal sumOneriCausaliByIstanzaAndRaggruppamento(Istanze istanza, Raggruppamentocausalioneri raggruppamentocausalioneri,
	    Date datapagamento, TipologiaOnere tipologiaOnere) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("istanza", istanza));
	criteria.createAlias("tipicausalioneri", "_tipicausalioneri");
	if (raggruppamentocausalioneri != null) {
	    criteria.add(Restrictions.eq("_tipicausalioneri.raggruppamentocausalioneri", raggruppamentocausalioneri));
	} else {// gestisce il caso in cui la causale onere non ha un raggruppamento configurato (dato non obbligatorio)
	    criteria.add(Restrictions.isNull("_tipicausalioneri.raggruppamentocausalioneri.id.codice"));
	}
	if (datapagamento != null) {
	    criteria.add(Restrictions.eq("datapagamento", datapagamento));
	} else {
	    criteria.add(Restrictions.isNull("datapagamento"));
	}
	ProjectionList projectionListPrecedenti = Projections.projectionList();
	switch (tipologiaOnere) {
	case CAUSALE_ONERE:
	    projectionListPrecedenti.add(Projections.sum("prezzo"));
	    break;
	case ISTRUTTORIA_ONERE:
	    projectionListPrecedenti.add(Projections.sum("prezzoistruttoria"));
	    break;
	default:
	    throw new RuntimeException("Deve essere scelta una tipologia di onere");
	}
	criteria.setProjection(projectionListPrecedenti);
	List<BigDecimal> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty() && list.get(0) != null) {
	    return list.get(0);
	}
	return new BigDecimal(0);
    }

    @Override
    public List<Amministrazioni> findAmministrazioniInIstanzeOneri(Istanze istanza) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("istanza", istanza));
	criteria.add(Restrictions.isNotNull("amministrazioniId"));
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.distinct(Projections.property("amministrazioniId")));
	criteria.setProjection(projectionList);
	List<Integer> codiciamministrazionis = getHibernateTemplate().findByCriteria(criteria);
	List<Amministrazioni> risultato = new ArrayList<Amministrazioni>();
	for (Integer codice : codiciamministrazionis) {
	    Amministrazioni amministrazioni = amministrazioniService.findById(new PkId(codice));
	    risultato.add(amministrazioni);
	}
	return risultato;
    }

    @Override
    public BigDecimal sumOneriCausaliByIstanzaAndAmministrazione(Istanze istanza, Amministrazioni amministrazioni, TipologiaOnere tipologiaOnere,
	    Boolean isEntrata) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("istanza", istanza));
	criteria.createAlias("amministrazioni", "_amministrazioni");
	criteria.add(Restrictions.eq("_amministrazioni.id.codice", amministrazioni.getId().getCodice()));
	ProjectionList projectionListPrecedenti = Projections.projectionList();
	if (isEntrata != null && isEntrata.equals(true)) {
	    criteria.add(Restrictions.eq("flentratauscita", true));
	}
	if (isEntrata != null && isEntrata.equals(false)) {
	    criteria.add(Restrictions.eq("flentratauscita", false));
	}
	switch (tipologiaOnere) {
	case CAUSALE_ONERE:
	    projectionListPrecedenti.add(Projections.sum("prezzo"));
	    break;
	case ISTRUTTORIA_ONERE:
	    projectionListPrecedenti.add(Projections.sum("prezzoistruttoria"));
	    break;
	default:
	    throw new RuntimeException("Deve essere scelta una tipologia di onere");
	}
	criteria.setProjection(projectionListPrecedenti);
	List<BigDecimal> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty() && list.get(0) != null) {
	    return list.get(0);
	}
	return new BigDecimal(0);
    }

    @Override
    public BigDecimal sumRibassiOneriByIstanzaAndAmministazioni(Istanze istanza, Amministrazioni amministrazioni) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("istanza", istanza));
	//criteria.createAlias("amministrazioni", "_amministrazioni");
	//	if (amministrazioni != null) {
	criteria.add(Restrictions.eq("amministrazioni", amministrazioni));
	//	} else {// gestisce il caso in cui la causale onere non ha un raggruppamento configurato (dato non obbligatorio)
	//	    criteria.add(Restrictions.isNull("_tipicausalioneri.raggruppamentocausalioneri.id.codice"));
	//	}
	criteria.add(Restrictions.eq("flentratauscita", false));
	criteria.add(Restrictions.eq("flribasso", true));
	List<Istanzeoneri> list = getHibernateTemplate().findByCriteria(criteria);
	// Calcolo la somma dei ribassi
	BigDecimal totale = new BigDecimal(0);
	BigDecimal subTotale = null;
	for (Istanzeoneri istanzeoneri : list) {
	    subTotale = new BigDecimal(0);
	    subTotale = istanzeoneri.getPrezzo().divide(new BigDecimal(100)).multiply(new BigDecimal(istanzeoneri.getPercribasso()));
	    totale = totale.add(subTotale);
	}
	return totale;
    }

    @Override
    public BigDecimal sumOneriImportoVersatoByIstanzaAndRaggruppamento(Istanze istanza, Raggruppamentocausalioneri raggruppamentocausalioneri,
	    Boolean isEntrata) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("istanza", istanza));
	criteria.createAlias("tipicausalioneri", "_tipicausalioneri");
	if (raggruppamentocausalioneri != null) {
	    criteria.add(Restrictions.eq("_tipicausalioneri.raggruppamentocausalioneri", raggruppamentocausalioneri));
	} else {// gestisce il caso in cui la causale onere non ha un raggruppamento configurato (dato non obbligatorio)
	    criteria.add(Restrictions.isNull("_tipicausalioneri.raggruppamentocausalioneri.id.codice"));
	}
	ProjectionList projectionListPrecedenti = Projections.projectionList();
	if (isEntrata != null && isEntrata.equals(new Boolean(true))) {
	    criteria.add(Restrictions.eq("flentratauscita", true));
	}
	if (isEntrata != null && isEntrata.equals(new Boolean(false))) {
	    criteria.add(Restrictions.eq("flentratauscita", false));
	}
	projectionListPrecedenti.add(Projections.sum("importopagato"));
	criteria.setProjection(projectionListPrecedenti);
	List<BigDecimal> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty() && list.get(0) != null) {
	    return list.get(0);
	}
	return new BigDecimal(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public BigDecimal sumOneriImportoVersatoByIstanza(Istanze istanza, Boolean isEntrata) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("istanza", istanza));
	ProjectionList projectionListPrecedenti = Projections.projectionList();
	if (isEntrata != null && isEntrata.equals(new Boolean(true))) {
	    criteria.add(Restrictions.eq("flentratauscita", true));
	}
	if (isEntrata != null && isEntrata.equals(new Boolean(false))) {
	    criteria.add(Restrictions.eq("flentratauscita", false));
	}
	projectionListPrecedenti.add(Projections.sum("importopagato"));
	criteria.setProjection(projectionListPrecedenti);
	List<BigDecimal> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty() && list.get(0) != null) {
	    return list.get(0);
	}
	return new BigDecimal(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IstanzeOneriNodoPagamentiHelper> findByIdPosizioneDebitoria(Integer idPosizioneDebitoria, String cfEnteCreditore) {

	log.debug("IstanzeoneriDAO.findIdIstanzeOneriByIdPosizioneDebitoria: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryOneriSistemaPagamentiHelper queryHelper = new QueryOneriSistemaPagamentiHelper(sessimpl, idPosizioneDebitoria, cfEnteCreditore);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(IstanzeOneriNodoPagamentiHelper.class));
	List<IstanzeOneriNodoPagamentiHelper> daBonificare = (List<IstanzeOneriNodoPagamentiHelper>) q.list();
	List<IstanzeOneriNodoPagamentiHelper> ret = new ArrayList<IstanzeOneriNodoPagamentiHelper>();
	for (IstanzeOneriNodoPagamentiHelper ioh : daBonificare) {
	    if (ioh.getIdIstanzeOneri() != null) {
		ret.add(ioh);
	    }
	}
	return ret;
    }

    @Override
    public void impostaOnerePagatoConImportoByIdPosizioneDebitoria(Integer idIstanzeOneri, Date dataPagamento, BigDecimal importoPagato,
	    Tipimodalitapagamento modalitaPagamento, String riferimentoPagamento) {

	log.debug(
		"impostaOnerePagatoConImportoByIdPosizioneDebitoria: idIstanzeOneri {}, dataPagamento {}, importoPagato {}, riferimentoPagamento {}",
		new Object[] { idIstanzeOneri, dataPagamento, importoPagato, riferimentoPagamento });
	Istanzeoneri entity = this.findById(new PkId(idIstanzeOneri));
	entity.setDatapagamento(dataPagamento);
	entity.setImportopagato(importoPagato);
	entity.setTipimodalitapagamento(modalitaPagamento);
	entity.setDocriferimento(riferimentoPagamento);
	this.update(entity);
    }

    @Override
    public void impostaOnerePagatoByIdPosizioneDebitoria(Integer idIstanzeOneri, Date dataPagamento, Tipimodalitapagamento modalitaPagamento,
	    String riferimentoPagamento) {

	Istanzeoneri entity = this.findById(new PkId(idIstanzeOneri));
	this.impostaOnerePagatoConImportoByIdPosizioneDebitoria(idIstanzeOneri, dataPagamento, entity.getPrezzo(), modalitaPagamento,
		riferimentoPagamento);
    }

    @Override
    public List<IstanzeoneriDerateizzatoBean> findOneriDaDerateizzare(Integer codiceIstanza, Integer codiceCausaleOneri) {

	log.debug("IstanzeoneriDAO.IstanzeoneriDerateizzatoBean: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	String sql = getSqlOneriDaRateizzare();
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("fkcanonetestata", Hibernate.INTEGER);
	q.addScalar("data", Hibernate.DATE);
	q.addScalar("datascadenza", Hibernate.DATE);
	q.addScalar("prezzo", Hibernate.BIG_DECIMAL);
	q.addScalar("prezzoistruttoria", Hibernate.BIG_DECIMAL);
	q.addScalar("interesse", Hibernate.BIG_DECIMAL);
	q.setResultTransformer(Transformers.aliasToBean(IstanzeoneriDerateizzatoBean.class));
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceIstanza);
	q.setInteger(2, codiceCausaleOneri);
	return (List<IstanzeoneriDerateizzatoBean>) q.list();
    }

    private String getSqlOneriDaRateizzare() {

	//	    private BigDecimal prezzo;
	//	    private BigDecimal interesse;
	//	    private BigDecimal prezzoistruttoria;
	//	    private Date datascadenza;
	//	    private Date data;
	//	    private Integer fkcanonetestata;
	return "select " + //
	       " sum(istanzeoneri.prezzo) as prezzo, " + //
	       " sum(istanzeoneri.importo_interesse) as interesse," + //
	       " sum(istanzeoneri.prezzoistruttoria) as prezzoistruttoria," + //
	       " min(istanzeoneri.datascadenza) as datascadenza, " + //
	       " min(istanzeoneri.data) as data, " + //
	       " istanzecalcolocanoni_o.FK_IDTESTATA as fkcanonetestata " + //
	       " from istanzeoneri " + //
	       " left join" + //
	       " istanzecalcolocanoni_o on istanzeoneri.idcomune = istanzecalcolocanoni_o.idcomune and istanzeoneri.id = istanzecalcolocanoni_o.fk_idistoneri" + //
	       " where " + //
	       " istanzeoneri.idcomune=? and " + //
	       " istanzeoneri.codiceistanza=? and " + //
	       " istanzeoneri.fkidtipocausale=? and" + //
	       " istanzeoneri.numerorata is not null and " + //
	       " istanzeoneri.datapagamento is null and " + //
	       " not exists (select 1 from istoneri_dett_posizioni where istoneri_dett_posizioni.idcomune = istanzeoneri.idcomune and istoneri_dett_posizioni.fk_istanzeoneri_id = istanzeoneri.id )" + // Non posso derateizzare rate con posizioni debitorie collegate
	       " group by istanzecalcolocanoni_o.fk_idtestata";
    }

    @Override
    public List<Integer> findOneriDerateizzatiDaEliminare(Integer codiceIstanza, Integer codiceCausaleOneri, Integer fkcanonetestata) {

	StringBuilder sb = new StringBuilder();
	sb.append("select istanzeoneri.id as id from istanzeoneri");
	sb.append(" LEFT JOIN");
	sb.append(" istanzecalcolocanoni_o on istanzeoneri.idcomune = istanzecalcolocanoni_o.idcomune AND");
	sb.append(" istanzeoneri.id = istanzecalcolocanoni_o.fk_idistoneri");
	sb.append(" WHERE");
	sb.append(" istanzeoneri.idcomune=? AND");
	sb.append(" istanzeoneri.CODICEISTANZA=? AND");
	sb.append(" istanzeoneri.fkidtipocausale=? AND");
	sb.append(" istanzeoneri.numerorata is not null AND");
	sb.append(" istanzeoneri.datapagamento is null AND");
	sb.append(
		"  not exists (select 1 from istoneri_dett_posizioni where istoneri_dett_posizioni.idcomune = istanzeoneri.idcomune and istoneri_dett_posizioni.fk_istanzeoneri_id = istanzeoneri.id ) "); // Non posso derateizzare rate con posizioni debitorie collegate
	if (fkcanonetestata != null) {
	    sb.append(" AND istanzecalcolocanoni_o.FK_IDTESTATA=?");
	} else {
	    sb.append(" AND istanzecalcolocanoni_o.FK_IDTESTATA is null");
	}
	String sql = sb.toString();
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("id", Hibernate.INTEGER);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceIstanza);
	q.setInteger(2, codiceCausaleOneri);
	if (fkcanonetestata != null) {
	    q.setInteger(3, fkcanonetestata);
	}
	return (List<Integer>) q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findOneriConMappaturaNPById(Set<Integer> idIstanzeOneri) {

	if (idIstanzeOneri == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo findById(Set<Integer> idIstanzeOneri) senza passare il parametro idIstanzeOneri");
	}
	if (idIstanzeOneri.isEmpty()) {
	    return new ArrayList<Integer>();
	}
	String qm = StringUtils.repeat("?,", idIstanzeOneri.size());
	qm = qm.substring(0, qm.length() - 1);
	String sb = "select io.ID as codiceIstOn";//
	sb += " from istanzeoneri io";//
	sb += " inner join tipicausalioneri t on io.IDCOMUNE = t.IDCOMUNE ";//
	sb += " and io.fkidtipocausale =t.CO_ID ";//
	sb += " inner join tipicausalioneridettaglio t2 on t.IDCOMUNE = t2.IDCOMUNE";//
	sb += " and t.CO_ID = t2.FKCAUSALE";//
	sb += " inner join conti c on t2.IDCOMUNE = c.IDCOMUNE";//
	sb += " and t2.FKCONTO = c.ID";//
	sb += " where";//
	sb += " io.IDCOMUNE = ?";//
	sb += " and c.mappaturanodopag is not null";//
	sb += " and io.ID in (" + qm + ")";//
	sb += " order by t.CO_ORDINAMENTO, t.CO_DESCRIZIONE";
	SQLQuery q = getSession().createSQLQuery(sb);
	q.addScalar("codiceIstOn", Hibernate.INTEGER);
	int pos = 0;
	q.setString(pos++, ORMHelper.getIdcomune());
	for (Integer co : idIstanzeOneri) {
	    q.setInteger(pos++, co);
	}
	return q.list();
    }

    @Override
    public Istanzeoneri findOnereByIdDettaglioPosizioneDebitoria(Integer idDettaglioPosizioneDebitoria) {

	String hql = "select io from Istanzeoneri io inner join io.istoneriDettPosizioni dp where dp.dettPosizioneDebitoria.id.idcomune=? and dp.dettPosizioneDebitoria.id.codice=?";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idDettaglioPosizioneDebitoria);
	List<Istanzeoneri> oneri = q.list();
	if (oneri.isEmpty()) {
	    return null;
	}
	Istanzeoneri io = oneri.get(0);
	return this.findById(new PkId(io.getId().getCodice()));
    }

    @Override
    public List<ScadenzarioOneri> findTabellaScadenzario(Date dataOdierna, String[] codiciComune) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	ScadenzarioOneriQueryHelper queryHelper = new ScadenzarioOneriQueryHelper(sessimpl, dataOdierna, codiciComune);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(ScadenzarioOneri.class));
	return q.list();
    }

    @Override
    public List<Integer> findCausaliPerMappatureConti(Integer codiceIstanza, Set<String> listaMappaturePerVersamento) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo findById(Set<Integer> idIstanzeOneri) senza passare il parametro idIstanzeOneri");
	}
	if (listaMappaturePerVersamento.isEmpty()) {
	    return new ArrayList<Integer>();
	}
	String qm = StringUtils.repeat("?,", listaMappaturePerVersamento.size());
	qm = qm.substring(0, qm.length() - 1);
	String sql = "SELECT " + //
		     "  tipicausalioneridettaglio.fkcausale as codicecausale " + //
		     " FROM " + //
		     "  istanzeoneri " + //
		     "  INNER JOIN tipicausalioneri ON tipicausalioneri.idcomune = istanzeoneri.idcomune " + //
		     "  AND tipicausalioneri.co_id = istanzeoneri.fkidtipocausale " + //
		     "  INNER JOIN tipicausalioneridettaglio ON tipicausalioneridettaglio.idcomune = tipicausalioneri.idcomune " + //
		     "  AND tipicausalioneridettaglio.FKCAUSALE = tipicausalioneri.co_id " + //
		     "  INNER JOIN conti ON conti.idcomune = tipicausalioneridettaglio.idcomune " + //
		     "  AND conti.id = tipicausalioneridettaglio.FKCONTO " + //
		     " WHERE " + //
		     "  istanzeoneri.idcomune = ? " + //
		     "  AND codiceistanza = ? " + //
		     "  AND tipicausalioneridettaglio.FLAG_ATTIVO = ? " + //
		     " AND conti.mappaturanodopag IN ( " + qm + " )" + //
		     " GROUP BY " + //
		     "  tipicausalioneridettaglio.fkcausale";
	SQLQuery q = getSession().createSQLQuery(sql.toString());
	q.addScalar("codicecausale", Hibernate.INTEGER);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceIstanza);
	q.setInteger(2, 1);
	int pos = 3;
	for (String m : listaMappaturePerVersamento) {
	    q.setString(pos++, m);
	}
	return q.list();
    }

    @Override
    public Integer findCodiceIstanzaByDettPosDebitoria(Integer codice) {

	String sql = "select " + //
		     "i.CODICEISTANZA as codiceistanza " + //
		     "from " + //
		     "istanzeoneri i " + //
		     "inner join istoneri_dett_posizioni idp on " + //
		     "i.IDCOMUNE = idp.idcomune " + //
		     "and i.id = idp.fk_istanzeoneri_id " + //
		     "inner join dett_posizione_debitoria dpd on " + //
		     "idp.IDCOMUNE = dpd.IDCOMUNE " + //
		     "and idp.fk_dettposdebitoria_id  = dpd.ID " + //
		     "where " + //
		     "dpd.IDCOMUNE = ? " + //
		     "and dpd.ID = ?";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("codiceistanza", Hibernate.INTEGER);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codice);
	List<Integer> list = q.list();
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
    }

    @Override
    public List<CalcolaInteressiDiMoraBean> calcolaInteressiDiMora(Integer idIstanzeOneri) {

	String sql = "select " + //
		     "t.fk_tipicausaliinteressi as idtipicausaliinteressi, " + //
		     "i.datapagamento as datapagamento," + //
		     "i.datascadenza as datascadenza," + //
		     "i.prezzo as prezzo," + //
		     "i.codiceistanza as codiceistanza," + //
		     "figlia.id as idfiglia " + //
		     "from " + //
		     "istanzeoneri i " + //
		     "left join tipicausalioneri t on " + //
		     "i.idcomune = t.idcomune " + //
		     "and i.fkidtipocausale = t.co_id " + //
		     "left join istanzeoneri figlia on " + //
		     "figlia.idcomune = i.idcomune " + // 
		     "and figlia.fk_idpadre = i.id " + //
		     "where " + //
		     "i.idcomune = ? and " + //
		     "i.id = ?";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("idtipicausaliinteressi", Hibernate.INTEGER);
	q.addScalar("datapagamento", Hibernate.DATE);
	q.addScalar("datascadenza", Hibernate.DATE);
	q.addScalar("prezzo", Hibernate.BIG_DECIMAL);
	q.addScalar("codiceistanza", Hibernate.INTEGER);
	q.addScalar("idfiglia", Hibernate.INTEGER);
	q.setResultTransformer(Transformers.aliasToBean(CalcolaInteressiDiMoraBean.class));
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idIstanzeOneri);
	return (List<CalcolaInteressiDiMoraBean>) q.list();
    }

    @Override
    public void deleteByIdPadre(Integer idPadre) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction r = new FilterRestriction();
	r.addFilterField(FilterUtils.equals("istanzeoneriPadre.id.codice", idPadre, int.class));
	ft.addRestriction(r);
	List<Istanzeoneri> righe = super.findByFilterTable(ft);
	for (Istanzeoneri riga : righe) {
	    this.delete(riga);
	}
    }
}
