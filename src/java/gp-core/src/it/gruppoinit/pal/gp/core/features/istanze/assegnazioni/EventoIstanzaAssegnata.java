package it.gruppoinit.pal.gp.core.features.istanze.assegnazioni;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoIstanzaAssegnata implements IEvent {

    private Integer codiceIstanza;
    private ResponsabileIstanzaEnum responsabileIstanzaEnum;

    public EventoIstanzaAssegnata(Integer codiceIstanza, ResponsabileIstanzaEnum responsabileIstanzaEnum) {

	super();
	this.codiceIstanza = codiceIstanza;
	this.responsabileIstanzaEnum = responsabileIstanzaEnum;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public ResponsabileIstanzaEnum getResponsabileIstanzaEnum() {

	return responsabileIstanzaEnum;
    }

    public static EventoIstanzaAssegnata fromResponsabileIstruttoria(Integer codiceIstanza) {

	return new EventoIstanzaAssegnata(codiceIstanza, ResponsabileIstanzaEnum.RESPONSABILE_ISTRUTTORIA);
    }

    public static EventoIstanzaAssegnata fromResponsabileProcedimento(Integer codiceIstanza) {

	return new EventoIstanzaAssegnata(codiceIstanza, ResponsabileIstanzaEnum.RESPONSABILE_PROCEDIMENTO);
    }
}
