package it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi;

import it.gruppoinit.pal.gp.core.domain.web.ValidaEliminazioneAutConcCommand;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoAutorizzazioneConcessioneEliminata implements IEvent {

    private Integer idAutorizzazioni;
    private Integer codiceIstanza;
    private EsitoCancellazioneAutOConc esito;
    private boolean concessione = false;
    private String estremiAtto;

    public EventoAutorizzazioneConcessioneEliminata(ValidaEliminazioneAutConcCommand cmd) {

	this.codiceIstanza = cmd.getCodiceIstanza();
	this.idAutorizzazioni = cmd.getIdAutorizzazioni();
	this.esito = cmd.getEsito();
	this.concessione = cmd.isConcessione();
	this.estremiAtto = cmd.getEstremiAtto();
    }

    public Integer getIdAutorizzazioni() {

	return idAutorizzazioni;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public EsitoCancellazioneAutOConc getEsito() {

	return esito;
    }

    public boolean isConcessione() {

	return concessione;
    }

    public String getEstremiAtto() {

	return estremiAtto;
    }
}
