package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.MercatiConti;

import java.io.Serializable;
import java.math.BigDecimal;

public class MercatiContiHelper implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2333289546726334283L;
    private Conti contoOld;
    private Conti contoNew;
    private BigDecimal moltiplicatore;
    private MercatiConti mercatiConti;
    private boolean usa = false;

    public MercatiContiHelper() {

	this.contoNew = new Conti();
	this.contoOld = new Conti();
	this.moltiplicatore = new BigDecimal(1);
	this.usa = true;
	this.mercatiConti = new MercatiConti();
    }

    public Conti getContoOld() {

	return contoOld;
    }

    public void setContoOld(Conti contoOld) {

	this.contoOld = contoOld;
    }

    public Conti getContoNew() {

	return contoNew;
    }

    public void setContoNew(Conti contoNew) {

	this.contoNew = contoNew;
    }

    public BigDecimal getMoltiplicatore() {

	return moltiplicatore;
    }

    public void setMoltiplicatore(BigDecimal moltiplicatore) {

	this.moltiplicatore = moltiplicatore;
    }

    public boolean isUsa() {

	return usa;
    }

    public void setUsa(boolean usa) {

	this.usa = usa;
    }

    public MercatiConti getMercatiConti() {

	return mercatiConti;
    }

    public void setMercatiConti(MercatiConti mercatiConti) {

	this.mercatiConti = mercatiConti;
    }
}
