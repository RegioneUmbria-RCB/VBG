package it.alveo.firmaremota.aruba.client;

import java.util.ArrayList;
import java.util.List;

public class FirmaResponse {

    private Esito esito;
    private List<DocumentoFirmatoBean> documentiFirmati;

    public List<DocumentoFirmatoBean> getDocumentiFirmati() {

	if (this.documentiFirmati == null) {
	    this.documentiFirmati = new ArrayList<>();
	}
	return documentiFirmati;
    }

    public Esito getEsito() {

	return this.esito;
    }

    public void setEsito(Esito esito) {

	this.esito = esito;
    }

    public static FirmaResponse fromOK() {

	FirmaResponse response = new FirmaResponse();
	response.esito = Esito.fromOK();
	return response;
    }

    public static FirmaResponse fromKO(Exception ex) {

	FirmaResponse response = new FirmaResponse();
	response.esito = Esito.fromKO(ex);
	return response;
    }
}
