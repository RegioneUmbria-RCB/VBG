package it.gruppoinit.pal.gp.core.service.helper;

import java.util.ArrayList;
import java.util.List;

public class GraduatorieMercatiBeanHelper {

    private String id;
    private boolean csv_stampabile;
    private boolean pdf_stampabile;
    private String data_riferimento_graduatoria;
    private List<AutorizzazioniGraduatoriaRestHelper> autorizzazioni;

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    public boolean isCsv_stampabile() {

	return csv_stampabile;
    }

    public boolean getCsv_stampabile() {

	return csv_stampabile;
    }

    public void setCsv_stampabile(boolean csv_stampabile) {

	this.csv_stampabile = csv_stampabile;
    }

    public boolean isPdf_stampabile() {

	return pdf_stampabile;
    }

    public boolean getPdf_stampabile() {

	return pdf_stampabile;
    }

    public void setPdf_stampabile(boolean pdf_stampabile) {

	this.pdf_stampabile = pdf_stampabile;
    }

    public String getData_riferimento_graduatoria() {

	return data_riferimento_graduatoria;
    }

    public void setData_riferimento_graduatoria(String data_riferimento_graduatoria) {

	this.data_riferimento_graduatoria = data_riferimento_graduatoria;
    }

    public List<AutorizzazioniGraduatoriaRestHelper> getAutorizzazioni() {

	if (this.autorizzazioni == null) {
	    this.autorizzazioni = new ArrayList<AutorizzazioniGraduatoriaRestHelper>(0);
	}
	return autorizzazioni;
    }

    public void setAutorizzazioni(List<AutorizzazioniGraduatoriaRestHelper> autorizzazioni) {

	this.autorizzazioni = autorizzazioni;
    }
}
