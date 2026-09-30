package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.helper.GraduatoriedDTO;

public class GraduatoriedFilter {

    private SchedaDinamicaFilter schedaDinamicaFilter;
    private GraduatoriedDTO graduatoriedDTO;

    public GraduatoriedFilter() {

	super();
	this.schedaDinamicaFilter = new SchedaDinamicaFilter();
	this.graduatoriedDTO = new GraduatoriedDTO();
    }

    public SchedaDinamicaFilter getSchedaDinamicaFilter() {

	return schedaDinamicaFilter;
    }

    public void setSchedaDinamicaFilter(SchedaDinamicaFilter schedaDinamicaFilter) {

	this.schedaDinamicaFilter = schedaDinamicaFilter;
    }

    public GraduatoriedDTO getGraduatoriedDTO() {

	return graduatoriedDTO;
    }

    public void setGraduatoriedDTO(GraduatoriedDTO graduatoriedDTO) {

	this.graduatoriedDTO = graduatoriedDTO;
    }
}
