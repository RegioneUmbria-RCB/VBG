package it.gruppoinit.pal.gp.core.features.manifestazioni.formule;

public class ValidazioneFormulaFallitaException extends Exception {

    private static final long serialVersionUID = -799415984140164351L;

    public ValidazioneFormulaFallitaException(String erroriValidazione) {

	super(erroriValidazione);
    }
}
