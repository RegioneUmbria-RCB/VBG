package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi;

public class EventoMassivaBase implements IEventoMassiva {

    private ContestoComunicazioneEnum contesto;

    private EventoMassivaBase() {

    }

    protected EventoMassivaBase(ContestoComunicazioneEnum contesto) {

	this.contesto = contesto;
    }

    @Override
    public ContestoComunicazioneEnum getContesto() {

	return this.contesto;
    }
}
