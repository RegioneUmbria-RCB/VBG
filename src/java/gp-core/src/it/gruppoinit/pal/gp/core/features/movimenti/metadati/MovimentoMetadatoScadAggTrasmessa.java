package it.gruppoinit.pal.gp.core.features.movimenti.metadati;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.MovimentiMetadati;
import it.gruppoinit.pal.gp.core.domain.MovimentiMetadatiId;

public class MovimentoMetadatoScadAggTrasmessa extends AbstractMovimentiMetadato {

    public static final String NOME_METADATO = "SCADENZA_AGGIORNATA_TRAMESSA_A_RABBIT";

    public enum VALORI_AMMESSI {

	TRASMESSO("1"),
	NON_TRASMESSO("0");

	private String valore;

	private VALORI_AMMESSI(String valore) {

	    this.valore = valore;
	}

	public String getValore() {

	    return valore;
	}
    }

    public static MovimentiMetadati fromMovimento(Integer codiceMovimento, VALORI_AMMESSI valore) {

	MovimentiMetadati md = new MovimentiMetadati();
	MovimentiMetadatiId id = new MovimentiMetadatiId(ORMHelper.getIdcomune(), codiceMovimento, NOME_METADATO);
	md.setId(id);
	md.setValore(valore.getValore());
	return md;
    }

    @Override
    public String getChiave() {

	return NOME_METADATO;
    }
}
