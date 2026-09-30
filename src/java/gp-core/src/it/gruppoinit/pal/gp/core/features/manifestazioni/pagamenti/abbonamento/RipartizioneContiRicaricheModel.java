package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import java.math.BigDecimal;
import java.math.RoundingMode;

import it.gruppoinit.pal.gp.core.domain.Conti;

public class RipartizioneContiRicaricheModel {

    private Conti conto;
    private BigDecimal percentuale;

    public Conti getConto() {

	return conto;
    }

    public void setConto(Conti conto) {

	this.conto = conto;
    }

    public BigDecimal getPercentuale() {

	return percentuale;
    }

    public void setPercentuale(BigDecimal percentuale) {

	this.percentuale = percentuale;
    }

    public static RipartizioneContiRicaricheModel fromParameters(Conti conto, BigDecimal percentuale) {

	RipartizioneContiRicaricheModel ret = new RipartizioneContiRicaricheModel();
	ret.conto = conto;
	ret.percentuale = percentuale;
	return ret;
    }

    public static void main(String[] args) {

	BigDecimal importototale = BigDecimal.valueOf(10.55);
	RipartizioneContiRicaricheModel r = new RipartizioneContiRicaricheModel();
	r.percentuale = BigDecimal.valueOf(65, 0);
	BigDecimal valore1 = r.calcolaImportoDaTotale(importototale);
	System.out.println(valore1);
	r.percentuale = BigDecimal.valueOf(35, 0);
	BigDecimal valore2 = r.calcolaImportoDaTotale(importototale);
	System.out.println(valore2);
	System.out.println(valore1.add(valore2));
    }

    public BigDecimal calcolaImportoDaTotale(BigDecimal importoTotale) {

	return importoTotale.multiply(BigDecimal.valueOf(percentuale.setScale(2).doubleValue() / 100)).setScale(2, RoundingMode.HALF_EVEN);
    }
}
