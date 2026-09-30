package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.eventi.sottoscrittori;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;

public abstract class SottoscrittoreEventoManifestazioniBase<T extends IEventoMassiva> implements IEventSubscriber<T> {

    @Override
    public void onEvent(T e) {

	if (e.getContesto() == ContestoComunicazioneEnum.MANIFESTAZIONI) {
	    onEventInternal(e);
	}
    }

    abstract void onEventInternal(T e);
}