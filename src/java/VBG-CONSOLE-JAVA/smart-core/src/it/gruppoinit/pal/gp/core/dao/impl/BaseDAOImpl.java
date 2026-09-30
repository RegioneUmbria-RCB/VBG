package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAORestrictionMode;
import it.gruppoinit.pal.gp.core.dao.helper.DynaBeanTransformer;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterOrder;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.exception.EntityValidationException;
import it.gruppoinit.pal.gp.core.service.rules.ServiceValidationRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;

public abstract class BaseDAOImpl<E, F extends Serializable> extends HibernateDaoSupport implements BaseDAO<E, F> {

    private static final Logger log = LoggerFactory.getLogger(BaseDAOImpl.class);

    /**
     * metodo per l'autowire della sessionFactory
     * 
     * @param sessionFactoryWrapper
     */
    @Autowired
    public void setSessionFactoryWrapper(@Qualifier("sessionFactory") SessionFactory sessionFactoryWrapper) {

	this.setSessionFactory(sessionFactoryWrapper);
    }

    @Override
    public void insert(E entity) {

	getHibernateTemplate().merge(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public E findById(F id) {

	if (null == id) {
	    return null;
	}
	return (E) getHibernateTemplate().get(getEntityClass(), id);
    }

    @Override
    public void update(E entity) {

	getHibernateTemplate().merge(entity);
    }

    @Override
    public void insertOrUpdate(E entity, F id, boolean isUpdate) {

	if (findById(id) != null) {
	    if (isUpdate) {
		_validateEntityForInsertOrUpdate(entity);
		update(entity);
	    }
	} else {
	    _validateEntityForInsertOrUpdate(entity);
	    insert(entity);
	}
    }

    private boolean _validateEntityForInsertOrUpdate(E entity) {

	ServiceValidationRules validationRule = (ServiceValidationRules) SigeproBusinessRules.getClassRules(ServiceValidationRules.class);
	boolean doEntityValidation = true;
	if (validationRule != null) {
	    doEntityValidation = validationRule.getCustomRule(ServiceValidationRules.CustomRuleEnum.doEntityValidation.name());
	}
	if (doEntityValidation) {
	    log.warn("validateEntity(): CacheManager is null");
	    ClassValidator<E> validator = new ClassValidator<E>(getEntityClass());
	    InvalidValue[] invalidValues = validator.getInvalidValues(entity);
	    if (invalidValues.length > 0) {
		log.error("entity: [{}] errori di validazione: {}", getEntityClass(), invalidValues);
		String valoriNonValidi = "";
		String entityName = getEntityClass().getSimpleName();
		for (InvalidValue iv : invalidValues) {
		    valoriNonValidi += "<br />" + entityName + "." + iv.getPropertyName() + ": " + iv.getMessage() + ". valore Attuale=["
			    + iv.getValue() + "]";
		}
		throw new EntityValidationException(new ArrayList<InvalidValue>(),
			"[" + getEntityClass() + "] errori di validazione: " + valoriNonValidi, null);
	    }
	}
	return true;
    }

    @Override
    public void delete(E entity) {

	// REDMINE #531
	Object identifier = null;
	try {
	    identifier = EntityUtils.getNestedProperty(entity, "id.codice");
	} catch (Exception e) {
	    // NON FACCIO NIENTE LA PROPRIETA' IDENTIFICATIVO NON E' ID.CODICE
	}
	if (identifier != null) {
	    if (identifier instanceof Integer) {
		Integer idVal = (Integer) identifier;
		if (idVal.intValue() > PkIdGenerator.MAX_HI_VALUE) {
		    if (WebConstants.getCODICI_INSTALLAZIONE_MASTER().contains(ORMHelper.getIdcomune())
			    || WebConstants.getCODICI_INSTALLAZIONE_MASTER().contains(WebConstants.CODICI_INSTALLAZIONE_MASTER_TUTTI_IDCOMUNE)) {
			// vedi siamo nel caso dell'installazione MASTER
			// DEVO AVVISARE L'UTENTE CHE STA CANCELANDO UN INFORMAZIONE DEL MASTER E DEVO
			// ATTIVARE UNA VARIABILE THREADLOCAL? CHE PERMETTE DI FORZARE LA CANCELLAZIONE 
			boolean utenteIntendeCancellare = false;
			if (SigeproBusinessRules.getCancellazioneDatiMaster() != null) {
			    utenteIntendeCancellare = SigeproBusinessRules.getCancellazioneDatiMaster().booleanValue();
			}
			if (!utenteIntendeCancellare) {
			    throw new RuntimeException(
				    "<div style=\"border-style: dotted;padding:10px; width:60%;\">Si sta per cancellare una informazione di una installazione MASTER. Questo dato potrebbe essere già usato da altri enti.<br />"
					    + "Se si intende procedere con la cancellazione cliccare"
					    + " <a style=\"font-weight:bolder;text-decoration: underline;font-size: 1.5em;\" href=\"javascript:void(0)\" onclick=\"setFieldCancellazioneMaster();\">qui</a>"
					    + " e ripetere la cancellazione tramite apposito bottone ELIMINA.</div>");
			}
		    } else {
			SecurityContext context = SecurityContextHolder.getContext();
			Authentication authentication = context.getAuthentication();
			if (authentication != null) {
			    LoggedUser u = (LoggedUser) authentication.getPrincipal();
			    if (!u.isAbilitaCancellazioneMasterSuSlave()) {
				throw new RuntimeException("Non è possibile cancellare il dato in quanto proveniente da una configurazione MASTER");
			    }
			}
		    }
		}
	    }
	}
	getHibernateTemplate().delete(entity);
    }

    @Override
    public void evict(E entity) {

	getHibernateTemplate().evict(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (null != firstResult && null != maxResult) {
	    return (List<E>) getHibernateTemplate().findByCriteria(det, firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<E>) getHibernateTemplate().findByCriteria(det);
	}
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> findAll(Integer firstResult, Integer maxResult, DAOEnum whereClauseMandatoryFields, String orderProperty,
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
	    detachedCriteria = getEmptyCriteriaForClass();
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
	    return (List<E>) getHibernateTemplate().findByCriteria(detachedCriteria, firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<E>) getHibernateTemplate().findByCriteria(detachedCriteria);
	}
    }

    @Override
    public abstract Class<E> getEntityClass();

    /**
     * Crea un oggetto DetachedCriteria per la classe con la proprietà alias impostata a
     * <code>"_" + getClass().getSimpleName()</code>
     * 
     * @return DetachedCriteria
     */
    protected DetachedCriteria getEmptyCriteriaForClass() {

	DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass(), "_" + getClass().getSimpleName());
	return criteria;
    }

    /**
     * Crea un oggetto DetachedCriteria con la proprietà idcomune impostata
     * 
     * @return DetachedCriteria
     */
    protected DetachedCriteria getIdcomuneCriteria() {

	DetachedCriteria criteria = getEmptyCriteriaForClass();
	criteria.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	if (logger.isTraceEnabled()) {
	    logger.trace("getIdcomuneCriteria() return a DetachedCriteria with idcomune: " + ORMHelper.getIdcomune());
	}
	return criteria;
    }

    /**
     * Crea un oggetto DetachedCriteria con la proprietà idcomune impostata
     * 
     * @return DetachedCriteria
     */
    protected DetachedCriteria getIdcomuneCriteria(String idcomune) {

	DetachedCriteria criteria = getEmptyCriteriaForClass();
	criteria.add(Restrictions.eq("id.idcomune", idcomune));
	if (logger.isTraceEnabled()) {
	    logger.trace("getIdcomuneCriteria() return a DetachedCriteria with idcomune: " + ORMHelper.getIdcomune());
	}
	return criteria;
    }

    /**
     * Crea un oggetto DetachedCriteria con la proprietà idcomune impostata
     * 
     * @return DetachedCriteria
     */
    protected DetachedCriteria getIdcomunebaseCriteria() {

	DetachedCriteria criteria = getEmptyCriteriaForClass();
	criteria.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomunebase()));
	if (logger.isTraceEnabled()) {
	    logger.trace("getIdcomunebaseCriteria() return a DetachedCriteria with idcomunebase: " + ORMHelper.getIdcomunebase());
	}
	return criteria;
    }

