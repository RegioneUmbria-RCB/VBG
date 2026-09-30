package it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi;

import java.util.Date;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoModificaDataFVPosizioneDebitoriaSuNodo implements IEvent {

    private Integer idDettPosizioneDebitoria;
    private Integer idBlackListMotivi;
    private Date dataFineValidita;

    public EventoModificaDataFVPosizioneDebitoriaSuNodo(Integer idDettPosizioneDebitoria, Integer idBlackListMotivi, Date dataFineValidita) {

	super();
	this.idDettPosizioneDebitoria = idDettPosizioneDebitoria;
	this.idBlackListMotivi = idBlackListMotivi;
	this.dataFineValidita = dataFineValidita;
    }

    public Integer getIdDettPosizioneDebitoria() {

	return idDettPosizioneDebitoria;
    }
       
    public Integer getIdBlackListMotivi() {
    
        return idBlackListMotivi;
    }

    public Date getDataFineValidita() {

	return dataFineValidita;
    }
}
