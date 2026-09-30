/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Oggetto del dominio utilizzato per settare gli importi rateizzati con la data di scadenza appropriata
 * 
 * @author francescop
 * @author gianpaolot
 * 
 */
public class ImportiRateizzati {

    private BigDecimal importoRateizzato;
    private BigDecimal importoInteresse;
    private BigDecimal importoRateizzatoSenzaInteresse;
    private Date scadenza;
    private Integer numerorata;

    public Integer getNumerorata() {

	return numerorata;
    }

    public void setNumerorata(Integer numerorata) {

	this.numerorata = numerorata;
    }

    public BigDecimal getImportoRateizzato() {

	return importoRateizzato;
    }

    public void setImportoRateizzato(BigDecimal importoRateizzato) {

	this.importoRateizzato = importoRateizzato;
    }

    public BigDecimal getImportoInteresse() {

	return importoInteresse;
    }

    public void setImportoInteresse(BigDecimal importoInteresse) {

	this.importoInteresse = importoInteresse;
    }

    public BigDecimal getImportoRateizzatoSenzaInteresse() {

	return importoRateizzatoSenzaInteresse;
    }

    public void setImportoRateizzatoSenzaInteresse(BigDecimal importoRateizzatoSenzaInteresse) {

	this.importoRateizzatoSenzaInteresse = importoRateizzatoSenzaInteresse;
    }

    public Date getScadenza() {

	return scadenza;
    }

    public void setScadenza(Date scadenza) {

	this.scadenza = scadenza;
    }
}
