package it.gruppoinit.pal.gp.core.features.comunicazioni.appio.model;

public class AppioTipimovimentoEndoBean {

    private String identificativoServizio;
    private String tipomovimento;
    private String movimento;
    private Integer codiceinventario;
    private String procedimento;
    private String templateOggetto;
    private String templateMessaggio;

    public String getIdentificativoServizio() {

	return identificativoServizio;
    }

    public void setIdentificativoServizio(String identificativoServizio) {

	this.identificativoServizio = identificativoServizio;
    }

    public String getTipomovimento() {

	return tipomovimento;
    }

    public void setTipomovimento(String tipomovimento) {

	this.tipomovimento = tipomovimento;
    }

    public String getMovimento() {

	return movimento;
    }

    public void setMovimento(String movimento) {

	this.movimento = movimento;
    }

    public Integer getCodiceinventario() {

	return codiceinventario;
    }

    public void setCodiceinventario(Integer codiceinventario) {

	this.codiceinventario = codiceinventario;
    }

    public String getProcedimento() {

	return procedimento;
    }

    public void setProcedimento(String procedimento) {

	this.procedimento = procedimento;
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
