package it.gruppoinit.pal.gp.core.features.buslightyear.samples;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

import java.util.Date;

/**
 * 
 * 
 *
 */
public class SampleEvent implements IEvent {

    private Date dataOperazione;

    public SampleEvent(Date dataOperazione) {

	super();
	this.dataOperazione = dataOperazione;
    }

    public Date getDataOperazione() {

	return dataOperazione;
    }
}
