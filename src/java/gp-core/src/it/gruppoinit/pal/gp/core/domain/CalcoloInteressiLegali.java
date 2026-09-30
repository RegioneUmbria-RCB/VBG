/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author francescop
 * 
 */
public class CalcoloInteressiLegali {

    private BigDecimal importo;
    private Date dataInizio;
    private Date dataFine;
    private BigDecimal tassoPercentuale;
    private BigDecimal interessi;
    private Integer giorni;

    public CalcoloInteressiLegali() {

	this.importo = new BigDecimal(0);
	this.tassoPercentuale = new BigDecimal(0);
	this.interessi = new BigDecimal(0);
    }

    public BigDecimal getImporto() {

	return importo;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }

    public Date getDataInizio() {

	return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {

	this.dataInizio = dataInizio;
    }

    public Date getDataFine() {

	return dataFine;
    }

    public void setDataFine(Date dataFine) {

	this.dataFine = dataFine;
    }

    public BigDecimal getTassoPercentuale() {

	return tassoPercentuale;
    }

    public void setTassoPercentuale(BigDecimal tassoPercentuale) {

	this.tassoPercentuale = tassoPercentuale;
    }

    public BigDecimal getInteressi() {

	return interessi;
    }

    public void setInteressi(BigDecimal interessi) {

	this.interessi = interessi;
    }

    public Integer getGiorni() {

	return giorni;
    }

    public void setGiorni(Integer giorni) {

	this.giorni = giorni;
    }
}
