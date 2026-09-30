/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain;

import java.math.BigDecimal;

/**
 * @author gianpaolot Oggetto che raccoglie le proprietà per cui filtra la vista delle rate non pagate
 */
public class RateNonpagateFilter implements java.io.Serializable {

    /**
     * questo oggetto lo serializzo su db
     */
    private static final long serialVersionUID = -2342286473865440794L;
    private Short anno;
    private RegistrazioniCausali registrazioniCausali;
    private Mercati mercati;
    private MercatiUso mercatiUso;
    private Integer rateNonPagate;
    private BigDecimal importoDaIncassareInf;
    private BigDecimal importoDaIncassareSup;

    public RateNonpagateFilter() {

	this.importoDaIncassareInf = new BigDecimal(0);
	this.registrazioniCausali = new RegistrazioniCausali();
	this.mercati = new Mercati();
	this.mercatiUso = new MercatiUso();
    }

    public Short getAnno() {

	return anno;
    }

    public void setAnno(Short anno) {

	this.anno = anno;
    }

    public RegistrazioniCausali getRegistrazioniCausali() {

	return registrazioniCausali;
    }

    public void setRegistrazioniCausali(RegistrazioniCausali registrazioniCausali) {

	this.registrazioniCausali = registrazioniCausali;
    }

    public Mercati getMercati() {

	return mercati;
    }

    public void setMercati(Mercati mercati) {

	this.mercati = mercati;
    }

    public MercatiUso getMercatiUso() {

	return mercatiUso;
    }

    public void setMercatiUso(MercatiUso mercatiUso) {

	this.mercatiUso = mercatiUso;
    }

    public Integer getRateNonPagate() {

	return rateNonPagate;
    }

    public void setRateNonPagate(Integer rateNonPagate) {

	this.rateNonPagate = rateNonPagate;
    }

    public BigDecimal getImportoDaIncassareInf() {

	return importoDaIncassareInf;
    }

    public void setImportoDaIncassareInf(BigDecimal importoDaIncassareInf) {

	this.importoDaIncassareInf = importoDaIncassareInf;
    }

    public BigDecimal getImportoDaIncassareSup() {

	return importoDaIncassareSup;
    }

    public void setImportoDaIncassareSup(BigDecimal importoDaIncassareSup) {

	this.importoDaIncassareSup = importoDaIncassareSup;
    }
}
