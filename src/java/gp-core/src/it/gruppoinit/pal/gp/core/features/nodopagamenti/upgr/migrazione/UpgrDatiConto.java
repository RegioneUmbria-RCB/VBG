package it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr.migrazione;

import java.util.Date;

public class UpgrDatiConto {

    private String cfcodiceprofilo;
    private String idcomune;
    private Integer id;
    private String codiceconto;
    private String datiriscossione;
    private String descrizione;
    private String note;
    private Integer iva;
    private Integer annoaccertamento;
    private String numeroaccertamento;
    private Date datascadenza;
    private String numerosottoaccertamento;
    private String codicetassonomia;
    private String codiceversamento;
    private Boolean attivo;

    public String getCfcodiceprofilo() {

	return cfcodiceprofilo;
    }

    public void setCfcodiceprofilo(String cfcodiceprofilo) {

	this.cfcodiceprofilo = cfcodiceprofilo;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getCodiceconto() {

	return codiceconto;
    }

    public void setCodiceconto(String codiceconto) {

	this.codiceconto = codiceconto;
    }

    public String getDatiriscossione() {

	return datiriscossione;
    }

    public void setDatiriscossione(String datiriscossione) {

	this.datiriscossione = datiriscossione;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public Integer getIva() {

	return iva;
    }

    public void setIva(Integer iva) {

	this.iva = iva;
    }

    public Integer getAnnoaccertamento() {

	return annoaccertamento;
    }

    public void setAnnoaccertamento(Integer annoaccertamento) {

	this.annoaccertamento = annoaccertamento;
    }

    public String getNumeroaccertamento() {

	return numeroaccertamento;
    }

    public void setNumeroaccertamento(String numeroaccertamento) {

	this.numeroaccertamento = numeroaccertamento;
    }

    public Date getDatascadenza() {

	return datascadenza;
    }

    public void setDatascadenza(Date datascadenza) {

	this.datascadenza = datascadenza;
    }

    public String getNumerosottoaccertamento() {

	return numerosottoaccertamento;
    }

    public void setNumerosottoaccertamento(String numerosottoaccertamento) {

	this.numerosottoaccertamento = numerosottoaccertamento;
    }

    public String getCodicetassonomia() {

	return codicetassonomia;
    }

    public void setCodicetassonomia(String codicetassonomia) {

	this.codicetassonomia = codicetassonomia;
    }

    public String getCodiceversamento() {

	return codiceversamento;
    }

    public void setCodiceversamento(String codiceversamento) {

	this.codiceversamento = codiceversamento;
    }

    public Boolean getAttivo() {

	return attivo;
    }

    public void setAttivo(Boolean attivo) {

	this.attivo = attivo;
    }

    @Override
    public String toString() {

	return "[idcomune: " + idcomune + ", id: " + id + ", descrizione: " + descrizione + "]";
    }
}
