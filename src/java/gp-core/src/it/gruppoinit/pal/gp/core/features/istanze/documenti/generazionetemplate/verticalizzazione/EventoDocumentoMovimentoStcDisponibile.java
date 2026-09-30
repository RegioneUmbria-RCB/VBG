package it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.verticalizzazione;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoDocumentoMovimentoStcDisponibile implements IEvent {

    private Integer codiceMovimento;

    public EventoDocumentoMovimentoStcDisponibile(Integer codiceMovimento) {

	super();
	this.setCodiceMovimento(codiceMovimento);
    }

    public Integer getCodiceMovimento() {

	return codiceMovimento;
    }

    public void setCodiceMovimento(Integer codiceMovimento) {

	this.codiceMovimento = codiceMovimento;
    }
}
