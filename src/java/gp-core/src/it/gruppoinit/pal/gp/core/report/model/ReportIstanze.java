package it.gruppoinit.pal.gp.core.report.model;

import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.report.filter.BaseFilter;

public class ReportIstanze extends BaseFilter {

    private IstanzeFilter istanzeFilter;
    private Istanze istanze;
    private Responsabili responsabili;
    private Esportazioni esportazioni;
    private boolean conteggioMqOrNrIstanze;
    private Letteretipo letteretipo;

    public ReportIstanze() {

	super();
	this.istanzeFilter = new IstanzeFilter();
	this.istanze = new Istanze();
	this.responsabili = new Responsabili();
	this.esportazioni = new Esportazioni();
	this.conteggioMqOrNrIstanze = false;
	this.letteretipo = new Letteretipo();
    }

    public IstanzeFilter getIstanzeFilter() {

	return istanzeFilter;
    }

    public void setIstanzeFilter(IstanzeFilter istanzeFilter) {

	this.istanzeFilter = istanzeFilter;
    }

    public Istanze getIstanze() {

	return istanze;
    }

    public void setIstanze(Istanze istanze) {

	this.istanze = istanze;
    }

    public Responsabili getResponsabili() {

	return responsabili;
    }

    public void setResponsabili(Responsabili responsabili) {

	this.responsabili = responsabili;
    }

    public Esportazioni getEsportazioni() {

	return esportazioni;
    }

    public void setEsportazioni(Esportazioni esportazioni) {

	this.esportazioni = esportazioni;
    }

    public boolean getConteggioMqOrNrIstanze() {

	return conteggioMqOrNrIstanze;
    }

    public void setConteggioMqOrNrIstanze(boolean conteggioMqOrNrIstanze) {

	this.conteggioMqOrNrIstanze = conteggioMqOrNrIstanze;
    }

    public Letteretipo getLetteretipo() {

	return letteretipo;
    }

    public void setLetteretipo(Letteretipo letteretipo) {

	this.letteretipo = letteretipo;
    }
}
