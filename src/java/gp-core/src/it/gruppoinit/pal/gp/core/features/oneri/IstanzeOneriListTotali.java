package it.gruppoinit.pal.gp.core.features.oneri;

import java.math.BigDecimal;

import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;

public class IstanzeOneriListTotali {

    private IstanzeOneriImporto entrate = new IstanzeOneriImporto(BigDecimal.ZERO, BigDecimal.ZERO);
    private IstanzeOneriImporto uscite = new IstanzeOneriImporto(BigDecimal.ZERO, BigDecimal.ZERO);
    private IstanzeOneriImporto incassato = new IstanzeOneriImporto(BigDecimal.ZERO, BigDecimal.ZERO);
    private IstanzeOneriImporto riversato = new IstanzeOneriImporto(BigDecimal.ZERO, BigDecimal.ZERO);

    public IstanzeOneriImporto getEntrate() {

	return entrate;
    }

    public IstanzeOneriImporto getUscite() {

	return uscite;
    }

    public IstanzeOneriImporto getIncassato() {

	return incassato;
    }

    public IstanzeOneriImporto getRiversato() {

	return riversato;
    }

    public BigDecimal getSaldoEntrateIncassato() {

	return this.entrate.getImportoComplessivo().subtract(this.incassato.getImportoComplessivo());
    }

    public BigDecimal getSaldoUsciteRiversato() {

	return this.uscite.getImportoComplessivo().subtract(this.riversato.getImportoComplessivo());
    }

    public BigDecimal getEntrateCausale() {

	return this.entrate.getImportoCausale();
    }

    public BigDecimal getEntrateIstruttoria() {

	return this.entrate.getImportoIstruttoria();
    }

    public void aggiungiEntrata(Istanzeoneri onere) {

	this.entrate = this.aggiungi(this.entrate, onere);
    }

    public void aggiungiUscita(Istanzeoneri onere) {

	this.uscite = this.aggiungi(this.uscite, onere);
    }

    public void aggiungiIncassato(Istanzeoneri onere) {

	if (this.incassato == null) {
	    this.incassato = new IstanzeOneriImporto(onere.getImportopagato(), BigDecimal.ZERO);
	}
	this.incassato.aggiungiImportoCausale(onere.getImportopagato());
	this.aggiungiEntrata(onere);
    }

    public void aggiungiRiversato(Istanzeoneri onere) {

	this.riversato = this.aggiungi(this.riversato, onere);
	this.aggiungiUscita(onere);
    }

    private IstanzeOneriImporto aggiungi(IstanzeOneriImporto importoPartenza, Istanzeoneri onere) {

	if (importoPartenza == null) {
	    return new IstanzeOneriImporto(onere.getPrezzo(), onere.getPrezzoistruttoria());
	}
	IstanzeOneriImporto retVal = importoPartenza;
	if (onere.getPrezzo() != null) {
	    retVal.aggiungiImportoCausale(onere.getPrezzo());
	}
	if (onere.getPrezzoistruttoria() != null) {
	    retVal.aggiungiImportoIstruttoria(onere.getPrezzoistruttoria());
	}
	return retVal;
    }
}
