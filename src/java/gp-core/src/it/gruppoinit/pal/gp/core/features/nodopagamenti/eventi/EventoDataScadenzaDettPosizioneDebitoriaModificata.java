package it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi;

import java.util.Date;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

/**
 * L'evento si verifica quando viene modificata la data di scadenza di DETT_POSIZIONE_DEBITORIA
 * 
 * @author riccardob
 *
 */
public class EventoDataScadenzaDettPosizioneDebitoriaModificata implements IEvent {

    private Integer idDettPosizioneDebitoria;
    private Date dataScadenza;

    public EventoDataScadenzaDettPosizioneDebitoriaModificata(Integer idDettPosizioneDebitoria, Date dataScadenza) {

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
