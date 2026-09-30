package it.gruppoinit.pal.gp.core.features.buslightyear;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;

import javax.annotation.PostConstruct;

/**
 * Classe astratta per definire un modulo di pubblicazione eventi (Publisher)
 *
 */
public abstract class EventBusModule {

    protected EventBusModule(IEventPublisher eventPublisher) {

	this.eventPublisher = eventPublisher;
    }

    private IEventPublisher eventPublisher;

    @PostConstruct
    public void publishInizializationEvent() {

	this.load(eventPublisher);
    }

    /**
     * 
     */
    public abstract void load(IEventPublisher eventPublisher);
}
