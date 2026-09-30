package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.subscr;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;

public abstract class SottoscrittoreEventoGenBase<T extends IEventoMassiva> implements IEventSubscriber<T> {

    @Override
    public void onEvent(T e) {

	if (e.getContesto() == ContestoComunicazioneEnum.MERCATI || e.getContesto() == ContestoComunicazioneEnum.ISTANZE) {
	    onEventInternal(e);
	}
    }

    abstract void onEventInternal(T e);
}
