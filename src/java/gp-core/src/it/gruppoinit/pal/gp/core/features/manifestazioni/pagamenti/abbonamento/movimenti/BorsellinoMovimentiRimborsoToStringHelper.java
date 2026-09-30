package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti;

public class BorsellinoMovimentiRimborsoToStringHelper implements IBorsellinoMovimentiToStringHelper {

    private String testo;
    public static final TipoEnum TIPO = TipoEnum.RIMBORSO;

    public BorsellinoMovimentiRimborsoToStringHelper() {
	this.testo = BorsellinoMovimentiRimborsoToStringHelper.TIPO.name();	
    }

    @Override
    public String movimentoToString() {

	return testo;
    }
}