package it.gruppoinit.pal.gp.core.features.istanze.eventi.messaggi;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;

public class CausaleImportoOnerePerMessaggio {

    private String causale;
    private BigDecimal importo;

    public static CausaleImportoOnerePerMessaggio daIstanzeOneri(Istanzeoneri istanzeoneri) {

	return new CausaleImportoOnerePerMessaggio(istanzeoneri.getTipicausalioneri().getCoDescrizione(), istanzeoneri.getPrezzo());
    }

    public CausaleImportoOnerePerMessaggio(String causale, BigDecimal importo) {

	this.causale = causale;
	this.importo = importo;
    }

    @Override
    public String toString() {

	DecimalFormat df2 = new DecimalFormat("#,###,###,##0.00");
	return "- " + this.causale + ": € " + df2.format(this.importo);
    }
}
