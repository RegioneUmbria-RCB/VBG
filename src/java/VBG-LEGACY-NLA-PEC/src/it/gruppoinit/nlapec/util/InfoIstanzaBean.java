package it.gruppoinit.nlapec.util;

import java.util.Date;

public class InfoIstanzaBean {

    private String codiceIstanza;
    private String numeroIstanza;
    private String oggetto;
    private Date dataPratica;
    private String codiceMovimento;
    private String tipoMovimento;
    private String codiceInventarioProcedimento;
    private String idcomune;
    private String software;

    public String getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(String codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public String getCodiceMovimento() {

	return codiceMovimento;
    }

    public void setCodiceMovimento(String codiceMovimento) {

	this.codiceMovimento = codiceMovimento;
    }

    public String getTipoMovimento() {

	return tipoMovimento;
    }

    public void setTipoMovimento(String tipoMovimento) {

	this.tipoMovimento = tipoMovimento;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getNumeroIstanza() {

	return numeroIstanza;
    }

    public void setNumeroIstanza(String numeroIstanza) {

	this.numeroIstanza = numeroIstanza;
    }

    public String getOggetto() {

	return oggetto;
    }

    public void setOggetto(String oggetto) {

	this.oggetto = oggetto;
    }

    public Date getDataPratica() {

	return dataPratica;
    }

    public void setDataPratica(Date dataPratica) {

	this.dataPratica = dataPratica;
    }

    public String getCodiceInventarioProcedimento() {

	return codiceInventarioProcedimento;
    }

    public void setCodiceInventarioProcedimento(String codiceInventarioProcedimento) {

	this.codiceInventarioProcedimento = codiceInventarioProcedimento;
    }
}
