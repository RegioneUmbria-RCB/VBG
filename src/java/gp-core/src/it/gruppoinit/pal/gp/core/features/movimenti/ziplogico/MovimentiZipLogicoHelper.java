package it.gruppoinit.pal.gp.core.features.movimenti.ziplogico;

import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogico;

public class MovimentiZipLogicoHelper {

    private MovimentiZipLogico movimentiZipLogico;

    public MovimentiZipLogicoHelper(MovimentiZipLogico movimentiZipLogico) {

	if (movimentiZipLogico == null) {
	    throw new IllegalArgumentException(
		    "Impossibile invocare MovimentiZipLogicoHelper(MovimentiZipLogico movimentiZipLogico) passando un valore null come parametro");
	}
	this.movimentiZipLogico = movimentiZipLogico;
    }

    public MovimentiZipLogico getMovimentiZipLogico() {

	return movimentiZipLogico;
    }

    public Integer getCodiceOggetto() {

	return this.movimentiZipLogico.getOggetti().getId().getCodice();
    }
}
