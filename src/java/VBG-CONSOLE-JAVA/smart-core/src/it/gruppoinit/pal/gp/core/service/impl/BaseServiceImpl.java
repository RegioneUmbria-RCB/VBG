package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.exception.EntityValidationException;
import it.gruppoinit.pal.gp.core.service.rules.ServiceValidationRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Table;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Classe astratta che contiene i metodi di utilità che i service devono estendere
 * 
 * @param <E>
 *            Il tipo di oggetto di dominio principale per il quale il service viene creato
 * @param <F>
 *            Il tipo della chiave primario dell'oggetto di dominio
 * @author Fabrizio Corsetti
 * @author Riccardo Bocci
 */
public abstract class BaseServiceImpl<E, F extends Serializable> implements BaseService<E, F> {

    private static final Logger log = LoggerFactory.getLogger(BaseServiceImpl.class);
    @Autowired
    protected ApplicationContext context;
    private CacheManager baseCacheManager;

    @Autowired(required = false)
    public void setBaseCacheManager(CacheManager baseCacheManager) {

	this.baseCacheManager = baseCacheManager;
    }

    /**
     * Metodo astratto che le classi che estendono <code>BaseServiceImpl</code> devono implementare per identificare la
     * classe di dominio sulla quale eseguire operazioni interne
     * 
     * @see {@link #validateEntity(Object)}
     * @return la classe dell'oggetto di dominio
     */
    protected abstract Class<E> getEntityClass();

    /**
     * Metodo che effettua la validazione su un oggetto di dominio attraverso {@link ClassValidator} usando le
     * annotazioni poste sugli oggetti di dominio
     * 
     * @param entity
     *            l'oggetto di dominio per il quale effettuare la validazione
     * @return <code>true</code> se non ci sono errori altrimenti popola la proprietà validationMessages e rilancia una
     *         EntityValidationException
     */
    @SuppressWarnings("unchecked")
    protected boolean validateEntity(E entity) {

	ServiceValidationRules validationRule = (ServiceValidationRules) SigeproBusinessRules.getClassRules(ServiceValidationRules.class);
	boolean doEntityValidation = true;
	if (validationRule != null) {
	    doEntityValidation = validationRule.getCustomRule(ServiceValidationRules.CustomRuleEnum.doEntityValidation.name());
	}
	if (doEntityValidation) {
	    ClassValidator<E> validator = null;
	    if (this.baseCacheManager != null) {
		Cache cache = baseCacheManager.getCache(WebConstants.CACHE_HIBERNATE_VALIDATORS);
		if (cache != null) {
		    Element obj = cache.get(getEntityClass().getName());
		    if (obj != null) {
			validator = (ClassValidator<E>) obj.getObjectValue();
		    } else {
			validator = new ClassValidator<E>(getEntityClass());
			Element element = new Element(getEntityClass().getName(), validator);
			cache.put(element);
			cache.flush();
		    }
		} else {
		    log.info("validateEntity(): Cache CACHE_HIBERNATE_VALIDATORS is null");
		    validator = new ClassValidator<E>(getEntityClass());
		}
	    } else {
		log.info("validateEntity(): CacheManager is null");
		validator = new ClassValidator<E>(getEntityClass());
	    }
	    InvalidValue[] invalidValues = validator.getInvalidValues(entity);
	    if (invalidValues.length > 0) {
		log.error("entity: [{}] errori di validazione: {}", getEntityClass(), invalidValues);
		List<InvalidValue> list = Arrays.asList(invalidValues);
		throw new EntityValidationException(list, getMessageFromBundle("error.entity_error_message", null), null);
	    }
	}
	return true;
    }

    /**
     * metodo che popola l'array validationMessages con i messaggi di errore passati come argomento e rilancia una
     * {@link BusinessValidationException}
     * 
     * @param ivs
     *            lista degli InvalidValue della business validate. (può essere nulla o vuota)
     */
    protected void throwValidationMessages(List<InvalidValue> ivs) {

	log.error("BusinessValidationException: [{}], errori di validazione.", getClass());
	try {
	    if (ivs != null) {
		for (InvalidValue invalidValue : ivs) {
		    log.error("BusinessValidationException: [{}], invalid value: {}", getClass(),
			    getMessageFromBundle(invalidValue.getMessage(), new Object[] { "" }));
		}
	    }
	} catch (Exception e) {
	}
	throw new BusinessValidationException(ivs, getMessageFromBundle("error.business_error_message", new Object[] { "" }), null);
    }

