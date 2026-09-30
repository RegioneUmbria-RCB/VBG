package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti;

import java.util.HashMap;
import java.util.Map;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimenti;

public class BorsellinoMovimentiToStringFactory {

    private TipoEnum tipo;
    private Map<TipoEnum, IBorsellinoMovimentiToStringHelper> resolvers = new HashMap<TipoEnum, IBorsellinoMovimentiToStringHelper>();

    private BorsellinoMovimentiToStringFactory(BorsellinoMovimentiFactoryRequest request) {

	this.resolvers.put(BorsellinoMovimentiRicaricaToStringHelper.TIPO,
		new BorsellinoMovimentiRicaricaToStringHelper(request.getPosizioneDebitoria()));
	this.resolvers.put(BorsellinoMovimentiUscitaToStringHelper.TIPO, new BorsellinoMovimentiUscitaToStringHelper(request.getMercato(),
		request.getGiorno(), request.getPosteggio(), request.getAutorizzazione()));
	this.resolvers.put(BorsellinoMovimentiStornoToStringHelper.TIPO, new BorsellinoMovimentiStornoToStringHelper(request.getMercato(),
		request.getGiorno(), request.getPosteggio(), request.getAutorizzazione()));
	this.resolvers.put(BorsellinoMovimentiRimborsoToStringHelper.TIPO, new BorsellinoMovimentiRimborsoToStringHelper());
    }

    public static BorsellinoMovimentiToStringFactory fromBorsellinoMovimenti(BorsellinoMovimenti borsellinoMovimenti) {

	if (borsellinoMovimenti == null) {
	    throw new NotImplementedException("Impossibile istanziare la factory a partire da un movimento senza passare il movimento");
	}
	BorsellinoMovimentiToStringFactory factory = new BorsellinoMovimentiToStringFactory(
		BorsellinoMovimentiFactoryRequest.fromBorsellinoMovimenti(borsellinoMovimenti));
	factory.tipo = TipoEnum.fromValue(borsellinoMovimenti.getTipo());
	return factory;
    }

    @Override
    public String toString() {

	return this.resolvers.get(this.tipo).movimentoToString();
    }
}
