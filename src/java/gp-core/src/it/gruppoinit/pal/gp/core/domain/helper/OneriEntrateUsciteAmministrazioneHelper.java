package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;

import java.math.BigDecimal;

/**
 * La classe serve per memorizzare gli oneri in uscita e in ingresso associati all'istanza per una specifica
 * amministrazione
 * 
 * @author gianpaolot
 * 
 */
public class OneriEntrateUsciteAmministrazioneHelper {

    private Amministrazioni amministrazione;
    private BigDecimal totaleUscitePerAmministrazione;
    private BigDecimal totaleEntratePerAmministrazione;
    private BigDecimal disavanzo;

    public Amministrazioni getAmministrazione() {

	return amministrazione;
    }

    public void setAmministrazione(Amministrazioni amministrazione) {

	this.amministrazione = amministrazione;
    }

    public BigDecimal getTotaleUscitePerAmministrazione() {

	return totaleUscitePerAmministrazione;
    }

    public void setTotaleUscitePerAmministrazione(BigDecimal totaleUscitePerAmministrazione) {

	this.totaleUscitePerAmministrazione = totaleUscitePerAmministrazione;
    }

    public BigDecimal getTotaleEntratePerAmministrazione() {

	return totaleEntratePerAmministrazione;
    }

    public void setTotaleEntratePerAmministrazione(BigDecimal totaleEntratePerAmministrazione) {

	this.totaleEntratePerAmministrazione = totaleEntratePerAmministrazione;
    }

    public BigDecimal getDisavanzo() {

	return disavanzo;
    }

    public void setDisavanzo(BigDecimal disavanzo) {

	this.disavanzo = disavanzo;
    }
}
