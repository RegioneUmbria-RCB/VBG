package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public interface IEventoMassiva extends IEvent {

    public enum ContestoComunicazioneEnum {
	BOLLETTAZIONE,
	COMMISSIONI,
	MANIFESTAZIONI,
	MERCATI,
	ISTANZE
    }

    public ContestoComunicazioneEnum getContesto();
}
