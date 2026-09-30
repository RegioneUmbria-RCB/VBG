package it.gruppoinit.pal.gp.core.features.buslightyear;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.OperazioneEventoBean;
import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;

@Service
public class EventPublisherServiceImpl implements IEventPublisher {

    private static final String ERRORE_NELLA_CREAZIONE_INVOCAZIONE_DEL_SUBSCRIBER_DI_TIPO = "Errore nella creazione/invocazione del subscriber di tipo ";
    private Logger log = Logger.getLogger(EventPublisherServiceImpl.class);
    private Map<String, List<String>> subscribers = new HashMap<String, List<String>>();
    private IOCKernel iocKernel;

    @Autowired
    public EventPublisherServiceImpl(IOCKernel iocKernel) {

	super();
	this.iocKernel = iocKernel;
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T extends IEvent> void publish(T evento) {

	if (this.subscribers.containsKey(evento.getClass().getName())) {
	    List<String> subs = this.subscribers.get(evento.getClass().getName());
	    for (String sub : subs) {
		try {
		    ((IEventSubscriber<T>) iocKernel.getBeanOfType(sub)).onEvent(evento);
		} catch (Exception e) {
		    log.error(ERRORE_NELLA_CREAZIONE_INVOCAZIONE_DEL_SUBSCRIBER_DI_TIPO + sub + ", {}", e);
		}
	    }
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IEvent> void publishAndThrowOnAllSubscriberFailure(T evento) throws EventAbortedException {

	List<OperazioneEventoBean> warnings = new ArrayList<OperazioneEventoBean>();
	List<OperazioneEventoBean> errors = new ArrayList<OperazioneEventoBean>();
	if (this.subscribers.containsKey(evento.getClass().getName())) {
	    for (String sub : this.subscribers.get(evento.getClass().getName())) {
		try {
		    ((IEventSubscriber<T>) iocKernel.getBeanOfType(sub)).onEvent(evento);
		} catch (EventAbortedException ea) {
		    if (ea.getEsito() != null) {
			warnings.addAll(ea.getEsito().getWarnings());
			errors.addAll(ea.getEsito().getErrors());
		    }
		} catch (Exception e) {
		    log.error(ERRORE_NELLA_CREAZIONE_INVOCAZIONE_DEL_SUBSCRIBER_DI_TIPO + sub + ", {}", e);
		}
	    }
	    if (!(warnings.isEmpty() && errors.isEmpty())) {
		throw new EventAbortedException(new EsitoElaborazioneEvento(warnings, errors), evento);
	    }
	}
    }

    /**
     * Richiama il metodo onEvent di tutti i subscribers, ma in caso di errore solleva un'eccezione di tipo
     * EventAbortedException. non prosegue nelle chiamete dei successivi subscribers e scrive una riga di log. Per
     * utilizzare questo metodo, bisogna per forza gestire l'eccezione EventAbortedException in fase di publish
     * 
     * @param <T>
     *            Classe che implementa l'evento
     * @param evento
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends IEvent> void publishThrowOnFailure(T evento) throws EventAbortedException {

	if (this.subscribers.containsKey(evento.getClass().getName())) {
	    List<String> subs = this.subscribers.get(evento.getClass().getName());
	    for (String sub : subs) {
		try {
		    ((IEventSubscriber<T>) iocKernel.getBeanOfType(sub)).onEvent(evento);
		} catch (EventAbortedException ea) {
		    throw ea;
		} catch (Exception e) {
		    log.error(ERRORE_NELLA_CREAZIONE_INVOCAZIONE_DEL_SUBSCRIBER_DI_TIPO + sub + ", {}", e);
		    throw new EventAbortedException(e, sub, evento);
		}
	    }
	}
    }

    @Override
    public <TEvent extends IEvent, THandler extends IEventSubscriber<TEvent>> IEventPublisher add(Class<TEvent> evento, Class<THandler> handler) {

	String eventName = evento.getName();
	String handlerName = handler.getName();
	if (!this.subscribers.containsKey(eventName)) {
	    List<String> list = new ArrayList<String>();
	    this.subscribers.put(eventName, list);
	}
	this.subscribers.get(eventName).add(handlerName);
	return this;
    }
}