    /**
     * Crea un oggetto DetachedCriteria con le proprietà idcomune e software impostate
     * 
     * @return DetachedCriteria
     */
    protected DetachedCriteria getIdcomuneAndSoftwareCriteria() {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("software.codice", ORMHelper.getSoftware()));
	if (logger.isTraceEnabled()) {
	    logger.trace("getIdcomuneAndSoftwareCriteria() return a DetachedCriteria with software: " + ORMHelper.getSoftware());
	}
	return criteria;
    }

    /**
     * Crea un oggetto DetachedCriteria con le proprietà idcomune e software impostate
     * 
     * @return DetachedCriteria
     */
    protected DetachedCriteria getIdcomunebaseAndSoftwareCriteria() {

	DetachedCriteria criteria = getIdcomunebaseCriteria();
	criteria.add(Restrictions.eq("software.codice", ORMHelper.getSoftware()));
	if (logger.isTraceEnabled()) {
	    logger.trace("getIdcomunebaseAndSoftwareCriteria() return a DetachedCriteria with software: " + ORMHelper.getSoftware());
	}
	return criteria;
    }

    /**
     * Crea un oggetto DetachedCriteria con le proprietà idcomune e software impostate
     * 
     * @return DetachedCriteria
     */
    protected DetachedCriteria getIdcomuneAndSoftwareCriteria(String idcomune) {

	DetachedCriteria criteria = getIdcomuneCriteria(idcomune);
	criteria.add(Restrictions.eq("software.codice", ORMHelper.getSoftware()));
	if (logger.isTraceEnabled()) {
	    logger.trace("getIdcomunebaseAndSoftwareCriteria() return a DetachedCriteria with software: " + ORMHelper.getSoftware());
	}
	return criteria;
    }

    @Override
    public List<E> findByFilterTable(FilterTable filterTable) {

	return findByFilterTable(filterTable, null, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult) {

	DetachedCriteria crit = getCriteriaForFilter(filterTable, false);
	List<E> list = null;
	if (null != firstResult && null != maxResult) {
	    list = getHibernateTemplate().findByCriteria(crit, firstResult, maxResult);
	} else {
	    list = getHibernateTemplate().findByCriteria(crit);
	}
	return list;
    }

    /**
     * Metodo che restituisce un Criterion per la ricerca di due stringhe splittate in due proprietà differenti.
     * 
     * @param textToFind
     * @param entityProperty1
     * @param entityProperty2
     * @return
     */
    static Criterion getCriterionForSplittableString(String textToFind, String entityProperty1, String entityProperty2) {

	Criterion nominativo = null;
	String[] nomeCognome = textToFind.split(" ");
	for (String criterio : nomeCognome) {
	    criterio = criterio.trim();
	    if (nominativo == null) {
		nominativo = Restrictions.or(Restrictions.ilike(entityProperty1, criterio, MatchMode.ANYWHERE),
			Restrictions.ilike(entityProperty2, criterio, MatchMode.ANYWHERE));
	    } else {
		if (!(criterio.equals("") || criterio.equals("%"))) {
		    nominativo = Restrictions.and(nominativo, Restrictions.or(Restrictions.ilike(entityProperty1, criterio, MatchMode.ANYWHERE),
			    Restrictions.ilike(entityProperty2, criterio, MatchMode.ANYWHERE)));
		}
	    }
	}
	return nominativo;
    }

    /**
     * Metodo che restituisce un Criterion per la ricerca di più valori.<br />
     * Può essere usata per risolvere metodi tipo DAO.findByDescrizioneAndTipologia <br />
     * <code>
     * 	<pre>
     * 	findByDescrizioneAndTipologia
     * 	--> call getCriterionForObjects(entityProperties, valuesToFind, stringMatchModes, restrictionMode)
     * 		-->entityProperties     = {"descrizione","tipologia"}
     * 		-->valuesToFind	        = {new String("testo da cercare"),new Integer(1)}
     * 		-->stringMatchModes     = {MatchMode.ANYWHERE,null}
     * 		-->restrictionMode      = {DAORestrictionMode.AND}
     *  </pre>	
     * </code>
     * 
     * @param entityProperties
     *            la lista delle properties sulle quali fare il criterion
     * @param valuesToFind
     *            la lista dei valori (se in numero minore allora per quelli mancanti non si esegue il match)
     * @param stringMatchModes
     *            per gli oggetti di tipo stringa se va fatto secondo le modalità descritte in {@link MatchMode}
     * @return un oggetto Criterion da usare nelle query HBM
     * @throws RuntimeException
     *             se non sono stati passati correttamente i parametri
     */
    static Criterion getCriterionForObjects(String[] entityProperties, Object[] valuesToFind, MatchMode[] stringMatchModes,
	    DAORestrictionMode restrictionMode) {

	if (entityProperties == null || entityProperties.length == 0) {
	    throw new RuntimeException("Attenzione! nessuna property specificata");
	}
	Object[] _valuesToFind = null;
	if (entityProperties.length > valuesToFind.length) {
	    _valuesToFind = new Object[entityProperties.length];
	    for (int i = 0; i < valuesToFind.length; i++) {
		_valuesToFind[i] = valuesToFind[i];
	    }
	    for (int j = valuesToFind.length; j < entityProperties.length; j++) {
		_valuesToFind[j] = "";
	    }
	}
	if (_valuesToFind == null) {
	    _valuesToFind = valuesToFind;
	}
	if (entityProperties.length > stringMatchModes.length) {
	    throw new RuntimeException("Attenzione! i criteri di match mode sono minori delle properties");
	}
	Criterion resultCriterion = null;
	List<Criterion> criterions = new ArrayList<Criterion>();
	for (int i = 0; i < entityProperties.length; i++) {
	    if (_valuesToFind[i] == null) {
		// se nullo effettuo la ricerca con isnull
		criterions.add(Restrictions.isNull(entityProperties[i]));
	    } else {
		if (_valuesToFind[i] instanceof String) {
		    if (StringUtils.isNotBlank((String) _valuesToFind[i])) {
			// se stringa non vuota
			criterions.add(Restrictions.ilike(entityProperties[i], _valuesToFind[i].toString(), stringMatchModes[i]));
		    }
		} else {
		    // se altro: presumibilmente numerici
		    criterions.add(Restrictions.eq(entityProperties[i], _valuesToFind[i]));
		}
	    }
	}
	if (restrictionMode == null) {
	    restrictionMode = DAORestrictionMode.AND;
	}
	for (Criterion criterion : criterions) {
	    if (resultCriterion == null) {
		resultCriterion = criterion;
	    } else {
		switch (restrictionMode) {
		case AND:
		    resultCriterion = Restrictions.and(resultCriterion, criterion);
		    break;
		case OR:
		    resultCriterion = Restrictions.or(resultCriterion, criterion);
		    break;
		}
	    }
	}
	return resultCriterion;
    }

    /**
     * Crea un oggetto DetachedCriteria a partire da una FilterTable impostata<br/>
     * Il metodo si comporta così:
     * <ol style="font-weight: bold">
     * <li>Recupera i criteri di default (IDCOMUNE e/o SOFTWARE oppure vuoto a seconda di filterTable.getDefaultWhere())
     * </li>
     * <li>Gestisce tutte le restrictions impostate in FilterTable<br/>
     * per ogni restriction
     * <ul>
     * <li>Gestisce i criteri di ogni restriction.getFilterFields()</li>
     * <li>Se il criterio è una collezione di campi allora gestisce come AND o OR a seconda del valore impostato in
     * restriction.getAndOrRestriction()</li>
     * </ul>
     * </li>
     * <li>Applica gli ordinamenti previsti da filterTable.getOrderings()</li>
     * </ol>
     * 
     * @param filterTable
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected DetachedCriteria getCriteriaForFilter(FilterTable filterTable, boolean excludeOrderBy) {

	Map<String, String> associationsCreated = new HashMap<String, String>();
	Map<String, String> inverseAssociationsCreated = new HashMap<String, String>();
	// CREO I CRITERI DI DEFAULT IDCOMUNE E SOFTWARE PER L'ENTITY CLASS
	DetachedCriteria mainCriteria = getDefaultCriteria(filterTable.getDefaultWhere());
	Set<FilterRestriction> restrictions = filterTable.getRestrictions();
	ProjectionList projections = Projections.projectionList();
	// Ciclo le restrizioni impostate
	for (FilterRestriction restriction : restrictions) {
	    // se il criterio è un insieme di AND o di OR
	    Set<FilterField> fields = restriction.getFilterFields();
	    Set<Criterion> criterios = new LinkedHashSet<Criterion>();
	    for (FilterField filterField : fields) {
		String associationPath = filterField.getAssociationPath();
		associationPath = (associationPath == null) ? "" : associationPath;
		if (!filterField.getOperationType().equals(FieldOperationsEnum.EXISTS)
			&& !filterField.getOperationType().equals(FieldOperationsEnum.NOTEXISTS)) {
		    // gestisco il path di associazione creando gli alias e le projections
		    associationPath = getAssociationPath(associationPath, mainCriteria, projections, associationsCreated, inverseAssociationsCreated);
		}
		Criterion criterio = null;
		// se la proprietà è di una entity innestata allora
		String propertyName = associationPath.concat(filterField.getPropertyName());
		switch (filterField.getOperationType()) {
		case BETWEEN:
		    if (filterField.getValori().length == 2) {
			Object[] vals = filterField.getValori();
			Object lo = vals[0];
			Object hi = vals[1];
			criterio = Restrictions.between(propertyName, lo, hi);
		    } else {
			String message = "Attenzione! la clausola " + FieldOperationsEnum.BETWEEN.toString()
				+ " necessita di almeno due valori per effettuare il confronto";
			log.error("getCriteriaForFilter: {}", message);
			throw new RuntimeException(message);
		    }
		    break;
		case CONTAINS:
		    criterio = Restrictions.ilike(propertyName, "%" + filterField.getValoreSingolo() + "%");
		    break;
		case EQ:
		    criterio = Restrictions.eq(propertyName, filterField.getValoreSingolo());
		    break;
		case EQIGNORECASE:
		    if (filterField.getValoreSingolo() != null) {
			criterio = Restrictions.ilike(propertyName, (String) filterField.getValoreSingolo(), MatchMode.EXACT);
		    } else {
			criterio = Restrictions.isNotNull(propertyName);
		    }
		    break;
		case EXISTS:
		    if (filterField.getOtherRestrictions() != null) {
			criterio = gestExistsCondition(filterField, true, true, filterField.getOtherRestrictions());
		    } else {
			criterio = gestExistsCondition(filterField, true, true);
		    }
		    break;
		case EXISTS_LIKE:
		    if (filterField.getOtherRestrictions() != null) {
			criterio = gestExistsCondition(filterField, true, false, filterField.getOtherRestrictions());
		    } else {
			criterio = gestExistsCondition(filterField, true, false);
		    }
		    break;
		case GE:
		    criterio = Restrictions.ge(propertyName, filterField.getValoreSingolo());
		    break;
		case GT:
		    criterio = Restrictions.gt(propertyName, filterField.getValoreSingolo());
		    break;
		case IN:
		    criterio = Restrictions.in(propertyName, filterField.getValori());
		    break;
		case ISEMPTY:
		    criterio = Restrictions.isEmpty(propertyName);
		    break;
		case ISNOTEMPTY:
		    criterio = Restrictions.isNotEmpty(propertyName);
		    break;
		case ISNOTNULL:
		    criterio = Restrictions.isNotNull(propertyName);
		    break;
		case ISNULL:
		    criterio = Restrictions.isNull(propertyName);
		    break;
		case LE:
		    criterio = Restrictions.le(propertyName, filterField.getValoreSingolo());
		    break;
		case LT:
		    criterio = Restrictions.lt(propertyName, filterField.getValoreSingolo());
		    break;
		case NE:
		    criterio = Restrictions.ne(propertyName, filterField.getValoreSingolo());
		    break;
		case NOTEXISTS:
		    if (filterField.getOtherRestrictions() != null) {
			criterio = gestExistsCondition(filterField, false, true, filterField.getOtherRestrictions());
		    } else {
			criterio = gestExistsCondition(filterField, false, true);
		    }
		    break;
		case NOTIN:
		    criterio = Restrictions.not(Restrictions.in(propertyName, filterField.getValori()));
		    break;
		case STARTSWITH:
		    criterio = Restrictions.ilike(propertyName, filterField.getValoreSingolo() + "%");
		    break;
		case NOTSTARTSWITH:
		    criterio = Restrictions.not(Restrictions.ilike(propertyName, filterField.getValoreSingolo() + "%"));
		    break;
		case ENDSWITH:
		    criterio = Restrictions.ilike(propertyName, "%" + filterField.getValoreSingolo());
		    break;
		case NOTENDSWITH:
		    criterio = Restrictions.not(Restrictions.ilike(propertyName, "%" + filterField.getValoreSingolo()));
		    break;
		}
		criterios.add(criterio);
	    }
	    gestisciAndOrRestrictions(mainCriteria, criterios, restriction.getAndOrRestriction());
	}
	if (!excludeOrderBy) {
	    Set<FilterOrder> order = filterTable.getOrderings();
	    for (FilterOrder ordering : order) {
		String associationPath = ordering.getFilterField().getAssociationPath();
		associationPath = (associationPath == null) ? "" : associationPath;
		associationPath = getAssociationPath(associationPath, mainCriteria, projections, associationsCreated, inverseAssociationsCreated);
		String propertyName = associationPath.concat(ordering.getFilterField().getPropertyName());
		switch (ordering.getSort()) {
		case ASC:
		    if (ordering.getOrderFunction() != null) {
			mainCriteria.addOrder(OrderBySqlFormula.asc(propertyName, ordering.getOrderFunction(), ordering.getOrderFunctionParams()));
		    } else {
			mainCriteria.addOrder(Order.asc(propertyName));
		    }
		    break;
		case DESC:
		    if (ordering.getOrderFunction() != null) {
			mainCriteria.addOrder(OrderBySqlFormula.desc(propertyName, ordering.getOrderFunction(), ordering.getOrderFunctionParams()));
		    } else {
			mainCriteria.addOrder(Order.desc(propertyName));
		    }
		    break;
		}
	    }
	}
	mainCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	return mainCriteria;
    }

    @SuppressWarnings("rawtypes")
    private Criterion gestExistsCondition(FilterField filterField, boolean isExists, boolean compareEqual) {

	Random rnd = new Random();
	String aliasTmp = "tmp" + Math.abs(rnd.nextInt(99999)) + "TableAlias";
	DetachedCriteria dc = DetachedCriteria.forClass(filterField.getEntityClass(), aliasTmp);
	String tmpAliasString = "";
	if (filterField.getInverseJoinChain() != null) {
	    String[] joinChain = filterField.getInverseJoinChain();
	    String aliasString = "";
	    String aliasPrefix = "_";
	    String aliasSeparator = ".";
	    for (String joinTable : joinChain) {
		if (StringUtils.isBlank(aliasString)) {
		    aliasString = joinTable;
		    tmpAliasString = aliasPrefix + rnd.nextInt(99999) + aliasString;
		} else {
		    aliasString = tmpAliasString + aliasSeparator + joinTable;
		    tmpAliasString = aliasPrefix + rnd.nextInt(99999) + joinTable;
		}
		dc.createAlias(aliasString, tmpAliasString, Criteria.LEFT_JOIN);
	    }
	    if (log.isDebugEnabled()) {
		log.debug("getAssociationPath: criteria.createAlias({},{}, Criteria.LEFT_JOIN)", aliasString, tmpAliasString);
	    }
	}
	String parentRelation = aliasTmp;
	if (tmpAliasString.equals("")) {
	    tmpAliasString = aliasTmp;
	} else {
	    parentRelation = tmpAliasString;
	}
	dc.add(Property.forName(parentRelation + "." + filterField.getExistsChildEntityId())
		.eqProperty("_" + getClass().getSimpleName() + "." + filterField.getExistsParentEntityId()));
	if (compareEqual) {
	    dc.add(Restrictions.eq(aliasTmp + "." + filterField.getPropertyName(), filterField.getValoreSingolo()));
	} else {
	    dc.add(Restrictions.ilike(aliasTmp + "." + filterField.getPropertyName(), "%" + filterField.getValoreSingolo() + "%"));
	}
	Criterion criterio = null;
	if (isExists) {
	    criterio = Subqueries.exists(dc.setProjection(Projections.property(aliasTmp + "." + filterField.getPropertyName())));
	} else {
	    criterio = Subqueries.notExists(dc.setProjection(Projections.property(aliasTmp + "." + filterField.getPropertyName())));
	}
	return criterio;
    }

    @SuppressWarnings("rawtypes")
    protected Criterion gestExistsCondition(FilterField filterField, boolean isExists, boolean compareEqual, FilterField... otherRestrictions) {

	Random rnd = new Random();
	String aliasTmp = "tmp" + Math.abs(rnd.nextInt(99999)) + "TableAlias";
	DetachedCriteria dc = DetachedCriteria.forClass(filterField.getEntityClass(), aliasTmp);
	String tmpAliasString = "";
	if (filterField.getInverseJoinChain() != null) {
	    String[] joinChain = filterField.getInverseJoinChain();
	    String aliasString = "";
	    String aliasPrefix = "_";
	    String aliasSeparator = ".";
	    for (String joinTable : joinChain) {
		if (StringUtils.isBlank(aliasString)) {
		    aliasString = joinTable;
		    tmpAliasString = aliasPrefix + rnd.nextInt(99999) + aliasString;
		} else {
		    aliasString = tmpAliasString + aliasSeparator + joinTable;
		    tmpAliasString = aliasPrefix + rnd.nextInt(99999) + joinTable;
		}
		dc.createAlias(aliasString, tmpAliasString, Criteria.LEFT_JOIN);
	    }
	}
	String parentRelation = aliasTmp;
	if (tmpAliasString.equals("")) {
	    tmpAliasString = aliasTmp;
	} else {
	    parentRelation = tmpAliasString;
	}
	dc.add(Property.forName(parentRelation + "." + filterField.getExistsChildEntityId())
		.eqProperty("_" + getClass().getSimpleName() + "." + filterField.getExistsParentEntityId()));
	if (compareEqual) {
	    dc.add(Restrictions.eq(aliasTmp + "." + filterField.getPropertyName(), filterField.getValoreSingolo()));
	} else {
	    dc.add(Restrictions.ilike(aliasTmp + "." + filterField.getPropertyName(), "%" + filterField.getValoreSingolo() + "%"));
	}
	if (otherRestrictions != null) {
	    for (FilterField filterField2 : otherRestrictions) {
		Criterion criterio = null;
		String propertyName = filterField2.getPropertyName();
		switch (filterField2.getOperationType()) {
		case BETWEEN:
		    if (filterField2.getValori().length == 2) {
			Object[] vals = filterField2.getValori();
			Object lo = vals[0];
			Object hi = vals[1];
			criterio = Restrictions.between(aliasTmp + "." + propertyName, lo, hi);
		    } else {
			String message = "Attenzione! la clausola " + FieldOperationsEnum.BETWEEN.toString()
				+ " necessita di almeno due valori per effettuare il confronto";
			throw new RuntimeException(message);
		    }
		    break;
		case CONTAINS:
		    criterio = Restrictions.ilike(aliasTmp + "." + propertyName, "%" + filterField2.getValoreSingolo() + "%");
		    break;
		case ENDSWITH:
		    criterio = Restrictions.ilike(aliasTmp + "." + propertyName, "%" + filterField2.getValoreSingolo());
		    break;
		case EQ:
		    criterio = Restrictions.eq(aliasTmp + "." + propertyName, filterField2.getValoreSingolo());
		    break;
		case EQIGNORECASE:
		    criterio = Restrictions.ilike(aliasTmp + "." + propertyName, (String) filterField2.getValoreSingolo(), MatchMode.EXACT);
		    break;
		case GE:
		    criterio = Restrictions.ge(aliasTmp + "." + propertyName, filterField2.getValoreSingolo());
		    break;
		case GT:
		    criterio = Restrictions.gt(aliasTmp + "." + propertyName, filterField2.getValoreSingolo());
		    break;
		case IN:
		    criterio = Restrictions.in(aliasTmp + "." + propertyName, filterField2.getValori());
		    break;
		case ISEMPTY:
		    criterio = Restrictions.isEmpty(aliasTmp + "." + propertyName);
		    break;
		case ISNOTEMPTY:
		    criterio = Restrictions.isNotEmpty(aliasTmp + "." + propertyName);
		    break;
		case ISNOTNULL:
		    criterio = Restrictions.isNotNull(aliasTmp + "." + propertyName);
		    break;
		case ISNULL:
		    criterio = Restrictions.isNull(aliasTmp + "." + propertyName);
		    break;
		case LE:
		    criterio = Restrictions.le(aliasTmp + "." + propertyName, filterField2.getValoreSingolo());
		    break;
		case LT:
		    criterio = Restrictions.lt(aliasTmp + "." + propertyName, filterField2.getValoreSingolo());
		    break;
		case NE:
		    criterio = Restrictions.ne(aliasTmp + "." + propertyName, filterField2.getValoreSingolo());
		    break;
		case NOTIN:
		    criterio = Restrictions.not(Restrictions.in(aliasTmp + "." + propertyName, filterField2.getValori()));
		    break;
		case STARTSWITH:
		    criterio = Restrictions.ilike(aliasTmp + "." + propertyName, filterField2.getValoreSingolo() + "%");
		    break;
		}
		dc.add(criterio);
	    }
	}
	Criterion criterio = null;
	if (isExists) {
	    criterio = Subqueries.exists(dc.setProjection(Projections.property(aliasTmp + "." + filterField.getPropertyName())));
	} else {
	    criterio = Subqueries.notExists(dc.setProjection(Projections.property(aliasTmp + "." + filterField.getPropertyName())));
	}
	return criterio;
    }

    /**
     * Gestisce per una restriction se i campi associati sono da valutare in AND o OR
     * 
     * @param mainCriteria
     * @param criterios
     * @param andOrRestriction
     */
    private void gestisciAndOrRestrictions(DetachedCriteria mainCriteria, Set<Criterion> criterios, AndOrRestriction andOrRestriction) {

	if (!criterios.isEmpty()) {
	    if (criterios.size() == 1) {
		Criterion crit = null;
		for (Criterion criterion : criterios) {
		    crit = criterion;
		}
		mainCriteria.add(crit);
		return;
	    }
	    Criterion lhs = null;
	    Criterion rhs = null;
	    List<Object[]> rlcs = new ArrayList<Object[]>();
	    boolean leftDone = false;
	    boolean rightDone = false;
	    int criteriosSize = criterios.size();
	    int counter = 0;
	    boolean isDispari = (criteriosSize % 2 == 0) ? false : true;
	    for (Criterion criterion : criterios) {
		counter++;
		if (leftDone == false) {
		    lhs = criterion;
		    leftDone = true;
		} else {
		    if (rightDone == false) {
			rhs = criterion;
			rightDone = true;
		    }
		}
		if (leftDone && rightDone) {
		    Object[] crits = new Object[] { lhs, rhs };
		    rlcs.add(crits);
		    leftDone = false;
		    rightDone = false;
		}
		if (counter == criteriosSize && isDispari) {
		    Object[] crits = new Object[] { lhs };
		    rlcs.add(crits);
		}
	    }
	    Criterion resultCriteria = null;
	    for (Object[] crits : rlcs) {
		if (crits.length == 2) {
		    if (resultCriteria == null) {
			if (andOrRestriction == AndOrRestriction.AND) {
			    resultCriteria = Restrictions.and((Criterion) crits[0], (Criterion) crits[1]);
			} else {
			    resultCriteria = Restrictions.or((Criterion) crits[0], (Criterion) crits[1]);
			}
		    } else {
			if (andOrRestriction == AndOrRestriction.AND) {
			    resultCriteria = Restrictions.and(resultCriteria, Restrictions.and((Criterion) crits[0], (Criterion) crits[1]));
			} else {
			    resultCriteria = Restrictions.or(resultCriteria, Restrictions.or((Criterion) crits[0], (Criterion) crits[1]));
			}
		    }
		} else {
		    if (resultCriteria == null) {
			resultCriteria = (Criterion) crits[0];
		    } else {
			if (andOrRestriction == AndOrRestriction.AND) {
			    resultCriteria = Restrictions.and(resultCriteria, (Criterion) crits[0]);
			} else {
			    resultCriteria = Restrictions.or(resultCriteria, (Criterion) crits[0]);
			}
		    }
		}
	    }
	    mainCriteria.add(resultCriteria);
	}
    }

    /**
     * Crea un oggetto DetachedCriteria a partire da una DefaultWhere specificata <code><pre>
    switch (defaultWhere) {
    case FIND_BY_IDCOMUNE:
        criteria = getIdcomuneCriteria();
        break;
    case FIND_BY_IDCOMUNE_AND_SOFTWARE:
        criteria = getIdcomuneAndSoftwareCriteria();
        break;
    default: // FIND_ALL
        criteria = getEmptyCriteriaForClass();
        break;
    }
     * </pre>
     * </code>
     * 
     * @param defaultWhere
     * @return
     */
    private DetachedCriteria getDefaultCriteria(DAOEnum defaultWhere) {

	DetachedCriteria criteria = null;
	switch (defaultWhere) {
	case FIND_BY_IDCOMUNE:
	    criteria = getIdcomuneCriteria();
	    break;
	case FIND_BY_IDCOMUNE_AND_SOFTWARE:
	    criteria = getIdcomuneAndSoftwareCriteria();
	    break;
	default: // FIND_ALL
	    criteria = getEmptyCriteriaForClass();
	    break;
	}
	return criteria;
    }

    /**
     * Recupera il path di associazione per un campo di filtro e setta sul criterio principale i vari alias
     * 
     * @param associationPath
     * @param mainCriteria
     * @param projections
     * @return
     */
    private String getAssociationPath(String associationPath, DetachedCriteria mainCriteria, ProjectionList projections,
	    Map<String, String> associationsCreated, Map<String, String> inverseAssociationCreated) {

	String result = "";
	String aliasString = "";
	String tmpAliasString = "";
	String aliasPrefix = "_";
	String aliasSeparator = ".";
	if (StringUtils.isNotBlank(associationPath)) {
	    StringTokenizer st = new StringTokenizer(associationPath, aliasSeparator);
	    while (st.hasMoreElements()) {
		String alias = st.nextToken();
		if (StringUtils.isBlank(aliasString)) {
		    aliasString = alias;
		    tmpAliasString = aliasPrefix + aliasString;
		} else {
		    aliasString = tmpAliasString + aliasSeparator + alias;
		    tmpAliasString = aliasPrefix + alias;
		}
		boolean alreadyCreated = (associationsCreated.get(tmpAliasString) == null) ? false : true;
		if (alreadyCreated) {
		    alreadyCreated = false;
		    alreadyCreated = (inverseAssociationCreated.get(aliasString) == null) ? false : true;
		    if (alreadyCreated) {
			tmpAliasString = inverseAssociationCreated.get(aliasString);
		    } else {
			Random random = new Random();
			tmpAliasString = aliasPrefix + random.nextInt(999) + tmpAliasString;
		    }
		}
		associationsCreated.put(tmpAliasString, aliasString);
		inverseAssociationCreated.put(aliasString, tmpAliasString);
		if (!alreadyCreated) {
		    mainCriteria.createAlias(aliasString, tmpAliasString, Criteria.LEFT_JOIN);
		}
		projections.add(Projections.property(aliasString), tmpAliasString);
		if (log.isDebugEnabled()) {
		    log.debug("getAssociationPath: criteria.createAlias({},{}, Criteria.LEFT_JOIN)", aliasString, tmpAliasString);
		}
		result = tmpAliasString + aliasSeparator;
	    }
	    if (log.isDebugEnabled()) {
		log.debug("getAssociationPath: association path = {}", result);
	    }
	}
	return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean existsRecords(FilterTable ft) {

	//	DAOEnum defaultWhere = DAOEnum.FIND_BY_IDCOMUNE;
	//	if (ft != null) {
	//	    defaultWhere = ft.getDefaultWhere();
	//	}
	//	DetachedCriteria criteria = getDefaultCriteria(defaultWhere);
	//	criteria.setProjection(Projections.rowCount());
	//	List list = getHibernateTemplate().findByCriteria(criteria);
	//	return ((Integer) list.get(0)).intValue() > 0;
	return countRecord(ft) > 0;
    }

    @Override
    public int countRecord(FilterTable filterTable) {

	DetachedCriteria criteria = getCriteriaForFilter(filterTable, true);
	criteria.setProjection(Projections.rowCount());
	int ris = ((Integer) getHibernateTemplate().findByCriteria(criteria).get(0)).intValue();
	return ris;
    }

    @Override
    public Object max(FilterTable filterTable, String propertyName) {

	Object maxVal = null;
	DetachedCriteria criteria = getCriteriaForFilter(filterTable, true);
	criteria.setProjection(Projections.max(propertyName));
	List<Object> maxResults = getHibernateTemplate().findByCriteria(criteria);
	if (maxResults.size() > 0) {
	    maxVal = maxResults.get(0);
	}
	return maxVal;
    }

    @Override
    public void flush() {

	getHibernateTemplate().flush();
    }

    @Override
    public void clear() {

	getHibernateTemplate().clear();
    }

    @SuppressWarnings("unchecked")
    public F newIdFromSequence(E entity) {

	log.debug("newIdFromSequence: getSessionImplementor");
	SessionImplementor source = (SessionImplementor) getHibernateTemplate().getSessionFactory().getCurrentSession();
	String entityName = getEntityClass().getName();
	EntityPersister persister = source.getEntityPersister(entityName, entity);
	log.debug("newIdFromSequence: got persister");
	Serializable generatedId = persister.getIdentifierGenerator().generate(source, entity);
	log.debug("newIdFromSequence: generated id {} for entity {}", generatedId, entityName);
	return (F) generatedId;
    };

    @Override
    public void commit() {

	Session session = this.getSession(false);
	//	Transaction t = session.getTransaction();
	//	t.commit();
	//	session.beginTransaction();
	//	session.doWork(new Work() {
	//
	//	    @Override
	//	    public void execute(Connection paramConnection) throws SQLException {
	//
	//		paramConnection.setReadOnly(false);
	//	    }
	//	});
	SQLQuery q = session.createSQLQuery("COMMIT");
	q.executeUpdate();
    }

    @Override
    public DynaBean findDynaBeanById(String idcomune, Integer codice, DynaClass dynaClass, Class daoEntityClass) {

	DetachedCriteria criteria = DetachedCriteria.forClass(daoEntityClass, "_" + daoEntityClass.getSimpleName());
	criteria.add(Restrictions.eq("id.idcomune", idcomune));
	criteria.add(Restrictions.eq("id.codice", codice));
	DynaProperty[] props = dynaClass.getDynaProperties();
	ProjectionList p = Projections.projectionList();
	for (DynaProperty dynaProperty : props) {
	    p.add(Projections.property(dynaProperty.getName().replaceAll("\\_", ".")), dynaProperty.getName());
	}
	criteria.setProjection(p);
	criteria.setResultTransformer(new DynaBeanTransformer(dynaClass));
	List list = getHibernateTemplate().findByCriteria(criteria);
	if (list.size() > 0) {
	    return (DynaBean) list.get(0);
	}
	return null;
    };

    @Override
    public List<DynaBean> findDynaBeanByFilterTable(FilterTable filterTable, DynaClass dynaClass, Integer firstResult, Integer maxResult,
	    Class daoEntityClass) {

	throw new NotImplementedException();
	//	DetachedCriteria criteria = DetachedCriteria.forClass(daoEntityClass, "_" + daoEntityClass.getSimpleName());
	//	getCriteriaForFilter(filterTable, excludeOrderBy)
	//	DynaProperty[] props = dynaClass.getDynaProperties();
	//	ProjectionList p = Projections.projectionList();
	//	for (DynaProperty dynaProperty : props) {
	//	    p.add(Projections.property(dynaProperty.getName().replaceAll("\\_", ".")), dynaProperty.getName());
	//	}
	//	criteria.setProjection(p);
	//	criteria.setResultTransformer(new DynaBeanTransformer(dynaClass));
	//	List<DynaBean> list = null;
	//	if (null != firstResult && null != maxResult) {
	//	    list = getHibernateTemplate().findByCriteria(criteria, firstResult, maxResult);
	//	} else {
	//	    list = getHibernateTemplate().findByCriteria(criteria);
	//	}
	//	return list;
    }
}
