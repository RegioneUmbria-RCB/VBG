package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.eventi;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class ProceduraInvioNodoBollettazioneStopEvento extends ProceduraInvioNodoBollettazioneBase implements IEvent {

    private String keyBollettazione;
    private int idBollettazione;

    public ProceduraInvioNodoBollettazioneStopEvento(int idBollettazione) {

	super();
	this.keyBollettazione = super.getKeyBollettazioneInvioNodoString(idBollettazione);
	this.idBollettazione = idBollettazione;
    }

    public String getKeyBollettazione() {

	return keyBollettazione;
    }

    public int getIdBollettazione() {

	return idBollettazione;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
