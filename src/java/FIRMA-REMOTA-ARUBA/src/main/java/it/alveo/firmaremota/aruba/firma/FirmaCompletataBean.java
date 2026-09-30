package it.alveo.firmaremota.aruba.firma;

import it.alveo.firmaremota.aruba.client.Esito;

public class FirmaCompletataBean {

    public static FirmaCompletataBean fromOk() {

	var firma = new FirmaCompletataBean();
	firma.esito = Esito.fromOK();
	return firma;
    }

    private Esito esito;

    public Esito getEsito() {

	return esito;
    }

    public void setEsito(Esito esito) {

	this.esito = esito;
    }
}
