package it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoPosizioneDebitoriaAnnullata implements IEvent {

    private Integer idDettaglioPosizioneDebitoria;
    private String noteAnnullamento;

    public EventoPosizioneDebitoriaAnnullata(Integer idDettaglioPosizioneDebitoria, String noteAnnullamento) {

	this.idDettaglioPosizioneDebitoria = idDettaglioPosizioneDebitoria;
	this.noteAnnullamento = noteAnnullamento;
    }

    public Integer getIdDettaglioPosizioneDebitoria() {

	return idDettaglioPosizioneDebitoria;
    }

    public String getNoteAnnullamento() {

	return noteAnnullamento;
    }
}