    protected void throwValidationMessages(List<InvalidValue> ivs, String message) {

	log.error("BusinessValidationException: [{}], errori di validazione. message: {}", getClass(), message);
	try {
	    if (ivs != null) {
		for (InvalidValue invalidValue : ivs) {
		    log.error("BusinessValidationException: [{}], invalid value: {}", getClass(),
			    getMessageFromBundle(invalidValue.getMessage(), new Object[] { "" }));
		}
	    }
	} catch (Exception e) {
	}
	throw new BusinessValidationException(ivs, getMessageFromBundle("error.business_error_message", new Object[] { message }), null);
    }

    /**
     * metodo che popola l'array validationMessages con il messaggio di errore passato come argomento e rilancia una
     * {@link BusinessValidationException}
     * 
     * @param iv
     *            InvalidValue della business validate. (può essere nullo)
     */
    protected void throwValidationMessage(InvalidValue iv) {

	List<InvalidValue> invalidValues = new ArrayList<InvalidValue>();
	if (iv != null) {
	    invalidValues.add(iv);
	}
	log.error("BusinessValidationException: [{}], errore di validazione.", getClass());
	log.error("BusinessValidationException: [{}], invalid value: {}", getClass(), getMessageFromBundle(iv.getMessage(), new Object[] { "" }));
	throw new BusinessValidationException(invalidValues, getMessageFromBundle("error.business_error_message", new Object[] { "" }), null);
    }

    /**
     * metodo da sovrascrivere per il controllo della cancellazione su DB
     * 
     * @param entity
     * @return
     */
    protected boolean isDeleteAllowed(E entity) {

	return true;
    }

    protected String getMessageFromBundle(String chiave, Object[] args) {

	return Utilities.getMessageFromBundle(context, chiave, args);
    }

    /**
     * metodo da sovrascrivere per il controllo della cancellazione su DB
     * 
     * @param entity
     * @return
     */
    protected void childDelete(E entity) {

	if (log.isDebugEnabled()) {
	    log.warn("childDelete: da implementare");
	}
    }

    @Override
    @SuppressWarnings("unchecked")
    public E bindDomainObjectForPkId(E entity) {

	if (entity == null) {
	    return null;
	}
	E domainObject = null;
	PkId id = (PkId) EntityUtils.getNestedProperty(entity, "id");
	if (id != null) {
	    if (id.getCodice() != null && StringUtils.isNotBlank(id.getIdcomune())) {
		AnnotatedElement classAnnotations = getEntityClass();
		Table table = classAnnotations.getAnnotation(Table.class);
		String tableName = table.name();
		F id1 = null;
		try {
		    id1 = (F) Class.forName(id.getClass().getName()).newInstance();
		} catch (InstantiationException e) {
		    log.error("bindDomainObject: {}", e.getMessage());
		    throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
		    log.error("bindDomainObject: {}", e.getMessage());
		    throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
		    log.error("bindDomainObject: {}", e.getMessage());
		    throw new RuntimeException(e);
		}
		id1 = setCodiceByReflection(id1, "id.codice", id.getCodice());
		id1 = setCodiceByReflection(id1, "id.idcomune", id.getIdcomune());
		domainObject = this.findById(id1);
		if (domainObject == null) {
		    log.error("bindDomainObject: nessun record con id '{}' nella tabella '{}'.", id, tableName);
		    throw new BusinessValidationException(null, getMessageFromBundle("service_error.nessun_record_trovato_nella_tabella_per_id",
			    new Object[] { tableName, id }), null);
		} else {
		    return domainObject;
		}
	    }
	}
	// domainObject = customBindDomainObject(entity);
	return domainObject;
    }

