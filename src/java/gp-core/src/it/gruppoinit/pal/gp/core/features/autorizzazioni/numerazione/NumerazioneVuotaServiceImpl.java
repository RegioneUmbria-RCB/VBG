package it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione;

public class NumerazioneVuotaServiceImpl implements NumerazioneService {

    public static NumerazioneEnum TipoNumerazione = NumerazioneEnum.VUOTO;

    @Override
    public EstremiAutorizzazione get() {

	return new EstremiAutorizzazione(null, null, null);
    }

    @Override
    public EstremiAutorizzazione assegnaNumero() {

	return new EstremiAutorizzazione(null, null, null);
    }
}
