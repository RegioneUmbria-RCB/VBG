package it.gruppoinit.stc.service.impl;

import it.gruppoinit.stc.service.BaseService;

import java.io.Serializable;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

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

    /**
     * Metodo astratto che le classi che estendono <code>BaseServiceImpl</code> devono implementare per identificare la
     * classe di dominio sulla quale eseguire operazioni interne
     * 
     * @see {@link #validate(Object)}
     * @return la classe dell'oggetto di dominio
     */
    protected abstract Class<E> getEntityClass();

    /**
     * Metodo che effettua la validazione su un oggetto di dominio attraverso
     * <code>org.hibernate.validator.ClassValidator</code> usando le annotazioni poste sugli oggetti di dominio
     * 
     * @param entity
     *            l'oggetto di dominio per il quale effettuare la validazione
     * @return <code>true</code> o <code>false</code> a seconda che la validazione sia avvenuta con successo o meno
     */
    protected boolean validate(E entity) {

	ClassValidator<E> validator = new ClassValidator<E>(getEntityClass());
	InvalidValue[] validationMessages = validator.getInvalidValues(entity);
	if (validationMessages != null && validationMessages.length > 0) {
	    String errors = "";
	    for (InvalidValue invalidValue : validationMessages) {
		errors += "<br/>" + invalidValue.getPropertyName() + " " + invalidValue.getMessage();
	    }
	    throw new RuntimeException("Attenzione! Errori di validazione:" + errors);
	}
	return true;
    }
}
