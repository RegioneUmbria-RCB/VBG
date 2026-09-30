package it.gruppoinit.pal.gp.core.features.autorizzazioni;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.MercatiConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.dao.MercatiDAO;
import it.gruppoinit.pal.gp.core.dao.MercatipresenzeTDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.AutorizzazioneSpuntistaHelper;
import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.DAORestrictionMode;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniDTO;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniExportHelper;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniHelperList;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniFilter;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniRestHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.RuoloAutorizzazioneEnum;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.mercatosrv.MercatoSrvRequest;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.model.AutorizzazioniMercatoSrvBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.query.AutorizzazioniMercatoSrvBeanQueryHelper;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.AutorizzazioniComposteSpostaPresenzeDTO;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.helper.OrdinamentoPresenzeAnalyzer;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.IVerticalizzazioneComportamentiMercatiService;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * 
 * @author fabrizioc
 */
@Repository
public class AutorizzazioniDAOImpl extends BaseDAOImpl<Autorizzazioni, PkId> implements AutorizzazioniDAO {

    private static final String DD_MM_YYYY = "dd/MM/yyyy";
    private static final String FORCE_INDEX = "#FORCE_INDEX#";
    private static final Logger log = LoggerFactory.getLogger(AutorizzazioniDAOImpl.class);
    @Autowired
    private IVerticalizzazioneComportamentiMercatiService vertComportamentiMercatiService;
    @Autowired
    private MercatiDAO mercatiDAO;

    @Override
    public Class<Autorizzazioni> getEntityClass() {

	return Autorizzazioni.class;
    }

    private MercatipresenzeTDAO mercatipresenzeTDAO;

    @Autowired
    public void setMercatipresenzeTDAO(MercatipresenzeTDAO mercatipresenzeTDAO) {

	this.mercatipresenzeTDAO = mercatipresenzeTDAO;
    }

    private MercatiConfigurazioneDAO mercatiConfigurazioneDAO;

    @Autowired
    public void setMercatiConfigurazioneDAO(MercatiConfigurazioneDAO mercatiConfigurazioneDAO) {

	this.mercatiConfigurazioneDAO = mercatiConfigurazioneDAO;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Autorizzazioni> findByIstanzaRegistro(Istanze istanze, Integer codiceregistro) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createCriteria("autorizzazioniConcessionisForFkAutconcAutatt", "aut_conc", DetachedCriteria.LEFT_JOIN);
	det.add(Restrictions.isNull("aut_conc.id.codice"));
	det.add(Restrictions.eq("istanza.id.codice", istanze.getId().getCodice()));
	det.add(Restrictions.eq("tipologiaregistro.id.codice", codiceregistro));
	det.addOrder(Order.desc("autorizdata"));
	List<Autorizzazioni> list = getHibernateTemplate().findByCriteria(det);
	return list;
    }

