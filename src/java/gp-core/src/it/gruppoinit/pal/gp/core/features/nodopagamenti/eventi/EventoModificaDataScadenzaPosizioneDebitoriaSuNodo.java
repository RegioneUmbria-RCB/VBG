package it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi;

import java.util.Date;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoModificaDataScadenzaPosizioneDebitoriaSuNodo implements IEvent {

    private Integer idDettPosizioneDebitoria;
    private Date dataScadenza;

    public EventoModificaDataScadenzaPosizioneDebitoriaSuNodo(Integer idDettPosizioneDebitoria, Date dataScadenza) {

	super();
	this.idDettPosizioneDebitoria = idDettPosizioneDebitoria;
	this.dataScadenza = dataScadenza;
    }

    public Integer getIdDettPosizioneDebitoria() {

	return idDettPosizioneDebitoria;
    }

    public Date getDataScadenza() {

	return dataScadenza;
    }
}
