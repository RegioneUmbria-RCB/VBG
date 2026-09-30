package it.gruppoinit.pal.gp.pay.tracciati.nexi.genova;

import java.math.BigDecimal;

public class RipartizioneHelper {

    private TracciatoRecordRipartizione tracciato;
    private BigDecimal importo;
    private String erroreDebito;

    public RipartizioneHelper(TracciatoRecordRipartizione tracciato, BigDecimal importo) {

	super();
	if (tracciato == null || importo == null) {
	    throw new IllegalArgumentException("Il campo importo o il campo tracciato non possono essere nulli");
	}
	this.tracciato = tracciato;
	this.importo = importo;
    }

    public void addImporto(BigDecimal augend) {

	if (augend == null) {
	    throw new IllegalArgumentException("Il campo importo non può essere nulli");
	}
	if (this.importo == null) {
	    this.importo = BigDecimal.ZERO;
	}
	this.importo = this.importo.add(augend);
    }

    public BigDecimal getImporto() {

	if (this.importo == null) {
	    this.importo = BigDecimal.ZERO;
	}
	return importo;
    }

    public TracciatoRecordRipartizione getTracciato() {

	return tracciato;
    }

    public void setErroreDebito(String erroreDebito) {

	this.erroreDebito = erroreDebito;
    }

    public String getErroreDebito() {

	return erroreDebito;
    }
}
