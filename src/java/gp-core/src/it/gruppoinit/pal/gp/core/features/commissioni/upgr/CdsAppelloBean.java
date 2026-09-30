package it.gruppoinit.pal.gp.core.features.commissioni.upgr;

public class CdsAppelloBean {

    private String idcomune;
    private Integer codicesoggetto;
    private String note;
    private String tipo;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getCodicesoggetto() {

	return codicesoggetto;
    }

    public void setCodicesoggetto(Integer codicesoggetto) {

	this.codicesoggetto = codicesoggetto;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public String getTipo() {

	return tipo;
    }

    public void setTipo(String tipo) {

	this.tipo = tipo;
    }

    public boolean isAmministrazioni() {

	return tipo.equalsIgnoreCase("amministrazioni");
    }
}
