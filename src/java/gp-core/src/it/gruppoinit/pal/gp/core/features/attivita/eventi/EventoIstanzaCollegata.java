package it.gruppoinit.pal.gp.core.features.attivita.eventi;

import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoIstanzaCollegata implements IEvent {

    private Integer idAttivita;
    private Date dataElaborazione;
    private Istanze istanza;

    public EventoIstanzaCollegata(Integer idAttivita, Date dataElaborazione) {

	super();
	if (idAttivita == null) {
	    throw new IllegalArgumentException("Parametri non validi");
	}
	this.idAttivita = idAttivita;
	this.dataElaborazione = dataElaborazione;
    }
    
    
    public EventoIstanzaCollegata(Integer idAttivita, Date dataElaborazione, Istanze istanza) {

   	super();
   	if (idAttivita == null) {
   	    throw new IllegalArgumentException("Parametri non validi");
   	}
   	this.idAttivita = idAttivita;
   	this.dataElaborazione = dataElaborazione;
   	this.istanza= istanza;       }

    public Integer getIdAttivita() {

	return idAttivita;
    }

    public Date getDataElaborazione() {

	return dataElaborazione;
    }


    
    public Istanze getIstanza() {
    
        return istanza;
    }

    
}