    @SuppressWarnings("unchecked")
    public E bindDomainObject(E entity, Class<?> idClass, String idPath) {

	if (entity == null) {
	    return null;
	}
	E domainObject = null;
	Object codice = EntityUtils.getNestedProperty(entity, idPath);
	if (codice != null) {
	    AnnotatedElement classAnnotations = getEntityClass();
	    Table table = classAnnotations.getAnnotation(Table.class);
	    String tableName = table.name();
	    F id = null;
	    if (idClass.getName().equalsIgnoreCase("java.lang.Integer")) {
		id = (F) Integer.valueOf(0);
	    } else {
		try {
		    id = (F) Class.forName(idClass.getName()).newInstance();
		} catch (InstantiationException e) {
		    log.error("bindDomainObject: {}", e.getMessage());
		    throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
		    log.error("bindDomainObject: {}", e.getMessage());
		    throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
		    log.error("bindDomainObject: {}", e.getMessage());
		    throw new RuntimeException(e);
		}
	    }
	    id = setCodiceByReflection(id, idPath, codice);
	    domainObject = this.findById(id);
	    if (domainObject == null) {
		log.error("bindDomainObject: nessun record con id '{}' nella tabella '{}'.", id, tableName);
		throw new BusinessValidationException(null, getMessageFromBundle("service_error.nessun_record_trovato_nella_tabella_per_id",
			new Object[] { tableName, codice }), null);
	    } else {
		return domainObject;
	    }
	}
	domainObject = customBindDomainObject(entity);
	return domainObject;
    }

    @SuppressWarnings("unchecked")
    private F setCodiceByReflection(F id, String idPath, Object value) {

	String propertyToSet = idPath;
	if (idPath.indexOf(".") > 0) {
	    propertyToSet = idPath.substring(idPath.indexOf(".") + 1);
	}
	if (id instanceof String || id instanceof Integer || id instanceof Long || id instanceof Byte || id instanceof Short) {
	    id = (F) value;
	} else {
	    try {
		BeanUtils.setProperty(id, propertyToSet, value);
	    } catch (Exception e) {
		throw new RuntimeException(e);
	    }
	}
	return id;
    }

    /**
     * Questo metodo deve essere sovrascritto dai service che vogliono ricercare un record su DB secondo regole
     * specifiche.
     * 
     * 
     * 
     * @param entity
     * @return null
     */
    protected E customBindDomainObject(E entity) {

	// log.trace("customBindDomainObject: non implementato per la classe {}", getClass());
	return null;
    }

    /**
     * Il metodo serve per ripulire le entity degli oggetti dipendenti non associati.
     * 
     * @param entity
     */
    protected void fixMergeEntityProperties(E entity) {

	log.warn("fixMergeEntityProperties: non implementato per la classe {}", getClass());
    }

    /**
     * Il metodo:
     * <ol>
     * <li>Se siamo in cancellazione [isDelete = true]: se all'entity è associato un oggetto ritorna quel codice oggetto
     * </li>
     * <li>Altrimenti [insert/update]
     * <ul>
     * <li>se l'entity associata aveva un codice oggetto diverso da quello attuale ritorna il codiceoggetto
     * precedentemente associato per poterlo cancellare</li>
     * <li>Altrimenti ritorna null</li>
     * </ul>
     * </li>
     * </ol>
     * 
     * @param entity
     *            l'entity che si vuole controllare
     * @param oggettiPropertyPath
     *            il path alla proprietà che rappresenta l'oggetto
     * @param isDelete
     *            true se usata nei metodi di cancellazione
     * @return
     */
    protected Integer controllaCancellaOggetti(E entity, String oggettiPropertyPath, boolean isDelete, F id) {

	Integer codiceOggetto = null;
	Integer codiceOggettoOld = null;
	String oggettiPath = oggettiPropertyPath + ".id.codice";
	if (EntityUtils.getNestedProperty(entity, oggettiPath) != null) {
	    codiceOggetto = (Integer) EntityUtils.getNestedProperty(entity, oggettiPath);
	}
	if (isDelete) {
	    // sono in cancellazione
	    if (!(null == codiceOggetto)) {
		return codiceOggetto;
	    }
	} else {
	    // sono in modifica / insert
	    // vedo se posso cancellare il vecchio oggetto
	    E entityCopy = this.findById(id);
	    if (null != entityCopy) {
		// recupero il vecchio id
		if (EntityUtils.getNestedProperty(entityCopy, oggettiPath) != null) {
		    codiceOggettoOld = (Integer) EntityUtils.getNestedProperty(entityCopy, oggettiPath);
		}
		if (null != codiceOggettoOld) {
		    if (!codiceOggettoOld.equals(codiceOggetto)) {
			return codiceOggettoOld;
		    }
		}
	    }
	}
	return null;
    }

    public F newIdFromSequencetable(E entity) {

	throw new NotImplementedException("newIdFromSequencetable: Metodo non implementato per la classe " + getClass());
    }
}
