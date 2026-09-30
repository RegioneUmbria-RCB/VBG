package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze;

import java.util.Date;

public class DataScadenzaResolver implements IDataScadenzaResolver {

    private ITipoScadenzaResolver resolver;

    public DataScadenzaResolver(TipoScadenzaEnum tipoScadenza, Date dataPartenza, int numeroRata, String periodo) {

	switch (tipoScadenza) {
	case FINE_MESE:
	    this.resolver = new TipoScadenzaFineMeseResolver(dataPartenza);
	    break;
	case FINE_MESE_ESCLUSA_PRIMA_RATA:
	    this.resolver = new TipoScadenzaFineMeseEsclusaPrimaRataResolver(dataPartenza, numeroRata);
	    break;
	case FINE_MESE_SUCCESSIVO:
	    this.resolver = new TipoScadenzaFineMeseSuccessivoResolver(dataPartenza);
	    break;
	case INIZIO_MESE:
	    this.resolver = new TipoScadenzaInizioMeseResolver(dataPartenza);
	    break;
	case LASCIA_INALTERATO:
	    this.resolver = new TipoScadenzaLasciaInalteratoResolver(dataPartenza);
	    break;
	case MESE_SUCCESSIVO_IL_GIORNO_15:
	    this.resolver = new TipoScadenzaQuindiciMeseSuccessivoResolver(dataPartenza);
	    break;
	case MESE_SUCCESSIVO_IL_GIORNO_15_ESCLUSA_PRIMA_RATA:
	    this.resolver = new TipoScadenzaQuindiciMeseSuccessivoEsclusaPrimaRataResolver(dataPartenza, numeroRata);
	    break;
	case QUINDICI_DEL_MESE:
	    this.resolver = new TipoScadenzaQuindiciMeseResolver(dataPartenza);
	    break;
	case QUINDICI_DEL_MESE_ESCLUSA_PRIMA_RATA:
	    this.resolver = new TipoScadenzaQuindiciMeseEsclusaPrimaRataResolver(dataPartenza, numeroRata);
	    break;
	case SCADENZA_PERIODICA_FISSA:
	    this.resolver = new TipoScadenzaPeriodica(dataPartenza, periodo, numeroRata);
	    break;
	case VENTI_DEL_MESE:    
	    this.resolver = new TipoScadenzaGiornoDelMeseResolver(dataPartenza, 20);
	    break;
	default:
	    throw new IllegalArgumentException("La tipologia di scadenza " + tipoScadenza + " non è gestita");
	}
    }

    @Override
    public Date calcolaScadenza() {

	return this.resolver.getScadenza();
    }
}
