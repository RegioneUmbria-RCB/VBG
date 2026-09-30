package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti;

import org.apache.commons.lang.StringUtils;

public class BorsellinoMovimentiRicaricaToStringHelper implements IBorsellinoMovimentiToStringHelper {

    private String testo;
    public static final TipoEnum TIPO = TipoEnum.RICARICA;

    public BorsellinoMovimentiRicaricaToStringHelper(String posizioneDebitoria) {

	this.testo = BorsellinoMovimentiRicaricaToStringHelper.TIPO.name();
	if (StringUtils.isNotBlank(posizioneDebitoria)) {
	    this.testo += ": (" + posizioneDebitoria + ")";
	}
    }

    @Override
    public String movimentoToString() {

	return testo;
    }
}