package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;

import java.util.ArrayList;
import java.util.List;

public class VerticalizzazioniparametriHelper {

    private Software software;
    private List<Verticalizzazioniparametri> verticalizzazioniparametris = new ArrayList<Verticalizzazioniparametri>();

    public Software getSoftware() {

	return software;
    }

    public void setSoftware(Software software) {

	this.software = software;
    }

    public List<Verticalizzazioniparametri> getVerticalizzazioniparametris() {

	return verticalizzazioniparametris;
    }

    public void setVerticalizzazioniparametris(List<Verticalizzazioniparametri> verticalizzazioniparametris) {

	this.verticalizzazioniparametris = verticalizzazioniparametris;
    }
}
