/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain;

import java.util.Date;

/**
 * @author francescop
 * 
 */
public class ContoInteressiLegali {

    private Conti conti;
    private Date dataInizio;

    public Conti getConti() {

	return conti;
    }

    public void setConti(Conti conti) {

	this.conti = conti;
    }

    public Date getDataInizio() {

	return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {

	this.dataInizio = dataInizio;
    }

    public ContoInteressiLegali() {

	this.conti = new Conti();
    }
}
