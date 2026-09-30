package it.gruppoinit.pal.gp.core.features.rateizzazioni;

import java.math.BigDecimal;
import java.util.Date;

public class IstanzeoneriDerateizzatoBean {

    private BigDecimal prezzo;
    private BigDecimal interesse;
    private BigDecimal prezzoistruttoria;
    private Date datascadenza;
    private Date data;
    private Integer fkcanonetestata;

    public BigDecimal getPrezzo() {

	return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {

	this.prezzo = prezzo;
    }

    public BigDecimal getInteresse() {

	return interesse;
    }

    public void setInteresse(BigDecimal interesse) {

	this.interesse = interesse;
    }

    public BigDecimal getPrezzoistruttoria() {

	return prezzoistruttoria;
    }

    public void setPrezzoistruttoria(BigDecimal prezzoistruttoria) {

	this.prezzoistruttoria = prezzoistruttoria;
    }

    public Date getDatascadenza() {

	return datascadenza;
    }

    public void setDatascadenza(Date datascadenza) {

	this.datascadenza = datascadenza;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public Integer getFkcanonetestata() {

	return fkcanonetestata;
    }

    public void setFkcanonetestata(Integer fkcanonetestata) {

	this.fkcanonetestata = fkcanonetestata;
    }
}
