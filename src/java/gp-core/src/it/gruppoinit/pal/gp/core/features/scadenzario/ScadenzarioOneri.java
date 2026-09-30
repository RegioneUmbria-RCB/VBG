package it.gruppoinit.pal.gp.core.features.scadenzario;

import java.math.BigDecimal;
import java.util.Date;

public class ScadenzarioOneri {

    public static final String SCADENZARIO_ONERI = "SCADENZARIO_ONERI";
    public static final String GIORNI_DA_DATA_ATTUALE = "GIORNI_DA_DATA_ATTUALE";
    private Date datascadenza;
    private String numeroistanza;
    private String richiedente;
    private String descrizione;
    private BigDecimal totale;
    private BigDecimal totalepagato;
    private Integer codiceistanza;
    private String codicestc;

    public String getRichiedente() {

	return richiedente;
    }

    public void setRichiedente(String richiedente) {

	this.richiedente = richiedente;
    }

    public BigDecimal getTotale() {

	return totale;
    }

    public void setTotale(BigDecimal totale) {

	this.totale = totale;
    }

    public Date getDatascadenza() {

	return datascadenza;
    }

    public void setDatascadenza(Date datascadenza) {

	this.datascadenza = datascadenza;
    }

    public String getNumeroistanza() {

	return numeroistanza;
    }

    public void setNumeroistanza(String numeroistanza) {

	this.numeroistanza = numeroistanza;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public BigDecimal getTotalepagato() {

	return totalepagato;
    }

    public void setTotalepagato(BigDecimal totalepagato) {

	this.totalepagato = totalepagato;
    }

    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    public String getCodicestc() {

	return codicestc;
    }

    public void setCodicestc(String codicestc) {

	this.codicestc = codicestc;
    }
}
