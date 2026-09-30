package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.Date;

public class DocumentazioneIstanzaHelper {

    private String nomeFile;
    private String descrizione;
    private Date dataInserimento;
    private String dataInserimentoString;
    private String endoprocedimento;
    private Boolean richiesto;
    private String descRichiesto;
    private Boolean presente;
    private String descPresente;
    private Integer valido;
    private String descValido;

    public DocumentazioneIstanzaHelper() {

	//	this.richiesto = Boolean.FALSE;
	//	this.presente = Boolean.FALSE;
    }

    public String getNomeFile() {

	return nomeFile;
    }

    public void setNomeFile(String nomeFile) {

	this.nomeFile = nomeFile;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public Date getDataInserimento() {

	return dataInserimento;
    }

    public void setDataInserimento(Date dataInserimento) {

	this.dataInserimento = dataInserimento;
    }

    public String getDataInserimentoString() {

	if (this.dataInserimento != null) {
	    String d = Utilities.formatDate(this.dataInserimento, false);
	    dataInserimentoString = d;
	}
	return dataInserimentoString;
    }

    public void setDataInserimentoString(String dataInserimentoString) {

	this.dataInserimentoString = dataInserimentoString;
    }

    public String getEndoprocedimento() {

	return endoprocedimento;
    }

    public void setEndoprocedimento(String endoprocedimento) {

	this.endoprocedimento = endoprocedimento;
    }

    public Boolean getRichiesto() {

	return richiesto;
    }

    public void setRichiesto(Boolean richiesto) {

	this.richiesto = richiesto;
    }

    public String getDescRichiesto() {

	if (this.richiesto != null) {
	    this.descRichiesto = this.richiesto ? "Si" : "No";
	}
	return descRichiesto;
    }

    public void setDescRichiesto(String descRichiesto) {

	this.descRichiesto = descRichiesto;
    }

    public Boolean getPresente() {

	return presente;
    }

    public void setPresente(Boolean presente) {

	this.presente = presente;
    }

    public String getDescPresente() {

	if (this.presente != null) {
	    this.descPresente = this.presente ? "Si" : "No";
	}
	return descPresente;
    }

    public void setDescPresente(String descPresente) {

	this.descPresente = descPresente;
    }

    public Integer getValido() {

	return valido;
    }

    public void setValido(Integer valido) {

	this.valido = valido;
    }

    public String getDescValido() {

	this.descValido = "Non verificato";
	if (this.valido != null) {
	    this.descValido = this.valido.equals(1) ? "Valido" : "Non valido";
	}
	return descValido;
    }

    public void setDescValido(String descValido) {

	this.descValido = descValido;
    }
}
