package it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.IstanzeoneriPosdebBatch;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoDocumentoPosizioneDebitoriaDisponibile implements IEvent {

    private Integer codiceIstanza;
    private String codiceComune;
    private List<IstanzeoneriPosdebBatch> listaRecord;

    public EventoDocumentoPosizioneDebitoriaDisponibile(Integer codiceIstanza, List<IstanzeoneriPosdebBatch> listaRecord, String codiceComune) {

	super();
	this.codiceIstanza = codiceIstanza;
	this.listaRecord = listaRecord;
	this.codiceComune = codiceComune;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public List<IstanzeoneriPosdebBatch> getListaRecord() {

	return listaRecord;
    }

    public String getCodiceComune() {

	return codiceComune;
    }
}
