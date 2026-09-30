package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.eventi;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class ProceduraInvioNodoBollettazionePosizioneElabEvento extends ProceduraInvioNodoBollettazioneBase implements IEvent {

    private String keyBollettazione;
    private int idBollettazione;
    private int idAnagrafica;

    public ProceduraInvioNodoBollettazionePosizioneElabEvento(int idBollettazione, int idAnagrafica) {

	super();
	this.keyBollettazione = super.getKeyBollettazioneInvioNodoString(idBollettazione);
	this.idBollettazione = idBollettazione;
	this.idAnagrafica = idAnagrafica;
    }

    public String getKeyBollettazione() {

	return keyBollettazione;
    }

    public int getIdBollettazione() {

	return idBollettazione;
    }

    public int getIdAnagrafica() {

	return idAnagrafica;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
