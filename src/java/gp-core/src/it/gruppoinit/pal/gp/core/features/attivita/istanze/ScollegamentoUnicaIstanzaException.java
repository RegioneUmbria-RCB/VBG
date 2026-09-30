package it.gruppoinit.pal.gp.core.features.attivita.istanze;

public class ScollegamentoUnicaIstanzaException extends RuntimeException {

    private final String denominazioneAttivita;
    private static final long serialVersionUID = -7469612494078751361L;

    public ScollegamentoUnicaIstanzaException(String denominazioneAttivita, String messaggio) {

	super(messaggio);
	this.denominazioneAttivita = denominazioneAttivita;
    }

    public String getDenominazioneAttivita() {

	return denominazioneAttivita;
    }
}
