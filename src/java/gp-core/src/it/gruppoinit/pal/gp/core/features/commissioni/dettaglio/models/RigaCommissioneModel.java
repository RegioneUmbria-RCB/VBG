package it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.models;

import java.util.Date;

public class RigaCommissioneModel {

    private Integer id;
    private Integer ordine;
    private Integer codiceIstanza;
    private String numeroIstanza;
    private Integer numeroDocumenti;
    private Date dataPresentazione;
    private String numeroProtocollo;
    private Date dataProtocollo;
    private Integer codiceMovimento;
    private String movimento;
    private Integer codiceMovimentoRientro;
    private String movimentoRientro;
    private String richiedente;
    private String lavori;
    private Date dataRichiesta;
    private String intervento;
    private String tipologiaParere;
    private String comune;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public int getOrdine() {

	return ordine;
    }

    public void setOrdine(int ordine) {

	this.ordine = ordine;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public String getNumeroIstanza() {

	return numeroIstanza;
    }

    public void setNumeroIstanza(String numeroIstanza) {

	this.numeroIstanza = numeroIstanza;
    }

    public Integer getNumeroDocumenti() {

	return numeroDocumenti;
    }

    public void setNumeroDocumenti(Integer numeroDocumenti) {

	if (numeroDocumenti == null) {
	    this.numeroDocumenti = 0;
	} else {
	    this.numeroDocumenti = numeroDocumenti;
	}
    }

    public Date getDataPresentazione() {

	return dataPresentazione;
    }

    public void setDataPresentazione(Date dataPresentazione) {

	this.dataPresentazione = dataPresentazione;
    }

    public String getNumeroProtocollo() {

	return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {

	this.numeroProtocollo = numeroProtocollo;
    }

    public Date getDataProtocollo() {

	return dataProtocollo;
    }

    public void setDataProtocollo(Date dataProtocollo) {

	this.dataProtocollo = dataProtocollo;
    }

    public Integer getCodiceMovimento() {

	return codiceMovimento;
    }

    public void setCodiceMovimento(Integer codiceMovimento) {

	this.codiceMovimento = codiceMovimento;
    }

    public String getMovimento() {

	return movimento;
    }

    public Integer getCodiceMovimentoRientro() {

	return codiceMovimentoRientro;
    }

    public void setCodiceMovimentoRientro(Integer codiceMovimentoRientro) {

	this.codiceMovimentoRientro = codiceMovimentoRientro;
    }

    public void setMovimento(String movimento) {

	this.movimento = movimento;
    }

    public String getMovimentoRientro() {

	return movimentoRientro;
    }

    public void setMovimentoRientro(String movimentoRientro) {

	this.movimentoRientro = movimentoRientro;
    }

    public String getRichiedente() {

	return richiedente;
    }

    public void setRichiedente(String richiedente) {

	this.richiedente = richiedente;
    }

    public String getLavori() {

	return lavori;
    }

    public void setLavori(String lavori) {

	this.lavori = lavori;
    }

    public Date getDataRichiesta() {

	return dataRichiesta;
    }

    public void setDataRichiesta(Date dataRichiesta) {

	this.dataRichiesta = dataRichiesta;
    }

    public String getIntervento() {

	return intervento;
    }

    public void setIntervento(String intervento) {

	this.intervento = intervento;
    }

    public String getTipologiaParere() {

	return tipologiaParere;
    }

    public void setTipologiaParere(String tipologiaParere) {

	this.tipologiaParere = tipologiaParere;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public String getMessaggioNumeroDocumenti() {

	switch (this.numeroDocumenti) {
	case 0:
	    return "Nessun documento selezionato";
	case 1:
	    return "1 documento selezionato";
	default:
	    return this.numeroDocumenti + " documenti selezionati";
	}
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((ordine == null) ? 0 : ordine.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	RigaCommissioneModel other = (RigaCommissioneModel) obj;
	if (ordine == null) {
	    if (other.ordine != null)
		return false;
	} else if (!ordine.equals(other.ordine))
	    return false;
	return true;
    }
}
