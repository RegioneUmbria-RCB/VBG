package it.gruppoinit.pal.gp.core.features.oneri;

import java.math.BigDecimal;
import java.util.Date;

public class CalcolaInteressiDiMoraBean {

    Integer idtipicausaliinteressi;
    Date datapagamento;
    Date datascadenza;
    BigDecimal prezzo;
    Integer idfiglia;
    Integer codiceistanza;
    String messaggio;

    public Integer getIdtipicausaliinteressi() {

	return idtipicausaliinteressi;
    }

    public void setIdtipicausaliinteressi(Integer idtipicausaliinteressi) {

	this.idtipicausaliinteressi = idtipicausaliinteressi;
    }

    public Date getDatapagamento() {

	return datapagamento;
    }

    public void setDatapagamento(Date datapagamento) {

	this.datapagamento = datapagamento;
    }

    public Date getDatascadenza() {

	return datascadenza;
    }

    public void setDatascadenza(Date datascadenza) {

	this.datascadenza = datascadenza;
    }

    public BigDecimal getPrezzo() {

	return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {

	this.prezzo = prezzo;
    }

    public Integer getIdfiglia() {

	return idfiglia;
    }

    public void setIdfiglia(Integer idfiglia) {

	this.idfiglia = idfiglia;
    }

    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    public String getMessaggio() {

	return messaggio;
    }

    public void setMessaggio(String messaggio) {

	this.messaggio = messaggio;
    }
}
