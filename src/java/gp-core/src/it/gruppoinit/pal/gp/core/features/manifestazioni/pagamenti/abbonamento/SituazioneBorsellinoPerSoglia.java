package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import java.math.BigDecimal;

public class SituazioneBorsellinoPerSoglia {

    private Integer id;
    private boolean sottosoglia;
    private BigDecimal totale;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public boolean isSottosoglia() {

	return sottosoglia;
    }

    public void setSottosoglia(boolean sottosoglia) {

	this.sottosoglia = sottosoglia;
    }

    public BigDecimal getTotale() {

	return totale;
    }

    public void setTotale(BigDecimal totale) {

	this.totale = totale;
    }
}
