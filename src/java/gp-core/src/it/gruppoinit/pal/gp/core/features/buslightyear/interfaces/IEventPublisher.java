package it.gruppoinit.pal.gp.core.features.buslightyear.interfaces;

import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;

/**
 * L'interfaccia del BUS su cui pubblicare gli eventi
 *
 */
public interface IEventPublisher {

    /**
     * Operazione di registrazione nel bus del pubblicatore eventi
     * 
     * @param module
     */
    //void load(EventBusModule module);
    /**
     * Operazione di pubblicazione di un evento
     * 
     * @param evento
     */
    <T extends IEvent> void publish(T evento);

    /**
     * Richiama il metodo onEvent di tutti i sottoscrittori, ma in caso di errore solleva un'eccezione di tipo
     * EventAbortedException. non prosegue nelle chiamate dei successivi sottoscrittori e scrive una riga di log. Per
     * utilizzare questo metodo, bisogna per forza gestire l'eccezione EventAbortedException in fase di publish
     * 
     * @param <T>
     *            Classe che implementa l'evento
     * @param evento
     */
    <T extends IEvent> void publishThrowOnFailure(T evento) throws EventAbortedException;

    /**
     * Aggiunta al pubblicatore di un Gestore (Subscriber) di Eventi
     * 
     * @param evento
     * @param handler
     */
    <TEvent extends IEvent, THandler extends IEventSubscriber<TEvent>> IEventPublisher add(Class<TEvent> evento, Class<THandler> handler);

    /**
     * Richiama il metodo onEvent di tutti i sottoscrittori, ma in caso di errore solleva un'eccezione di tipo
     * EventAbortedException al termine dell'elaborazione di tutti i sottoscrittori. Ilmetodo prosegue nelle chiamate
     * dei successivi sottoscrittori e scrive una riga di log. Per utilizzare questo metodo, bisogna per forza gestire
     * l'eccezione EventAbortedException in fase di publish
     * 
     * @param <T>
     *            Classe che implementa l'evento
     * @param evento
     */
    <T extends IEvent> void publishAndThrowOnAllSubscriberFailure(T evento) throws EventAbortedException;
}
