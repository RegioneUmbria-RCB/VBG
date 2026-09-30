package it.gruppoinit.pal.gp.core.features.comunicazioni.appio.model;

public class TemplateMessaggioAppIo {

    private String templateOggetto;
    private String templateMessaggio;

    public TemplateMessaggioAppIo() {

	// 
	super();
    }

    public TemplateMessaggioAppIo(String templateOggetto, String templateMessaggio) {

	this();
	this.templateOggetto = templateOggetto;
	this.templateMessaggio = templateMessaggio;
    }

    public String getTemplateOggetto() {

	return templateOggetto;
    }

    public void setTemplateOggetto(String templateOggetto) {

	this.templateOggetto = templateOggetto;
    }

    public String getTemplateMessaggio() {

	return templateMessaggio;
    }

    public void setTemplateMessaggio(String templateMessaggio) {

	this.templateMessaggio = templateMessaggio;
    }
}
