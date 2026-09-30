package it.gruppoinit.pal.gp.core.features.bollettazione;

public class BollettazioneNonConsentitaException extends Exception {

    private static final long serialVersionUID = -1079923026111656245L;

    public BollettazioneNonConsentitaException(String erroriValidazione) {

	super(erroriValidazione);
    }
}
