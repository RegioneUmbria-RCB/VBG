package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.math.BigDecimal;
import java.util.Date;

public class DettaglioRateizzazione {

    private Integer numeroRata;
    private Date scadenza;
    private BigDecimal importoTotale;

    public Integer getNumeroRata() {

	return numeroRata;
    }

    public void setNumeroRata(Integer numeroRata) {

	this.numeroRata = numeroRata;
    }

    public Date getScadenza() {

	return scadenza;
    }

    public void setScadenza(Date scadenza) {

	this.scadenza = scadenza;
    }

    public BigDecimal getImportoTotale() {

	return importoTotale;
    }

    public void setImportoTotale(BigDecimal importoTotale) {

	this.importoTotale = importoTotale;
    }
}
