package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.eventi;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class ProceduraInvioNodoBollettazioneStartEvento extends ProceduraInvioNodoBollettazioneBase implements IEvent {

    private String keyBollettazione;
    private int idBollettazione;
    private int totalePosizioni;

    public ProceduraInvioNodoBollettazioneStartEvento(int idBollettazione, int totalePosizioni) {

	super();
	this.keyBollettazione = super.getKeyBollettazioneInvioNodoString(idBollettazione);
	this.idBollettazione = idBollettazione;
	this.totalePosizioni = totalePosizioni;
    }

    public String getKeyBollettazione() {

	return keyBollettazione;
    }

    public int getIdBollettazione() {

	return idBollettazione;
    }

    public int getTotalePosizioni() {

	return totalePosizioni;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
