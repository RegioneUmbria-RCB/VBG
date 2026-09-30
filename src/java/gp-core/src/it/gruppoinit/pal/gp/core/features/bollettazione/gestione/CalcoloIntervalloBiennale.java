package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.Date;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;

public class CalcoloIntervalloBiennale extends CalcoloIntervalloStrategyBase {

    public CalcoloIntervalloBiennale() {

	throw new NotImplementedException("Il tipo di calcolo biennale non è supportato nella bollettazione");
    }

    @Override
    protected Date calcolaDataInizio(Date dataInizioOperazione) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    protected Date calcolaDataFine(Date dataInizioOperazione) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Date calcolaDataInizioOperazioneDaDataRiferimento(TIPO_PERIODO periodo, Date dataRiferimento) {

	// TODO Auto-generated method stub
	return null;
    }
}
