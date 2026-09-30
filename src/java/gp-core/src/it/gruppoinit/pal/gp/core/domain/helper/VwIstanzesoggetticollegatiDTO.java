package it.gruppoinit.pal.gp.core.domain.helper;

import java.io.Serializable;

public class VwIstanzesoggetticollegatiDTO implements Serializable {

    private static final long serialVersionUID = -2604214148682479992L;
    private String tipologia;
    private Integer codiceistanza;
    private Integer codicerichiedente;
    private Integer codiceinvitato;
    private Integer codicetiposoggetto;
    private String idcomune;
    private Integer codiceanagrafecoll;
    private String descrsoggetto;
    private Integer codiceprocuratore;
    private Integer fk_idi_attivita;
    private String software;

    public String getTipologia() {

	return tipologia;
    }

    public void setTipologia(String tipologia) {

	this.tipologia = tipologia;
    }

    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    public Integer getCodicerichiedente() {

	return codicerichiedente;
    }

    public void setCodicerichiedente(Integer codicerichiedente) {

	this.codicerichiedente = codicerichiedente;
    }

    public Integer getCodiceinvitato() {

	return codiceinvitato;
    }

    public void setCodiceinvitato(Integer codiceinvitato) {

	this.codiceinvitato = codiceinvitato;
    }

    public Integer getCodicetiposoggetto() {

	return codicetiposoggetto;
    }

    public void setCodicetiposoggetto(Integer codicetiposoggetto) {

	this.codicetiposoggetto = codicetiposoggetto;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getCodiceanagrafecoll() {

	return codiceanagrafecoll;
    }

    public void setCodiceanagrafecoll(Integer codiceanagrafecoll) {

	this.codiceanagrafecoll = codiceanagrafecoll;
    }

    public String getDescrsoggetto() {

	return descrsoggetto;
    }

    public void setDescrsoggetto(String descrsoggetto) {

	this.descrsoggetto = descrsoggetto;
    }

    public Integer getCodiceprocuratore() {

	return codiceprocuratore;
    }

    public void setCodiceprocuratore(Integer codiceprocuratore) {

	this.codiceprocuratore = codiceprocuratore;
    }

    public Integer getFk_idi_attivita() {

	return fk_idi_attivita;
    }

    public void setFk_idi_attivita(Integer fk_idi_attivita) {

	this.fk_idi_attivita = fk_idi_attivita;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }
}
