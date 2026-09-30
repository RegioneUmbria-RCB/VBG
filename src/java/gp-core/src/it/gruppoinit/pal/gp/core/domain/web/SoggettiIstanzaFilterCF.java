package it.gruppoinit.pal.gp.core.domain.web;

public class SoggettiIstanzaFilterCF {

    private String richiedenteCF;
    private String titolarelegaleCF;
    private String professionistaCF;
    private String istanzerichiedentisCF;
    private boolean isPopolato;

    public String getRichiedenteCF() {

	return richiedenteCF;
    }

    public void setRichiedenteCF(String richiedenteCF) {

	this.richiedenteCF = richiedenteCF;
	setPopolato(true);
    }

    public String getTitolarelegaleCF() {

	return titolarelegaleCF;
    }

    public void setTitolarelegaleCF(String titolarelegaleCF) {

	this.titolarelegaleCF = titolarelegaleCF;
	setPopolato(true);
    }

    public String getProfessionistaCF() {

	return professionistaCF;
    }

    public void setProfessionistaCF(String professionistaCF) {

	this.professionistaCF = professionistaCF;
	setPopolato(true);
    }

    public String getIstanzerichiedentisCF() {

	return istanzerichiedentisCF;
    }

    public void setIstanzerichiedentisCF(String istanzerichiedentisCF) {

	this.istanzerichiedentisCF = istanzerichiedentisCF;
	setPopolato(true);
    }

    public boolean isPopolato() {

	return isPopolato;
    }

    private void setPopolato(boolean isPopolato) {

	this.isPopolato = isPopolato;
    }
}
