package it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo;

import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;

public class InvioFlussoResult {

    private String errore;
    private Boolean erroreTemporaneo;

    public String getErrore() {

	return errore;
    }

    public void setErrore(String errore) {

	this.errore = errore;
    }

    public Boolean getErroreTemporaneo() {

	return erroreTemporaneo;
    }

    public void setErroreTemporaneo(Boolean erroreTemporaneo) {

	this.erroreTemporaneo = erroreTemporaneo;
    }

    public static InvioFlussoResult fromEsitoOperazionePosizioneDebitoriaType(EsitoOperazionePosizioneDebitoriaType esitoToCheckException) {

	InvioFlussoResult result = new InvioFlussoResult();
	result.setErrore(esitoToCheckException.getMessaggio());
	result.setErroreTemporaneo(esitoToCheckException.isErroreTemporaneo());
	return result;
    }
}
