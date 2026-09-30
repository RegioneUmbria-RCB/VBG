package it.gruppoinit.pal.gp.core.report.model;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.web.AnagrafeFilter;
import it.gruppoinit.pal.gp.core.report.filter.BaseFilter;

public class ReportBase extends BaseFilter {

    private AnagrafeFilter anagrafeFilter;
    private Amministrazioni amministrazioni;

    public ReportBase() {

	super();
	this.anagrafeFilter = new AnagrafeFilter();
	this.amministrazioni = new Amministrazioni();
    }

    public AnagrafeFilter getAnagrafeFilter() {

	return anagrafeFilter;
    }

    public void setAnagrafeFilter(AnagrafeFilter anagrafeFilter) {

	this.anagrafeFilter = anagrafeFilter;
    }

    public Amministrazioni getAmministrazioni() {

	return amministrazioni;
    }

    public void setAmministrazioni(Amministrazioni amministrazioni) {

	this.amministrazioni = amministrazioni;
    }
}
