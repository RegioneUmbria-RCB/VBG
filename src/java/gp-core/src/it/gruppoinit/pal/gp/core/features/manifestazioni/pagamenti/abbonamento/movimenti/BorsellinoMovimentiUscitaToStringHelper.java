package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti;

public class BorsellinoMovimentiUscitaToStringHelper implements IBorsellinoMovimentiToStringHelper {

    private String testo;
    public static final TipoEnum TIPO = TipoEnum.USCITA;

    public BorsellinoMovimentiUscitaToStringHelper(String mercato, String giorno, String posteggio, String autorizzazione) {

	this.testo = BorsellinoMovimentiUscitaToStringHelper.TIPO.name() + ": " + mercato + ", " + giorno + ", Posteggio " + posteggio +
		     ", Autorizzazione " + autorizzazione;
    }

    @Override
    public String movimentoToString() {

	return testo;
    }
}