    @Override
    public List<Autorizzazioni> findByIstanzaRegistro(Integer codIstanze, Integer codiceregistro) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createCriteria("autorizzazioniConcessionisForFkAutconcAutatt", "aut_conc", DetachedCriteria.LEFT_JOIN);
	det.add(Restrictions.isNull("aut_conc.id.codice"));
	det.add(Restrictions.eq("istanza.id.codice", codIstanze));
	det.add(Restrictions.eq("tipologiaregistro.id.codice", codiceregistro));
	det.addOrder(Order.desc("autorizdata"));
	@SuppressWarnings("unchecked")
	List<Autorizzazioni> list = getHibernateTemplate().findByCriteria(det);
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Autorizzazioni> findByIstanzaMovimento(Istanze istanze, Movimenti movimento) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createCriteria("autorizzazioniConcessionisForFkAutconcAutatt", "aut_conc", DetachedCriteria.LEFT_JOIN);
	det.add(Restrictions.isNull("aut_conc.id.codice"));
	det.add(Restrictions.eq("istanza.id.codice", istanze.getId().getCodice()));
	det.add(Restrictions.eq("movimenti", movimento));
	det.addOrder(Order.desc("autorizdata"));
	List<Autorizzazioni> list = getHibernateTemplate().findByCriteria(det);
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Autorizzazioni findAutOConcAttivaByEstremi(String autoriznumero, Date autorizdata, String codicecomune, Integer codiceregistro) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("autoriznumero", autoriznumero));
	det.add(Restrictions.eq("autorizdata", autorizdata));
	det.add(Restrictions.eq("autorizcomune.codicecomune", codicecomune));
	det.add(Restrictions.eq("tipologiaregistro.id.codice", codiceregistro));
	det.add(Restrictions.eq("flagAttiva", true));
	List<Autorizzazioni> list = getHibernateTemplate().findByCriteria(det);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Autorizzazioni> findByAnagrafe(Integer codiceAnagrafe) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createCriteria("autorizzazioniConcessionisForFkAutconcAutatt", "aut_conc", DetachedCriteria.LEFT_JOIN);
	det.add(Restrictions.isNull("aut_conc.id.codice"));
	det.add(Restrictions.eq("anagrafe.id.codice", codiceAnagrafe));
	List<Autorizzazioni> list = getHibernateTemplate().findByCriteria(det);
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Autorizzazioni> findByFilter(AutorizzazioniFilter filter, Integer firstResult, Integer maxResult) {

	DetachedCriteria det = createCriteriaFilter(filter);
	List<Autorizzazioni> list = null;
	if (firstResult != null && maxResult != null) {
	    list = getHibernateTemplate().findByCriteria(det, firstResult, maxResult);
	} else {
	    list = getHibernateTemplate().findByCriteria(det);
	}
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Autorizzazioni findAutOConcByEstremi(String autoriznumero, Date autorizdata, String codicecomune, Integer codiceregistro) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("autoriznumero", autoriznumero));
	det.add(Restrictions.eq("autorizdata", autorizdata));
	det.add(Restrictions.eq("autorizcomune.codicecomune", codicecomune));
	det.add(Restrictions.eq("tipologiaregistro.id.codice", codiceregistro));
	List<Autorizzazioni> list = getHibernateTemplate().findByCriteria(det);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Autorizzazioni> findByIstanza(Istanze istanza) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createCriteria("autorizzazioniConcessionisForFkAutconcAutatt", "aut_conc", DetachedCriteria.LEFT_JOIN);
	det.add(Restrictions.isNull("aut_conc.id.codice"));
	det.add(Restrictions.eq("istanza.id.codice", istanza.getId().getCodice()));
	List<Autorizzazioni> list = getHibernateTemplate().findByCriteria(det);
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Autorizzazioni> findConcessioniByAnagrafe(Integer codiceAnagrafe) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createCriteria("autorizzazioniConcessionisForFkAutconcAutatt", "aut_conc", DetachedCriteria.INNER_JOIN);
	det.add(Restrictions.eq("anagrafe.id.codice", codiceAnagrafe));
	List<Autorizzazioni> list = getHibernateTemplate().findByCriteria(det);
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Autorizzazioni> findByEstremi(String estremi, Integer maxResult) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	List<Autorizzazioni> list = new ArrayList<Autorizzazioni>();
	criteria.createCriteria("autorizcomune", "_autorizcomune", DetachedCriteria.LEFT_JOIN);
	criteria.createCriteria("tipologiaregistro", "_tipologiaregistro", DetachedCriteria.LEFT_JOIN);
	MatchMode[] modes = new MatchMode[] { MatchMode.ANYWHERE, MatchMode.ANYWHERE, MatchMode.ANYWHERE, MatchMode.ANYWHERE };
	String[] values = estremi.split(",");
	if (values != null) {
	    Criterion criterion = null;
	    String[] props = new String[] { "autoriznumero", "autorizdata", "_autorizcomune.comune", "_tipologiaregistro.trDescrizione" };
	    Date autorizdate = null;
	    if (values.length >= 2) {
		SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
		try {
		    autorizdate = sdf.parse(values[1]);
		} catch (ParseException e) {
		    values[1] = "";
		}
	    }
	    if (autorizdate != null) {
		Object[] newValues = new Object[values.length];
		for (int i = 0; i < newValues.length; i++) {
		    if (i == 1) {
			newValues[i] = autorizdate;
		    } else {
			newValues[i] = values[i];
		    }
		}
		criterion = getCriterionForObjects(props, newValues, modes, DAORestrictionMode.AND);
	    } else {
		criterion = getCriterionForObjects(props, values, modes, DAORestrictionMode.AND);
	    }
	    if (criterion != null) {
		criteria.add(criterion);
		if (maxResult != null) {
		    list = getHibernateTemplate().findByCriteria(criteria, 0, maxResult);
		} else {
		    list = getHibernateTemplate().findByCriteria(criteria);
		}
	    }
	}
	return list;
    }

    @Override
    public int countByFilter(AutorizzazioniFilter filter) {

	DetachedCriteria criteria = createCriteriaFilter(filter);
	criteria.setProjection(Projections.rowCount());
	int ris = ((Integer) getHibernateTemplate().findByCriteria(criteria).get(0)).intValue();
	return ris;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Autorizzazioni> findByAutorizzazioniFilter(AutorizzazioniFilter filter, Integer firstResult, Integer maxResult) {

	DetachedCriteria criteria = createCriteriaFilter(filter);
	List<Autorizzazioni> list = new ArrayList<Autorizzazioni>();
	if (null != firstResult && null != maxResult) {
	    list = getHibernateTemplate().findByCriteria(criteria, firstResult, maxResult);
	} else {
	    list = getHibernateTemplate().findByCriteria(criteria);
	}
	return list;
    }

    @Override
    public DetachedCriteria createCriteriaFilter(AutorizzazioniFilter filter) {

	DetachedCriteria det = getIdcomuneCriteria();
	// /////////////////////////////////////////////////// FILTRI PER DATI AUTORIZZAZIONE //////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	if (StringUtils.isNotBlank(filter.getAutoriznumero())) {
	    if (StringUtils.indexOf(filter.getAutoriznumero(), "%") >= 0) {
		det.add(Restrictions.like("autoriznumero", filter.getAutoriznumero()));
	    } else {
		det.add(Restrictions.eq("autoriznumero", filter.getAutoriznumero()));
	    }
	}
	if (filter.getDallaData() != null) {
	    det.add(Restrictions.ge("autorizdata", filter.getDallaData()));
	}
	if (filter.getAllaData() != null) {
	    det.add(Restrictions.le("autorizdata", filter.getAllaData()));
	}
	if (filter.getAutorizcomune() != null && StringUtils.isNotBlank(filter.getAutorizcomune().getCodicecomune())) {
	    det.add(Restrictions.eq("autorizcomune.codicecomune", filter.getAutorizcomune().getCodicecomune()));
	}
	if (filter.getDallaDataScadenza() != null) {
	    det.add(Restrictions.ge("datascadenza", filter.getDallaDataScadenza()));
	}
	if (filter.getAllaDataScadenza() != null) {
	    det.add(Restrictions.le("datascadenza", filter.getAllaDataScadenza()));
	}
	det.createAlias("anagrafe", "_anagrafe", Criteria.LEFT_JOIN);
	if (filter.getAnagrafe() != null && filter.getAnagrafe().getId() != null && filter.getAnagrafe().getId().getCodice() != null) {
	    det.add(Restrictions.or(Restrictions.eq("anagrafe.id.codice", filter.getAnagrafe().getId().getCodice()),
		    Restrictions.eq("occupante.id.codice", filter.getAnagrafe().getId().getCodice())));
	}
	if (!filter.getIncludiCessate()) {
	    det.add(Restrictions.eq("flagAttiva", true));
	}
	if (filter.isEscludiConcessioni()) {
	    det.createCriteria("autorizzazioniConcessionisForFkAutconcAutatt", "aut_conc", DetachedCriteria.LEFT_JOIN);
	    det.add(Restrictions.isNull("aut_conc.id.codice"));
	}
	////----------------------------------------------------  START ------------------------------------------------------------------///
	// FILTRO DATI DELLA MANIFESTAZIONE (PRESENTI SOLO SE PER IDCOMUNE E SOFTWARE IN USO E' ATTIVA LA CONFIGURAZIONE DELLE MANIFESTAZIONI)
	// Se è impostato almeno uno dei tre filtri creo la condizione di join
	// Era commentato?!?!?!?!?!?!!?
	if (EntityUtils.getNestedProperty(filter.getMercati(), "id.codice") != null
		|| EntityUtils.getNestedProperty(filter.getMercatiUso(), "id.codice") != null
		|| EntityUtils.getNestedProperty(filter.getMercatiD(), "id.codice") != null) {
	    det.createCriteria("autorizzazioniConcessionisForFkAutconcAutatt", "_autorizzazioniConcessionisForFkAutconcAutatt",
		    DetachedCriteria.LEFT_JOIN);
	}
	if (EntityUtils.getNestedProperty(filter.getMercati(), "id.codice") != null) {
	    det.add(Restrictions.eq("_autorizzazioniConcessionisForFkAutconcAutatt.mercatiId", filter.getMercati().getId().getCodice()));
	}
	if (EntityUtils.getNestedProperty(filter.getMercatiUso(), "id.codice") != null) {
	    det.add(Restrictions.eq("_autorizzazioniConcessionisForFkAutconcAutatt.mercatiUsoId", filter.getMercatiUso().getId().getCodice()));
	}
	if (EntityUtils.getNestedProperty(filter.getMercatiD(), "id.codice") != null) {
	    det.add(Restrictions.eq("_autorizzazioniConcessionisForFkAutconcAutatt.mercatiDId", filter.getMercatiD().getId().getCodice()));
	}
	////----------------------------------------------------  END ------------------------------------------------------------------///
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////// FILTRI PER DATI DELL'ISTANZA /////////////////////////////////////////////////
	det.createAlias("istanza", "_istanza", Criteria.LEFT_JOIN);
	// Se passo l'id dell'istanza filtro per istanza specifica
	if (filter.getIstanzeFilter() != null && filter.getIstanzeFilter().getCodiceIstanza() != null) {
	    det.add(Restrictions.eq("_istanza.id.codice", filter.getIstanzeFilter().getCodiceIstanza()));
	}
	// criterio per includere le aut non collegate all'istanza (quindi collegate all'anagrafica)
	//	Criterion softwareCrit = Restrictions.or(Restrictions.eq("_istanza.software.codice", ORMHelper.getSoftware()),
	//		Restrictions.isNull("_istanza.id.codice"));
	//	det.add(softwareCrit);
	det.createAlias("tipologiaregistro", "_tipologiaregistro", Criteria.INNER_JOIN);
	if (filter.getTipologiaregistro() != null && filter.getTipologiaregistro().getId() != null
		&& filter.getTipologiaregistro().getId().getCodice() != null) {
	    det.add(Restrictions.eq("_tipologiaregistro.id.codice", filter.getTipologiaregistro().getId().getCodice()));
	}
	Criterion softwareCrit = Restrictions.eq("_tipologiaregistro.software.codice", ORMHelper.getSoftware());
	det.add(softwareCrit);
	if (filter.getCodiceIstanzaDaEscludere() != null) {
	    Criterion codiceIstanzaCrit = Restrictions.or(Restrictions.isNull("_istanza.id.codice"),
		    Restrictions.ne("_istanza.id.codice", filter.getCodiceIstanzaDaEscludere()));
	    det.add(codiceIstanzaCrit);
	}
	if (StringUtils.isNotBlank(filter.getIstanzeFilter().getNumeroistanza())) {
	    det.add(Restrictions.eq("_istanza.numeroistanza", filter.getIstanzeFilter().getNumeroistanza()));
	}
	if (filter.getIstanzeFilter().getDallaData() != null) {
	    det.add(Restrictions.ge("_istanza.data", filter.getIstanzeFilter().getDallaData()));
	}
	if (filter.getIstanzeFilter().getAllaData() != null) {
	    det.add(Restrictions.le("_istanza.data", filter.getIstanzeFilter().getAllaData()));
	}
	if (EntityUtils.getNestedProperty(filter.getIstanzeFilter().getAlberoproc(), "id.codice") != null) {
	    det.createCriteria("_istanza.alberoproc", "_alberoproc");
	    det.add(Restrictions.eq("_alberoproc.id.codice", filter.getIstanzeFilter().getAlberoproc().getId().getCodice()));
	}
	if (EntityUtils.getNestedProperty(filter.getIstanzeFilter().getProcedura(), "id.codice") != null) {
	    det.createCriteria("_istanza.procedura", "_procedura");
	    det.add(Restrictions.eq("_procedura.id.codice", filter.getIstanzeFilter().getProcedura().getId().getCodice()));
	}
	boolean isIstanzestradarioJoinPresent = false;
	boolean isStradarioJoinPresent = false;
	if (EntityUtils.getNestedProperty(filter.getIstanzeFilter().getIstanzestradario().getStradario(), "id.codice") != null) {
	    det.createCriteria("_istanza.istanzestradarios", "istanzestradario");
	    det.createCriteria("istanzestradario.stradario", "_stradario");
	    det.add(Restrictions.eq("_stradario.id.codice", filter.getIstanzeFilter().getIstanzestradario().getStradario().getId().getCodice()));
	    isStradarioJoinPresent = true;
	}
	if (StringUtils.isNotBlank(filter.getIstanzeFilter().getIstanzestradario().getCap())) {
	    if (!isStradarioJoinPresent) {
		det.createCriteria("_istanza.istanzestradarios", "istanzestradario");
		isIstanzestradarioJoinPresent = true;
	    }
	    det.add(Restrictions.eq("istanzestradario.cap", filter.getIstanzeFilter().getIstanzestradario().getCap()));
	}
	if (filter.getIstanzeFilter().getComune() != null && StringUtils.isNotBlank(filter.getIstanzeFilter().getComune().getCodicecomune())) {
	    det.createCriteria("_istanza.comune", "_comune");
	    det.add(Restrictions.eq("_comune.codicecomune", filter.getIstanzeFilter().getComune().getCodicecomune()));
	}
	if (EntityUtils.getNestedProperty(filter.getIstanzeFilter().getRichiedente(), "id.codice") != null) {
	    det.createAlias("_istanza.richiedente", "_richiedenteistanza", Criteria.LEFT_JOIN);
	    det.add(Restrictions.eq("_richiedenteistanza.id.codice", filter.getIstanzeFilter().getRichiedente().getId().getCodice()));
	}
	/////////////////////////////////////////// GESSTIONE ORDINAMENBTO /////////////////////////////////////////////////////////
	String[] padNumeroautorizzazione = new String[] { "20", "' '" };
	String[] padNumeroistanza = new String[] { "20", "' '" };
	if (filter.getOrderBy() != null) {
	    String[] field = filter.getOrderBy().split(",");
	    for (int i = 0; i < field.length; i++) {
		if (field[i].equals("autorizdata")) {
		    setOrdine(field[i], det, filter.getOrderAscDesc(), "data", null);
		}
		if (field[i].equalsIgnoreCase("tipologiaregistro.trDescrizione")) {
		    setOrdine("_tipologiaregistro.trDescrizione", det, filter.getOrderAscDesc(), "standard", null);
		}
		if (field[i].equalsIgnoreCase("istanza.data")) {
		    setOrdine("_istanza.data", det, filter.getOrderAscDesc(), "data", null);
		}
		if (field[i].equalsIgnoreCase("anagrafeautotizzazione")) {
		    setOrdine("_anagrafe.nominativo", det, filter.getOrderAscDesc(), "standard", null);
		}
		if (field[i].equals("istanza.numeroistanza")) {
		    setOrdine("_istanza.numeroistanza", det, filter.getOrderAscDesc(), "leftpad", padNumeroistanza);
		}
		if (field[i].equals("autoriznumero")) {
		    setOrdine("autoriznumero", det, filter.getOrderAscDesc(), "leftpad", padNumeroautorizzazione);
		}
		if (field[i].equals("istanza.istanzestradario.stradario.descrizione")) {
		    if (isStradarioJoinPresent) {
			setOrdine("_stradario.descrizione", det, filter.getOrderAscDesc(), "standard", null);
		    } else if (isIstanzestradarioJoinPresent) {
			det.createCriteria("istanzestradario.stradario", "_stradario");
			setOrdine("_stradario.descrizione", det, filter.getOrderAscDesc(), "standard", null);
		    } else {
			det.createCriteria("_istanza.istanzestradarios", "istanzestradario", DetachedCriteria.FULL_JOIN);
			det.createCriteria("istanzestradario.stradario", "_stradario", DetachedCriteria.FULL_JOIN);
			setOrdine("_stradario.descrizione", det, filter.getOrderAscDesc(), "standard", null);
		    }
		}
	    }
	}
	return det;
    }

    /**
     * <pre>
     * Aggionge al DetachedCriteria passato l'ordinamento dove:
     * &#64;param FiledOrder: obbligatorio - indica il campo per cui ordinare 
     * &#64;param det : obbligatorio - DetachedCriteria a cui aggiungere l'ordinamento 
     * &#64;param orderTypeEnum : Obbligatorio - ASC o DESC
     * &#64;param tipoDatoOrdinamento : tipologia di ordinamento 
     * 					standard : campo semplice
     *                                  data     : campo data (applica ordinamento fatto tramite la funzione FunctionsEnum.NLV_FUNCTION )
     *                                  leftpad  : campo stringa (applica ordinamento fatto tramite la funzione FunctionsEnum.LPAD_FUNCTION )
     * &#64;param parametro
     * </pre>
     */
    private void setOrdine(String FiledOrder, DetachedCriteria det, OrderTypeEnum orderTypeEnum, String tipoDatoOrdinamento, String... parametro) {

	if (tipoDatoOrdinamento.equalsIgnoreCase("standard")) {
	    if (orderTypeEnum.name().equalsIgnoreCase("ASC")) {
		det.addOrder(Order.asc(FiledOrder));
	    } else {
		det.addOrder(Order.desc(FiledOrder));
	    }
	}
	if (tipoDatoOrdinamento.equalsIgnoreCase("data")) {
	    if (orderTypeEnum.name().equalsIgnoreCase("ASC")) {
		det.addOrder(
			OrderBySqlFormula.asc(FiledOrder, FunctionsEnum.NVL_FUNCTION, "'01/01/2999'", OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	    } else {
		det.addOrder(
			OrderBySqlFormula.desc(FiledOrder, FunctionsEnum.NVL_FUNCTION, "'01/01/2999'", OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	    }
	}
	if (tipoDatoOrdinamento.equalsIgnoreCase("leftpad")) {
	    if (orderTypeEnum.name().equalsIgnoreCase("ASC")) {
		det.addOrder(OrderBySqlFormula.asc(FiledOrder, FunctionsEnum.LPAD_FUNCTION, parametro));
	    } else {
		det.addOrder(OrderBySqlFormula.desc(FiledOrder, FunctionsEnum.LPAD_FUNCTION, parametro));
	    }
	}
    }

    /**
     * SELECT AUT_CONC.FK_IDPOSTEGGIO,AUT.AUTORIZNUMERO,AUT_SUB.AUTORIZNUMERO from AUTORIZZAZIONI AUT inner join
     * AUTORIZZAZIONI_CONCESSIONI AUT_CONC on AUT.ID=AUT_CONC.FK_IDAUT_ATTUALE and AUT.IDCOMUNE=AUT_CONC.IDCOMUNE left
     * join AUTORIZZAZIONI_SUBENTRI AUT_SUB on AUT_SUB.FK_IDAUT_ATTUALE=AUT_CONC.FK_IDAUT_ATTUALE and
     * AUT_SUB.IDCOMUNE=AUT_CONC.IDCOMUNE where AUT.IDCOMUNE=? and (AUT.FKIDISTANZA=? or AUT_SUB.FKIDISTANZA=?);
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AutorizzazioniDTO> findConcESub(Integer codiceIstanza) {

	DetachedCriteria criteria = getBaseCriteriaFindConcessioniESubertri(codiceIstanza);
	List<AutorizzazioniDTO> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    public List<AutorizzazioniDTO> findConcESub(Integer codiceIstanza, Boolean escludiCessate) {

	DetachedCriteria criteria = getBaseCriteriaFindConcessioniESubertri(codiceIstanza);
	if (escludiCessate != null && escludiCessate == true) {
	    criteria.add(Restrictions.isNull("dataCessazione"));
	}
	criteria.addOrder(Order.desc("autorizdata"));
	@SuppressWarnings("unchecked")
	List<AutorizzazioniDTO> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    private DetachedCriteria getBaseCriteriaFindConcessioniESubertri(Integer codiceIstanza) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("autorizcomune", "_autorizcomune");
	criteria.createAlias("tipologiaregistro", "_tipologiaregistro");
	criteria.createAlias("autorizzazioniConcessionisForFkAutconcAutatt", "aut_conc");
	criteria.createAlias("aut_conc.mercatiD", "_mercatiD");
	criteria.createAlias("aut_conc.mercatiUso", "_mercatiUso");
	criteria.createAlias("autorizzazioniSubentris", "aut_sub", DetachedCriteria.LEFT_JOIN);
	criteria.add(
		Restrictions.or(Restrictions.eq("istanza.id.codice", codiceIstanza), Restrictions.eq("aut_sub.istanze.id.codice", codiceIstanza)));
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	plist.add(Projections.property("_autorizcomune.comune"), "AUTORIZCOMUNE");
	plist.add(Projections.property("autoriznumero"), "AUTORIZNUMERO");
	plist.add(Projections.property("autorizdata"), "AUTORIZDATA");
	plist.add(Projections.property("_tipologiaregistro.trDescrizione"), "TIPOLOGIAREGISTRO");
	plist.add(Projections.property("flagAttiva"), "FLAGATTIVA");
	plist.add(Projections.property("dataCessazione"), "DATACESSAZIONE");
	plist.add(Projections.property("_mercatiD.codiceposteggio"), "CODICEPOSTEGGIO");
	plist.add(Projections.property("_mercatiD.id.codice"), "IDPOSTEGGIO");
	plist.add(Projections.property("aut_sub.autoriznumero"), "AUTORIZNUMEROSUBENTRO");
	plist.add(Projections.property("_mercatiUso.descrizione"), "MERCATIUSODESCRIZIONE");
	//
	criteria.setProjection(plist);
	//
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(AutorizzazioniDTO.class));
	return criteria;
    }

    @Override
    public List<AutorizzazioneSpuntistaHelper> findAutorizzazioniObjSpuntisti(Integer codiceMercato, Integer codiceUso,
	    Integer idGiornataRiferimento) {

	//nuova logica per il recupero della data 
	MercatiConfigurazione mConfigurazione = mercatiConfigurazioneDAO.findConfigurazione();
	Date dataRiferimento = Calendar.getInstance().getTime();
	if (idGiornataRiferimento != null) {
	    MercatipresenzeT mpt = mercatipresenzeTDAO.findById(new PkId(idGiornataRiferimento));
	    if (mpt != null && mpt.getDataRegistrazione() != null && mConfigurazione.getGradIntervalloDate() != null) {
		dataRiferimento = calcolaDataRegistrazione(mConfigurazione.getGradIntervalloDate(), mpt.getDataRegistrazione());
	    }
	}
	Mercati m = mercatiDAO.findById(new PkId(codiceMercato));
	Integer tipoManifestazione = m.getManifestazione().getComportamentoPresenze();
	log.debug("#findAutorizzazioniObjSpuntisti - DataRiferimento per calcolo autorizzazioni: {}", dataRiferimento);
	// controlla se per il codice mercato e giorno sono presenti record 
	boolean isConfigurazioneSpuntistiMercatiAttiva = MercatiConfigurazione.checkConfigurazioneGradSpuntisti(tipoManifestazione, mConfigurazione);
	int flagConteggiaPresAss = 1; // solo delle giornata che effettuano il conteggio delle presenze
	String qu = "SELECT id as idautorizzazione,  autorizdata,autoriznumero,codiceanagrafe,nominativo,nome,partitaiva,codicefiscale, dataiscrrea, dataregditte, data_anzianita as dataanzianita, sum(numeropresenze) as numpresenze, idcomune, autorizcodcomune, autorizcomune, " + //
		    "   autoriginnumero,  codiceGerente,  nominativogerente,  nomegerente,  partitaivagerente, codicefiscalegerente,autorizcomprov,autorizcomsiglaprov" + //
		    ",protocolloaut,dataprotocolloaut, " + //
		    "codicetitolare,nominativotitolare,nometitolare,partitaivatitolare,codicefiscaletitolare," + //
		    "dataiscrreatitolare,dataregdittetitolare " + //
		    "FROM (SELECT" + //
		    "   autorizzazioni.id ,  autorizzazioni.autorizdata,  autorizzazioni.autoriznumero,  anagrafe.codiceanagrafe,  anagrafe.nominativo,  anagrafe.nome,  anagrafe.partitaiva,   anagrafe.codicefiscale, case when gerente.codiceanagrafe is null then anagrafe.dataiscrrea else gerente.dataiscrrea end as dataiscrrea,case when gerente.codiceanagrafe is null then anagrafe.dataregditte else gerente.dataregditte end as dataregditte,autorizzazioni.data_anzianita,  mpd.numeropresenze, autorizzazioni.idcomune, " + //
		    "   autorizzazioni.autorizcomune as autorizcodcomune, comautoriz.comune as autorizcomune, " +
		    " autorizzazioni.autorig_numero as autoriginnumero,  gerente.codiceanagrafe as codiceGerente,  gerente.nominativo as nominativogerente,  gerente.nome as nomegerente,  gerente.partitaiva as partitaivagerente, gerente.codicefiscale as codicefiscalegerente" + //
		    ",comuneautoriz.provincia as autorizcomprov,comuneautoriz.siglaprovincia as autorizcomsiglaprov,autcsi.protocollo as protocolloaut,autcsi.data_protocollo as dataprotocolloaut" + //
		    ",titolare.codiceanagrafe as codicetitolare,titolare.nominativo as nominativotitolare,titolare.nome as nometitolare,titolare.partitaiva as partitaivatitolare,titolare.codicefiscale as codicefiscaletitolare," + //
		    "titolare.dataiscrrea as dataiscrreatitolare,titolare.dataregditte as dataregdittetitolare " + //
		    " FROM" + //
		    "    #SCHEMA_NAME#mercatipresenze_d mpd" + //
		    "    INNER JOIN #SCHEMA_NAME#mercatipresenze_t ON mpd.idcomune = mercatipresenze_t.idcomune" + //
		    "                                    AND mpd.fkidtestata = mercatipresenze_t.id" + //
		    "    INNER JOIN #SCHEMA_NAME#mercati ON mercatipresenze_t.idcomune = mercati.idcomune" + //
		    "                          AND mercatipresenze_t.fkcodicemercato = mercati.codicemercato" + //
		    "    INNER JOIN #SCHEMA_NAME#mercati_uso ON mercati_uso.idcomune = mercatipresenze_t.idcomune" + //
		    "                              AND mercati_uso.id = mercatipresenze_t.fkidmercatiuso" + //
		    "    INNER JOIN #SCHEMA_NAME#autorizzazioni ON autorizzazioni.idcomune = mpd.idcomune" + //
		    "                                 AND autorizzazioni.id = mpd.fk_autorizzazioni_id" + //
		    "    INNER JOIN #SCHEMA_NAME#anagrafe ON autorizzazioni.idcomune = anagrafe.idcomune" + //
		    "                           AND autorizzazioni.codiceoccupante = anagrafe.codiceanagrafe" + //
		    "    INNER JOIN #SCHEMA_NAME#anagrafe titolare ON autorizzazioni.idcomune = titolare.idcomune" + //
		    "                           AND autorizzazioni.fk_codiceanagrafe = titolare.codiceanagrafe" + //
		    "    INNER JOIN #SCHEMA_NAME#vw_entilocali comautoriz ON comautoriz.codicecomune = autorizzazioni.autorizcomune " + //
		    "    LEFT JOIN #SCHEMA_NAME#comuni comuneautoriz ON comautoriz.codicecomune = comuneautoriz.codicecomune " + //
		    "    LEFT JOIN #SCHEMA_NAME#autorizzazioni_csi autcsi ON autorizzazioni.idcomune = autcsi.idcomune" + //
		    "                        	AND autorizzazioni.id = autcsi.fk_autorizzazioni_id " +
		    "    LEFT JOIN #SCHEMA_NAME#anagrafe gerente ON gerente.idcomune = autcsi.idcomune" + //
		    "                                 AND gerente.codiceanagrafe = autcsi.fk_codicegerente " + //
		    " WHERE" + //
		    "    mpd.idcomune = ?" + //
		    "    AND mercati.software = ?" + //
		    "    AND mercatipresenze_t.fkcodicemercato = ?" + //
		    "    AND mercatipresenze_t.fkidmercatiuso = ?" + //
		    "    AND mercatipresenze_t.dataregistrazione <= ? " + //
		    "    AND mpd.spuntista = ?" + //
		    "    AND autorizzazioni.flag_attiva=?" + //
		    "    AND mercatipresenze_t.flag_conteggia_pres_ass = ? " + //
		    " UNION ALL " + //
		    "select" + //
		    "   autorizzazioni.id, autorizzazioni.autorizdata,autorizzazioni.autoriznumero,anagrafe.codiceanagrafe,anagrafe.nominativo,anagrafe.nome,anagrafe.partitaiva,anagrafe.codicefiscale,case when gerente.codiceanagrafe is null then anagrafe.dataiscrrea else gerente.dataiscrrea end as dataiscrrea, case when gerente.codiceanagrafe is null then anagrafe.dataregditte else gerente.dataregditte end as dataregditte,autorizzazioni.data_anzianita,mercatipresenze_storico.numeropresenze, autorizzazioni.idcomune, " + //
		    "   autorizzazioni.autorizcomune as autorizcodcomune, comautoriz.comune as autorizcomune, " + //
		    " autorizzazioni.autorig_numero as autoriginnumero,  gerente.codiceanagrafe as codiceGerente,  gerente.nominativo as nominativogerente,  gerente.nome as nomegerente,  gerente.partitaiva as partitaivagerente, gerente.codicefiscale as codicefiscalegerente" + //
		    ",comuneautoriz.provincia as autorizcomprov,comuneautoriz.siglaprovincia as autorizcomsiglaprov,autcsi.protocollo as protocolloaut,autcsi.data_protocollo as dataprotocolloaut  " + //
		    ",titolare.codiceanagrafe as codicetitolare,titolare.nominativo as nominativotitolare,titolare.nome as nometitolare,titolare.partitaiva as partitaivatitolare,titolare.codicefiscale as codicefiscaletitolare," + //
		    "titolare.dataiscrrea as dataiscrreatitolare,titolare.dataregditte as dataregdittetitolare " + //
		    " FROM" + //
		    "    #SCHEMA_NAME#mercatipresenze_storico " + //
		    "    INNER JOIN #SCHEMA_NAME#mercati ON mercatipresenze_storico.idcomune = mercati.idcomune" + //
		    "                          AND mercatipresenze_storico.fkcodicemercato = mercati.codicemercato" + //
		    "    INNER JOIN #SCHEMA_NAME#mercati_uso ON mercati_uso.idcomune = mercatipresenze_storico.idcomune" + //
		    "                              AND mercati_uso.id = mercatipresenze_storico.fkidmercatiuso" + //
		    "    INNER JOIN #SCHEMA_NAME#autorizzazioni ON autorizzazioni.idcomune = mercatipresenze_storico.idcomune" + //
		    "                                 AND autorizzazioni.id = mercatipresenze_storico.fk_autorizzazioni_id" + //
		    "    INNER JOIN #SCHEMA_NAME#anagrafe ON autorizzazioni.idcomune = anagrafe.idcomune" + //
		    "                           AND autorizzazioni.codiceoccupante = anagrafe.codiceanagrafe" + //
		    "    INNER JOIN #SCHEMA_NAME#anagrafe titolare ON autorizzazioni.idcomune = titolare.idcomune" + //
		    "                           AND autorizzazioni.fk_codiceanagrafe = titolare.codiceanagrafe" + //
		    "    INNER JOIN #SCHEMA_NAME#vw_entilocali comautoriz ON comautoriz.codicecomune = autorizzazioni.autorizcomune " + //
		    "    LEFT JOIN #SCHEMA_NAME#comuni comuneautoriz ON comautoriz.codicecomune = comuneautoriz.codicecomune " + //
		    "    LEFT JOIN #SCHEMA_NAME#autorizzazioni_csi autcsi ON autorizzazioni.idcomune = autcsi.idcomune" + //
		    "                        	AND autorizzazioni.id = autcsi.fk_autorizzazioni_id " +
		    "    LEFT JOIN #SCHEMA_NAME#anagrafe gerente ON gerente.idcomune = autcsi.idcomune" + //
		    "                                 AND gerente.codiceanagrafe = autcsi.fk_codicegerente " + //
		    " WHERE" + //
		    "    mercatipresenze_storico.idcomune = ?" + //
		    "    AND   mercati.software = ?" + //
		    "    AND   mercatipresenze_storico.fkcodicemercato = ?" + //
		    "    AND   mercatipresenze_storico.fkidmercatiuso = ?" + //
		    "    AND   mercatipresenze_storico.data <= ?" + // dataRiferimento 
		    "    AND   autorizzazioni.flag_attiva=?" + //
		    //		"    GROUP BY" + //
		    //		"    autorizzazioni.id" //
		    ") aut_presenze where  coalesce(numeropresenze,0)<>?  group by  id, autorizdata,autoriznumero,codiceanagrafe,nominativo,nome,partitaiva,codicefiscale,dataiscrrea,dataregditte,data_anzianita, idcomune,autorizcodcomune,autorizcomune,autoriginnumero,  codiceGerente,  nominativogerente,  nomegerente,  partitaivagerente, codicefiscalegerente, autorizcomprov, autorizcomsiglaprov,protocolloaut,dataprotocolloaut  " +
		    " ,codicetitolare,nominativotitolare,nometitolare,partitaivatitolare,codicefiscaletitolare,dataiscrreatitolare,dataregdittetitolare ";
	if (!isConfigurazioneSpuntistiMercatiAttiva) {
	    qu += "  " + getDefaultOrdinamento(false, false);
	}
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	// Se isConfigurazioneSpuntistiMercatiAttiva== true, significa che per il mercato, uso e autorizzazione esiste un record nella 
	// tabella spuntisti_mercati. Questo comporta che la graduatoria deve essere creata a partire da tale tabella, andando a considerare 
	// solo i recordo con flag_attivo=1. La tebella mercati_presenze_d e mercati_presenze_storico verranno utilizzati per ordinare
	// la graduatoria utilizzando la somma delle presenze e data rea
	if (isConfigurazioneSpuntistiMercatiAttiva) {
	    qu = "select autorizzazioni.id as idautorizzazione,   autorizzazioni.autorizdata,   autorizzazioni.autoriznumero,   anagrafe.codiceanagrafe,   anagrafe.nominativo,   " + //
		 "anagrafe.nome,   anagrafe.partitaiva,   anagrafe.codicefiscale,case when gerente.codiceanagrafe is null then anagrafe.dataiscrrea else gerente.dataiscrrea end as dataiscrrea, case when gerente.codiceanagrafe is null then anagrafe.dataregditte else gerente.dataregditte end as dataregditte,autorizzazioni.data_anzianita as dataanzianita, coalesce(sum(numpresenze),0) as numpresenze,   " + //
		 "spuntisti_mercati.idcomune, autorizzazioni.autorizcomune as autorizcodcomune,comautoriz.comune as autorizcomune , " + //
		 "   autorizzazioni.autorig_numero as autoriginnumero,  gerente.codiceanagrafe as codiceGerente,  gerente.nominativo as nominativogerente,  gerente.nome as nomegerente,  gerente.partitaiva as partitaivagerente, gerente.codicefiscale as codicefiscalegerente " + //
		 ",comuneautoriz.provincia as autorizcomprov,comuneautoriz.siglaprovincia as autorizcomsiglaprov,autcsi.protocollo as protocolloaut,autcsi.data_protocollo as dataprotocolloaut  " + //
		 ",titolare.codiceanagrafe as codicetitolare,titolare.nominativo as nominativotitolare,titolare.nome as nometitolare,titolare.partitaiva as partitaivatitolare,titolare.codicefiscale as codicefiscaletitolare," + //
		 "titolare.dataiscrrea as dataiscrreatitolare,titolare.dataregditte as dataregdittetitolare " + //		    
		 " from #SCHEMA_NAME#spuntisti_mercati inner join #SCHEMA_NAME#istanze on spuntisti_mercati.idcomune =istanze.idcomune and " +
		 " spuntisti_mercati.fk_istanza = istanze.codiceistanza " +
		 " inner join #SCHEMA_NAME#autorizzazioni on spuntisti_mercati.idcomune =autorizzazioni.idcomune and " + //
		 "spuntisti_mercati.fk_autorizzazione=autorizzazioni.id " + //
		 "inner join #SCHEMA_NAME#anagrafe on autorizzazioni.idcomune = anagrafe.idcomune and " + //
		 "autorizzazioni.codiceoccupante = anagrafe.codiceanagrafe " + //
		 "inner join #SCHEMA_NAME#anagrafe titolare on autorizzazioni.idcomune = titolare.idcomune and " + //
		 "autorizzazioni.fk_codiceanagrafe = titolare.codiceanagrafe " + //
		 "left join   (" + //
		 qu;
	    qu = qu + ") presenze on presenze.idcomune =autorizzazioni.idcomune and presenze.idautorizzazione=autorizzazioni.id " + "" //
		 + //
		 " LEFT JOIN #SCHEMA_NAME#vw_entilocali comautoriz ON comautoriz.codicecomune = autorizzazioni.autorizcomune " + //
		 "    LEFT JOIN #SCHEMA_NAME#comuni comuneautoriz ON comautoriz.codicecomune = comuneautoriz.codicecomune " + //
		 "    LEFT JOIN #SCHEMA_NAME#autorizzazioni_csi autcsi ON autorizzazioni.idcomune = autcsi.idcomune" + //
		 "                        	AND autorizzazioni.id = autcsi.fk_autorizzazioni_id " +
		 "    LEFT JOIN #SCHEMA_NAME#anagrafe gerente ON gerente.idcomune = autcsi.idcomune" + //
		 "                             AND gerente.codiceanagrafe = autcsi.fk_codicegerente " + //
		 "where " + //
		 "spuntisti_mercati.idcomune =? and spuntisti_mercati.fk_mercato =? " + //
		 " {CONDIZIONE_USO} " + " and spuntisti_mercati.flg_attivo =? and autorizzazioni.flag_attiva = ? "; //
	    String data_validita = BaseQueryHelper.applySimpleNVLFunction("istanze.datavalidita",
		    BaseQueryHelper.stringToDate_DDMMYYYY("31/12/1900", DialettoEnum.fromHibernateDialect(sfi.getDialect().toString())).toString(),
		    DialettoEnum.fromHibernateDialect(sfi.getDialect().toString())).toString();
	    qu += " and " + data_validita + " <= ?" + //
		  "group by autorizzazioni.id,   autorizzazioni.autorizdata,   autorizzazioni.autoriznumero, autorizzazioni.data_anzianita, " + //
		  " anagrafe.codiceanagrafe,   anagrafe.nominativo,   anagrafe.nome,   anagrafe.partitaiva,   " + //
		  "anagrafe.codicefiscale,   CASE WHEN gerente.codiceanagrafe IS NULL THEN anagrafe.dataiscrrea ELSE gerente.dataiscrrea END , CASE WHEN gerente.codiceanagrafe IS NULL THEN anagrafe.dataregditte ELSE gerente.dataregditte END, autorizzazioni.data_anzianita, spuntisti_mercati.idcomune, autorizzazioni.autorizcomune,comautoriz.comune," +
		  "autorizzazioni.autorig_numero ,  gerente.codiceanagrafe ,  gerente.nominativo ,  gerente.nome ,  gerente.partitaiva , gerente.codicefiscale " +
		  ",comuneautoriz.provincia ,comuneautoriz.siglaprovincia,autcsi.protocollo ,autcsi.data_protocollo  " +
		  ",titolare.codiceanagrafe,titolare.nominativo,titolare.nome,titolare.partitaiva,titolare.codicefiscale ," + //
		  "titolare.dataiscrrea ,titolare.dataregditte " + //	
		  //.add ordinamento
		  "  " + getDefaultOrdinamento(true, false);
	    qu = qu.replace("{CONDIZIONE_USO}", " and spuntisti_mercati.fk_mercato_uso=? ");
	}
	String sql = qu.replaceAll("#SCHEMA_NAME#", schemaName + ".");
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("idautorizzazione", Hibernate.INTEGER);
	int pos = 0;
	q.setString(pos++, ORMHelper.getIdcomune());
	q.setString(pos++, ORMHelper.getSoftware());
	q.setInteger(pos++, codiceMercato);
	q.setInteger(pos++, codiceUso);
	q.setDate(pos++, dataRiferimento);
	q.setInteger(pos++, 1); // spuntista
	q.setInteger(pos++, 1); // autorizzazioni_flag_attiva
	q.setInteger(pos++, flagConteggiaPresAss); // flagConteggiaPresAss
	q.setString(pos++, ORMHelper.getIdcomune());
	q.setString(pos++, ORMHelper.getSoftware());
	q.setInteger(pos++, codiceMercato);
	q.setInteger(pos++, codiceUso);
	q.setDate(pos++, dataRiferimento);
	q.setInteger(pos++, 1); // autorizzazioni_flag_attiva	
	q.setInteger(pos++, 0);
	if (isConfigurazioneSpuntistiMercatiAttiva) {
	    q.setString(pos++, ORMHelper.getIdcomune());
	    q.setInteger(pos++, codiceMercato);
	    q.setInteger(pos++, codiceUso);
	    q.setInteger(pos++, 1);
	    q.setInteger(pos++, 1); // autorizzazioni_flag_attiva
	    Calendar t = Calendar.getInstance();
	    t.set(Calendar.HOUR, 23);
	    t.set(Calendar.MINUTE, 59);
	    t.set(Calendar.SECOND, 59);
	    q.setDate(pos++, t.getTime());
	}
	// setScalar
	q.addScalar("idautorizzazione", Hibernate.INTEGER);
	q.addScalar("autorizdata", Hibernate.DATE);
	q.addScalar("autoriznumero", Hibernate.STRING);
	q.addScalar("codiceanagrafe", Hibernate.INTEGER);
	q.addScalar("nominativo", Hibernate.STRING);
	q.addScalar("nome", Hibernate.STRING);
	q.addScalar("partitaiva", Hibernate.STRING);
	q.addScalar("codicefiscale", Hibernate.STRING);
	q.addScalar("dataregditte", Hibernate.DATE);
	q.addScalar("dataanzianita", Hibernate.DATE);
	q.addScalar("numpresenze", Hibernate.INTEGER);
	q.addScalar("dataiscrrea", Hibernate.DATE);
	q.addScalar("dataregditte", Hibernate.DATE);
	q.addScalar("autorizcodcomune", Hibernate.STRING);
	q.addScalar("autorizcomune", Hibernate.STRING);
	q.addScalar("autoriginnumero", Hibernate.STRING);
	q.addScalar("codiceGerente", Hibernate.INTEGER);
	q.addScalar("nominativogerente", Hibernate.STRING);
	q.addScalar("nomegerente", Hibernate.STRING);
	q.addScalar("partitaivagerente", Hibernate.STRING);
	q.addScalar("codicefiscalegerente", Hibernate.STRING);
	q.addScalar("autorizcomprov", Hibernate.STRING);
	q.addScalar("autorizcomsiglaprov", Hibernate.STRING);
	q.addScalar("protocolloaut", Hibernate.STRING);
	q.addScalar("dataprotocolloaut", Hibernate.DATE);
	q.addScalar("codicetitolare", Hibernate.INTEGER);
	q.addScalar("nominativotitolare", Hibernate.STRING);
	q.addScalar("nometitolare", Hibernate.STRING);
	q.addScalar("partitaivatitolare", Hibernate.STRING);
	q.addScalar("codicefiscaletitolare", Hibernate.STRING);
	q.addScalar("dataregdittetitolare", Hibernate.DATE);
	q.addScalar("dataiscrreatitolare", Hibernate.DATE);
	// transformer
	q.setResultTransformer(Transformers.aliasToBean(AutorizzazioneSpuntistaHelper.class));
	@SuppressWarnings("unchecked")
	List<AutorizzazioneSpuntistaHelper> ret = q.list();
	return ret;
    }

    // Metodo che serve per calcolare la data di registrazione 
    public Date calcolaDataRegistrazione(String gradIntervalloDate, Date dataMercato) {

	Calendar cal = Calendar.getInstance();
	int anno = cal.get(Calendar.YEAR);
	ArrayList<String> date = estraiListaDate(gradIntervalloDate);
	//controllo come sono ordinate le date degli intervalli per poi settare l'anno corretto
	List<Date> array = getOrderArrayOfDate(date, anno);
	int cont = 0;
	Date dateInterv = new Date();
	for (int i = 0; i < array.size(); i++) {
	    if (dataMercato.compareTo(array.get(i)) <= 0) {
		if (cont == 0) {
		    dateInterv = Utilities.removeYears(array.get(array.size() - 1), 1);
		} else {
		    dateInterv = array.get(i - 1);
		}
		//esco dal ciclo poiché ho trovato l'estremo più piccolo
		break;
	    } else {
		if (array.size() - 1 == i) {
		    dateInterv = array.get(i);
		}
		cont++;
	    }
	}
	dataMercato = dateInterv;
	return dataMercato;
    }

    private static List<Date> getOrderArrayOfDate(List<String> date, int anno) {

	List<Date> dat = new ArrayList<Date>();
	try {
	    dat.add(new SimpleDateFormat(DD_MM_YYYY).parse(date.get(0) + "/" + anno));
	    for (int i = 0; i < date.size() - 1; i++) {
		if (date.get(i).compareTo(date.get(i + 1)) > 0) {
		    anno += 1;
		}
		dat.add(new SimpleDateFormat(DD_MM_YYYY).parse(date.get(i + 1) + "/" + anno));
	    }
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	return dat;
    }

    private ArrayList<String> estraiListaDate(String gradIntervalloDate) {

	final String regex = "([0-9].\\/[0-9].)";
	Pattern pattern = Pattern.compile(regex);
	Matcher matcher = pattern.matcher(gradIntervalloDate);
	ArrayList<String> date = new ArrayList<String>();
	while (matcher.find()) {
	    date.add(matcher.group());
	}
	Collections.sort(date);
	return date;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AutorizzazioniRestHelper> findRestHelper(Set<Integer> auts, Integer idGiornata, Integer codiceMercato, Integer codiceUso) {

	//
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	//
	String inClause = "";
	int num = auts.size();
	Double filterGetListaCodiceAttivitaLength = Double.valueOf(num);
	Double cicli = filterGetListaCodiceAttivitaLength / 1000;
	int cicliDaMille = cicli.intValue();
	int resto = num - (cicliDaMille * 1000);
	inClause = "  ( 1=2 ";
	for (int i = 0; i < cicliDaMille; i++) {
	    String qm = StringUtils.repeat("?,", 1000);
	    qm = qm.substring(0, qm.length() - 1);
	    inClause += " or autorizzazioni.id in (" + qm + ")";
	}
	if (resto > 0) {
	    String qm = StringUtils.repeat("?,", resto);
	    qm = qm.substring(0, qm.length() - 1);
	    inClause += " or autorizzazioni.id in (" + qm + ")";
	}
	inClause += ")";
	//
	String sql = getSQL(sfi).replace("#SCHEMA_NAME#", schemaName + ".").replace("AUTORIZZAZIONI_ID_IN_CLAUSE", inClause)
		.replace("AUTORIZZAZIONI_ATTIVE_CLAUSE", " and autorizzazioni.flag_attiva = ? ");
	Mercati m = this.getById(Mercati.class, new PkId(codiceMercato));
	// controlla se per il codice mercato e giorno sono presenti record
	MercatiConfigurazione mConfigurazione = mercatiConfigurazioneDAO.findConfigurazione();
	boolean isConfigurazioneSpuntistiMercatiAttiva = mConfigurazione != null
		&& MercatiConfigurazione.checkConfigurazioneGradSpuntisti(m.getManifestazione().getComportamentoPresenze(), mConfigurazione);
	String orderBy = getDefaultOrdinamento(isConfigurazioneSpuntistiMercatiAttiva, true);
	SQLQuery q = getSession().createSQLQuery(sql + " " + orderBy);
	q.setInteger(0, codiceMercato);
	q.setInteger(1, codiceUso);
	//nuova logica di recupero della data registrazione 
	Date c = this.settaDataRegistrazione();
	q.setDate(2, c);
	q.setInteger(3, codiceMercato);
	q.setInteger(4, codiceUso);
	q.setDate(5, c);
	q.setInteger(6, 1); // flag_manifestazioni
	q.setInteger(7, idGiornata);
	q.setString(8, ORMHelper.getIdcomune());
	//////////////////////////
	int position = 9;
	q.setInteger(position++, 1); // SOLO LE ATTIVE
	for (Integer idAutorizzazione : auts) {
	    q.setInteger(position++, idAutorizzazione);
	}
	/////////////////////////
	q.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(AutorizzazioniRestHelper.class));
	addScalar(q);
	return q.list();
    }

    private Date settaDataRegistrazione() {

	MercatiConfigurazione mc = mercatiConfigurazioneDAO.findConfigurazione();
	Date dataRiferimento = Calendar.getInstance().getTime();
	if (mc != null && mc.getGradIntervalloDate() != null) {
	    dataRiferimento = this.calcolaDataRegistrazione(mc.getGradIntervalloDate(), dataRiferimento);
	}
	return dataRiferimento;
    }

    @Override
    public List<CodiceDescrizioneBean> findAttivitaInAutorizzazioni() {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("tipologiaregistro", "_tipologiaregistro");
	criteria.createAlias("_tipologiaregistro.software", "_software");
	criteria.createAlias("autAts", "_autAts");
	criteria.createAlias("_autAts.attivita", "_attivita");
	criteria.add(Restrictions.eq("_software.codice", ORMHelper.getSoftware()));
	criteria.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.groupProperty("_attivita.id.codiceistat"));
	plist.add(Projections.groupProperty("_attivita.istat"));
	criteria.setProjection(plist);
	//
	List<CodiceDescrizioneBean> result = new ArrayList<CodiceDescrizioneBean>();
	CodiceDescrizioneBean cdb = null;
	@SuppressWarnings("unchecked")
	List<Object> list = (List<Object>) getHibernateTemplate().findByCriteria(criteria);
	for (Object object : list) {
	    Object[] el = (Object[]) object;
	    cdb = new CodiceDescrizioneBean();
	    cdb.setCodice((String) el[0]);
	    cdb.setDescrizione((String) el[1]);
	    result.add(cdb);
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AutorizzazioniRestHelper> findAnagraficheConAutorizzazione(String testo, Integer firstResult, Integer maxResults) {

	if (testo != null) {
	    testo = testo.toUpperCase();
	}
	//
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	//
	String inClause = "  (upper(autorizzazioni.AUTORIZNUMERO) LIKE ?  OR upper(autorizzazionicsi.AUT_PRECEDENTE_NUMERO) LIKE ?) or " +
			  "(upper(concat(concat(anagrafe.nominativo,' '),(case when  anagrafe.nome is null then '' else anagrafe.nome end))) " +
			  "like ?  or (upper(anagrafe.codicefiscale) like ? or upper(anagrafe.partitaiva) like ?)) or " +
			  "(upper(concat(concat(occupante.nominativo,' '),(case when  occupante.nome is null then '' else occupante.nome end))) " +
			  "like ?  or (upper(occupante.codicefiscale) like ? or upper(occupante.partitaiva) like ?)) or " +
			  "(upper(concat(concat(gerente.nominativo,' '),(case when  gerente.nome is null then '' else gerente.nome end))) " +
			  "like ?  or (upper(gerente.codicefiscale) like ? or upper(gerente.partitaiva) like ?)) ";
	//
	String sql = getSQL(sfi).replace("#SCHEMA_NAME#", schemaName + ".").replace("AUTORIZZAZIONI_ID_IN_CLAUSE", inClause)
		.replace("AUTORIZZAZIONI_ATTIVE_CLAUSE", "");
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setInteger(0, -1);
	q.setInteger(1, -1);
	//nuova logica di recupero della data registrazione 
	Date c = this.settaDataRegistrazione();
	q.setDate(2, c);
	q.setInteger(3, -1);
	q.setInteger(4, -1);
	q.setDate(5, c);
	q.setInteger(6, 1); // flag_manifestazioni
	q.setInteger(7, -1);
	q.setString(8, ORMHelper.getIdcomune());
	//////////////////////////
	int pos = 9;
	// q.setInteger(pos++, 1); // SOLO LE ATTIVE
	q.setString(pos++, "%" + testo + "%");
	q.setString(pos++, "%" + testo + "%");
	q.setString(pos++, "%" + testo + "%");
	q.setString(pos++, "%" + testo + "%");
	q.setString(pos++, "%" + testo + "%");
	q.setString(pos++, "%" + testo + "%");
	q.setString(pos++, "%" + testo + "%");
	q.setString(pos++, "%" + testo + "%");
	q.setString(pos++, "%" + testo + "%");
	q.setString(pos++, "%" + testo + "%");
	q.setString(pos++, "%" + testo + "%");
	/////////////////////////
	q.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(AutorizzazioniRestHelper.class));
	addScalar(q);
	return q.list();
    }

    private String getSQL(SessionFactoryImplementor sfi) {

	DialettoEnum dialetto = DialettoEnum.fromHibernateDialect(sfi.getDialect().toString());
	String replacement = "  ";
	if (DialettoEnum.MYSQL.equals(dialetto)) {
	    replacement = " FORCE INDEX (FK_MERCPRESD_AUT) ";
	}
	return getSQLDaModificare().replace(FORCE_INDEX, replacement);
    }

    private String getSQLDaModificare() {

	StringBuilder sqlAuth = new StringBuilder("SELECT");
	sqlAuth.append(" autorizzazioni.id as idAutorizzazione,");
	sqlAuth.append(" autorizzazioni.autoriznumero as autoriznumero,");
	sqlAuth.append(" autorizzazioni.autorizdata as autorizdata,");
	sqlAuth.append(" autorizzazioni.codiceoccupante as codiceOccupante,");
	sqlAuth.append(" autorizzazioni.note_sistema as noteSistema,");
	sqlAuth.append(" autorizzazioni.note as note,");
	sqlAuth.append(" autorizzazioni.autorig_numero as autorignumero,");
	sqlAuth.append(" autorizzazioni.autorig_data as autorigdata,");
	sqlAuth.append(" autorizzazioni.data_cessazione as dataCessazione,");
	sqlAuth.append(" autorizzazioni.data_anzianita as autDataAnzianita,");
	sqlAuth.append(" vw_entilocali.comune as autorizcomune,");
	sqlAuth.append(" comuniorig.comune as autorigcomune,");
	sqlAuth.append(" occupante.nominativo as nominativo,");
	sqlAuth.append(" occupante.nome as nome,");
	sqlAuth.append(" occupante.codicefiscale as codicefiscale,");
	sqlAuth.append(" occupante.partitaiva as partitaiva ,");
	sqlAuth.append(" occupante.indirizzo as indirizzo,");
	sqlAuth.append(" occupante.citta as citta,");
	sqlAuth.append(" occupante.cap as cap,");
	sqlAuth.append(" occupante.provincia as provincia,");
	sqlAuth.append(" occupante.numiscrrea as numiscrrea,");
	sqlAuth.append(" occupante.dataiscrrea as dataiscrrea,");
	sqlAuth.append(" occupante.provinciarea as provinciarea,");
	sqlAuth.append(" occupante.email as email,");
	sqlAuth.append(" occupante.telefono as telefono,");
	sqlAuth.append(" occupante.data_inizio_attivita as dataInizioAttivita,");
	sqlAuth.append(" occupante.dataregditte as dataregditte,");
	sqlAuth.append(" attivita.codiceistat as codicefiltracategoria, attivita.istat as descrizionefiltracategoria,");
	sqlAuth.append(" coalesce((select sum(presenze.numeropresenze) from ");
	sqlAuth.append("  mercatipresenze_d presenze ").append(FORCE_INDEX);
	sqlAuth.append("  INNER JOIN mercatipresenze_t presenze_testata ON presenze_testata.idcomune = presenze.idcomune  ");
	sqlAuth.append("  AND presenze_testata.id = presenze.fkidtestata ");
	sqlAuth.append("  AND presenze_testata.fkcodicemercato =? ");
	sqlAuth.append("  AND presenze_testata.fkidmercatiuso =?  ");
	sqlAuth.append("  AND presenze_testata.DATAREGISTRAZIONE <= ? ");
	sqlAuth.append("  where presenze.idcomune = autorizzazioni.idcomune ");
	sqlAuth.append("  AND presenze.fk_autorizzazioni_id = autorizzazioni.id ");
	sqlAuth.append("  ),0)  + ");
	sqlAuth.append(" coalesce((select sum(presenzestorico.numeropresenze) from ");
	sqlAuth.append("  mercatipresenze_storico presenzestorico ");
	sqlAuth.append("  where presenzestorico.idcomune = autorizzazioni.idcomune ");
	sqlAuth.append("  AND presenzestorico.fk_autorizzazioni_id = autorizzazioni.id ");
	sqlAuth.append("  AND presenzestorico.fkcodicemercato = ? ");
	sqlAuth.append("  AND presenzestorico.fkidmercatiuso = ? ");
	sqlAuth.append("  AND presenzestorico.data <= ? ");
	sqlAuth.append("  ),0) as numeropresenze, ");
	sqlAuth.append(" mercatipresenze_d.flag_pagato as pagamentoEffettuato, ");
	sqlAuth.append(" mercatipresenze_d.numeropresenze as numeropresenzeoggi, ");
	sqlAuth.append(" posteggio_rinunciato.idposteggio as idposteggiorinunciato, ");
	sqlAuth.append(" posteggio_rinunciato.codiceposteggio as posteggiorinunciato, ");
	sqlAuth.append(" mercatipresenze_d.flag_rinuncia_presenza as presenzaRinunciata, ");
	sqlAuth.append(" autorizzazionicsi.id  as idautorizzazionicsi,");
	sqlAuth.append(" autorizzazionicsi.stato_autorizzazione  as statoAutorizzazione,");
	sqlAuth.append(" autorizzazionicsi.stato_warning as statowarning,");
	sqlAuth.append(" autorizzazionicsi.data_sosp_da  as  dataSospDa,");
	sqlAuth.append(" autorizzazionicsi.data_sosp_a  as dataSospA,");
	sqlAuth.append(" autorizzazionicsi.data_fine_gerenza  as dataFineGerenza,");
	sqlAuth.append(" autorizzazionicsi.causale_sospensione  as  causaleSospensione,");
	sqlAuth.append(" autorizzazionicsi.valida_spunta  as validaSpunta,");
	sqlAuth.append(" autorizzazionicsi.data_inizio_gerenza  as dataInizioGerenza, ");
	sqlAuth.append(" autorizzazionicsi.aut_precedente_numero  as autPrecedenteNumero, ");
	sqlAuth.append(" autorizzazionicsi.aut_precedente_data  as autPrecedenteData, ");
	sqlAuth.append(" comuniprec.comune  as autPrecComune, ");
	sqlAuth.append(" gerente.codiceanagrafe as gerentecodice, ");
	sqlAuth.append(" gerente.nominativo as gerenteNominativo, ");
	sqlAuth.append(" gerente.nome as gerenteNome, ");
	sqlAuth.append(" gerente.partitaiva as gerentePartitaiva, ");
	sqlAuth.append(" gerente.codicefiscale as gerenteCodicefiscale, ");
	sqlAuth.append(" gerente.email as gerenteEmail, ");
	sqlAuth.append(" gerenteFormagiuridica.formagiuridica as gerenteFormagiuridica, ");
	sqlAuth.append(" coadiuvante.codiceanagrafe as coadiuvantecodice, ");
	sqlAuth.append(" coadiuvante.nominativo as coadiuvanteNominativo, ");
	sqlAuth.append(" coadiuvante.nome as coadiuvanteNome, ");
	sqlAuth.append(" coadiuvante.partitaiva as coadiuvantePartitaiva, ");
	sqlAuth.append(" coadiuvante.codicefiscale as coadiuvanteCodicefiscale, ");
	sqlAuth.append(" coadiuvante.email as coadiuvanteEmail, ");
	sqlAuth.append(" gerenteFormagiuridica.formagiuridica as coadiuvanteFormagiuridica, ");
	sqlAuth.append(" coalesce(autorizzazionicsi.tipologia_battitori,'N') as tipologiaBattitori, ");
	sqlAuth.append(" mercatipresenze_d.fkidposteggio as posteggioOccupato, ");
	sqlAuth.append(" autorizzazioni.flag_attiva as flagAttiva, ");
	sqlAuth.append(" mercatipresenze_d.fk_pay_pos_deb as posdebspunt, ");
	// CAMPI TITOLARE
	sqlAuth.append(" autorizzazioni.fk_codiceanagrafe as codiceTitolare,");
	sqlAuth.append(" anagrafe.nominativo as titNominativo,");
	sqlAuth.append(" anagrafe.nome as titNome,");
	sqlAuth.append(" anagrafe.codicefiscale as titCodicefiscale,");
	sqlAuth.append(" anagrafe.partitaiva as titPartitaiva ,");
	sqlAuth.append(" anagrafe.indirizzo as titIndirizzo,");
	sqlAuth.append(" anagrafe.citta as titCitta,");
	sqlAuth.append(" anagrafe.cap as titCap,");
	sqlAuth.append(" anagrafe.provincia as titProvincia,");
	sqlAuth.append(" anagrafe.numiscrrea as titNumiscrrea,");
	sqlAuth.append(" anagrafe.dataiscrrea as titDataiscrrea,");
	sqlAuth.append(" anagrafe.provinciarea as titProvinciarea,");
	sqlAuth.append(" anagrafe.email as titEmail,");
	sqlAuth.append(" anagrafe.telefono as titTelefono,");
	sqlAuth.append(" anagrafe.data_inizio_attivita as titDataInizioAttivita, ");
	sqlAuth.append(" anagrafe.dataregditte as titDataregditte ");
	// END CAMPI TITOLARE
	sqlAuth.append(" FROM ");
	sqlAuth.append(" #SCHEMA_NAME#autorizzazioni ");
	// solo autorizzazioni con registro manifestazioni
	sqlAuth.append(" inner JOIN #SCHEMA_NAME#tipologiaregistri ");
	sqlAuth.append(" ON tipologiaregistri.idcomune = autorizzazioni.idcomune ");
	sqlAuth.append(" AND tipologiaregistri.tr_id = autorizzazioni.fkidregistro ");
	sqlAuth.append(" AND tipologiaregistri.flag_manifestazioni=? ");
	// END solo autorizzazioni con registro manifestazioni
	sqlAuth.append(" left JOIN #SCHEMA_NAME#mercatipresenze_d ").append(FORCE_INDEX);
	sqlAuth.append(" ON mercatipresenze_d.idcomune = autorizzazioni.idcomune ");
	sqlAuth.append(" AND mercatipresenze_d.fk_autorizzazioni_id = autorizzazioni.id");
	sqlAuth.append(" AND mercatipresenze_d.fkidtestata = ?");
	sqlAuth.append(
		" left join #SCHEMA_NAME#mercati_d posteggio_rinunciato on mercatipresenze_d.idcomune = posteggio_rinunciato.idcomune and mercatipresenze_d.fkidposteggio_rinunciato = posteggio_rinunciato.idposteggio ");
	sqlAuth.append(
		" left join #SCHEMA_NAME#attivita on mercatipresenze_d.idcomune=attivita.idcomune and mercatipresenze_d.fk_codiceistat=attivita.codiceistat  ");
	sqlAuth.append(" INNER JOIN #SCHEMA_NAME#vw_entilocali ON vw_entilocali.codicecomune = autorizzazioni.autorizcomune");
	sqlAuth.append(" left JOIN #SCHEMA_NAME#vw_entilocali comuniorig ON comuniorig.codicecomune = autorizzazioni.autorig_codcomune");
	sqlAuth.append(" INNER JOIN #SCHEMA_NAME#anagrafe ON autorizzazioni.idcomune = anagrafe.idcomune");
	sqlAuth.append(" AND autorizzazioni.fk_codiceanagrafe = anagrafe.codiceanagrafe");
	sqlAuth.append(" LEFT JOIN #SCHEMA_NAME#anagrafe occupante ON autorizzazioni.idcomune = occupante.idcomune");
	sqlAuth.append(" AND autorizzazioni.codiceoccupante = occupante.codiceanagrafe");
	sqlAuth.append("  left join #SCHEMA_NAME#AUTORIZZAZIONI_CSI autorizzazionicsi on ");
	sqlAuth.append("  autorizzazionicsi.fk_autorizzazioni_id = autorizzazioni.id and autorizzazionicsi.idcomune = autorizzazioni.idcomune ");
	sqlAuth.append("  left join #SCHEMA_NAME#ANAGRAFE gerente on ");
	sqlAuth.append("  gerente.codiceanagrafe = autorizzazionicsi.fk_codicegerente and gerente.idcomune = autorizzazionicsi.idcomune ");
	sqlAuth.append("  left JOIN #SCHEMA_NAME#vw_entilocali comuniprec ON comuniprec.codicecomune = autorizzazionicsi.aut_precedente_comune ");
	sqlAuth.append("  left join #SCHEMA_NAME#FORMEGIURIDICHE gerenteFormagiuridica on ");
	sqlAuth.append(
		"  gerenteFormagiuridica.codiceformagiuridica = gerente.formagiuridica and gerenteFormagiuridica.idcomune = gerente.idcomune ");
	sqlAuth.append("  left join #SCHEMA_NAME#ANAGRAFE coadiuvante on ");
	sqlAuth.append(
		"  coadiuvante.codiceanagrafe = mercatipresenze_d.fk_codicecollaboratore and coadiuvante.idcomune = mercatipresenze_d.idcomune ");
	sqlAuth.append("  left join #SCHEMA_NAME#FORMEGIURIDICHE coadiuvanteFormagiuridica on ");
	sqlAuth.append(
		"  coadiuvanteFormagiuridica.codiceformagiuridica = coadiuvante.formagiuridica and coadiuvanteFormagiuridica.idcomune = coadiuvante.idcomune ");
	sqlAuth.append(" WHERE");
	sqlAuth.append(" autorizzazioni.idcomune = ? ");
	sqlAuth.append(" AUTORIZZAZIONI_ATTIVE_CLAUSE ");
	sqlAuth.append(" AND ( AUTORIZZAZIONI_ID_IN_CLAUSE ) ");
	return sqlAuth.toString();
    }

    private void addScalar(SQLQuery q) {

	q.addScalar("idAutorizzazione", Hibernate.INTEGER);
	q.addScalar("autoriznumero", Hibernate.STRING);
	q.addScalar("autorizdata", Hibernate.DATE);
	q.addScalar("codiceOccupante", Hibernate.INTEGER);
	q.addScalar("noteSistema", Hibernate.STRING);
	q.addScalar("note", Hibernate.STRING);
	q.addScalar("autorignumero", Hibernate.STRING);
	q.addScalar("autorigdata", Hibernate.DATE);
	q.addScalar("dataCessazione", Hibernate.DATE);
	q.addScalar("autorigcomune", Hibernate.STRING);
	q.addScalar("autorizcomune", Hibernate.STRING);
	q.addScalar("nominativo", Hibernate.STRING);
	q.addScalar("nome", Hibernate.STRING);
	q.addScalar("codicefiscale", Hibernate.STRING);
	q.addScalar("partitaiva", Hibernate.STRING);
	q.addScalar("indirizzo", Hibernate.STRING);
	q.addScalar("citta", Hibernate.STRING);
	q.addScalar("cap", Hibernate.STRING);
	q.addScalar("provincia", Hibernate.STRING);
	q.addScalar("numiscrrea", Hibernate.STRING);
	q.addScalar("dataiscrrea", Hibernate.DATE);
	q.addScalar("provinciarea", Hibernate.STRING);
	q.addScalar("telefono", Hibernate.STRING);
	q.addScalar("email", Hibernate.STRING);
	q.addScalar("dataInizioAttivita", Hibernate.DATE);
	q.addScalar("codicefiltracategoria", Hibernate.STRING);
	q.addScalar("descrizionefiltracategoria", Hibernate.STRING);
	q.addScalar("numeropresenze", Hibernate.INTEGER);
	q.addScalar("pagamentoEffettuato", Hibernate.BOOLEAN);
	q.addScalar("numeropresenzeoggi", Hibernate.INTEGER);
	q.addScalar("idposteggiorinunciato", Hibernate.INTEGER);
	q.addScalar("posteggiorinunciato", Hibernate.STRING);
	q.addScalar("presenzaRinunciata", Hibernate.BOOLEAN);
	//Auto csi
	q.addScalar("idautorizzazionicsi", Hibernate.INTEGER);
	q.addScalar("statoAutorizzazione", Hibernate.STRING);
	q.addScalar("statowarning", Hibernate.STRING);
	q.addScalar("dataSospDa", Hibernate.DATE);
	q.addScalar("dataSospA", Hibernate.DATE);
	q.addScalar("dataFineGerenza", Hibernate.DATE);
	q.addScalar("causaleSospensione", Hibernate.STRING);
	q.addScalar("validaSpunta", Hibernate.BOOLEAN);
	q.addScalar("dataInizioGerenza", Hibernate.DATE);
	q.addScalar("autPrecedenteNumero", Hibernate.STRING);
	q.addScalar("autPrecedenteData", Hibernate.DATE);
	q.addScalar("autPrecComune", Hibernate.STRING);
	// aut_csi gerente
	q.addScalar("gerenteNominativo", Hibernate.STRING);
	q.addScalar("gerenteNome", Hibernate.STRING);
	q.addScalar("gerentePartitaiva", Hibernate.STRING);
	q.addScalar("gerenteCodicefiscale", Hibernate.STRING);
	q.addScalar("gerenteEmail", Hibernate.STRING);
	q.addScalar("gerenteFormagiuridica", Hibernate.STRING);
	// coadiuvante
	q.addScalar("coadiuvanteNominativo", Hibernate.STRING);
	q.addScalar("coadiuvanteNome", Hibernate.STRING);
	q.addScalar("coadiuvantePartitaiva", Hibernate.STRING);
	q.addScalar("coadiuvanteCodicefiscale", Hibernate.STRING);
	q.addScalar("coadiuvanteEmail", Hibernate.STRING);
	q.addScalar("coadiuvanteFormagiuridica", Hibernate.STRING);
	// battitori
	q.addScalar("tipologiaBattitori", Hibernate.STRING);
	q.addScalar("posteggioOccupato", Hibernate.INTEGER);
	//
	q.addScalar("gerentecodice", Hibernate.INTEGER);
	q.addScalar("coadiuvantecodice", Hibernate.INTEGER);
	q.addScalar("flagAttiva", Hibernate.BOOLEAN);
	q.addScalar("posdebspunt", Hibernate.INTEGER);
	q.addScalar("codiceTitolare", Hibernate.INTEGER);
	q.addScalar("titNominativo", Hibernate.STRING);
	q.addScalar("titNome", Hibernate.STRING);
	q.addScalar("titCodicefiscale", Hibernate.STRING);
	q.addScalar("titPartitaiva", Hibernate.STRING);
	q.addScalar("titIndirizzo", Hibernate.STRING);
	q.addScalar("titCitta", Hibernate.STRING);
	q.addScalar("titCap", Hibernate.STRING);
	q.addScalar("titProvincia", Hibernate.STRING);
	q.addScalar("titNumiscrrea", Hibernate.STRING);
	q.addScalar("titDataiscrrea", Hibernate.DATE);
	q.addScalar("titProvinciarea", Hibernate.STRING);
	q.addScalar("titEmail", Hibernate.STRING);
	q.addScalar("titTelefono", Hibernate.STRING);
	q.addScalar("titDataInizioAttivita", Hibernate.DATE);
	q.addScalar("titDataregditte", Hibernate.DATE);
	q.addScalar("autDataAnzianita", Hibernate.DATE);
	q.addScalar("dataregditte", Hibernate.DATE);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AutorizzazioniRestHelper> findAutorizzazioniAnagrafiche(Set<Integer> codiciAnagrafe,
	    boolean consideraAncheIlProprietarioTraLeAnagrafiche, RuoloAutorizzazioneEnum ruolo, boolean soloAutorizzazioniAttive) {

	//
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	//
	String qMarks = StringUtils.repeat("?,", codiciAnagrafe.size());
	qMarks = qMarks.substring(0, (qMarks.length() - 1));
	String inClause = "";
	String titolare = "";
	if (ruolo != null) {
	    if (ruolo.equals(RuoloAutorizzazioneEnum.TitolareEOccupante)) {
		inClause = " ( (occupante.codiceanagrafe in (" + qMarks + ")) or ( gerente.codiceanagrafe in(" + qMarks + ")) TITOLARE_IN_CLAUSE ) ";
		titolare = " "; // sulle ricerche es FRONT vanno recuperate anche le autorizzazioni percui le anagrafiche sono titolari
	    } else if (ruolo.equals(RuoloAutorizzazioneEnum.DataInAffitto)) {
		inClause = " ( anagrafe.codiceanagrafe in(" + qMarks //
			   + ") and not ( occupante.codiceanagrafe in (" + qMarks + //
			   ") or  gerente.codiceanagrafe in(" + qMarks + //
			   ")) TITOLARE_IN_CLAUSE ) ";
		titolare = " "; // sulle ricerche es FRONT vanno recuperate anche le autorizzazioni percui le anagrafiche sono titolari
	    } else if (ruolo.equals(RuoloAutorizzazioneEnum.PresaInAffitto)) {
		throw new NotImplementedException(
			"[findAutorizzazioniAnagrafiche]: Non implementata la ricerca per " + RuoloAutorizzazioneEnum.PresaInAffitto);
	    }
	    // sui pagamenti no inquanto pagano l'occupante o il gerente
	} else {
	    inClause = " ( (occupante.codiceanagrafe in (" + qMarks + ")) or ( gerente.codiceanagrafe in(" + qMarks + ")) TITOLARE_IN_CLAUSE ) ";
	    titolare = " "; // sulle ricerche es FRONT vanno recuperate anche le autorizzazioni percui le anagrafiche sono titolari
	    // sui pagamenti no inquanto pagano l'occupante o il gerente
	    if (consideraAncheIlProprietarioTraLeAnagrafiche) {
		titolare = " or ( anagrafe.codiceanagrafe in(" + qMarks + ")) ";
	    }
	}
	inClause = inClause.replace("TITOLARE_IN_CLAUSE", titolare);
	//
	String sql = getSQL(sfi).replaceAll("#SCHEMA_NAME#", schemaName + ".").replace("AUTORIZZAZIONI_ID_IN_CLAUSE", inClause);
	if (soloAutorizzazioniAttive) {
	    sql = sql.replace("AUTORIZZAZIONI_ATTIVE_CLAUSE", " and autorizzazioni.flag_attiva = ? ");
	} else {
	    sql = sql.replace("AUTORIZZAZIONI_ATTIVE_CLAUSE", " ");
	}
	SQLQuery q = getSession().createSQLQuery(sql);
	//.addEntity("autorizzazioni", Autorizzazioni.class).addEntity("comuni", Comuni.class).addEntity("anagrafe", Anagrafe.class).addEntity("attivita", Attivita.class)
	q.setInteger(0, -1);
	q.setInteger(1, -1);
	//nuova logica di recupero della data registrazione 
	Date c = this.settaDataRegistrazione();
	q.setDate(2, c);
	q.setInteger(3, -1);
	q.setInteger(4, -1);
	q.setDate(5, c);
	q.setInteger(6, 1); // flag_manifestazioni
	q.setInteger(7, -1);
	q.setString(8, ORMHelper.getIdcomune());
	//////////////////////////
	int pos = 9;
	if (soloAutorizzazioniAttive) {
	    q.setInteger(pos++, 1);
	} else {
	    // non faccio niente
	}
	// q.setInteger(pos++, 1); // SOLO LE ATTIVE
	if (ruolo != null) {
	    if (ruolo.equals(RuoloAutorizzazioneEnum.TitolareEOccupante)) {
		//		inClause = " ( (occupante.codiceanagrafe in (" + qMarks + ")) or ( gerente.codiceanagrafe in(" + qMarks + ")) TITOLARE_IN_CLAUSE ) ";
		//		titolare = " "; // sulle ricerche es FRONT vanno recuperate anche le autorizzazioni percui le anagrafiche sono titolari
		for (Integer ca : codiciAnagrafe) {
		    q.setInteger(pos++, ca);
		}
		for (Integer ca : codiciAnagrafe) {
		    q.setInteger(pos++, ca);
		}
	    } else if (ruolo.equals(RuoloAutorizzazioneEnum.DataInAffitto)) {
		//		inClause = " ( anagrafe.codiceanagrafe in(" + qMarks //		
		//			   + ") and not ( occupante.codiceanagrafe in (" + qMarks + //
		//			   ") or  gerente.codiceanagrafe in(" + qMarks + //
		//			   ")) TITOLARE_IN_CLAUSE ) ";
		//		titolare = " "; // sulle ricerche es FRONT vanno recuperate anche le autorizzazioni percui le anagrafiche sono titolari
		for (Integer ca : codiciAnagrafe) {
		    q.setInteger(pos++, ca);
		}
		for (Integer ca : codiciAnagrafe) {
		    q.setInteger(pos++, ca);
		}
		for (Integer ca : codiciAnagrafe) {
		    q.setInteger(pos++, ca);
		}
	    } else if (ruolo.equals(RuoloAutorizzazioneEnum.PresaInAffitto)) {
		throw new NotImplementedException(
			"[findAutorizzazioniAnagrafiche]: Non implementata la ricerca per " + RuoloAutorizzazioneEnum.PresaInAffitto);
	    }
	    // sui pagamenti no inquanto pagano l'occupante o il gerente
	} else {
	    for (Integer ca : codiciAnagrafe) {
		q.setInteger(pos++, ca);
	    }
	    for (Integer ca : codiciAnagrafe) {
		q.setInteger(pos++, ca);
	    }
	    if (consideraAncheIlProprietarioTraLeAnagrafiche) {
		for (Integer ca : codiciAnagrafe) {
		    q.setInteger(pos++, ca);
		}
	    }
	}
	/////////////////////////
	q.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(AutorizzazioniRestHelper.class));
	addScalar(q);
	return q.list();
    }

    @Override
    public AutorizzazioniRestHelper findAutorizzazioneRestHelper(Integer idAutorizzazione) {

	//
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	//
	String inClause = " ( autorizzazioni.id = ? ) ";
	//
	String sql = getSQL(sfi).replaceAll("#SCHEMA_NAME#", schemaName + ".").replace("AUTORIZZAZIONI_ID_IN_CLAUSE", inClause)
		.replace("AUTORIZZAZIONI_ATTIVE_CLAUSE", "");
	SQLQuery q = getSession().createSQLQuery(sql);
	//.addEntity("autorizzazioni", Autorizzazioni.class).addEntity("comuni", Comuni.class).addEntity("anagrafe", Anagrafe.class).addEntity("attivita", Attivita.class)
	q.setInteger(0, -1);
	q.setInteger(1, -1);
	//nuova logica di recupero della data registrazione 
	Date c = this.settaDataRegistrazione();
	q.setDate(2, c);
	q.setInteger(3, -1);
	q.setInteger(4, -1);
	q.setDate(5, c);
	q.setInteger(6, 1); // flag_manifestazioni
	q.setInteger(7, -1);
	q.setString(8, ORMHelper.getIdcomune());
	//////////////////////////
	int pos = 9;
	// q.setInteger(pos++, 1); // SOLO LE ATTIVE
	q.setInteger(pos++, idAutorizzazione);
	/////////////////////////
	q.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(AutorizzazioniRestHelper.class));
	addScalar(q);
	@SuppressWarnings("unchecked")
	List<AutorizzazioniRestHelper> result = q.list();
	if (result.size() != 1) {
	    throw new RuntimeException("Dati non corretti per l'autorizzazione " + idAutorizzazione);
	}
	return result.get(0);
    }

    private int pageSize = 200;

    @Override
    public void exportModalitaPentaho(AutorizzazioniExportHelper autorizzazioniExportHelper, Esportazioni esportazioni, Date _data, String email,
	    String contestoExport, boolean isInvioMail) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	int count = this.countByFilter(autorizzazioniExportHelper.getAutorizzazioniFilter());
	log.debug("exportModalitaPentaho# Numero record da esportare: {}", count);
	int pageNumber = 0;
	int startRow = 0;
	int rowEnd;
	if (count > 0) {
	    pageNumber = count / pageSize;
	    if (count % pageSize > 0) {
		pageNumber++;
	    }
	    for (int i = 0; i < pageNumber; i++) {
		startRow = i * pageSize;
		rowEnd = pageSize;
		List<AutorizzazioniHelperList> idAutorizzazioni = this.findNumeroAutByFilter(autorizzazioniExportHelper.getAutorizzazioniFilter(),
			startRow, rowEnd);
		//List<Autorizzazioni> list = this.findByAutorizzazioniFilter(autorizzazioniExportHelper.getAutorizzazioniFilter(), startRow, rowEnd);
		for (AutorizzazioniHelperList autorizzazioni : idAutorizzazioni) {
		    String schema = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
		    String sql = "INSERT INTO " + schema + ".tmp_esportazioni (IDCOMUNE, SESSIONID, CODICE, CODICECOMUNE, DATA) VALUES (?,?,?,?,?)";
		    SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		    sqlQuery = sqlQuery.addScalar("IDCOMUNE", Hibernate.STRING).addScalar("SESSIONID", Hibernate.STRING)
			    .addScalar("CODICE", Hibernate.INTEGER).addScalar("CODICECOMUNE", Hibernate.STRING).addScalar("DATA", Hibernate.DATE);
		    sqlQuery.setString(0, autorizzazioni.getIdComune());
		    sqlQuery.setString(1, ORMHelper.getToken());
		    sqlQuery.setInteger(2, new Integer(autorizzazioni.getId()));
		    String codiceComune = StringUtils.defaultIfEmpty(autorizzazioni.getComune(), null);
		    sqlQuery.setString(3, codiceComune);
		    sqlQuery.setDate(4, new Date());
		    sqlQuery.executeUpdate();
		}
	    }
	    log.debug("exportModalitaPentaho# Fine inserimento record nella tabella tmp_esportazioni ");
	}
    }

    private List<AutorizzazioniHelperList> findNumeroAutByFilter(AutorizzazioniFilter filter, Integer firstResult, Integer maxResult) {

	DetachedCriteria criteria = createCriteriaFilter(filter);
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "ID");
	plist.add(Projections.property("autorizcomune.codicecomune"), "COMUNE");
	plist.add(Projections.property("id.idcomune"), "IDCOMUNE");
	criteria.setProjection(plist);
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(AutorizzazioniHelperList.class));
	@SuppressWarnings("unchecked")
	List<AutorizzazioniHelperList> list = getHibernateTemplate().findByCriteria(criteria, firstResult, maxResult);
	return list;
    }

    @Override
    public void cessaAutorizzazione(int idAutorizzazione, Date dataCessazione, int idCausaleCessazione) {

	if (dataCessazione == null) {
	    throw new IllegalArgumentException("Impossibile richiamare cessaAutorizzazione senza passare la data di cessazione");
	}
	String sql = "update " + "  autorizzazioni " + "set " + "  flag_attiva = ?, " + "  data_cessazione = ?, " + "  fk_causale_cessazione = ? " +
		     "where " + "  idcomune = ? and " + "  id = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Autorizzazioni.class);
	query.setInteger(0, 0);
	query.setDate(1, dataCessazione);
	query.setInteger(2, idCausaleCessazione);
	query.setString(3, ORMHelper.getIdcomune());
	query.setInteger(4, idAutorizzazione);
	query.executeUpdate();
    }

    @Override
    public void cambiaBloccoAutorizzazione(Integer codiceAutorizzazione, boolean bloccata) {

	if (codiceAutorizzazione == null) {
	    throw new IllegalArgumentException("Impossibile richiamare cambiaBloccoAutorizzazione senza passare l'id dell'autorizzazione");
	}
	String sql = "update autorizzazioni set modificabloccata = ? where idcomune = ? and id = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Autorizzazioni.class);
	query.setInteger(0, bloccata ? 1 : 0);
	query.setString(1, ORMHelper.getIdcomune());
	query.setInteger(2, codiceAutorizzazione);
	query.executeUpdate();
    }

    @Override
    public void aggiornaEstremiAutorizzazioni(Integer codiceAutorizzazione, String numero, Date data) {

	if (StringUtils.isBlank(numero)) {
	    throw new IllegalArgumentException("Impossibile richiamare aggiornaEstremiAutorizzazioni senza passare il numero dell'autorizzazione");
	}
	if (data == null) {
	    throw new IllegalArgumentException("Impossibile richiamare aggiornaEstremiAutorizzazioni senza passare la data dell'autorizzazione");
	}
	String sql = "update autorizzazioni set autoriznumero = ?, autorizdata = ? where idcomune = ? and id = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Autorizzazioni.class);
	query.setString(0, numero);
	query.setDate(1, data);
	query.setString(2, ORMHelper.getIdcomune());
	query.setInteger(3, codiceAutorizzazione);
	query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findAutorizzazioniScaduteAllaDataENonCessate(Date oggi) {

	String sql = "select autorizzazioni.id from autorizzazioni inner join tipologiaregistri on tipologiaregistri.idcomune=autorizzazioni.idcomune and tipologiaregistri.tr_id=autorizzazioni.fkidregistro where autorizzazioni.idcomune = :idcomune and autorizzazioni.flag_attiva = :flagattiva and " +
		     coalesceData("autorizzazioni.datascadenza") + " < :oggi and tipologiaregistri.software=:software";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Autorizzazioni.class);
	query.setString("idcomune", ORMHelper.getIdcomune());
	query.setInteger("flagattiva", 1);
	query.setDate("oggi", Utilities.impostaOrarioAData(oggi, 0, 0, 0, Calendar.AM));
	query.setString("software", ORMHelper.getSoftware());
	query.addScalar("id", Hibernate.INTEGER);
	return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findAutorizzazioniCessateAllaDataEAncoraAttive(Date oggi) {

	String sql = "select autorizzazioni.id from autorizzazioni inner join tipologiaregistri on tipologiaregistri.idcomune=autorizzazioni.idcomune and tipologiaregistri.tr_id=autorizzazioni.fkidregistro where autorizzazioni.idcomune = :idcomune and autorizzazioni.flag_attiva = :flagattiva and " +
		     coalesceData("autorizzazioni.data_cessazione") + " < :oggi  and tipologiaregistri.software=:software";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Autorizzazioni.class);
	query.setString("idcomune", ORMHelper.getIdcomune());
	query.setInteger("flagattiva", 1);
	query.setDate("oggi", Utilities.impostaOrarioAData(oggi, 0, 0, 0, Calendar.AM));
	query.setString("software", ORMHelper.getSoftware());
	query.addScalar("id", Hibernate.INTEGER);
	return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findAutorizzazioniConAffittoScadutoENonRientrateInPossesso(Date oggi) {

	String sql = "select autorizzazioni.id from autorizzazioni inner join tipologiaregistri on tipologiaregistri.idcomune=autorizzazioni.idcomune and tipologiaregistri.tr_id=autorizzazioni.fkidregistro  where autorizzazioni.idcomune = :idcomune and autorizzazioni.flag_attiva = :flagattiva and " +
		     coalesceData("autorizzazioni.data_fine_Affitto") + " < :oggi  and tipologiaregistri.software=:software ";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Autorizzazioni.class);
	query.setString("idcomune", ORMHelper.getIdcomune());
	query.setInteger("flagattiva", 1);
	query.setDate("oggi", Utilities.impostaOrarioAData(oggi, 0, 0, 0, Calendar.AM));
	query.setString("software", ORMHelper.getSoftware());
	query.addScalar("id", Hibernate.INTEGER);
	return query.list();
    }

    private String coalesceData(String colonna) {

	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	DialettoEnum dialetto = DialettoEnum.fromHibernateDialect(sfi.getDialect().toString());
	if (DialettoEnum.MYSQL.equals(dialetto)) {
	    return " COALESCE(" + colonna + ",STR_TO_DATE('2999-01-01','%Y-%m-%d')) ";
	}
	if (DialettoEnum.ORACLE.equals(dialetto)) {
	    return "COALESCE(" + colonna + ",TO_DATE('2999-01-01','YYYY-MM-DD')) ";
	}
	return colonna;
    }

    private String getDefaultOrdinamento(boolean forGraduatoriaSpuntisti, boolean forRestHelper) {

	String crit = null;
	if (forGraduatoriaSpuntisti) {
	    crit = vertComportamentiMercatiService.criteriOrdinamentoGraduatorieSeAttivaGradSpuntisti();
	    if (StringUtils.isBlank(crit)) {
		crit = " numpresenze DESC, dataregditte ASC, autorizzazioni.data_anzianita ASC ";
	    }
	} else {
	    crit = vertComportamentiMercatiService.criteriOrdinamentoGraduatoriePredefinito();
	    if (StringUtils.isBlank(crit)) {
		crit = " coalesce(nominativogerente,nominativo), coalesce(nomegerente,nome) ";
	    }
	}
	if (forRestHelper) {
	    return " order by " + OrdinamentoPresenzeAnalyzer.parseSQLCriterioForRestHelper(crit);
	}
	return " order by " + OrdinamentoPresenzeAnalyzer.parseSQLCriterio(crit, forGraduatoriaSpuntisti);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AutorizzazioniMercatoSrvBean> findAutorizzazioniMercatoSrvBean(MercatoSrvRequest req) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	AutorizzazioniMercatoSrvBeanQueryHelper qih = new AutorizzazioniMercatoSrvBeanQueryHelper(req, sessimpl);
	String sql = qih.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	qih.setFilterValues(q);
	qih.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(AutorizzazioniMercatoSrvBean.class));
	return (List<AutorizzazioniMercatoSrvBean>) q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AutorizzazioniComposteSpostaPresenzeDTO> findAutorizzazioniComposteSpostaPresenze(Integer codice, String idComune) {

	String registroAutPonte = vertComportamentiMercatiService.registroAutPonte();
	StringBuilder cleanedAutPonte = new StringBuilder();
	if (registroAutPonte != null && !registroAutPonte.trim().isEmpty()) {
	    String[] splits = registroAutPonte.replaceAll("\\s+", "").split(",");
	    for (String split : splits) {
		if (!split.isEmpty()) {
		    if (cleanedAutPonte.length() > 0) {
			cleanedAutPonte.append(",");
		    }
		    cleanedAutPonte.append(split.trim());
		}
	    }
	}
	// IMPORTANTE: impostare PRIMA la parte generale (dove il discriminator è idComune), per far lavorare il DB su
	// un'area più ristretta di dati
	String sql = "SELECT a.ID, " + "    CASE " + "        WHEN ac.FK_IDAUT_COLLEGATA IS NOT NULL " +
		     "            THEN CONCAT(a.AUTORIZNUMERO, CONCAT(' (Aut. Coll. ', CONCAT(ac2.AUTORIZNUMERO, ')'))) " +
		     "        ELSE a.AUTORIZNUMERO " + "    END AS autorizNumeroFull " + "FROM autorizzazioni a " +
		     "    LEFT JOIN autorizzazioni_concessioni ac " + "        ON a.IDCOMUNE = ac.IDCOMUNE " +
		     "        AND a.ID = ac.FK_IDAUT_ATTUALE " + "    LEFT JOIN autorizzazioni ac2 " + "        ON ac.IDCOMUNE = ac2.IDCOMUNE " +
		     "        AND ac.FK_IDAUT_COLLEGATA = ac2.ID " + "WHERE a.IDCOMUNE = :idComune " + "    AND a.FKIDISTANZA = :codIstanza " +
		     "    AND a.FLAG_ATTIVA = :flagAttiva " + "    AND NOT EXISTS ( " + "        SELECT 1 " +
		     "        FROM autorizzazioni_concessioni ac_exclude " + "        WHERE ac_exclude.IDCOMUNE = a.IDCOMUNE " +
		     "        AND ac_exclude.FK_IDAUT_COLLEGATA = a.ID " + "    ) " +
		     (cleanedAutPonte.length() > 0 ? "AND a.FKIDREGISTRO IN (" + cleanedAutPonte + ") " : "") + "ORDER BY a.ID ";
	SQLQuery query = getSession().createSQLQuery(sql);
	query.setString("idComune", idComune);
	query.setInteger("codIstanza", codice);
	query.setInteger("flagAttiva", 1);
	query.addScalar("ID", Hibernate.INTEGER);
	query.addScalar("autorizNumeroFull", Hibernate.STRING);
	List<Object[]> rows = query.list();
	List<AutorizzazioniComposteSpostaPresenzeDTO> result = new ArrayList<AutorizzazioniComposteSpostaPresenzeDTO>();
	for (Object[] row : rows) {
	    AutorizzazioniComposteSpostaPresenzeDTO dto = new AutorizzazioniComposteSpostaPresenzeDTO();
	    dto.setId((Integer) row[0]);
	    dto.setAutorizNumeroFull((String) row[1]);
	    result.add(dto);
	}
	return result;
    }
}
