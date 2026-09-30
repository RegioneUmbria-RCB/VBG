package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti;

public class BorsellinoMovimentiStornoToStringHelper implements IBorsellinoMovimentiToStringHelper {

    private String testo;
    public static final TipoEnum TIPO = TipoEnum.STORNO;

    public BorsellinoMovimentiStornoToStringHelper(String mercato, String giorno, String posteggio, String autorizzazione) {

	this.testo = BorsellinoMovimentiStornoToStringHelper.TIPO
		.name() + ": " + mercato + ", " + giorno + ", Posteggio " + posteggio + ", Autorizzazione " + autorizzazione;
    }

    @Override
    public String movimentoToString() {

	return testo;
    }
}
