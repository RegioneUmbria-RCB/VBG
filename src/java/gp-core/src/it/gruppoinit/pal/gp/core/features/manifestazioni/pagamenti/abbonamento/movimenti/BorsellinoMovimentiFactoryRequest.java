package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimenti;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class BorsellinoMovimentiFactoryRequest {

    private String posizioneDebitoria;
    private String mercato;
    private String giorno;
    private String posteggio;
    private String autorizzazione;

    public String getPosizioneDebitoria() {

	return posizioneDebitoria;
    }

    public String getMercato() {

	return mercato;
    }

    public String getGiorno() {

	return giorno;
    }

    public String getPosteggio() {

	return posteggio;
    }

    public String getAutorizzazione() {

	return autorizzazione;
    }

    public static BorsellinoMovimentiFactoryRequest fromBorsellinoMovimenti(BorsellinoMovimenti borsellinoMovimenti) {

	if (borsellinoMovimenti == null) {
	    throw new NotImplementedException("Impossibile istanziare la factory a partire da un movimento senza passare il movimento");
	}
	BorsellinoMovimentiFactoryRequest request = new BorsellinoMovimentiFactoryRequest();
	request.posizioneDebitoria = borsellinoMovimenti.getDettPosizioneDebitoria() == null ? null
		: borsellinoMovimenti.getDettPosizioneDebitoria().toDescrizioneEstesa();
	request.mercato = borsellinoMovimenti.getMercatiD() == null || borsellinoMovimenti.getMercatiD().getMercati() == null ? null
		: borsellinoMovimenti.getMercatiD().getMercati().getDescrizione();
	request.giorno = borsellinoMovimenti.getMercatipresenzeT() == null ? null
		: Utilities.formatDate(borsellinoMovimenti.getMercatipresenzeT().getDataRegistrazione(), false);
	request.posteggio = borsellinoMovimenti.getMercatiD() == null ? null : borsellinoMovimenti.getMercatiD().getCodiceposteggio();
	request.autorizzazione = borsellinoMovimenti.getAutorizzazione() == null ? null
		: borsellinoMovimenti.getAutorizzazione().getTransientEstremiAutNoRegistro();
	return request;
    }
}
