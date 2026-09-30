package it.sgp.middleware.security.service.impl;

import java.io.Serializable;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import it.sgp.middleware.security.domain.ComunisecurityApp;
import it.sgp.middleware.security.exceptions.NotImplementedException;
import it.sgp.middleware.security.service.BaseService;
import it.sgp.middleware.security.validation.ObjectsValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

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
    // protected InvalidValue[] validationMessages;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private ObjectsValidator<E> validator;

    @Override
    public void insertNoCheck(E entity) {

	throw new NotImplementedException(getMessageFromBundle("error.metodo_non_implementato", new String[] { "insertNoCheck" }));
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
    protected boolean validateEntity(E entity) {

	Set<ConstraintViolation<E>> validationErrors = validator.validate(entity);
	if (!validationErrors.isEmpty()) {
	    throw new ConstraintViolationException(validationErrors);
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
    //    protected void throwValidationMessages(List<InvalidValue> ivs) {
    //
    //	if (ivs != null && !ivs.isEmpty()) {
    //	    InvalidValue[] _ivs = new InvalidValue[ivs.size()];
    //	    this.validationMessages = ivs.toArray(_ivs);
    //	}
    //	log.error("BusinessValidationException: {} - errori di validazione.", getClass());
    //	throw new BusinessValidationException(getMessageFromBundle("error.business_error_message", null));
    //    }
    /**
     * metodo che popola l'array validationMessages con il messaggio di errore passato come argomento e rilancia una
     * {@link BusinessValidationException}
     * 
     * @param iv
     *            InvalidValue della business validate. (può essere nullo)
     */
    //    protected void throwValidationMessage(InvalidValue iv) {
    //
    //	if (iv != null) {
    //	    InvalidValue[] _iv = new InvalidValue[1];
    //	    _iv[0] = iv;
    //	    this.validationMessages = _iv;
    //	}
    //	log.error("BusinessValidationException: {} - errori di validazione.", getClass());
    //	throw new BusinessValidationException(getMessageFromBundle("error.business_error_message", null));
    //    }
    /**
     * metodo utilizzato dai Controller per recuperare gli errori riscontrati sul Service
     */
    //    public InvalidValue[] getValidationMessages() {
    //
    //	return this.validationMessages;
    //    }
    //
    //    public void setValidationMessages(InvalidValue[] validationMessages) {
    //
    //	this.validationMessages = validationMessages;
    //    }

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

	String message = "";
	try {
	    message = context.getMessage(chiave, args, LocaleContextHolder.getLocale());
	} catch (NoSuchMessageException e) {
	    log.error("chiave del resource bundle non trovata: {}", chiave);
	    message = "???" + chiave + "???";
	}
	return message;
    }
}